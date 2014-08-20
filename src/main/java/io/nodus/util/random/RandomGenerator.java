package io.nodus.util.random;

/**
 * Created by erwolff on 6/27/14.
 */
public interface RandomGenerator<T> {

  public T random();

  //TODO: In javadoc specify implementing class may choose to throw UnsupportedOperationException for:
  public T[] randomArray();
}
