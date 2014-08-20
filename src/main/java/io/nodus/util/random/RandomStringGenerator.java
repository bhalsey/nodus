package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/27/14.
 */
public class RandomStringGenerator implements RandomGenerator<String> {

  private Random random;
  private int maxStringLen;
  private int maxArrayElems;
  private List<?> selections;

  public RandomStringGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxStringLen = config.getMaxStringLen();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  @Override
  public String random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomStringFromSelection();
    }
    return randomString();
  }

  @Override
  public String[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    String[] array = new String[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomStringFromSelection();
      } else {
        array[i] = randomString();
      }
    }
    return array;
  }

  private String randomString() {
    return RandomStringUtils.random(maxStringLen, 0, 0, true, false, null, random);
  }

  private String randomStringFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return String.valueOf(selections.get(gen.random()));
  }
}
