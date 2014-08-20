package io.nodus.model;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by erwolff on 7/2/2014.
 */
public class Billing {

  private Address[] addresses;
  private Locale locale;
  private TimeZone timeZone;

  public Address[] getAddresses() {
    return addresses;
  }

  public void setAddresses(Address[] addresses) {
    this.addresses = addresses;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }
}
