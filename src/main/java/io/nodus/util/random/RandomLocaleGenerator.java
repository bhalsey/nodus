package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by erwolff on 7/5/2014.
 */
public class RandomLocaleGenerator implements RandomGenerator<Locale> {

  private Random random;
  private int maxArrayElems;
  private List<?> selections;
  private Locale[] locales;

  public RandomLocaleGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    locales = Locale.getAvailableLocales();
  }

  @Override
  public Locale random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomLocaleFromSelection();
    }
    return randomLocale();
  }

  @Override
  public Locale[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Locale[] array = new Locale[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomLocaleFromSelection();
      } else {
        array[i] = randomLocale();
      }
    }
    return array;
  }

  private Locale randomLocale() {
    RandomIntGenerator gen = new RandomIntGenerator(locales.length);
    return locales[gen.random()];
  }

  private Locale randomLocaleFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Locale) selections.get(gen.random());
  }
}
