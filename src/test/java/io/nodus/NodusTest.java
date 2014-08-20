package io.nodus;

import com.fasterxml.jackson.databind.JsonNode;
import io.nodus.model.Address;
import io.nodus.model.Content;
import io.nodus.model.Person;
import io.nodus.model.Place;
import io.nodus.util.field.FieldDescriptor;
import io.nodus.util.field.FirstNameField;
import io.nodus.util.field.SameValuesField;
import io.nodus.util.field.TimestampField;
import io.nodus.util.var.Selector;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by erwolff on 6/23/14.
 */

@Test
public class NodusTest {

  private File file = new File("src/test/resources/address.json");

  public void testReadFile() {
    Nodus nodus = Nodus.builder(Address.class).file(file).build();
    Address address = nodus.getObject();

    assertEquals(address.getStreet(), "NaxIGDrh");
  }

  public void testWithClassPOJO() {
    Nodus addressNodus = Nodus.builder(Address.class).build();
    Address address = addressNodus.getObject();
    JsonNode node = addressNodus.getJson();

    assertEquals(address.getStreet(), node.get("street").asText());
  }

  public void testWithClassNestedPOJO() {
    Nodus personNodus = Nodus.builder(Person.class).build();
    Person person = personNodus.getObject();
    JsonNode node = personNodus.getJson();

    assertEquals(person.getAddress().getStreet(), node.get("address").get("street").asText());
  }

  public void testWithInstance() {
    Address address = new Address();
    address.setStreet("testStreet");
    Nodus nodus = Nodus.builder().instance(address).build();
    JsonNode node = nodus.getJson();

    assertEquals(address.getStreet(), "testStreet");
    assertEquals(node.get("street").asText(), "testStreet");
  }

  public void testArrays() {
    Nodus nodus = Nodus.builder(Place.class).build();
    Place place = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertTrue(node.get("viewers").isArray());
  }

  public void testPutNodus() {
    Nodus addressNodus = Nodus.builder(Address.class).build();
    Address address = addressNodus.getObject();

    Nodus placeNodus = Nodus.builder(Place.class).build();

    placeNodus.putNodus(addressNodus);

    JsonNode node = placeNodus.getJson();

    assertEquals(address.getStreet(), node.get("address").get("street").asText());
  }

  public void testPutNodi() {
    Nodus addressNodus = Nodus.builder(Address.class).build();
    Address address = addressNodus.getObject();

    Nodus placeNodus = Nodus.builder(Place.class).build();
    Nodus contentNodus = Nodus.builder(Content.class).build();

    Nodus personNodus = Nodus.builder(Person.class).build();
    personNodus.putNodi("nodi", addressNodus, placeNodus, contentNodus);

    JsonNode node = personNodus.getJson();

    assertEquals(address.getStreet(), node.get("nodi").get(0).get("street").asText());
  }

  public void testNodusClassConstructor() {
    Nodus addressNodus = Nodus.builder(Address.class).build();
    Address address = addressNodus.getObject();
    JsonNode node = addressNodus.getJson();

    assertEquals(address.getStreet(), node.get("street").asText());
  }

  @Test(expectedExceptions = ClassCastException.class)
  public void testInvalidClassCast() {
    Nodus addressNodus = Nodus.builder(Address.class).build();

    //Should generate ClassCastException
    Place place = addressNodus.getObject();
  }

  public void testAsString() {
    Nodus nodus = Nodus.builder(Address.class).build();
    nodus.asString("stringField");
    JsonNode node = nodus.getJson();

    assertTrue(node.has("stringField"));
  }

  //TODO: Fix this functionality
  @Test(enabled = false)
  public void testCustomNodus() {
    Nodus nodus = Nodus.builder().name("nodus").build();
    nodus.asString("stringField1");
    nodus.asString("stringField2");
    nodus.asLong("longField1");
    nodus.asLong("longField2");
    nodus.asInt("intField1");
    nodus.asInt("intField2");

    Nodus nestedNodus = Nodus.builder().name("nestedNodus").build();
    nestedNodus.asStringArray("stringArray");
    nestedNodus.asLongArray("longArray");
    nestedNodus.asIntArray("intArray");

    nodus.putNodus(nestedNodus);

    JsonNode node = nodus.getJson();

    assertTrue(node.get("nestedNodus").has("longArray"));
  }

  public void testNodeToClass() {
    Nodus nodus = Nodus.builder(Address.class).build();
    JsonNode node = nodus.getJson();

    Nodus nodus2 = Nodus.builder(Address.class).node(node).build();
    JsonNode node2 = nodus.getJson();

    assertEquals(node.get("street"), node2.get("street"));
  }

  public void testFieldDescriptorWithSelector() {
    FieldDescriptor firstNameDesc = FirstNameField.builder().fields("firstName").build();
    FieldDescriptor lastNameDesc = FirstNameField.builder().fields("lastName").build();
    Nodus nodus = Nodus.builder(Person.class).fieldDescriptors(firstNameDesc, lastNameDesc).build();
    FieldDescriptor timestampDesc = TimestampField.builder().fields("zip").build();
    FieldDescriptor fieldDesc = SameValuesField.builder().fields("street", "city").build();
    Selector selector = Selector.builder().field("street").values("aStreet", "bStreet", "cStreet").build();

    Nodus addressNodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc, timestampDesc).selectors(selector).build();
    Address address = addressNodus.getObject();

    assertEquals(address.getStreet(), address.getCity());

    List<String> testList = new ArrayList<>();
    testList.addAll(Arrays.asList("aStreet", "bStreet", "cStreet"));

    assertTrue(testList.contains(address.getStreet()));
  }
}
