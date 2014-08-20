package io.nodus.util.var;

import io.nodus.util.field.FieldDescriptorType;

/**
 * Created by erwolff on 8/14/2014.
 */
public class FirstLastEmailMatch {

  private String first;
  private String last;
  private String fullName;
  private String emailHost;
  private String email;

  public FirstLastEmailMatch() {

  }

  public FirstLastEmailMatch(String first, String last, String email) {
    this.first = first;
    this.last = last;
    this.email = email;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEmailHost(String emailHost) {
    this.emailHost = emailHost;
  }

  public void buildFullName() {
    if (first != null && last != null) {
      fullName = first + " " + last;
    }
  }

  public void buildEmail() {
    if (first != null && last != null && emailHost != null) {
      email = first + "." + last + "@" + emailHost;
    }
  }

  public String resolveFieldDescriptorTypeToVar(FieldDescriptorType type) {
    switch (type) {
      case FIRST_NAME:
        return first;
      case LAST_NAME:
        return last;
      case FULL_NAME:
        return fullName;
      case EMAIL:
        return email;
    }
    return null;
  }

  public void clearVarFromFieldDescriptorType(FieldDescriptorType type) {
    switch (type) {
      case FIRST_NAME:
        first = null;
        break;
      case LAST_NAME:
        last = null;
        break;
      case FULL_NAME:
        fullName = null;
        break;
      case EMAIL:
        email = null;
        break;
    }
  }

  public FirstLastEmailMatch getCopy() {
    FirstLastEmailMatch copy = new FirstLastEmailMatch(first, last, email);
    copy.setFullName(fullName);
    copy.setEmailHost(emailHost);
    return copy;
  }
}
