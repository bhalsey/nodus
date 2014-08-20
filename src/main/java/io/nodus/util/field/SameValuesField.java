package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */

public class SameValuesField implements FieldDescriptor {

  private final List<String> fields;
  private Object[] values;

  private SameValuesField(SameValueFieldBuilder builder) {
    this.fields = builder.fields;
    this.values = builder.values;
  }

  public static SameValueFieldBuilder builder() {
    return new SameValueFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.SAME_VALUE;
  }

  public Object[] getValues() {
    return values;
  }

  public void setValues(Object... values) {
    this.values = values;
  }

  public static final class SameValueFieldBuilder {

    private List<String> fields;
    private Object[] values;

    protected SameValueFieldBuilder() {

    }

    public SameValuesField build() {
      return new SameValuesField(this);
    }

    public SameValueFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }

    public SameValueFieldBuilder values(Object... values) {
      this.values = values;
      return this;
    }
  }
}
