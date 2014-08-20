package io.nodus.util.random;

import io.nodus.model.Address;

/**
 * Created by erwolff on 7/2/2014.
 */
public class RandomAddressGeneratorWithArray implements RandomGenerator {

  @Override
  public Address random() {
    //This isn't really random. We're testing that this object is the one that gets set by nodus
    Address address = new Address();
    address.setCity("testRandomCity");
    address.setStreet("testRandomStreet");
    address.setZip(80020L);
    return address;
  }

  @Override
  public Address[] randomArray() {
    Address[] addresses = new Address[2];

    Address address = new Address();
    address.setCity("testRandomCity");
    address.setStreet("testRandomStreet");
    address.setZip(80020L);
    addresses[0] = address;

    Address address2 = new Address();
    address2.setCity("testRandomCity2");
    address2.setStreet("testRandomStreet2");
    address2.setZip(90030L);
    addresses[1] = address2;

    return addresses;
  }
}
