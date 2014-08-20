package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by erwolff on 7/5/2014.
 */
public class RandomTimeZoneGenerator implements RandomGenerator<TimeZone> {

  private int maxArrayElems;
  private List<?> selections;
  private String[] timeZones;

  public RandomTimeZoneGenerator(NodusConfig config, List<?> selections) {
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    timeZones = TimeZone.getAvailableIDs();
  }

  @Override
  public TimeZone random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomTimeZoneFromSelection();
    }
    return randomTimeZone();
  }

  @Override
  public TimeZone[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    TimeZone[] array = new TimeZone[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomTimeZoneFromSelection();
      } else {
        array[i] = randomTimeZone();
      }
    }
    return array;
  }

  private TimeZone randomTimeZone() {
    RandomIntGenerator gen = new RandomIntGenerator(timeZones.length);
    return TimeZone.getTimeZone(timeZones[gen.random()]);
  }

  private TimeZone randomTimeZoneFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (TimeZone) selections.get(gen.random());
  }
}
