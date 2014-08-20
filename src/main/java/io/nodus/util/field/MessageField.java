package io.nodus.util.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 8/15/14.
 */
public class MessageField implements FieldDescriptor {

  private final List<String> fields;

  private MessageField(MessageFieldBuilder builder) {
    this.fields = builder.fields;
  }

  public static MessageFieldBuilder builder() {
    return new MessageFieldBuilder();
  }

  @Override
  public List<String> getFields() {
    return fields;
  }

  @Override
  public FieldDescriptorType getType() {
    return FieldDescriptorType.MESSAGE;
  }

  public static final class MessageFieldBuilder {

    private List<String> fields;

    protected MessageFieldBuilder() {

    }

    public MessageField build() {
      return new MessageField(this);
    }

    public MessageFieldBuilder fields(String... fields) {
      this.fields = new ArrayList<>(Arrays.asList(fields));
      return this;
    }
  }
}
