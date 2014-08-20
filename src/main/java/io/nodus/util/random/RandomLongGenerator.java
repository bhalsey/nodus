package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/27/14.
 */
public class RandomLongGenerator implements RandomGenerator<Long> {

  private Random random;
  private int maxLongLen;
  private long maxLong;
  private int maxArrayElems;
  private List<?> selections;

  public RandomLongGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxLongLen = config.getMaxLongLen();
    maxLong = config.getMaxLong();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  @Override
  public Long random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomLongFromSelection();
    }
    return randomLong();
  }

  @Override
  public Long[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Long[] array = new Long[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomLongFromSelection();
      } else {
        array[i] = randomLong();
      }
    }
    return array;
  }

  private Long randomLong() {
    return Math.abs(random.nextLong() + 1) % maxLong;
  }

  private Long randomLongFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Long) selections.get(gen.random());
  }
}
