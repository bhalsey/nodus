package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/30/2014.
 */
public class RandomIdGenerator implements RandomGenerator<Long> {

  private Random random;
  private int idLen;
  private int maxArrayElems;
  private List<?> selections;
  private long minId;
  private long maxId;

  public RandomIdGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    idLen = config.getIdLen();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    determineMaxId();
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

  private void determineMaxId() {
    StringBuilder sb = new StringBuilder("1");
    for (int i = 0; i < idLen - 1; i++) {
      sb.append("0");
    }
    minId = Long.valueOf(sb.toString());
    sb.append("0");
    maxId = Long.valueOf(sb.toString()) - 1;

  }

  private Long randomLong() {
    return Math.abs(random.nextLong()) % (maxId - minId) + minId;
  }

  private Long randomLongFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Long) selections.get(gen.random());
  }
}
