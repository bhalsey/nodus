package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 8/17/2014.
 */
public class NullableField implements FieldDescriptor {

  private final List<String> fields;
  private final boolean always;
  private final boolean jsonInclude;

  private NullableField(NullableFieldBuilder builder) {
    this.fields = builder.fields;
    this.always = builder.always;
    this.jsonInclude = builder.jsonInclude;
  }

  public static NullableFieldBuilder builder() {
    return new NullableFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.NULLABLE;
  }

  public boolean isAlways() {
    return always;
  }

  public boolean isJsonInclude() {
    return jsonInclude;
  }

  public static final class NullableFieldBuilder {

    private List<String> fields;
    private boolean always;
    private boolean jsonInclude;

    protected NullableFieldBuilder() {

    }

    public NullableField build() {
      return new NullableField(this);
    }

    public NullableFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }

    public NullableFieldBuilder always(boolean always) {
      this.always = always;
      return this;
    }

    public NullableFieldBuilder jsonInclude(boolean jsonInclude) {
      this.jsonInclude = jsonInclude;
      return this;
    }
  }
}
