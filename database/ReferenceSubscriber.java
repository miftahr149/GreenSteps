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

}
