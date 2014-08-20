package io.nodus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.nodus.util.annotation.AnnotationProcessor;
import io.nodus.util.config.NodusConfig;
import io.nodus.util.field.*;
import io.nodus.util.random.RandomGenerator;
import io.nodus.util.random.RandomGeneratorFactory;
import io.nodus.util.var.Locator;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static io.nodus.util.field.TypeUtil.*;

/**
 * Created by erwolff on 6/23/14.
 */
public class Nodus {
  private static final Logger log = LoggerFactory.getLogger(Nodus.class);

  private String name;
  private ObjectNode node;
  private List<Nodus> nodi = new ArrayList<>();
  private Map<String, List<Nodus>> nodusMap = new LinkedHashMap<>();
  private ObjectMapper mapper = new ObjectMapper();
  private Object clazz;
  private NodusParams params;
  private Locator locator;
  private boolean isNodeBuilt = false;

  protected Nodus(NodusBuilder builder) {
    mapper.registerModule(new GuavaModule());
    this.name = builder.getName();
    this.node = builder.getNode();
    this.locator = new Locator();
    buildParams(builder);
    buildNodus(builder);
  }

  public static NodusBuilder builder() {
    return new NodusBuilder();
  }

  @SuppressWarnings("unchecked")
  public static <T> NodusBuilder builder(Class<T> clazz) {
    return new NodusBuilder(clazz);
  }

  @SuppressWarnings("unchecked")
  private void buildParams(NodusBuilder builder) {
    params = new NodusParams();
    setConfig(builder.getConfig());
    if (builder.getSelectors() != null) {
      params.setSelectors(builder.getSelectors());
    }
    if (builder.getFieldDescriptors() != null) {
      params.setFieldDescriptors(builder.getFieldDescriptors());
    }
  }

  private void buildNodus(NodusBuilder builder) {
    if (builder.getClazz() != null) {
      setName(builder.getClazz());
      if (node.size() > 0) {
        buildFromNode(node, builder.getClazz());
      } else if (builder.getFile() != null) {
        buildFromFile(builder.getFile(), builder.getClazz());
      } else if (builder.getBytes() != null) {
        buildFromBytes(builder.getBytes(), builder.getClazz());
      } else {
        buildFromClass(builder.getClazz());
      }
      buildNode();
    } else if (builder.getInstance() != null) {
      setName(builder.getInstance().getClass());
      buildFromInstance(builder.getInstance());
      buildNode();
    }
  }

  public NodusConfig getConfig() {
    return params.getConfig();
  }

  private void setConfig(NodusConfig config) {
    if (config == null) {
      NodusConfig.NodusConfigBuilder defaultConfig = NodusConfig.builder();
      params.setConfig(defaultConfig.build());
    } else {
      params.setConfig(config);
    }
  }

  public String asString(String field) {
    return asString(field, null);
  }

  public Long asLong(String field) {
    return asLong(field, null);
  }

  public Integer asInt(String field) {
    return asInt(field, null);
  }

  public String[] asStringArray(String field) {
    return asStringArray(field, null);
  }

  public Integer[] asIntArray(String field) {
    return asIntArray(field, null);
  }

  public Long[] asLongArray(String field) {
    return asLongArray(field, null);
  }

  public String asString(String field, List<String> values) {
    String randomString = getRandomOfType(String.class, values);
    node.put(field, randomString);
    return randomString;
  }

  public Long asLong(String field, List<Long> values) {
    Long randomLong = getRandomOfType(Long.class, values);
    node.put(field, randomLong);
    return randomLong;
  }

  public Integer asInt(String field, List<Integer> values) {
    Integer randomInt = getRandomOfType(Integer.class, values);
    node.put(field, randomInt);
    return randomInt;
  }

  public String[] asStringArray(String field, List<String> values) {
    ArrayNode arrayNode = mapper.createArrayNode();
    String[] array = getRandomArrayOfType(String[].class, values);
    for (String randomString : array) {
      arrayNode.add(randomString);
    }
    node.put(field, arrayNode);
    return array;
  }

  public Integer[] asIntArray(String field, List<Integer> values) {
    ArrayNode arrayNode = mapper.createArrayNode();
    Integer[] array = getRandomArrayOfType(Integer[].class, values);
    for (Integer randomInt : array) {
      arrayNode.add(randomInt);
    }
    node.put(field, arrayNode);
    return array;
  }

