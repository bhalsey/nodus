package io.nodus.model;

import com.google.common.base.Optional;

/**
 * Created by erwolff on 7/3/2014.
 */
public class AddressOptional {

  private String street;
  private String city;
  private Long zip;
  private Optional<Stuff> stuff;

  public AddressOptional() {
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Long getZip() {
    return zip;
  }

  public void setZip(Long zip) {
    this.zip = zip;
  }

  public Optional<Stuff> getStuff() {
    return stuff;
  }

  public void setStuff(Optional<Stuff> stuff) {
    this.stuff = stuff;
  }

  public static enum Stuff {
    FOO,
    BAR;
  }
}
