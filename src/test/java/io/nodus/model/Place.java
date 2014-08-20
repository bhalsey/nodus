package io.nodus.model;

/**
 * Created by erwolff on 6/23/14.
 */
public class Place {

  private Long id;
  private String name;
  private String displayName;
  private String url;
  private Long creationDate;
  private Long[] viewers;
  private Integer[] numbers;
  private Boolean isVisible;
  private Double avgViewers;

  public Place() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Long creationDate) {
    this.creationDate = creationDate;
  }

  public Long[] getViewers() {
    return viewers;
  }

  public void setViewers(Long... viewers) {
    this.viewers = viewers;
  }

  public Integer[] getNumbers() {
    return numbers;
  }

  public void setNumbers(Integer[] numbers) {
    this.numbers = numbers;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public Double getAvgViewers() {
    return avgViewers;
  }

  public void setAvgViewers(Double avgViewers) {
    this.avgViewers = avgViewers;
  }
}
