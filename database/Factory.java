package database;

abstract class Factory<T extends BaseRecord> {
  abstract public T newInstance(CallbackRecord callback);

  abstract public String getFilename();

  abstract public String getFileDirname();

  abstract public String[] getSaveAttributes();

  public T load(String strRecord, int page, CallbackRecord callback) {
    T returnValue = this.newInstance(callback);
    returnValue.decode(strRecord, page);
    return returnValue;
  }

  public void onCreate(T record) {};
}
