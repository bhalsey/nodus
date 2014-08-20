package io.nodus.util.annotation;

import java.util.HashMap;
import java.util.Map;

import static io.nodus.util.annotation.AnnotationType.*;

/**
 * Created by erwolff on 8/16/2014.
 */
public class AnnotationMap {

  private static Map<Class<?>, AnnotationType> annotationMap = new HashMap<>();

  static {
    annotationMap.put(FieldDescription.class, FIELD_DESCRIPTION);
    annotationMap.put(Selection.class, SELECTION);
    annotationMap.put(Nullable.class, NULLABLE);
  }

  public static Map<Class<?>, AnnotationType> getAnnotationMap() {
    return annotationMap;
  }

  public static AnnotationType resolveClassToType(Class<?> clazz) {
    return annotationMap.get(clazz);
  }
}
