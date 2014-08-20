package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 8/14/2014.
 */
public class FullNameField implements FieldDescriptor {

  private final List<String> fields;

  private FullNameField(FullNameFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static FullNameFieldBuilder builder() {
    return new FullNameFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.FULL_NAME;
  }

  public static final class FullNameFieldBuilder {

    private List<String> fields;

    protected FullNameFieldBuilder() {

    }

    public FullNameField build() {
      return new FullNameField(this);
    }

    public FullNameFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
