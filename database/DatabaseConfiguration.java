package database;

public class DatabaseConfiguration {
  private static final String folderDir = "storage";

  public static String setFileDirname(String filename) {
    return String.format("%s/%s.txt", DatabaseConfiguration.folderDir, filename);
  }

  public static void configure() {
    new RecordManager<Student>(Student.studentFactory);
  }
}
