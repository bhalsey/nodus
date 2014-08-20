package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 8/14/2014.
 */
public class LastNameField implements FieldDescriptor {

  private final List<String> fields;

  private LastNameField(LastNameFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static LastNameFieldBuilder builder() {
    return new LastNameFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.LAST_NAME;
  }

  public static final class LastNameFieldBuilder {

    private List<String> fields;

    protected LastNameFieldBuilder() {

    }

    public LastNameField build() {
      return new LastNameField(this);
    }

    public LastNameFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
