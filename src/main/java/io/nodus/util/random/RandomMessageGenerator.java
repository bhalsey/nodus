package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import io.nodus.util.provider.MessageProvider;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by erwolff on 8/15/14.
 */
public class RandomMessageGenerator implements RandomGenerator<String> {

  private int maxArrayElems;
  private List<?> selections;

  public RandomMessageGenerator(NodusConfig config, List<?> selections) {
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;

  }

  @Override
  public String random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomMessageFromSelection();
    }
    return randomMessage();
  }

  @Override
  public String[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    String[] array = new String[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomMessageFromSelection();
      } else {
        array[i] = randomMessage();
      }
    }
    return array;
  }

  private String randomMessage() {
    RandomIntGenerator messageGen = new RandomIntGenerator(MessageProvider.getMessages().size());
    return MessageProvider.getMessages().get(messageGen.random());
  }

  private String randomMessageFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (String) selections.get(gen.random());
  }
}
