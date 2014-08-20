package io.nodus.util.var;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erwolff on 6/27/2014.
 */
public class Selector {

  private final FieldValuesPair selection;

  private Selector(SelectorBuilder builder) {
    this.selection = builder.selection;
  }

  public static SelectorBuilder builder() {
    return new SelectorBuilder();
  }

  public FieldValuesPair getSelection() {
    return selection;
  }

  public static final class SelectorBuilder {

    private FieldValuesPair selection;
    private String field;
    private List<Object> values = new ArrayList<>();

    protected SelectorBuilder() {

    }

    public Selector build() {
      if (field == null || values == null)
        throw new IllegalArgumentException("A selector must contain a field and values");

      selection = new FieldValuesPair(field, values);
      return new Selector(this);
    }

    public SelectorBuilder field(String field) {
      this.field = field;
      return this;
    }

    public <T> SelectorBuilder values(T... values) {
      for (T value : values) {
        this.values.add(value);
      }
      return this;
    }
  }
}