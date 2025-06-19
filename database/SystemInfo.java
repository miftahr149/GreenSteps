package database;

import java.util.Date;

class SystemInfo {
  private String[] saveAttribute = {"currentDate"};
  private RecordFileManager fileManager;
  private String fileDirname = DatabaseConfiguration.setFileDirname("systemInfo.txt");
  private SystemInfo instance = new SystemInfo();
  private int page;

  private Date currentDate;

  private SystemInfo() {
    this.fileManager = new RecordFileManager(this.fileDirname);
    this.currentDate = DateParser.parse("01-2001", null);

    if (!this.fileManager.isFileExist()) {
      this.fileManager.init();
    }
    this.fileManager.extractFromFile(this::handleExtractFile);
  }

  private void handleExtractFile(String recordStr, int page) {
    this.page = page;
    ObjectParser.decode(this, this.saveAttribute, recordStr);
  }

}
