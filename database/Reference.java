package database;

interface ReferenceCallback {
  void handleDelete(BaseRecord oppositeRecord);
}


public class Reference<T extends BaseRecord> {

}
