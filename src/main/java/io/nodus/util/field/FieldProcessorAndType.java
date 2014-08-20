package io.nodus.util.field;

import io.nodus.util.var.Selector;

/**
 * Created by erwolff on 8/16/2014.
 */
public class FieldProcessorAndType {

  private Selector selector;
  private FieldDescriptor fieldDescriptor;
  private FieldProcessorType fieldProcessorType;

  private FieldProcessorAndType(FieldProcessorAndTypeBuilder builder) {
    this.selector = builder.selector;
    this.fieldDescriptor = builder.fieldDescriptor;
    this.fieldProcessorType = builder.fieldProcessorType;
  }

  public static FieldProcessorAndTypeBuilder builder() {
    return new FieldProcessorAndTypeBuilder();
  }

  public Selector getSelector() {
    return selector;
  }

  public FieldDescriptor getFieldDescriptor() {
    return fieldDescriptor;
  }

  public FieldProcessorType getFieldProcessorType() {
    return fieldProcessorType;
  }

  public static final class FieldProcessorAndTypeBuilder {

    private Selector selector;
    private FieldDescriptor fieldDescriptor;
    private FieldProcessorType fieldProcessorType;

    protected FieldProcessorAndTypeBuilder() {

    }

    public FieldProcessorAndType build() {
      if (selector != null) {
        fieldProcessorType = FieldProcessorType.SELECTOR;
      } else {
        fieldProcessorType = FieldProcessorType.FIELD_DESCRIPTOR;
      }
      return new FieldProcessorAndType(this);
    }

    public FieldProcessorAndTypeBuilder selector(Selector selector) {
      this.selector = selector;
      return this;
    }

    public FieldProcessorAndTypeBuilder fieldDescriptor(FieldDescriptor descriptor) {
      this.fieldDescriptor = descriptor;
      return this;
    }
  }

}
