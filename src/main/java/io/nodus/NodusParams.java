package io.nodus;

import io.nodus.util.config.NodusConfig;
import io.nodus.util.field.FieldDescriptor;
import io.nodus.util.var.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public class NodusParams {

  private NodusConfig config;
  private List<Selector> selectors = new ArrayList<>();
  private List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

  protected NodusParams() {

  }

  protected NodusParams(NodusConfig config, List<Selector> selectors, List<FieldDescriptor> fieldDescriptors) {
    this.config = config;
    this.selectors = selectors;
    this.fieldDescriptors = fieldDescriptors;
  }

  public NodusConfig getConfig() {
    return config;
  }

  protected void setConfig(NodusConfig config) {
    this.config = config;
  }

  public List<Selector> getSelectors() {
    return selectors;
  }

  protected void setSelectors(List<Selector> selectors) {
    this.selectors = selectors;
  }

  public List<FieldDescriptor> getFieldDescriptors() {
    return fieldDescriptors;
  }

  protected void setFieldDescriptors(List<FieldDescriptor> fieldDescriptors) {
    this.fieldDescriptors = fieldDescriptors;
  }

  public void addSelector(Selector selector) {
    selectors.add(selector);
  }

  public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
    fieldDescriptors.add(fieldDescriptor);
  }
}
