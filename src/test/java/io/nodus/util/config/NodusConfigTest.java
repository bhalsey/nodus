package io.nodus.util.config;

import io.nodus.Nodus;
import io.nodus.model.Address;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by erwolff on 6/28/2014.
 */
@Test
public class NodusConfigTest {

  public void testConfigBuilder() {
    NodusConfig.NodusConfigBuilder nodusConfigBuilder = NodusConfig.builder();
    NodusConfig nodusConfig =
            nodusConfigBuilder.maxArrayElems(6)
                    .maxIntLen(3)
                    .maxStringLen(8)
                    .maxLongLen(5)
                    .build();

    Nodus nodus = Nodus.builder(Address.class).config(nodusConfig).build();

    NodusConfig returnedNodusConfig = nodus.getConfig();

    assertEquals(nodusConfig.getMaxArrayElems(), returnedNodusConfig.getMaxArrayElems());
    assertEquals(nodusConfig.getMaxIntLen(), returnedNodusConfig.getMaxIntLen());
    assertEquals(nodusConfig.getMaxStringLen(), returnedNodusConfig.getMaxStringLen());
    assertEquals(nodusConfig.getMaxLongLen(), returnedNodusConfig.getMaxLongLen());
    assertEquals(nodusConfig.getMaxInt(), NodusConfigDefaults.MAX_INT);
    assertEquals(nodusConfig.getMaxLong(), NodusConfigDefaults.MAX_LONG);
  }
}
