package database;

import java.util.ArrayList;

interface ReferenceCallback {
  void handleDelete(BaseRecord oppositeRecord);
}

public class Reference<T extends BaseRecord> {
  private static ArrayList<Reference<?>> referenceList = new ArrayList<Reference<?>>();

  private int referenceID;
  private boolean isExtracted;
  private String referenceRecordManagerName;
  private T record;
  private ReferenceCallback oppositeCallback;
  private ReferenceCallback callback;
  private BaseRecord parentRecord;

  public Reference(String referenceRecordManagerName, BaseRecord parentRecord) {
    this.referenceRecordManagerName = referenceRecordManagerName;
    this.isExtracted = false;
    this.referenceID = -1;
    this.callback = this.generateCallback(this);
    this.parentRecord = parentRecord;
    Reference.referenceList.add(this);
  }

  private void extract() {
    RecordManager<T> manager = RecordManager.get(this.referenceRecordManagerName);
    this.record = manager.get(this.referenceID);
    this.oppositeCallback = this.record.addReference(this.parentRecord, this.callback);
  }

  private void clear() {
    this.isExtracted = false;
    this.record = null;
    this.oppositeCallback = null;
    this.referenceID = -1;
  }

  public T get() {
    if (!this.isExtracted)
      extract();
    return this.record;
  }

  public void deleteReference() {
    this.oppositeCallback.handleDelete(this.parentRecord);
    this.clear();
  }

  public void add(int referenceID) {
    this.referenceID = referenceID;
    if (this.isExtracted)
      this.oppositeCallback.handleDelete(this.parentRecord);
    this.extract();
  }

  static void extractAllReference() {
    for (Reference<?> reference : Reference.referenceList) {
      if (reference.referenceID == -1)
        continue;
      reference.extract();
    }
  }

  private ReferenceCallback generateCallback(Reference<T> manager) {
    return new ReferenceCallback() {
      @Override
      public void handleDelete(BaseRecord oppositeRecord) {
        manager.clear();
      }
    };
  }
}
