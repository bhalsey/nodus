package io.nodus.util.var;

import io.nodus.NodusBuilder;

/**
 * Created by erwolff on 6/28/2014.
 */
public class Validator {

  public static void validate(NodusBuilder builder) {
    if (builder.getClazz() == null && builder.getFile() == null && builder.getBytes() == null
            && builder.getNode() == null && builder.getInstance() == null && builder.getName() == null) {
      throw new IllegalArgumentException("A nodus must be created from a class, instance, file, byte[], or JsonNode");
    }
    if (builder.getClazz() != null && builder.getInstance() != null) {
      throw new IllegalArgumentException("A nodus can only be created from either a class or an instantiated object, not both");
    }
    if (builder.getFile() != null && builder.getBytes() != null) {
      throw new IllegalArgumentException("A nodus can only be created from either a file or a byte[], not both");
    }
    if (builder.getClazz() == null && (builder.getFile() != null || builder.getBytes() != null || builder.getNode() != null)) {
      throw new IllegalArgumentException("A class must be specified when a file, byte[], or JsonNode is supplied");
    }
  }
}
