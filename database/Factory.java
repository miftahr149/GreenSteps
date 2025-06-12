package database;

interface Factory<T extends BaseRecord> {
  public T newInstance(CallbackRecord callback);

  public String getFilename();

  public String getFileDirname();

  public String[] getSaveAttributes();
}
