package io.nodus.util.var;

import java.util.Random;
import java.util.UUID;

/**
 * Created by erwolff on 6/29/2014.
 */
public class UuidHelper {

  //Pull the necessary methods out of java.util.UUID so we can use our own seeded random generator with a UUID

  private final long mostSigBits;
  private final long leastSigBits;

  private UuidHelper(byte[] data) {
    long msb = 0;
    long lsb = 0;
    assert data.length == 16 : "data must be 16 bytes in length";
    for (int i = 0; i < 8; i++)
      msb = (msb << 8) | (data[i] & 0xff);
    for (int i = 8; i < 16; i++)
      lsb = (lsb << 8) | (data[i] & 0xff);
    this.mostSigBits = msb;
    this.leastSigBits = lsb;
  }

  public static UUID randomUUID(Random random) {
    byte[] randomBytes = new byte[16];
    random.nextBytes(randomBytes);
    randomBytes[6] &= 0x0f;  /* clear version        */
    randomBytes[6] |= 0x40;  /* set to version 4     */
    randomBytes[8] &= 0x3f;  /* clear variant        */
    randomBytes[8] |= 0x80;  /* set to IETF variant  */
    return UUID.fromString(new UuidHelper(randomBytes).toString());
  }

  private static String digits(long val, int digits) {
    long hi = 1L << (digits * 4);
    return Long.toHexString(hi | (val & (hi - 1))).substring(1);
  }

  public String toString() {
    return (digits(mostSigBits >> 32, 8) + "-" +
            digits(mostSigBits >> 16, 4) + "-" +
            digits(mostSigBits, 4) + "-" +
            digits(leastSigBits >> 48, 4) + "-" +
            digits(leastSigBits, 12));
  }
}
