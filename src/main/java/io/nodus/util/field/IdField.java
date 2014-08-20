package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/30/2014.
 */
public class IdField implements FieldDescriptor {

  private final List<String> fields;

  private IdField(IdFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static IdFieldBuilder builder() {
    return new IdFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.ID;
  }

  public static final class IdFieldBuilder {

    private List<String> fields;

    protected IdFieldBuilder() {

    }

    public IdField build() {
      return new IdField(this);
    }

    public IdFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
