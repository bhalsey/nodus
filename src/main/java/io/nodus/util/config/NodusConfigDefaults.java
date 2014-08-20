package io.nodus.util.config;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by erwolff on 6/26/14.
 */
public class NodusConfigDefaults {

  public static final int MAX_STRING_LEN = 10;
  public static final int MAX_INT_LEN = 6;
  public static final int MAX_LONG_LEN = 10;
  public static final int MAX_INT = Integer.MAX_VALUE;
  public static final long MAX_LONG = Long.MAX_VALUE;
  public static final int MIN_ARRAY_ELEMS = 3;
  public static final int MAX_ARRAY_ELEMS = 6;
  public static final long AFTER = 0;
  public static final long BEFORE = System.currentTimeMillis();
  public static final int ID_LEN = 5;
  public static final String URI_HOST = "http://localhost";
  public static final int URI_PORT = 19200;
  public static final DateTimeFormatter DATE_FORMATTER = ISODateTimeFormat.dateTimeNoMillis();
}
