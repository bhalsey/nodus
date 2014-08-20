package io.nodus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.nodus.util.config.NodusConfig;
import io.nodus.util.field.FieldDescriptor;
import io.nodus.util.random.RandomSeeder;
import io.nodus.util.var.Selector;
import io.nodus.util.var.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/27/14.
 */
public class NodusBuilder<T> {

  private String name;
  private ObjectNode node;
  private Long randomSeed;
  private Class<T> clazz;
  private NodusConfig config;
  private Object instance;
  private File file;
  private byte[] bytes;
  private List<Selector> selectors;
  private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
  private ObjectMapper mapper = new ObjectMapper();


  protected NodusBuilder() {

  }

  protected NodusBuilder(Class<T> clazz) {
    this.clazz = clazz;
  }

  public Nodus build() {
    Validator.validate(this);
    if (node == null) node = mapper.createObjectNode();
    RandomSeeder.seed(randomSeed);

    return new Nodus(this);
  }

  public NodusBuilder name(String name) {
    this.name = name;
    return this;
  }

  public NodusBuilder node(JsonNode node) {
    if (node != null && node instanceof ObjectNode) {
      this.node = (ObjectNode) node;
    }
    return this;
  }

  public NodusBuilder instance(Object instance) {
    this.instance = instance;
    return this;
  }

  public NodusBuilder file(File file) {
    this.file = file;
    return this;
  }

  public NodusBuilder bytes(byte[] bytes) {
    this.bytes = bytes;
    return this;
  }

  public NodusBuilder randomSeed(Long seed) {
    this.randomSeed = seed;
    return this;
  }

  public NodusBuilder config(NodusConfig config) {
    this.config = config;
    return this;
  }

  public NodusBuilder selectors(Selector... selectors) {
    this.selectors = new ArrayList<>(Arrays.asList(selectors));
    return this;
  }

  public NodusBuilder selectors(List<Selector> selectors) {
    this.selectors = selectors;
    return this;
  }

  public NodusBuilder fieldDescriptors(FieldDescriptor... descriptors) {
    this.fieldDescriptors = new ArrayList<>(Arrays.asList(descriptors));
    return this;
  }

  public NodusBuilder fieldDescriptors(List<FieldDescriptor> descriptors) {
    this.fieldDescriptors = descriptors;
    return this;
  }

  public String getName() {
    return name;
  }

  public ObjectNode getNode() {
    return node;
  }

  public Long getRandomSeed() {
    return randomSeed;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public NodusConfig getConfig() {
    return config;
  }

  public Object getInstance() {
    return instance;
  }

  public File getFile() {
    return file;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public List<Selector> getSelectors() {
    return selectors;
  }

  public List<FieldDescriptor> getFieldDescriptors() {
    return fieldDescriptors;
  }
}

