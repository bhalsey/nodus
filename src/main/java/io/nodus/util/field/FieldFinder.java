package io.nodus.util.field;

import io.nodus.util.var.Locator;
import io.nodus.util.var.Selector;
import org.apache.commons.collections.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 * Created by erwolff on 6/29/2014.
 */
public class FieldFinder {

  private List<Selector> selectors;
  private List<?> selectionValues;
  private List<FieldDescriptor> fieldDescriptors;
  private FieldDescriptor appliedDescriptor;

  public FieldFinder(List<Selector> selectors, List<FieldDescriptor> descriptors) {
    this.selectors = selectors;
    this.fieldDescriptors = descriptors;
  }

  public List<?> getSelection(PropertyDescriptor descriptor, Locator locator) {
    if (CollectionUtils.isNotEmpty(selectionValues)) {
      return selectionValues;
    }
    if (CollectionUtils.isNotEmpty(selectors)) {
      for (Selector selector : selectors) {
        if (selectors != null && selector.getSelection() != null) {
          if (("." + locator.currentLoc() + "." + descriptor.getName() + "_").toLowerCase().contains(("." + selector.getSelection().getField() + "_").toLowerCase())) {
            selectionValues = selector.getSelection().getValues();
            return selectionValues;
          }
          //In the case of a SameValueField this selector could still apply
          if (getDescriptor(descriptor, locator, FieldDescriptorType.SAME_VALUE) != null) {
            selectionValues = selector.getSelection().getValues();
            return selectionValues;
          }
        }
      }
    }
    return null;
  }

  public FieldDescriptor getDescriptor(PropertyDescriptor descriptor, Locator locator) {
    return getDescriptor(descriptor, locator, null);
  }

  private FieldDescriptor getDescriptor(PropertyDescriptor descriptor, Locator locator, FieldDescriptorType type) {
    if (appliedDescriptor != null && type != null && appliedDescriptor.getType() == type) {
      return appliedDescriptor;
    }
    if (CollectionUtils.isNotEmpty(fieldDescriptors)) {
      for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
        if (type != null && !(fieldDescriptor.getType() == type)) {
          continue;
        }
        for (String field : fieldDescriptor.getFields()) {
          if (("." + locator.currentLoc() + "." + descriptor.getName() + "_").toLowerCase().contains(("." + field + "_").toLowerCase())) {
            appliedDescriptor = fieldDescriptor;
            return appliedDescriptor;
          }
        }
      }
    }
    return null;
  }

  public boolean shouldStoreValue() {
    return appliedDescriptor != null
            && appliedDescriptor.getType() == FieldDescriptorType.SAME_VALUE
            && ((SameValuesField) appliedDescriptor).getValues() == null;
  }
}
