package io.nodus.util.random;

import java.util.Random;

/**
 * Created by erwolff on 6/27/14.
 */
public class RandomSeeder {

  private static Random random;
  private static long randomSeed;
  private static boolean seeded = false;

  public static long getRandomSeed() {
    return randomSeed;
  }

  public static Random getRandom() {
    return random;
  }

  public static void seed() {
    seed(null);
  }

  public static void seed(Long seed) {
    if (seed != null) {
      randomSeed = seed;
    } else {
      randomSeed = System.currentTimeMillis();
    }
    random = new Random(randomSeed);
  }
}
