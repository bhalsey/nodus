package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 7/11/2014.
 */
public class EmailField implements FieldDescriptor {

  private final List<String> fields;

  private EmailField(EmailFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static EmailFieldBuilder builder() {
    return new EmailFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.EMAIL;
  }

  public static final class EmailFieldBuilder {

    private List<String> fields;

    protected EmailFieldBuilder() {

    }

    public EmailField build() {
      return new EmailField(this);
    }

    public EmailFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
