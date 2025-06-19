package database;


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
      return new String[] {};
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
  }
}
