package io.nodus.util.var;

import io.nodus.Nodus;
import io.nodus.model.Address;
import io.nodus.model.Person;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by erwolff on 6/28/2014.
 */
@Test
public class SelectorTest {

  public void testSelector() {
    Selector selector = Selector.builder().field("street").values("aStreet", "bStreet", "cStreet").build();

    Nodus nodus = Nodus.builder(Address.class).selectors(selector).build();
    Address address = nodus.getObject();

    List<String> testList = new ArrayList<>();
    testList.addAll(Arrays.asList("aStreet", "bStreet", "cStreet"));
    assertTrue(testList.contains(address.getStreet()));
  }

  public void testMultipleSelectors() {
    Selector streetSelector = Selector.builder().field("street").values("aStreet", "bStreet", "cStreet").build();
    Selector zipSelector = Selector.builder().field("zip").values(12345L, 67890L, 23456L, 34567L, 45678L).build();

    Nodus nodus = Nodus.builder(Address.class).selectors(streetSelector, zipSelector).build();
    Address address = nodus.getObject();

    List<String> testStreetList = new ArrayList<>();
    testStreetList.addAll(Arrays.asList("aStreet", "bStreet", "cStreet"));

    List<Long> testZipList = new ArrayList<>();
    testZipList.addAll(Arrays.asList(12345L, 67890L, 23456L, 34567L, 45678L));

    assertTrue(testStreetList.contains(address.getStreet()));
    assertTrue(testZipList.contains(address.getZip()));
  }

  public void testNestedSelectors() {
    Selector streetSelector = Selector.builder().field("address.street").values("aStreet", "bStreet", "cStreet").build();
    Selector zipSelector = Selector.builder().field("address.zip").values(12345L, 67890L, 23456L, 34567L, 45678L).build();

    Nodus nodus = Nodus.builder(Person.class).selectors(streetSelector, zipSelector).build();
    Person person = nodus.getObject();

    List<String> testStreetList = new ArrayList<>();
    testStreetList.addAll(Arrays.asList("aStreet", "bStreet", "cStreet"));

    List<Long> testZipList = new ArrayList<>();
    testZipList.addAll(Arrays.asList(12345L, 67890L, 23456L, 34567L, 45678L));

    assertTrue(testStreetList.contains(person.getAddress().getStreet()));
    assertTrue(testZipList.contains(person.getAddress().getZip()));
  }
}
