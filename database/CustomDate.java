package database;

import java.util.Date;

public class CustomDate {
  private Date date;

  public CustomDate() {
    this(null);
  }

  public CustomDate(Date date) {
    this.date = date;
  }

  public Date dateValue() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return DateParser.format(this.dateValue());
  }
}
