package io.nodus.util.random;

import io.nodus.Nodus;
import io.nodus.NodusParams;
import io.nodus.util.config.NodusConfig;
import io.nodus.util.field.*;
import io.nodus.util.var.FirstLastEmailMatch;
import io.nodus.util.var.Locator;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erwolff on 6/26/14.
 */
public class RandomGeneratorFactory {

  public static RandomFirstLastEmailGenerator exhaustiveFirstLastEmailGenerator;
  public static FirstLastEmailMatch exhaustiveMatch;
  public static int specificSelection;

  @SuppressWarnings("unchecked")
  public static RandomGenerator getRandomGenerator(PropertyDescriptor descriptor, NodusParams params, Locator locator, FieldFinder finder, FieldInfo fieldInfo, Nodus nodus) {
    List<?> selections = finder.getSelection(descriptor, locator);
    if (fieldInfo.getDescriptorType() != null) {
      return getRandomCustomGenerator(fieldInfo, params.getConfig(), selections, nodus);
    }
    if (fieldInfo.getEntityType() != null) {
      return getRandomPrimitiveGenerator(fieldInfo, params.getConfig(), selections, nodus);
    }
    return new RandomPojoGenerator(params.getConfig(), fieldInfo.getEntity(), nodus);
  }


  public static RandomGenerator getRandomCustomGenerator(FieldInfo fieldInfo, NodusConfig config, List<?> selections, Nodus nodus) {
    switch (fieldInfo.getDescriptorType()) {
      case TIMESTAMP:
        return new RandomTimestampGenerator(config, selections);
      case DATE:
        return new RandomDateStringGenerator(config, selections);
      case UUID_STRING:
        return new RandomUuidStringGenerator(config, selections);
      case ID:
        return new RandomIdGenerator(config, selections);
      case MESSAGE:
        return new RandomMessageGenerator(config, selections);
      case FIRST_NAME:
      case LAST_NAME:
      case FULL_NAME:
      case EMAIL:
        return getFirstLastEmailGenerator(config, selections, fieldInfo.getDescriptorType());
      case CUSTOM:
        return ((CustomRandomField) fieldInfo.getDescriptor()).getCustomGenerator();
      case SAME_VALUE:
        if (((SameValuesField) fieldInfo.getDescriptor()).getValues() != null) {
          return new NonRandomGenerator(config, Arrays.asList(((SameValuesField) fieldInfo.getDescriptor()).getValues()));
        }
      default:
        return getRandomPrimitiveGenerator(fieldInfo, config, selections, nodus);
    }
  }

  @SuppressWarnings("unchecked")
  public static RandomGenerator getRandomPrimitiveGenerator(FieldInfo fieldInfo, NodusConfig config, List<?> selections, Nodus nodus) {
    switch (fieldInfo.getEntityType()) {
      case LONG:
        return new RandomLongGenerator(config, selections);
      case INTEGER:
        return new RandomIntGenerator(config, selections);
      case DOUBLE:
        return new RandomDoubleGenerator(config, selections);
      case STRING:
        return new RandomStringGenerator(config, selections);
      case BOOLEAN:
        return new RandomBoolGenerator(config, selections);
      case DATE:
        return new RandomDateGenerator(config, selections);
      case LOCALE:
        return new RandomLocaleGenerator(config, selections);
      case URI:
        return new RandomUriGenerator(config, selections);
      case UUID:
        return new RandomUuidGenerator(config, selections);
      case TIMEZONE:
        return new RandomTimeZoneGenerator(config, selections);
      case ENUM:
        return new RandomEnumGenerator(config, selections, fieldInfo.getEntity());
      default:
        throw new IllegalArgumentException("Unable to assign a random generator to parameter type: " + fieldInfo.getEntityType());
    }
  }

  private static RandomGenerator getFirstLastEmailGenerator(NodusConfig config, List<?> selections, FieldDescriptorType type) {
    RandomFirstLastEmailGenerator returnedGen;
    //TODO: Handle the same specific selection for each type (firstName, lastName, etc)
/*        if (CollectionUtils.isNotEmpty(selections)) {
            RandomIntGenerator gen = new RandomIntGenerator(selections.size());
            specificSelection = gen.random();
            exhaustiveFirstLastEmailGenerator = new RandomFirstLastEmailGenerator(config, selections, specificSelection);
            return exhaustiveFirstLastEmailGenerator;
        }*/
    if (exhaustiveMatch == null || exhaustiveMatch.resolveFieldDescriptorTypeToVar(type) == null) {
      RandomFirstLastEmailGenerator tempGen = new RandomFirstLastEmailGenerator(config, selections, null, type, null);
      exhaustiveMatch = tempGen.randomFirstLastEmail();
    }
    returnedGen = new RandomFirstLastEmailGenerator(config, selections, null, type, exhaustiveMatch.getCopy());
    exhaustiveMatch.clearVarFromFieldDescriptorType(type);
    return returnedGen;
        /*if (exhaustiveMatch != null && exhaustiveMatch.resolveFieldDescriptorTypeToVar(type) != null) {
            returnedGen = new RandomFirstLastEmailGenerator(config, new ArrayList<>(Arrays.asList(exhaustiveMatch.getCopy())), 0);
            exhaustiveMatch.clearVarFromFieldDescriptorType(type);
            return returnedGen;
        }
        else {
            RandomFirstLastEmailGenerator tempGen = new RandomFirstLastEmailGenerator(config, null, null);
            exhaustiveMatch = tempGen.random();
            returnedGen =  new RandomFirstLastEmailGenerator(config, Arrays.asList(exhaustiveMatch.getCopy()), 0);
            exhaustiveMatch.clearVarFromFieldDescriptorType(type);
            return returnedGen;
        }*/
  }
}
