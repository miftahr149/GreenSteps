package database;

import java.util.Date;

public class MonthlyUsage extends BaseRecord {
  private CustomDate date;
  private double electricityUsage;
  private Reference<Room> room;

  public static Factory<MonthlyUsage> factory = new Factory<>() {
    @Override
    public MonthlyUsage newInstance(CallbackRecord callback) {
      return new MonthlyUsage(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"date", "electricityUsage", "room"};
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

  public Date getDate() {
    return this.date.dateValue();
  }

  public Double getElectricityUsage() {
    return this.electricityUsage;
  }
}
