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

  public Reference(String referenceRecordManagerName) {
    this.referenceRecordManagerName = referenceRecordManagerName;
    this.isExtracted = false;
    this.referenceID = -1;
    this.callback = this.generateCallback(this);
    Reference.referenceList.add(this);
  }

  private void extract() {
    RecordManager<T> manager = RecordManager.get(this.referenceRecordManagerName);
    this.record = manager.get(this.referenceID);
    this.oppositeCallback = this.record.addReference(this, this.callback);
  }

  private void clear() {
    this.isExtracted = false;
    this.record = null;
    this.oppositeCallback = null;
    this.referenceID = -1;
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
