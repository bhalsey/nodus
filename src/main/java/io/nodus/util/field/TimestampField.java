package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public class TimestampField implements FieldDescriptor {

  private final List<String> fields;

  private TimestampField(TimestampFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static TimestampFieldBuilder builder() {
    return new TimestampFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.TIMESTAMP;
  }

  public static final class TimestampFieldBuilder {

    private List<String> fields;

    protected TimestampFieldBuilder() {

    }

    public TimestampField build() {
      return new TimestampField(this);
    }

    public TimestampFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
