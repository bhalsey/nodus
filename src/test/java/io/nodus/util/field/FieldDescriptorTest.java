package io.nodus.util.field;

import com.fasterxml.jackson.databind.JsonNode;
import io.nodus.Nodus;
import io.nodus.model.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by erwolff on 6/29/2014.
 */

@Test
public class FieldDescriptorTest {

  public void testTimestampField() {
    //Using zip as timestamp field just because it's already a long
    FieldDescriptor fieldDesc = TimestampField.builder().fields("zip").build();

    Nodus addressNodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc).build();
    Address address = addressNodus.getObject();

    assertTrue(address.getZip() <= System.currentTimeMillis());
  }

  public void testSameValuesField() {
    FieldDescriptor fieldDesc = SameValuesField.builder().fields("street", "city").build();

    Nodus addressNodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc).build();
    Address address = addressNodus.getObject();

    assertEquals(address.getStreet(), address.getCity());
  }

  public void testUuidField() {
    FieldDescriptor fieldDesc = UuidStringField.builder().fields("street").build();

    Nodus addressNodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc).build();
    Address address = addressNodus.getObject();

    assertTrue(address.getStreet().contains("-"));
  }

  public void testIdField() {
    FieldDescriptor fieldDesc = IdField.builder().fields("zip").build();

    Nodus nodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc).build();
    Address address = nodus.getObject();

    assertTrue(String.valueOf(address.getZip()).length() == 5);
  }

  public void testDateStringField() {
    FieldDescriptor fieldDesc = DateStringField.builder().fields("street").build();

    Nodus nodus = Nodus.builder(Address.class).fieldDescriptors(fieldDesc).build();
    Address address = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertEquals(address.getStreet(), node.get("street").asText());
  }

  public void testMultipleSameValueDescriptors() {
    FieldDescriptor nameDesc = SameValuesField.builder().fields("name", "displayName").build();
    FieldDescriptor idDesc = SameValuesField.builder().fields("id", "creationDate").build();

    Nodus placeNodus = Nodus.builder(Place.class).fieldDescriptors(nameDesc, idDesc).build();
    Place place = placeNodus.getObject();

    assertEquals(place.getName(), place.getDisplayName());
    assertEquals(place.getId(), place.getCreationDate());
  }

  public void testMultipleMixedFieldDescriptors() {
    FieldDescriptor zipDesc = TimestampField.builder().fields("zip").build();
    FieldDescriptor streetDesc = SameValuesField.builder().fields("street", "city").build();

    Nodus addressNodus = Nodus.builder(Address.class).fieldDescriptors(zipDesc, streetDesc).build();
    Address address = addressNodus.getObject();

    assertTrue(address.getZip() <= System.currentTimeMillis());
    assertEquals(address.getStreet(), address.getCity());
  }

  public void testNestedFieldDescriptors() {
    FieldDescriptor fieldDesc = SameValuesField.builder().fields("address.street", "address.city").build();

    Nodus personNodus = Nodus.builder(Person.class).fieldDescriptors(fieldDesc).build();
    Person person = personNodus.getObject();

    assertEquals(person.getAddress().getStreet(), person.getAddress().getCity());
  }

  public void testDate() {
    Nodus nodus = Nodus.builder(Content.class).build();
    Content content = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertEquals(content.getCreationDate().getTime(), node.get("creationDate").asLong());
  }

  public void testLocale() {
    Nodus nodus = Nodus.builder(Billing.class).build();
    Billing billing = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertEquals(billing.getLocale().toString(), node.get("locale").asText());
  }

  public void testTimeZone() {
    Nodus nodus = Nodus.builder(Billing.class).build();
    Billing billing = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertEquals(billing.getTimeZone().getID(), node.get("timeZone").asText());
  }
}
