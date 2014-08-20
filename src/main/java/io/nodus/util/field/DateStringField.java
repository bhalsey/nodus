package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 7/1/14.
 */
public class DateStringField implements FieldDescriptor {

  private final List<String> fields;

  private DateStringField(DateFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static DateFieldBuilder builder() {
    return new DateFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.DATE;
  }

  public static final class DateFieldBuilder {

    private List<String> fields;

    protected DateFieldBuilder() {

    }

    public DateStringField build() {
      return new DateStringField(this);
    }

    public DateFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
