package io.nodus.util.field;

import com.google.common.base.Optional;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;

/**
 * Created by erwolff on 6/30/2014.
 */
public class TypeUtil {

  protected static Map<Class<?>, FieldType> typeMap;

  static {
    buildTypeMap();
  }

  public static boolean isArray(Class<?> type) {
    return type != null && Object[].class.isAssignableFrom(type);
  }

  public static boolean isList(Class<?> type) {
    return type != null && List.class.isAssignableFrom(type);
  }

  public static <T> List<T> convertArrayToList(T[] array) {
    return Arrays.asList(array);
  }

  public static boolean isOptional(Class<?> type) {
    return type != null && Optional.class.isAssignableFrom(type);
  }

  public static Type getEnclosedParamType(Method method) {
    Type[] types = method.getGenericParameterTypes();
    for (Type type : types) {
      if (type instanceof ParameterizedType) {
        ParameterizedType paramType = (ParameterizedType) type;
        Type[] actualTypes = paramType.getActualTypeArguments();
        return actualTypes[0];
      }
    }
    return null;
  }

  public static FieldType getFieldType(Class<?> clazz) {
    return typeMap.get(clazz);
  }


  private static void buildTypeMap() {
    typeMap = new HashMap<>();
    typeMap.put(Long.class, FieldType.LONG);
    typeMap.put(Long.TYPE, FieldType.LONG);
    typeMap.put(Integer.class, FieldType.INTEGER);
    typeMap.put(Integer.TYPE, FieldType.INTEGER);
    typeMap.put(Double.class, FieldType.DOUBLE);
    typeMap.put(Double.TYPE, FieldType.DOUBLE);
    typeMap.put(String.class, FieldType.STRING);
    typeMap.put(Boolean.class, FieldType.BOOLEAN);
    typeMap.put(Boolean.TYPE, FieldType.BOOLEAN);
    typeMap.put(Date.class, FieldType.DATE);
    typeMap.put(Locale.class, FieldType.LOCALE);
    typeMap.put(TimeZone.class, FieldType.TIMEZONE);
    typeMap.put(URI.class, FieldType.URI);
    typeMap.put(UUID.class, FieldType.UUID);
    typeMap.put(Enum.class, FieldType.ENUM);
    typeMap.put(List.class, FieldType.LIST);
    typeMap.put(Optional.class, FieldType.OPTIONAL);
  }
}
