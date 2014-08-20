package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 7/1/2014.
 */
public class RandomDateGenerator implements RandomGenerator<Date> {

  private Random random;
  private int maxArrayElems;
  private long before;
  private long after;
  private List<?> selections;

  public RandomDateGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    this.before = config.getBefore();
    this.after = config.getAfter();
  }

  @Override
  public Date random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomDateFromSelection();
    }
    return randomDate();
  }

  @Override
  public Date[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Date[] array = new Date[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomDateFromSelection();
      } else {
        array[i] = randomDate();
      }
    }
    return array;
  }

  private Date randomDate() {
    long timestamp = Math.abs(random.nextLong()) % (before - after) + after;
    return new DateTime(timestamp).toDate();
  }

  private Date randomDateFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Date) selections.get(gen.random());
  }
}
