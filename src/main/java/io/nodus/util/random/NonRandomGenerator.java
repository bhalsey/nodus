package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by erwolff on 6/30/2014.
 */
public class NonRandomGenerator implements RandomGenerator {

  private List<?> selections;

  public NonRandomGenerator(NodusConfig config, List<?> selections) {
    this.selections = selections;
  }

  @Override
  public Object random() {
    if (CollectionUtils.isEmpty(selections)) {
      return null;
    }
    if (selections.size() == 1) {
      return selections.get(0);
    }
    throw new IllegalArgumentException("Expected a single element to be returned by the NonRandomGenerator, but found: " + selections.size());
  }

  @Override
  public Object[] randomArray() {
    return CollectionUtils.isEmpty(selections) ? null : selections.toArray();
  }
}
