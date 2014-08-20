package io.nodus.util.field;

import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public interface FieldDescriptor {

  public List<String> getFields();

  public FieldDescriptorType getType();

}
