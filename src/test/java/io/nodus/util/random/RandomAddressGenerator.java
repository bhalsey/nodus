package io.nodus.util.random;

import io.nodus.model.Address;

/**
 * Created by erwolff on 7/2/2014.
 */
public class RandomAddressGenerator implements RandomGenerator {

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
    throw new UnsupportedOperationException("RandomAddressGenerator does not implement randomArray()");
  }
}
