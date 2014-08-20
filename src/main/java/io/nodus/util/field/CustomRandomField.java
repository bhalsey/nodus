package io.nodus.util.field;

import io.nodus.util.random.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 7/2/2014.
 */
public class CustomRandomField implements FieldDescriptor {

  private final List<String> fields;
  private final RandomGenerator customGenerator;

  private CustomRandomField(CustomRandomFieldBuilder builder) {
    this.fields = builder.fields;
    this.customGenerator = builder.customGenerator;
  }

  public static CustomRandomFieldBuilder builder() {
    return new CustomRandomFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.CUSTOM;
  }

  public RandomGenerator getCustomGenerator() {
    return customGenerator;
  }

  public static final class CustomRandomFieldBuilder {

    private List<String> fields;
    private RandomGenerator customGenerator;

    protected CustomRandomFieldBuilder() {

    }

    public CustomRandomField build() {
      if (customGenerator == null) {
        throw new IllegalArgumentException("Must supply a RandomGenerator with a CustomRandomField");
      }
      return new CustomRandomField(this);
    }

    public CustomRandomFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }

    public CustomRandomFieldBuilder customGenerator(RandomGenerator customGenerator) {
      this.customGenerator = customGenerator;
      return this;
    }
  }
}
