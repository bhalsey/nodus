package io.nodus.util.annotation;

import io.nodus.util.field.FieldDescriptorType;
import io.nodus.util.field.FieldType;
import io.nodus.util.random.NoOpGenerator;
import io.nodus.util.random.RandomGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by erwolff on 8/16/2014.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDescription {

  FieldDescriptorType type();

  Class<? extends RandomGenerator> generator() default NoOpGenerator.class;

  String[] sameValueFields() default "";

  Selection selection() default @Selection(type = FieldType.NO_OP, values = "");

  Nullable nullable() default @Nullable;
}
