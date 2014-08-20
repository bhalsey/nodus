package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/30/14.
 */
public class RandomBoolGenerator implements RandomGenerator<Boolean> {

  private Random random;
  private int maxArrayElems;
  private List<?> selections;

  public RandomBoolGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  @Override
  public Boolean random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomBoolFromSelection();
    }
    return randomBool();
  }

  @Override
  public Boolean[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Boolean[] array = new Boolean[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomBoolFromSelection();
      } else {
        array[i] = randomBool();
      }
    }
    return array;
  }

  private Boolean randomBool() {
    return random.nextBoolean();
  }

  private Boolean randomBoolFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Boolean) selections.get(gen.random());
  }
}
