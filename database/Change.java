package database;

import java.util.function.Consumer;

abstract class Change {
  private BaseRecord record;
  private Consumer<BaseRecord> commitCallback;

  public Change(BaseRecord record, Consumer<BaseRecord> commitCallback) {
    this.record = record;
    this.commitCallback = commitCallback;
  }

  BaseRecord getRecord() {
    return this.record;
  }

  @Override
  public boolean equals(Object obj) {
    String classChange1 = this.getClass().toString();
    String classChange2 = obj.getClass().toString();

    if (!classChange1.equals(classChange2))
      return false;
    Change change = (Change) obj;
    return this.record.equals(change.getRecord());
  }

  public void commit() {
    this.commitCallback.accept(record);
  }
}
