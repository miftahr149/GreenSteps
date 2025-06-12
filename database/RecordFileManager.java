package database;

import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@FunctionalInterface
interface OnExtractFromFile {
  void accept(String recordStr, int page);
}

class RecordFileManager {
  private static final int MAX_PAGE_SIZE = 255;
  private static final String EMPTY_PAGE = " ".repeat(RecordFileManager.MAX_PAGE_SIZE) + "\n";

  private ArrayList<Integer> availablePageList;
  private String fileDirname;

  public RecordFileManager(String fileDirname) {
    this.fileDirname = fileDirname;
    this.availablePageList = new ArrayList<Integer>();
  }

  public void extractFromFile(OnExtractFromFile func) {
    try (RandomAccessFile fileStream = new RandomAccessFile(this.fileDirname, "r")) {
      String strRecord;
      int page = -1;

      while ((strRecord = fileStream.readLine()) != null) {
        page++;
        if (strRecord.equals(RecordFileManager.EMPTY_PAGE)) {
          this.availablePageList.add(page);
          continue;
        }
        func.accept(strRecord, page);
      }
      fileStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void init() {
    this.write(0, RecordFileManager.EMPTY_PAGE);
  }

  public int getAvailablePage() throws Error {

    if (this.availablePageList.size() == 0) {
      return this.createNewPage();
    }
    int returnValue = this.availablePageList.get(0);
    this.availablePageList.remove(0);
    return returnValue;
  }

  private int createNewPage() {
    int newPage = -1;

    try {
      RandomAccessFile fileStream = new RandomAccessFile(this.fileDirname, "rw");
      fileStream.seek(fileStream.length());
      fileStream.write(RecordFileManager.EMPTY_PAGE.getBytes());
      fileStream.close();

      Path filePath = Path.of(this.fileDirname);
      newPage = (int) Files.lines(filePath).count() - 1;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return newPage;
  }

  private long toPhysicalPosition(int page) {
    return page * RecordFileManager.EMPTY_PAGE.length();
  }

  public void write(int page, String strRecord) {
    try (RandomAccessFile fileStream = new RandomAccessFile(this.fileDirname, "rw")) {
      long physicalPosition = this.toPhysicalPosition(page);
      fileStream.seek(physicalPosition);
      fileStream.write(RecordFileManager.EMPTY_PAGE.getBytes());
      fileStream.seek(physicalPosition);
      fileStream.write(strRecord.getBytes());
      fileStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void release(int page) {
    try (RandomAccessFile fileStream = new RandomAccessFile(this.fileDirname, "rw")) {
      fileStream.seek(this.toPhysicalPosition(page));
      fileStream.write(RecordFileManager.EMPTY_PAGE.getBytes());
      fileStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.availablePageList.add(page);
  }

  public boolean isFileExist() {
    return new File(this.fileDirname).exists();
  }
}
