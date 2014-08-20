package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/29/2014.
 */
public class RandomTimestampGenerator implements RandomGenerator<Long> {

  private Random random;
  private int maxArrayElems;
  private long before;
  private long after;
  private List<?> selections;

  public RandomTimestampGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    this.before = config.getBefore();
    this.after = config.getAfter();

  }

  @Override
  public Long random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomTimestampFromSelection();
    }
    return randomTimestamp();
  }

  @Override
  public Long[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Long[] array = new Long[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomTimestampFromSelection();
      } else {
        array[i] = randomTimestamp();
      }
    }
    return array;
  }

  private Long randomTimestamp() {
    return Math.abs(random.nextLong()) % (before - after) + after;
  }

  private Long randomTimestampFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Long) selections.get(gen.random());
  }
}
