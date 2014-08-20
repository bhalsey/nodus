package io.nodus.util.field;

/**
 * Created by erwolff on 7/6/2014.
 */
//TODO: Combine FieldType and WrapperType intelligently
public enum FieldType {
  LONG,
  INTEGER,
  DOUBLE,
  STRING,
  BOOLEAN,
  DATE,
  LOCALE,
  TIMEZONE,
  URI,
  UUID,
  ENUM,
  ARRAY,
  LIST,
  OPTIONAL,
  WRAPPER,
  NO_OP;
}
