package database;

import java.util.HashMap;
import java.util.Map;

public class ReferenceSubscriber {
  Map<BaseRecord, ReferenceCallback> subscribers;
  BaseRecord parentRecord;
  ReferenceCallback callback;

  private ReferenceCallback generateCallback(ReferenceSubscriber manager) {
    return new ReferenceCallback() {
      @Override
      public void handleDelete(BaseRecord oppositeRecord) {
        manager.subscribers.remove(oppositeRecord);
      }
    };
  }

  public ReferenceSubscriber(BaseRecord parentRecord) {
    this.subscribers = new HashMap<BaseRecord, ReferenceCallback>();
    this.parentRecord = parentRecord;
    this.callback = generateCallback(this);
  }

  public ReferenceCallback add(BaseRecord record, ReferenceCallback callback) {
    this.subscribers.put(record, callback);
    return this.callback;
  }
}
