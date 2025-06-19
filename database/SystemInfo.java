package database;

class SystemInfo {
  private String[] saveAttribute = {"currentDate"};
  private RecordFileManager fileManager;
  private String fileDirname = DatabaseConfiguration.setFileDirname("systemInfo.txt");
  private SystemInfo instance = new SystemInfo();
  private int page;
}
