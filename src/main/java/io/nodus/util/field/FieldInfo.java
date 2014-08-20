package io.nodus.util.field;

import java.util.Stack;

/**
 * Created by erwolff on 7/5/2014.
 */
public class FieldInfo {

  private Class<?> entity;
  private FieldType entityType;
  private Stack<FieldType> wrapperTypes;
  private FieldDescriptor descriptor;
  private FieldDescriptorType descriptorType;
  private boolean isArray;

  public FieldInfo() {
    wrapperTypes = new Stack<>();
  }

  public Class<?> getEntity() {
    return entity;
  }

  public void setEntity(Class<?> entity) {
    this.entity = entity;
  }

  public int numWrappers() {
    return wrapperTypes.size();
  }

  public FieldType getEntityType() {
    return entityType;
  }

  public void setEntityType(FieldType entityType) {
    this.entityType = entityType;
  }

  public FieldType checkWrapper() {
    return wrapperTypes.peek();
  }

  public boolean isWrapper(FieldType fieldType) {
    return wrapperTypes.contains(fieldType);
  }

  public FieldType getWrapper() {
    return wrapperTypes.pop();
  }

  public void addWrapperType(FieldType type) {
    wrapperTypes.push(type);
  }

  public FieldDescriptor getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(FieldDescriptor descriptor) {
    this.descriptor = descriptor;
  }

  public FieldDescriptorType getDescriptorType() {
    return descriptorType;
  }

  public void setDescriptorType(FieldDescriptorType descriptorType) {
    this.descriptorType = descriptorType;
  }

  public boolean isArray() {
    return isArray;
  }

  public void setIsArray(boolean isArray) {
    this.isArray = isArray;
  }
}
