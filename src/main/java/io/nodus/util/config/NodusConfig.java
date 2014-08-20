package io.nodus.util.config;

import org.joda.time.format.DateTimeFormatter;

/**
 * Created by erwolff on 6/26/14.
 */
public class NodusConfig {

  private final int maxStringLen, maxIntLen, maxLongLen;
  private final int maxInt;
  private final long maxLong;
  private final int minArrayElems;
  private final int maxArrayElems;
  private final long after;
  private final long before;
  private final int idLen;
  private final String uriHost;
  private final int uriPort;
  private final DateTimeFormatter dateFormatter;

  private NodusConfig(NodusConfigBuilder builder) {
    this.maxStringLen = builder.maxStringLen;
    this.maxIntLen = builder.maxIntLen;
    this.maxLongLen = builder.maxLongLen;
    this.maxInt = builder.maxInt;
    this.maxLong = builder.maxLong;
    this.minArrayElems = builder.minArrayElems;
    this.maxArrayElems = builder.maxArrayElems;
    this.after = builder.after;
    this.before = builder.before;
    this.idLen = builder.idLen;
    this.uriHost = builder.uriHost;
    this.uriPort = builder.uriPort;
    this.dateFormatter = builder.dateFormatter;
  }

  public static NodusConfigBuilder builder() {
    return new NodusConfigBuilder();
  }

  public int getMaxStringLen() {
    return maxStringLen;
  }

  public int getMaxIntLen() {
    return maxIntLen;
  }

  public int getMaxLongLen() {
    return maxLongLen;
  }

  public int getMaxInt() {
    return maxInt;
  }

  public long getMaxLong() {
    return maxLong;
  }

  public int getMinArrayElems() {
    return minArrayElems;
  }

  public int getMaxArrayElems() {
    return maxArrayElems;
  }

  public long getAfter() {
    return after;
  }

  public long getBefore() {
    return before;
  }

  public int getIdLen() {
    return idLen;
  }

  public String getUriHost() {
    return uriHost;
  }

  public int getUriPort() {
    return uriPort;
  }

  public DateTimeFormatter getDateFormatter() {
    return dateFormatter;
  }

  public static final class NodusConfigBuilder {

    private Integer maxStringLen, maxIntLen, maxLongLen;
    private Integer maxInt;
    private Long maxLong;
    private Integer minArrayElems;
    private Integer maxArrayElems;
    private Long after;
    private Long before;
    private Integer idLen;
    private String uriHost;
    private Integer uriPort;
    private DateTimeFormatter dateFormatter;

    protected NodusConfigBuilder() {

    }

    public NodusConfig build() {
      setDefaults();
      return new NodusConfig(this);
    }

    public NodusConfigBuilder maxStringLen(int maxStringLen) {
      this.maxStringLen = maxStringLen;
      return this;
    }

    public NodusConfigBuilder maxIntLen(int maxIntLen) {
      this.maxIntLen = maxIntLen;
      return this;
    }

    public NodusConfigBuilder maxLongLen(int maxLongLen) {
      this.maxLongLen = maxLongLen;
      return this;
    }

    public NodusConfigBuilder maxInt(int maxInt) {
      this.maxInt = maxInt;
      return this;
    }

    public NodusConfigBuilder maxLong(long maxLong) {
      this.maxLong = maxLong;
      return this;
    }

    public NodusConfigBuilder minArrayElems(int minArrayElems) {
      this.minArrayElems = minArrayElems;
      return this;
    }

    public NodusConfigBuilder maxArrayElems(int maxArrayElems) {
      this.maxArrayElems = maxArrayElems;
      return this;
    }

    public NodusConfigBuilder after(long after) {
      this.after = after;
      return this;
    }

    public NodusConfigBuilder before(long before) {
      this.before = before;
      return this;
    }

    public NodusConfigBuilder idLen(int idLen) {
      this.idLen = idLen;
      return this;
    }

    public NodusConfigBuilder uriHost(String uriHost) {
      this.uriHost = uriHost;
      return this;
    }

    public NodusConfigBuilder uriPort(Integer uriPort) {
      this.uriPort = uriPort;
      return this;
    }

    public NodusConfigBuilder dateTimeFormatter(DateTimeFormatter dateFormatter) {
      this.dateFormatter = dateFormatter;
      return this;
    }

    private void setDefaults() {
      if (maxStringLen == null) maxStringLen = NodusConfigDefaults.MAX_STRING_LEN;
      if (maxIntLen == null) maxIntLen = NodusConfigDefaults.MAX_INT_LEN;
      if (maxLongLen == null) maxLongLen = NodusConfigDefaults.MAX_LONG_LEN;
      if (maxInt == null) maxInt = NodusConfigDefaults.MAX_INT;
      if (maxLong == null) maxLong = NodusConfigDefaults.MAX_LONG;
      if (minArrayElems == null) minArrayElems = NodusConfigDefaults.MIN_ARRAY_ELEMS;
      if (maxArrayElems == null) maxArrayElems = NodusConfigDefaults.MAX_ARRAY_ELEMS;
      if (after == null) after = NodusConfigDefaults.AFTER;
      if (before == null) before = NodusConfigDefaults.BEFORE;
      if (idLen == null) idLen = NodusConfigDefaults.ID_LEN;
      if (uriHost == null) uriHost = NodusConfigDefaults.URI_HOST;
      if (uriPort == null) uriPort = NodusConfigDefaults.URI_PORT;
      if (dateFormatter == null) dateFormatter = NodusConfigDefaults.DATE_FORMATTER;
    }
  }
}
