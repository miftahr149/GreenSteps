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


}
