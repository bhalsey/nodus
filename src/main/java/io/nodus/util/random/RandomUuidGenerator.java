package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import io.nodus.util.var.UuidHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by erwolff on 7/11/2014.
 */
public class RandomUuidGenerator implements RandomGenerator<UUID> {

  private Random random;
  private int maxArrayElems;
  private List<?> selections;

  public RandomUuidGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxArrayElems = config.getMaxArrayElems();
    this.selections = selections;
  }

  @Override
  public UUID random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomUuidFromSelection();
    }
    return randomUuid();
  }

  @Override
  public UUID[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    UUID[] array = new UUID[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomUuidFromSelection();
      } else {
        array[i] = randomUuid();
      }
    }
    return array;
  }

  private UUID randomUuid() {
    return UuidHelper.randomUUID(random);
  }

  private UUID randomUuidFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (UUID) selections.get(gen.random());
  }
}
