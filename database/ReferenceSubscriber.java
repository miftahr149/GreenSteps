package database;

import java.util.Map;

public class ReferenceSubscriber {
  Map<BaseRecord, ReferenceCallback> subscribers;
  BaseRecord parentRecord;
  ReferenceCallback callback;
}
