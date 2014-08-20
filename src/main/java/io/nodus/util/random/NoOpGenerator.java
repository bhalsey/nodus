package io.nodus.util.random;

/**
 * Created by erwolff on 8/16/2014.
 */
public class NoOpGenerator implements RandomGenerator {


  @Override
  public Object random() {
    throw new UnsupportedOperationException("The no op generator should never be instantiated and called");
  }

  @Override
  public Object[] randomArray() {
    throw new UnsupportedOperationException("The no op generator should never be instantiated and called");
  }
}
