package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import io.nodus.util.var.UuidHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/29/2014.
 */
public class RandomUuidStringGenerator implements RandomGenerator<String> {

  private Random random;
  private int maxArrayElems;
  private List<?> selections;

  public RandomUuidStringGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  @Override
  public String random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomUuidFromSelection();
    }
    return randomUuid();
  }

  @Override
  public String[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    String[] array = new String[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomUuidFromSelection();
      } else {
        array[i] = randomUuid();
      }
    }
    return array;
  }

  private String randomUuid() {
    return UuidHelper.randomUUID(random).toString();
  }

  private String randomUuidFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return String.valueOf(selections.get(gen.random()));
  }
}
