package database;

import java.util.Date;

public class MonthlyUsage extends BaseRecord {
  private CustomDate date;
  private double electricityUsage;
  private double carbonFootprint;
  private Reference<Room> room;
  private Reference<MonthlyReport> monthlyReport;

  public static Factory<MonthlyUsage> factory = new Factory<>() {
    @Override
    public MonthlyUsage newInstance(CallbackRecord callback) {
      return new MonthlyUsage(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"date", "electricityUsage", "room", "monthlyReport", "carbonFootprint"};
    }

    @Override
    public String getFilename() {
      return "monthlyUsage";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(getFilename());
    }
  };

  MonthlyUsage(CallbackRecord callback) {
    super(callback, MonthlyUsage.factory);
    this.room = new Reference<>(Room.factory.getFilename(), this);
    this.monthlyReport = new Reference<>(MonthlyReport.factory.getFilename(), this);
  }

  public void setDate(Date date) {
    super.callback.update(this);
    this.date.setDate(date);
  }

  public void setElectricityUsage(double electricityUsage) {
    super.callback.update(this);
    this.electricityUsage = electricityUsage;
  }

  public void setRoom(Room room) {
    super.callback.update(this);
    this.room.add(room.getID());
  }

  public void setMonthlyReport(MonthlyReport monthlyReport) {
    super.callback.update(this);
    this.monthlyReport.add(monthlyReport.getID());
  }

  public Date getDate() {
    return this.date.dateValue();
  }

  public Double getElectricityUsage() {
    return this.electricityUsage;
  }
}
