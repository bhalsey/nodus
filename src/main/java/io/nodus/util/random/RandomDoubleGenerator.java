package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 6/30/14.
 */
public class RandomDoubleGenerator implements RandomGenerator<Double> {

  private Random random;
  private double maxDouble;
  private int maxArrayElems;
  private List<?> selections;

  public RandomDoubleGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }


  @Override
  public Double random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomDoubleFromSelection();
    }
    return randomDouble();
  }

  @Override
  public Double[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    Double[] array = new Double[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomDoubleFromSelection();
      } else {
        array[i] = randomDouble();
      }
    }
    return array;
  }

  private Double randomDouble() {
    return random.nextDouble();
  }

  private Double randomDoubleFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (Double) selections.get(gen.random());
  }
}
