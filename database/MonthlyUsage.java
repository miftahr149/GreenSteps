package database;

import java.util.Date;

public class MonthlyUsage extends BaseRecord {
  private CustomDate date;
  private double electricityUsage;

  public static Factory<MonthlyUsage> factory = new Factory<>() {
    @Override
    public MonthlyUsage newInstance(CallbackRecord callback) {
      return new MonthlyUsage(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"date", "electricityUsage"};
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
  }

  public void setDate(Date date) {
    super.callback.update(this);
    this.date.setDate(date);
  }

  public void setElectricityUsage(double electricityUsage) {
    super.callback.update(this);
    this.electricityUsage = electricityUsage;
  }
}
