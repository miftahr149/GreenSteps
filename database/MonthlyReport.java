package database;

import java.util.Date;
import java.util.ArrayList;

public class MonthlyReport extends BaseRecord {
  private CustomDate date;
  private double totalCarbonFootprint;

  public static Factory<MonthlyReport> factory = new Factory<>() {
    @Override
    public MonthlyReport newInstance(CallbackRecord callback) {
      return new MonthlyReport(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"date", "totalCarbonFootprint"};
    }

    @Override
    public String getFilename() {
      return "monthlyReport";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(this.getFilename());
    }
  };

  MonthlyReport(CallbackRecord callback) {
    super(callback, MonthlyReport.factory);
    this.date = new CustomDate();
  }

  public void setDate(Date date) {
    super.callback.update(this);
    this.date.setDate(date);
  }

  public void setTotalCarbonFootprint(double totalCarbonFootprint) {
    this.callback.update(this);
    this.totalCarbonFootprint = totalCarbonFootprint;
  }

  public Date getDate() {
    return this.date.dateValue();
  }

  public double getTotalCarbonFootprint() {
    return this.totalCarbonFootprint;
  }

  public ArrayList<MonthlyUsage> getMonthlyUsage() {
    return super.referenceSubscriber.get(MonthlyUsage.class);
  }
}
