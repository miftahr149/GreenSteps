package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

interface CallbackRecord {
  void update(BaseRecord record);

  void delete(BaseRecord record);

  void save(BaseRecord record);
}


public class RecordManager<T extends BaseRecord> {
  static private Map<String, RecordManager<?>> managerInstanceList =
      new HashMap<String, RecordManager<?>>();
  static private Vector<RecordManager<?>> managerInstanceListTest = new Vector<>();

  private Factory<T> factory;
  private Vector<Change> changedRecordList;
  private Vector<T> recordList;
  private RecordFileManager fileManager;
  private CallbackRecord callback;
  private String filename;

  private int highestID;
  private boolean isManagerChange = false;
  private String[] saveAttributes = {"highestID"};

  static private void addManagerInstance(RecordManager<?> instance) {
    RecordManager.managerInstanceListTest.add(instance);
  }

  static public <U extends BaseRecord> RecordManager<U> get(String name) {
    RecordManager<?> result = null;
    for (RecordManager<?> iter : RecordManager.managerInstanceListTest) {
      if (iter.getFilename().equals(name)) {
        result = iter;
        break;
      }
    }

    @SuppressWarnings("unchecked")
    RecordManager<U> returnValue = (RecordManager<U>) result;
    return returnValue;
  }

  private String getFilename() {
    return this.filename;
  }

  public RecordManager(Factory<T> factory) {
    this.factory = factory;
    this.filename = this.factory.getFilename();
    RecordManager.addManagerInstance(this);
    this.changedRecordList = new Vector<Change>();
    this.recordList = new Vector<T>();
    this.callback = this.generateCallbackRecord(this);

    this.fileManager = new RecordFileManager(this.factory.getFileDirname());
    if (!this.fileManager.isFileExist()) {
      this.fileManager.init();
      this.fileManager.write(0, ObjectParser.encode(this, this.saveAttributes));
    } else
      this.fileManager.extractFromFile(this::handleExtractFromFile);
  }

  private void handleExtractFromFile(String strRecord, int page) {
    if (page == 0) {
      ObjectParser.decode(this, this.saveAttributes, strRecord);
      return;
    }

    T records = this.factory.load(strRecord, page, this.callback);
    this.recordList.add(records);
  }

  private CallbackRecord generateCallbackRecord(RecordManager<T> manager) {
    return new CallbackRecord() {
      @Override
      public void update(BaseRecord record) {
        Change elements = new UpdateRecord(record, (_record) -> {
          manager.fileManager.write(_record.getPage(), _record.encode());
        });
        if (!this.isValidChange(elements))
          return;
        manager.managerSave();
        manager.changedRecordList.add(elements);
      }

      @Override
      public void delete(BaseRecord record) {
        Change elements = new DeleteRecord(record, (_record) -> {
          _record.removeAllReferences();
          manager.recordList.remove(_record);
          manager.fileManager.release(_record.getPage());
        });

        if (!this.isValidChange(elements))
          return;
        manager.changedRecordList.add(elements);
      }

      @Override
      public void save(BaseRecord record) {
        ArrayList<Change> changes = findChanges(record);
        for (Change change : changes) {
          change.commit();
        }
      }

      private ArrayList<Change> findChanges(BaseRecord record) {
        ArrayList<Change> result = new ArrayList<Change>();

        for (Change change : manager.changedRecordList) {
          if (change.getRecord() == record) {
            result.add(change);
          }
        }

        for (Change changeResult : result) {
          manager.changedRecordList.remove(changeResult);
        }

        return result;
      }

      private boolean isValidChange(Change change) {
        return manager.changedRecordList.indexOf(change) == -1
            && manager.recordList.indexOf(change.getRecord()) != -1;
      }
    };
  }

  private int setCreatedIDRecord() {
    this.isManagerChange = true;
    int recordID = this.highestID;
    this.highestID++;
    return recordID;
  }

  public T create() {
    T returnValue = this.factory.newInstance(this.callback);
    this.recordList.add(returnValue);
    int recordID = this.setCreatedIDRecord();
    int recordPage = this.fileManager.getAvailablePage();
    returnValue.create(recordID, recordPage);
    this.factory.onCreate(returnValue);

    return returnValue;
  }

  private void managerSave() {
    if (!this.isManagerChange)
      return;
    String strManager = ObjectParser.encode(this, this.saveAttributes);
    this.fileManager.write(0, strManager);
    this.isManagerChange = false;
  }

  public void save() {
    this.managerSave();

    for (Change change : this.changedRecordList) {
      change.commit();
    }
    this.changedRecordList.clear();
  }

  public ArrayList<T> query(RecordQuery<T> callback) {
    ArrayList<T> returnResult = new ArrayList<T>();
    for (T record : this.recordList) {
      if (callback.accept(record)) {
        returnResult.add(record);
      }
    }
    return returnResult;
  }

  public T get(int id) {
    ArrayList<T> queryResult = this.query((record) -> {
      return record.getID() == id;
    });
    return queryResult.size() == 0 ? null : queryResult.get(0);
  }
}
