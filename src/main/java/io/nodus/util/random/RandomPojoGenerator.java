package io.nodus.util.random;

import io.nodus.Nodus;
import io.nodus.util.config.NodusConfig;

import java.lang.reflect.Array;

/**
 * Created by erwolff on 7/6/2014.
 */
public class RandomPojoGenerator<T extends Object> implements RandomGenerator<T> {

  private int maxArrayElems;
  private Class<T> type;
  private Nodus nodus;

  public RandomPojoGenerator(NodusConfig config, Class<T> type, Nodus nodus) {
    maxArrayElems = config.getMaxArrayElems();
    this.type = type;
    this.nodus = nodus;
  }

  @Override
  public T random() {
    return nodus.buildFromClass(type);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    T[] array = (T[]) Array.newInstance(type, n);
    for (int i = 0; i < n; i++) {
      array[i] = nodus.buildFromClass(type);
    }
    return array;
  }
}
