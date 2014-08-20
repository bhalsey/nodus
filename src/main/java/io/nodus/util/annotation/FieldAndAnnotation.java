package io.nodus.util.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by erwolff on 8/16/2014.
 */
public class FieldAndAnnotation {

  private Field field;
  private Annotation annotation;

  public FieldAndAnnotation() {

  }

  public FieldAndAnnotation(Field field, Annotation annotation) {
    this.field = field;
    this.annotation = annotation;
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public Annotation getAnnotation() {
    return annotation;
  }

  public void setAnnotation(Annotation annotation) {
    this.annotation = annotation;
  }
}