  public Long[] asLongArray(String field, List<Long> values) {
    ArrayNode arrayNode = mapper.createArrayNode();
    Long[] array = getRandomArrayOfType(Long[].class, values);
    for (Long randomLong : array) {
      arrayNode.add(randomLong);
    }
    node.put(field, arrayNode);
    return array;
  }

  public Nodus putNodus(Nodus nodus) {
    isNodeBuilt = false;
    nodi.add(nodus);
    return this;
  }

  public void putNodi(String field, Nodus... nodi) {
    isNodeBuilt = false;
    nodusMap.put(field, Arrays.asList(nodi));
  }

  private void buildNode() {
    if (isNodeBuilt) {
      return;
    }
    ObjectNode nodeBuilder = mapper.createObjectNode();
    if (clazz != null) {
      nodeBuilder = mapper.convertValue(clazz, ObjectNode.class);
    }
    if (node.size() > 0) {
      mergeNodes(nodeBuilder, node);
    }
    for (Nodus nodus : nodi) {
      nodeBuilder.put(nodus.name, nodus.getJson());
    }
    if (!nodusMap.isEmpty()) {
      for (Map.Entry<String, List<Nodus>> entry : nodusMap.entrySet()) {
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(transform(entry.getValue()));
        nodeBuilder.put(entry.getKey(), arrayNode);
      }
    }
    isNodeBuilt = true;
    node = nodeBuilder;
  }

  public ObjectNode getJson() {
    if (!isNodeBuilt) {
      buildNode();
    }
    return node;
  }

  private JsonNode mergeNodes(JsonNode mainNode, JsonNode updateNode) {
    Iterator<String> fieldNames = updateNode.fieldNames();
    while (fieldNames.hasNext()) {
      String fieldName = fieldNames.next();
      JsonNode jsonNode = mainNode.get(fieldName);
      if (jsonNode != null && jsonNode.isObject()) {
        mergeNodes(jsonNode, updateNode.get(fieldName));
      } else {
        if (mainNode instanceof ObjectNode) {
          JsonNode value = updateNode.get(fieldName);
          ((ObjectNode) mainNode).put(fieldName, value);
        }
      }

    }
    return mainNode;
  }

  private List<JsonNode> transform(List<Nodus> nodi) {
    return Lists.transform(nodi, new Function<Nodus, JsonNode>() {
      public JsonNode apply(Nodus input) {
        return input == null ? null : input.getJson();
      }
    });
  }

  private <T> void buildFromNode(JsonNode node, Class<T> clazz) {
    try {
      this.clazz = mapper.convertValue(node, clazz);
      isNodeBuilt = true;
    } catch (Exception e) {
      log.error("Unable to successfully create nodus from supplied node");
    }
  }

  private <T> void buildFromFile(File src, Class<T> clazz) {
    try {
      this.clazz = mapper.readValue(src, clazz);
    } catch (Exception e) {
      log.error("Unable to convert to supplied class for file: " + src);
    }
  }

  private <T> void buildFromBytes(byte[] src, Class<T> clazz) {
    try {
      this.clazz = mapper.readValue(src, clazz);
    } catch (Exception e) {
      log.error("Unable to convert to supplied class for byte[]: " + new String(src));
    }
  }

  public <T> T buildFromClass(Class<T> clazz) {
    T instance = getInstance(clazz);
    return buildFromInstance(instance);
  }

  private <T> T buildFromInstance(T instance) {
    locator.add(instance);
    populateInstance(instance, PropertyUtils.getPropertyDescriptors(instance));
    locator.backtrack();
    this.clazz = instance;
    return instance;
  }

  private <T> void populateInstance(T instance, PropertyDescriptor[] descriptors) {
    for (PropertyDescriptor descriptor : descriptors) {
      //sometimes we get a descriptor of type Class - do not invoke write if this is the case
      if (descriptor.getWriteMethod() != null && !descriptor.getPropertyType().isAssignableFrom(Class.class)) {
        if (isEmpty(invokeReadMethod(instance, descriptor))) {
          populateField(instance, descriptor);
        }
      }
    }
  }

  private boolean isEmpty(Object o) {
    return o == null || isOptional(o.getClass()) || isZeroValue(o);
  }

