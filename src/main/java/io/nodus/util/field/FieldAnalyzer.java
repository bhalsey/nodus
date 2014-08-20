package io.nodus.util.field;

import io.nodus.util.var.Locator;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;

import static io.nodus.util.field.TypeUtil.*;

/**
 * Created by erwolff on 7/5/2014.
 */
public class FieldAnalyzer {

  public static FieldInfo analyze(PropertyDescriptor descriptor, Locator locator, FieldFinder finder) {
    FieldInfo fieldInfo = new FieldInfo();
    Class<?> type = descriptor.getPropertyType();
    FieldDescriptor fieldDescriptor = finder.getDescriptor(descriptor, locator);
    setDescriptorAndType(fieldDescriptor, fieldInfo);
    setFieldType(type, descriptor, fieldInfo);

    return fieldInfo;
  }

  private static void setDescriptorAndType(FieldDescriptor desc, FieldInfo fieldInfo) {
    if (desc != null) {
      fieldInfo.setDescriptor(desc);
      fieldInfo.setDescriptorType(desc.getType());
    }
  }

  //TODO: Refactor this
  private static void setFieldType(Class<?> type, PropertyDescriptor descriptor, FieldInfo fieldInfo) {
    if (type.isEnum()) {
      fieldInfo.setEntityType(FieldType.ENUM);
      fieldInfo.setEntity(type);
      return;
    }
    FieldType fieldType = getFieldType(type);

    if (fieldType == null) {
      if (isArray(type)) {
        fieldInfo.setIsArray(true);
        fieldInfo.addWrapperType(FieldType.ARRAY);
        setFieldType(type.getComponentType(), descriptor, fieldInfo);
      }
      if (fieldInfo.getEntity() == null) {
        fieldInfo.setEntity(type);
      }
      return;
    }

    if (fieldType == FieldType.LIST) {
      fieldInfo.addWrapperType(fieldType);
      fieldInfo.setIsArray(true);
      unwrapEntity(getEnclosedParamType(descriptor.getWriteMethod()), fieldInfo);
    }

    if (fieldType == FieldType.OPTIONAL) {
      fieldInfo.addWrapperType(fieldType);
      unwrapEntity(getEnclosedParamType(descriptor.getWriteMethod()), fieldInfo);
    }

    if (fieldInfo.getEntity() == null) {
      fieldInfo.setEntity(type);
      fieldInfo.setEntityType(fieldType);
    }
  }

  private static void unwrapEntity(Type type, FieldInfo fieldInfo) {
    if (type instanceof Class) {
      Class<?> entity = (Class) type;
      FieldType entityType = getFieldType(entity);
      fieldInfo.setEntity(entity);
      fieldInfo.setEntityType(entityType);
    } else if (type instanceof ParameterizedTypeImpl) {
      Class<?> wrapperClass = ((ParameterizedTypeImpl) type).getRawType();
      Type[] innerTypes = ((ParameterizedTypeImpl) type).getActualTypeArguments();

      FieldType wrapperType = getFieldType(wrapperClass);

      if (wrapperType != null) {
        fieldInfo.addWrapperType(wrapperType);
      }
      if (innerTypes != null && innerTypes.length == 1) {
        unwrapEntity(innerTypes[0], fieldInfo);
      }
    }
  }
}
