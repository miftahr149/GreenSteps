package database;

import java.util.Set;
import java.util.HashSet;

interface ReferenceCallback {
  void handleDelete(BaseRecord oppositeRecord);
}


public class Reference<T extends BaseRecord> {
  private static Set<Reference<?>> referenceList = new HashSet<Reference<?>>();

  private int referenceID;
  private boolean isExtracted;
  private String referenceRecordManagerName;
  private T record;
  private ReferenceCallback oppositeCallback;
  private ReferenceCallback callback;
  private BaseRecord parentRecord;

  public Reference(Factory<T> factory, BaseRecord parentRecord) {
    this(factory.getFilename(), parentRecord);
  }

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
    this.isExtracted = true;
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

  private void setReferenceID(int referenceID) {
    this.referenceID = referenceID;
  }

  public void add(int referenceID) {
    this.setReferenceID(referenceID);
    if (this.isExtracted)
      this.oppositeCallback.handleDelete(this.parentRecord);
    this.extract();
    this.isExtracted = true;
  }

  static void extractAllReference() {
    for (Reference<?> reference : Reference.referenceList) {
      if (reference.referenceID == -1 || reference.isExtracted)
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

  public static <T extends BaseRecord> Reference<T> parseReference(String str, Object obj) {
    int endManagerNameIndex = str.indexOf("[");
    int strLength = str.length();
    String strReferenceID = str.substring(endManagerNameIndex + 1, strLength - 1);

    String managerName = str.substring(0, endManagerNameIndex);
    int referenceID = Integer.parseInt(strReferenceID);

    Reference<T> returnValue = new Reference<T>(managerName, (BaseRecord) obj);
    returnValue.setReferenceID(referenceID);
    return returnValue;
  }

  @Override
  public String toString() {
    return String.format("%s[%d]", this.referenceRecordManagerName, this.referenceID);
  }
}
