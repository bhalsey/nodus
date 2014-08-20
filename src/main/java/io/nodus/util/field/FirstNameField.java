package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 8/14/2014.
 */
public class FirstNameField implements FieldDescriptor {

  private final List<String> fields;

  private FirstNameField(FirstNameFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static FirstNameFieldBuilder builder() {
    return new FirstNameFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.FIRST_NAME;
  }

  public static final class FirstNameFieldBuilder {

    private List<String> fields;

    protected FirstNameFieldBuilder() {

    }

    public FirstNameField build() {
      return new FirstNameField(this);
    }

    public FirstNameFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
