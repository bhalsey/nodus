package io.nodus.model;

/**
 * Created by erwolff on 6/23/14.
 */
public class Address {

  private String street;
  private String city;
  private Long zip;

  public Address() {
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
}
