package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public class UuidStringField implements FieldDescriptor {

  private final List<String> fields;

  private UuidStringField(UuidStringFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static UuidStringFieldBuilder builder() {
    return new UuidStringFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.UUID_STRING;
  }

  public static final class UuidStringFieldBuilder {

    private List<String> fields;

    protected UuidStringFieldBuilder() {

    }

    public UuidStringField build() {
      return new UuidStringField(this);
    }

    public UuidStringFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
