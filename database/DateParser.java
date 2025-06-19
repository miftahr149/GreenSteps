package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateParser {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");

  public static Date parse(String valueStr, Object obj) {
    Date returnValue = null;
    try {
      returnValue = dateFormat.parse(valueStr);
    } catch (ParseException e) {
      System.err.printf("Unable to parse %s to MM-yyyy format\n", valueStr);
    }
    return returnValue;
  }
}
