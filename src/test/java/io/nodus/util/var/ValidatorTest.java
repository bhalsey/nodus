package io.nodus.util.var;

import com.fasterxml.jackson.databind.JsonNode;
import io.nodus.Nodus;
import io.nodus.model.Address;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;

/**
 * Created by erwolff on 6/28/2014.
 */
@Test
public class ValidatorTest {

  private File file = new File("src/test/resources/address.json");

  public void testValidNodus() {
    Nodus addressNodus = Nodus.builder(Address.class).build();
    Address address = addressNodus.getObject();
    JsonNode node = addressNodus.getJson();

    assertEquals(address.getStreet(), node.get("street").asText());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testEmptyNodusInvalid() {
    Nodus nodus = Nodus.builder().build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testClassAndInstanceInvalid() {
    Address address = new Address();
    Nodus nodus = Nodus.builder(Address.class).instance(address).build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testFileAndBytesInvalid() {
    String testString = "testString";
    byte[] bytes = testString.getBytes();
    Nodus nodus = Nodus.builder(Address.class).file(file).bytes(bytes).build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testFileWithoutClassInvalid() {
    Nodus nodus = Nodus.builder().file(file).build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testBytesWithoutClassInvalid() {
    String testString = "testString";
    byte[] bytes = testString.getBytes();
    Nodus nodus = Nodus.builder().bytes(bytes).build();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testNodeWithoutClassInvalid() {
    Nodus nodus = Nodus.builder(Address.class).build();
    JsonNode node = nodus.getJson();
    Nodus nodus2 = Nodus.builder().node(node).build();
  }

}
