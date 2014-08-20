package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by erwolff on 7/3/14.
 */
public class RandomEnumGenerator<T extends Enum> implements RandomGenerator<T> {

  private int maxArrayElems;
  private List<?> selections;
  private Class<T> type;

  public RandomEnumGenerator(NodusConfig config, List<?> selections, Class<T> type) {
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
    this.type = type;
  }

  @Override
  public T random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomEnumFromSelection();
    }
    return randomEnum();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    T[] array = (T[]) Array.newInstance(type, n);
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomEnumFromSelection();
      } else {
        array[i] = randomEnum();
      }
    }
    return null; //return array;
  }

  private T randomEnum() {
    T[] values = type.getEnumConstants();
    RandomIntGenerator gen = new RandomIntGenerator(values.length);
    return values[gen.random()];
  }

  @SuppressWarnings("unchecked")
  private T randomEnumFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (T) selections.get(gen.random());
  }
}
