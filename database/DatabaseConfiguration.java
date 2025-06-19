package database;

public class DatabaseConfiguration {
  private static final String folderDir = "storage";

  public static String setFileDirname(String filename) {
    return String.format("%s/%s.txt", DatabaseConfiguration.folderDir, filename);
  }

  public static void configure() {
    new RecordManager<Room>(Room.factory);
    new RecordManager<Item>(Item.factory);
    new RecordManager<ItemMetadata>(ItemMetadata.factory);
    new RecordManager<MonthlyUsage>(MonthlyUsage.factory);
    new RecordManager<MonthlyReport>(MonthlyReport.factory);

    Reference.extractAllReference();
  }
}
