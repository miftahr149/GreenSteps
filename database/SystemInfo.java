package database;

import java.util.Date;

public class SystemInfo {
  private String[] saveAttribute = {"currentDate"};
  private RecordFileManager fileManager;
  private String fileDirname = DatabaseConfiguration.setFileDirname("systemInfo");
  private static SystemInfo instance;
  private int page;

  private CustomDate currentDate;

  SystemInfo() {
    this.fileManager = new RecordFileManager(this.fileDirname);
    this.currentDate = DateParser.parse("01-2001", null);

    if (!this.fileManager.isFileExist()) {
      this.fileManager.init();
      this.save();
    }
    this.fileManager.extractFromFile(this::handleExtractFile);
  }

  private void handleExtractFile(String recordStr, int page) {
    this.page = page;
    ObjectParser.decode(this, this.saveAttribute, recordStr);
  }

  private void save() {
    String recordStr = ObjectParser.encode(this, this.saveAttribute);
    this.fileManager.write(this.page, recordStr);
  }

  public static void init() {
    SystemInfo.instance = new SystemInfo();
  }

  public static Date getCurrentDate() {
    return SystemInfo.instance.currentDate.dateValue();
  }

  public static void setCurrentDate(Date date) {
    SystemInfo.instance.currentDate.setDate(date);
    SystemInfo.instance.save();
  }
}
