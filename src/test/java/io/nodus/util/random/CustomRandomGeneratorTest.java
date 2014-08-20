package io.nodus.util.random;

import com.fasterxml.jackson.databind.JsonNode;
import io.nodus.Nodus;
import io.nodus.model.Billing;
import io.nodus.model.BillingList;
import io.nodus.model.Person;
import io.nodus.util.field.CustomRandomField;
import io.nodus.util.field.FieldDescriptor;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by erwolff on 7/2/2014.
 */

@Test
public class CustomRandomGeneratorTest {

  public void testCustomRandomGenerator() {
    FieldDescriptor desc = CustomRandomField.builder().fields("address").customGenerator(new RandomAddressGenerator()).build();
    Nodus nodus = Nodus.builder(Person.class).fieldDescriptors(desc).build();

    Person person = nodus.getObject();
    JsonNode node = nodus.getJson();

    assertEquals(person.getAddress().getCity(), "testRandomCity");
    assertEquals(person.getAddress().getStreet(), "testRandomStreet");
    assertEquals(person.getAddress().getZip().longValue(), 80020L);
    assertEquals(person.getAddress().getStreet(), node.get("address").get("street").asText());
  }

  public void testCustomRandomGeneratorArray() {
    FieldDescriptor desc = CustomRandomField.builder().fields("addresses").customGenerator(new RandomAddressGeneratorWithArray()).build();
    Nodus nodus = Nodus.builder(Billing.class).fieldDescriptors(desc).build();

    Billing billing = nodus.getObject();

    assertEquals(billing.getAddresses()[0].getCity(), "testRandomCity");
    assertEquals(billing.getAddresses()[0].getStreet(), "testRandomStreet");
    assertEquals(billing.getAddresses()[0].getZip().longValue(), 80020L);

    assertEquals(billing.getAddresses()[1].getCity(), "testRandomCity2");
    assertEquals(billing.getAddresses()[1].getStreet(), "testRandomStreet2");
    assertEquals(billing.getAddresses()[1].getZip().longValue(), 90030L);
  }

  public void testCustomRandomGeneratorList() {
    FieldDescriptor desc = CustomRandomField.builder().fields("addressList").customGenerator(new RandomAddressGeneratorWithArray()).build();
    Nodus nodus = Nodus.builder(BillingList.class).fieldDescriptors(desc).build();

    BillingList billingList = nodus.getObject();

    assertEquals(billingList.getAddressList().get(0).getCity(), "testRandomCity");
    assertEquals(billingList.getAddressList().get(0).getStreet(), "testRandomStreet");
    assertEquals(billingList.getAddressList().get(0).getZip().longValue(), 80020L);

    assertEquals(billingList.getAddressList().get(1).getCity(), "testRandomCity2");
    assertEquals(billingList.getAddressList().get(1).getStreet(), "testRandomStreet2");
    assertEquals(billingList.getAddressList().get(1).getZip().longValue(), 90030L);
  }

  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void testCustomRandomGeneratorArrayUnsupported() {
    FieldDescriptor desc = CustomRandomField.builder().fields("addresses").customGenerator(new RandomAddressGenerator()).build();
    Nodus nodus = Nodus.builder(Billing.class).fieldDescriptors(desc).build();
  }

  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void testCustomRandomGeneratorListUnsupported() {
    FieldDescriptor desc = CustomRandomField.builder().fields("addressList").customGenerator(new RandomAddressGenerator()).build();
    Nodus nodus = Nodus.builder(BillingList.class).fieldDescriptors(desc).build();
  }
}
