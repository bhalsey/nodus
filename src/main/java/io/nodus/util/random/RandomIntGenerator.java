package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/27/14.
 */
public class RandomIntGenerator implements RandomGenerator<Integer> {

  private Random random;
  private int maxIntLen;
  private int maxInt;
  private int maxArrayElems;
  private List<?> selections;

  public RandomIntGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxIntLen = config.getMaxIntLen();
    maxInt = config.getMaxInt();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  public RandomIntGenerator(int max) {
    random = RandomSeeder.getRandom();
    maxInt = max;
  }

  @Override
  public Integer random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomIntFromSelection();
    }
    return randomInt();
  }

  @Override
  public Integer[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Integer[] array = new Integer[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomIntFromSelection();
      } else {
        array[i] = randomInt();
      }
    }
    return array;
  }

  private Integer randomInt() {
    int i = random.nextInt(maxInt);
    return i >= 0 ? i : 1;
  }

  private Integer randomIntFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Integer) selections.get(gen.random());
  }
}
