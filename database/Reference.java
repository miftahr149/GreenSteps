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

  public Reference(String referenceRecordManagerName) {
    this.referenceRecordManagerName = referenceRecordManagerName;
    this.isExtracted = false;
    this.referenceID = -1;
    Reference.referenceList.add(this);
  }

  private void extract() {
    RecordManager<T> manager = RecordManager.get(this.referenceRecordManagerName);
    this.record = manager.get(this.referenceID);
    this.oppositeCallback = this.record.addReference(this, this::onDelete);
  }


}
