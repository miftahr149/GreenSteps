package database;

public abstract class BaseRecord {
  private class Attribute {
    public String[] saveAttribute = {"id"};
    public int id;
  }

  protected CallbackRecord callback;
  private String[] childSaveAttribute;
  private int page;
  private Attribute attribute;
  protected ReferenceSubscriber referenceSubscriber;

  public BaseRecord(CallbackRecord callback, Factory<?> factory) {
    this.callback = callback;
    this.childSaveAttribute = factory.getSaveAttributes();
    this.attribute = new Attribute();
    this.referenceSubscriber = new ReferenceSubscriber(this);
  }

  public String encode() {
    StringBuilder returnValue = new StringBuilder();
    returnValue.append(ObjectParser.encode(this.attribute, this.attribute.saveAttribute));
    returnValue.append(ObjectParser.encode(this, this.childSaveAttribute));
    return returnValue.toString();
  }

  public void decode(String encodeStr, int pageNumber) {
    this.page = pageNumber;
    ObjectParser.decode(this, this.childSaveAttribute, encodeStr);
    ObjectParser.decode(this.attribute, this.attribute.saveAttribute, encodeStr);
  }

  public void delete() {
    this.callback.delete(this);
  }

  public void save() {
    this.callback.save(this);
  }

  void create(int id, int page) {
    this.page = page;
    this.attribute.id = id;
  }

  int getPage() {
    return this.page;
  }

  protected int getID() {
    return this.attribute.id;
  }

  protected ReferenceCallback addReference(BaseRecord record, ReferenceCallback callback) {
    return this.referenceSubscriber.add(record, callback);
  }

  protected void removeAllReferences() {
    this.referenceSubscriber.removeAll();
  }
}
