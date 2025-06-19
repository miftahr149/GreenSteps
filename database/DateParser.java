package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateParser {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");

  public static CustomDate parse(String valueStr, Object obj) {
    CustomDate returnValue = null;
    try {
      Date date = DateParser.dateFormat.parse(valueStr);
      returnValue = new CustomDate(date);
    } catch (ParseException e) {
      System.err.printf("Unable to parse %s to MM-yyyy format\n", valueStr);
    }
    return returnValue;
  }

  public static String format(Date date) {
    return DateParser.dateFormat.format(date);
  }
}
