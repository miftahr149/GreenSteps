package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

interface CallbackRecord {
  void update(BaseRecord record);

  void delete(BaseRecord record);

  void save(BaseRecord record);
}


public class RecordManager<T extends BaseRecord> {
  static private Map<String, RecordManager<?>> managerInstanceList =
      new HashMap<String, RecordManager<?>>();

  private Factory<T> factory;
  private ArrayList<Change> changedRecordList;
  private ArrayList<T> recordList;
  private RecordFileManager fileManager;
  private CallbackRecord callback;

  private int highestID;
  private boolean isManagerChange = false;
  private String[] saveAttributes = {"highestID"};

  static private void addManagerInstance(String name, RecordManager<?> instance) {
    RecordManager.managerInstanceList.put(name, instance);
  }

  static public <U extends BaseRecord> RecordManager<U> get(String name) {
    RecordManager<?> result = RecordManager.managerInstanceList.get(name);
    return (RecordManager<U>) result;
  }

  public RecordManager(Factory<T> factory) {
    this.factory = factory;
    RecordManager.addManagerInstance(this.factory.getFilename(), this);
    this.changedRecordList = new ArrayList<Change>();
    this.recordList = new ArrayList<T>();
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

    T records = this.factory.newInstance(this.callback);
    records.decode(strRecord, page);
    this.recordList.add(records);
  }

  private CallbackRecord generateCallbackRecord(RecordManager<T> manager) {
    return new CallbackRecord() {
      @Override
      public void update(BaseRecord record) {
        Change elements = new UpdateRecord(record, (_record) -> {
          manager.fileManager.write(_record.getPage(), _record.encode());
        });
        if (manager.changedRecordList.indexOf(elements) != -1)
          return;
        manager.changedRecordList.add(elements);
      }

      @Override
      public void delete(BaseRecord record) {
        Change elements = new DeleteRecord(record, (_record) -> {
          manager.recordList.remove(_record);
          manager.fileManager.release(_record.getPage());
        });

        if (manager.changedRecordList.indexOf(elements) != -1)
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
        ArrayList<Integer> indexDelete = new ArrayList<Integer>();

        int index = 0;
        for (Change change : manager.changedRecordList) {
          if (change.getRecord() == record) {
            result.add(change);
            indexDelete.add(index);
          }
          index++;
        }

        for (int _index : indexDelete) {
          manager.changedRecordList.remove(_index);
        }

        return result;
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

    return returnValue;
  }

  public void save() {
    if (this.isManagerChange) {
      String strManager = ObjectParser.encode(this, this.saveAttributes);
      this.fileManager.write(0, strManager);
    }

    for (Change change : this.changedRecordList) {
      change.commit();
    }
    this.changedRecordList.clear();
    this.isManagerChange = false;
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
