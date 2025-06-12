package database;

import java.util.function.Consumer;

class DeleteRecord extends Change {
  public DeleteRecord(BaseRecord record, Consumer<BaseRecord> callback) {
    super(record, callback);
  }
}
