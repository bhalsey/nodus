package io.nodus.util.var;

import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public class FieldValuesPair {

  private String field;
  private List<?> values;

  public FieldValuesPair() {

  }

  public FieldValuesPair(String field, List<?> values) {
    this.field = field;
    this.values = values;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public List<?> getValues() {
    return values;
  }

  public void setValues(List<?> values) {
    this.values = values;
  }
}
