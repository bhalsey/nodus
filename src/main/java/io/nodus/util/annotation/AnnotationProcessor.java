package io.nodus.util.annotation;

import io.nodus.util.field.*;
import io.nodus.util.random.NoOpGenerator;
import io.nodus.util.random.RandomGenerator;
import io.nodus.util.var.Selector;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import static io.nodus.util.field.FieldDescriptorType.NULLABLE;

/**
 * Created by erwolff on 8/16/2014.
 */
public class AnnotationProcessor {

  public static FieldProcessorAndType process(Class<?> clazz, String fieldName) {
    FieldAndAnnotation fieldAndAnnotation = getFieldAnnotation(clazz, fieldName);
    if (fieldAndAnnotation != null) {
      return processAnnotation(fieldAndAnnotation);
    }
    return null;
  }


  private static FieldProcessorAndType processAnnotation(FieldAndAnnotation fieldAndAnnotation) {
    Field field = fieldAndAnnotation.getField();
    AnnotationType type = AnnotationMap.resolveClassToType(field.getAnnotations()[0].annotationType());
    switch (type) {
      case FIELD_DESCRIPTION:
        return processFieldDescriptorAnnotation(field, field.getAnnotation(FieldDescription.class));
      case SELECTION:
        return processSelectionAnnotation(field, field.getAnnotation(Selection.class));
      case NULLABLE:
        return processNullableAnnotation(field, field.getAnnotation(Nullable.class));
    }
    return null;
  }

  private static FieldAndAnnotation getFieldAnnotation(Class<?> clazz, String fieldName) {
    try {
      Field field = clazz.getDeclaredField(fieldName);
      if (field != null) {
        Annotation[] annotations = field.getAnnotations();
        if (ArrayUtils.isEmpty(annotations)) {
          return null;
        }
        if (annotations.length > 1) {
          throw new IllegalArgumentException("Only a single annotation is allowed per member variable");
        }
        FieldAndAnnotation fieldAndAnnotation = new FieldAndAnnotation(field, annotations[0]);
        return fieldAndAnnotation;
      }
    } catch (NoSuchFieldException e) {
      return null;
    }
    return null;
  }

  private static FieldProcessorAndType processNullableAnnotation(Field field, Nullable nullable) {
    FieldDescription fieldDescription = getInstanceOfFieldDescription(NULLABLE, nullable);
    return processFieldDescriptorAnnotation(field, fieldDescription);
  }

  private static FieldDescription getInstanceOfFieldDescription(final FieldDescriptorType type, final Nullable nullable) {
    FieldDescription fieldDescription = new FieldDescription() {
      @Override
      public FieldDescriptorType type() {
        return type;
      }

      @Override
      public Class<? extends RandomGenerator> generator() {
        return NoOpGenerator.class;
      }

      @Override
      public String[] sameValueFields() {
        return null;
      }

      @Override
      public Selection selection() {
        return null;
      }

      @Override
      public Nullable nullable() {
        return nullable;
      }

      @Override
      public Class<? extends Annotation> annotationType() {
        return FieldDescription.class;
      }
    };
    return fieldDescription;
  }

  private static FieldProcessorAndType processFieldDescriptorAnnotation(Field field, FieldDescription fieldDescription) {
    FieldDescriptorType type = fieldDescription.type();
    FieldDescriptor descriptor = buildDescriptor(type, field, fieldDescription);
    return FieldProcessorAndType.builder().fieldDescriptor(descriptor).build();
  }

  private static FieldDescriptor buildDescriptor(FieldDescriptorType type, Field field, FieldDescription fieldDescription) {
    switch (type) {
      case CUSTOM:
        return processCustomDescriptor(field, fieldDescription);
      case SAME_VALUE:
        return processSameValueDescriptor(field, fieldDescription);
      case TIMESTAMP:
        return processTimestampDescriptor(field);
      case UUID_STRING:
        return processUUIDStringDescriptor(field);
      case ID:
        return processIDDescriptor(field);
      case DATE:
        return processDateDescriptor(field);
      case EMAIL:
        return processEmailDescriptor(field);
      case FIRST_NAME:
        return processFirstNameDescriptor(field);
      case LAST_NAME:
        return processLastNameDescriptor(field);
      case FULL_NAME:
        return processFullNameDescriptor(field);
      case MESSAGE:
        return processMessageDescriptor(field);
      case NULLABLE:
        return null; //processNullableDescriptor(field, fieldDescription);
    }

    return null;
  }

