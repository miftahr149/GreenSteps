package database;

import java.util.function.Consumer;

public class UpdateRecord extends Change {
  public UpdateRecord(BaseRecord record, Consumer<BaseRecord> callback) {
    super(record, callback);
  }
}