  private boolean isZeroValue(Object o) {
    if (o == null) return true;
    FieldType type = getFieldType(o.getClass());
    switch (type) {
      case INTEGER:
        return ((Integer) o) == 0;
      case LONG:
        return ((Long) o) == 0;
      case DOUBLE:
        return ((Double) o) == 0.0;
      case BOOLEAN:
        return !((Boolean) o);
    }

    return false;
  }

  private <T> T getInstance(Class<T> clazz) {
    T instance = null;
    try {
      instance = clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      log.error("Unable to successfully generate new instance from supplied class");
    }
    return instance;
  }

  private <T> Object invokeReadMethod(T instance, PropertyDescriptor descriptor) {
    try {
      if (descriptor.getReadMethod() != null) {
        return descriptor.getReadMethod().invoke(instance);
      }
    } catch (InvocationTargetException | IllegalAccessException e) {
      log.error("Unable to invoke read method for descriptor " + descriptor.getName());
    }
    return null;
  }

  private <T> void populateField(T instance, PropertyDescriptor descriptor) {
    processAnnotations(instance, descriptor);
    FieldFinder finder = new FieldFinder(params.getSelectors(), params.getFieldDescriptors());
    FieldInfo fieldInfo = FieldAnalyzer.analyze(descriptor, locator, finder);
    RandomGenerator generator = RandomGeneratorFactory.getRandomGenerator(descriptor, params, locator, finder, fieldInfo, this);
    invokeWriteMethod(instance, descriptor, generator, fieldInfo);
    storeSameValue(instance, descriptor, finder);
  }

  private <T> void processAnnotations(T instance, PropertyDescriptor descriptor) {
    FieldProcessorAndType procAndType = AnnotationProcessor.process(instance.getClass(), descriptor.getDisplayName());
    if (procAndType != null) {
      switch (procAndType.getFieldProcessorType()) {
        case SELECTOR:
          params.addSelector(procAndType.getSelector());
          break;
        case FIELD_DESCRIPTOR:
          params.addFieldDescriptor(procAndType.getFieldDescriptor());
          break;
      }
    }
  }

  private <T> void invokeWriteMethod(T instance, PropertyDescriptor descriptor, RandomGenerator generator, FieldInfo fieldInfo) {
    try {
      Object args = generateArgs(fieldInfo, generator);
      descriptor.getWriteMethod().invoke(instance, args);
    } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
      log.error("Unable to invoke write method for descriptor " + descriptor.getName());
    }
  }

  //TODO: Refactor this
  private Object generateArgs(FieldInfo fieldInfo, RandomGenerator generator) {
    Object entity;
    if (fieldInfo.numWrappers() > 0 && fieldInfo.isWrapper(FieldType.LIST)) {
      entity = convertArrayToList(generator.randomArray());
    } else {
      entity = fieldInfo.isArray() ? generator.randomArray() : generator.random();
    }
    //TODO: Implement further wrapper cases
    while (fieldInfo.numWrappers() > 0) {
      switch (fieldInfo.getWrapper()) {
        case OPTIONAL:
          entity = Optional.of(entity);
      }
    }
    return entity;
  }

  private <T> void storeSameValue(T instance, PropertyDescriptor descriptor, FieldFinder finder) {
    if (finder.shouldStoreValue()) {
      ((SameValuesField) finder.getDescriptor(descriptor, locator)).setValues(invokeReadMethod(instance, descriptor));
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject() {
    return (T) clazz;
  }

  private <T> void setName(Class<T> clazz) {
    if (name == null && clazz != null) {
      this.name = clazz.getSimpleName().toLowerCase();
    }
  }

  //TODO: Add this functionality back
  @SuppressWarnings("unchecked")
  private <T> T getRandomOfType(Class<T> type, List<?> selections) {
    return null;
    //return (T) RandomGeneratorFactory.getRandomPrimitiveGenerator(type, params.getConfig(), selections).random();
  }

  //TODO: Add this functionality back
  @SuppressWarnings("unchecked")
  private <T> T getRandomArrayOfType(Class<T> type, List<?> selections) {
    return null;
    //return (T) RandomGeneratorFactory.getRandomPrimitiveGenerator(type, params.getConfig(), selections).randomArray();
  }
}
