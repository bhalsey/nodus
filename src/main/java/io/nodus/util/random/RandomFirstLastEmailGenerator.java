package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import io.nodus.util.field.FieldDescriptorType;
import io.nodus.util.provider.EmailProvider;
import io.nodus.util.provider.NameProvider;
import io.nodus.util.var.FirstLastEmailMatch;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by erwolff on 7/11/2014.
 */
public class RandomFirstLastEmailGenerator implements RandomGenerator<String> {

  private int maxArrayElems;
  private List<?> selections;
  private Integer specificSelection;
  private FirstLastEmailMatch exhaustiveMatch;
  private FieldDescriptorType type;

  public RandomFirstLastEmailGenerator(NodusConfig config, List<?> selections, Integer specificSelection, FieldDescriptorType type,
                                       FirstLastEmailMatch exhaustiveMatch) {
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    this.specificSelection = specificSelection;
    this.exhaustiveMatch = exhaustiveMatch;
    this.type = type;
  }

  @Override
  public String random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomFirstLastEmailFromSelection();
    }
    return randomFirstLastEmailString();
  }

  @Override
  public String[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    String[] array = new String[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomFirstLastEmailFromSelection();
      } else {
        array[i] = randomFirstLastEmailString();
      }
    }
    return array;
  }

  public FirstLastEmailMatch randomFirstLastEmail() {
    RandomIntGenerator nameGen = new RandomIntGenerator(NameProvider.getFirstNames().size());
    RandomIntGenerator emailGen = new RandomIntGenerator(EmailProvider.getEmailHosts().size());
    exhaustiveMatch = new FirstLastEmailMatch();
    exhaustiveMatch.setFirst(NameProvider.getFirstNames().get(nameGen.random()));
    exhaustiveMatch.setLast(NameProvider.getLastNames().get(nameGen.random()));
    exhaustiveMatch.buildFullName();
    exhaustiveMatch.setEmailHost(EmailProvider.getEmailHosts().get(emailGen.random()));
    exhaustiveMatch.buildEmail();
    return exhaustiveMatch;
  }

  private String randomFirstLastEmailString() {
    if (exhaustiveMatch != null && exhaustiveMatch.resolveFieldDescriptorTypeToVar(type) != null) {
      String value = exhaustiveMatch.resolveFieldDescriptorTypeToVar(type);
      exhaustiveMatch.clearVarFromFieldDescriptorType(type);
      return value;
    }
    exhaustiveMatch = randomFirstLastEmail();
    String value = exhaustiveMatch.resolveFieldDescriptorTypeToVar(type);
    exhaustiveMatch.clearVarFromFieldDescriptorType(type);
    return value;
  }

  private String randomFirstLastEmailFromSelection() {
    if (specificSelection != null) {
      return (String) selections.get(specificSelection);
    }
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (String) selections.get(gen.random());
  }
}