  private static FieldDescriptor processMessageDescriptor(Field field) {
    return MessageField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processFullNameDescriptor(Field field) {
    return FullNameField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processLastNameDescriptor(Field field) {
    return LastNameField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processFirstNameDescriptor(Field field) {
    return FirstNameField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processEmailDescriptor(Field field) {
    return EmailField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processDateDescriptor(Field field) {
    return DateStringField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processIDDescriptor(Field field) {
    return IdField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processUUIDStringDescriptor(Field field) {
    return UuidStringField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processTimestampDescriptor(Field field) {
    return TimestampField.builder().fields(buildFieldName(field)).build();
  }

  private static FieldDescriptor processCustomDescriptor(Field field, FieldDescription fieldDescription) {
    try {
      if (fieldDescription.generator() == NoOpGenerator.class) {
        throw new UnsupportedOperationException("A custom random generator must be supplied with a @FieldDescription of type CUSTOM");
      }
      return CustomRandomField.builder().customGenerator(fieldDescription.generator().newInstance()).fields(buildFieldName(field)).build();
    } catch (Exception e) {
      throw new UnsupportedOperationException("Unable to instantiate custom random generator - only a default constructor may be called");
    }
  }

  private static String buildFieldName(Field field) {
    return field.getDeclaringClass().getSimpleName() + "." + field.getName();
  }

  private static FieldDescriptor processSameValueDescriptor(Field field, FieldDescription fieldDescription) {
    if (fieldDescription.sameValueFields().length == 1 && fieldDescription.sameValueFields()[0].equals("")) {
      throw new UnsupportedOperationException("At least one sameValueField must be supplied with a @FieldDescription of type SAME_VALUE");
    }
    if (fieldDescription.selection().type() == FieldType.NO_OP) {
      throw new UnsupportedOperationException("A @Selection with valid type/values must be supplied with a @FieldDescription of type SAME_VALUE");
    }
    Object[] values = processValues(fieldDescription.selection());
    return SameValuesField.builder().fields(fieldDescription.sameValueFields()).values(values).build();
  }

  private static FieldProcessorAndType processSelectionAnnotation(Field field, Selection selection) {
    Object[] values = processValues(selection);
    Selector selector = Selector.builder().field(buildFieldName(field)).values(values).build();
    return FieldProcessorAndType.builder().selector(selector).build();
  }

  private static Object[] processValues(Selection selection) {
    switch (selection.type()) {
      case INTEGER:
        return processIntegerValues(selection);
      case DOUBLE:
        return processDoubleValues(selection);
      case LONG:
        return processLongValues(selection);
      case DATE:
        return processDateValues(selection);
      case LOCALE:
        return processLocaleValues(selection);
      case TIMEZONE:
        return processTimeZoneValues(selection);
      case UUID:
        return processUUIDValues(selection);
      case STRING:
        return selection.values();
    }
    throw new UnsupportedOperationException("@Selection of type: " + selection.type().name() + " is not currently supported");
  }

  private static Integer[] processIntegerValues(Selection selection) {
    Integer[] values = new Integer[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = Integer.valueOf(selection.values()[i]);
    }
    return values;
  }

  private static Double[] processDoubleValues(Selection selection) {
    Double[] values = new Double[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = Double.valueOf(selection.values()[i]);
    }
    return values;
  }

  private static Long[] processLongValues(Selection selection) {
    Long[] values = new Long[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = Long.valueOf(selection.values()[i]);
    }
    return values;
  }

  private static Date[] processDateValues(Selection selection) {
    Date[] values = new Date[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = new Date((selection.values()[i]));
    }
    return values;
  }

  private static Locale[] processLocaleValues(Selection selection) {
    Locale[] values = new Locale[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = new Locale(selection.values()[i]);
    }
    return values;
  }

  private static TimeZone[] processTimeZoneValues(Selection selection) {
    TimeZone[] values = new TimeZone[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = TimeZone.getTimeZone(selection.values()[i]);
    }
    return values;
  }

  private static UUID[] processUUIDValues(Selection selection) {
    UUID[] values = new UUID[selection.values().length];
    for (int i = 0; i < selection.values().length; i++) {
      values[i] = UUID.fromString(selection.values()[i]);
    }
    return values;
  }
}
