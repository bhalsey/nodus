package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 7/1/2014.
 */
public class RandomDateStringGenerator implements RandomGenerator<String> {

  private Random random;
  private int maxArrayElems;
  private long before;
  private long after;
  private List<?> selections;
  private DateTimeFormatter formatter;

  public RandomDateStringGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    this.before = config.getBefore();
    this.after = config.getAfter();
    this.formatter = config.getDateFormatter();
  }

  @Override
  public String random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomDateStringFromSelection();
    }
    return randomDateString();
  }

  @Override
  public String[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    String[] array = new String[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomDateStringFromSelection();
      } else {
        array[i] = randomDateString();
      }
    }
    return array;
  }

  private String randomDateString() {
    long timestamp = Math.abs(random.nextLong()) % (before - after) + after;
    return formatTimestamp(timestamp, formatter);
  }

  private String randomDateStringFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (String) selections.get(gen.random());
  }

  public String formatTimestamp(Long timestamp, DateTimeFormatter dateFormatter) {
    return timestamp == null ? null : dateFormatter.print(new DateTime(timestamp));
  }
}
