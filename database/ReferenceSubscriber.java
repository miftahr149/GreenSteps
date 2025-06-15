package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    this.callback = this.generateCallback(this);
  }

  public ReferenceCallback add(BaseRecord record, ReferenceCallback callback) {
    this.subscribers.put(record, callback);
    return this.callback;
  }

  public void remove(BaseRecord record) {
    ReferenceCallback callback = this.subscribers.get(record);
    callback.handleDelete(this.parentRecord);
    this.subscribers.remove(record);
  }

  public void removeAll() {
    Set<BaseRecord> list = new HashSet<BaseRecord>();
    list.addAll(this.subscribers.keySet());

    for (BaseRecord subscriber : list) {
      this.remove(subscriber);
    }
  }
}
