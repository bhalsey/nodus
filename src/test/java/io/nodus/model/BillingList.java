package io.nodus.model;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Created by erwolff on 7/2/2014.
 */
public class BillingList {

  private List<Address> addressList;
  private Optional<List<Long>> ids;

  public List<Address> getAddressList() {
    return addressList;
  }

  public void setAddressList(List<Address> addressList) {
    this.addressList = addressList;
  }

  public Optional<List<Long>> getIds() {
    return ids;
  }

  public void setIds(Optional<List<Long>> ids) {
    this.ids = ids;
  }
}
