package database;

public class Item extends BaseRecord {
  public int quantity;
  public int averageHours;

  public static Factory<Item> factory = new Factory<Item>() {
    @Override
    public Item newInstance(CallbackRecord callback) {
      return new Item(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"quantity", "averageHours"};
    }

    @Override
    public String getFilename() {
      return "item";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(getFilename());
    }
  };

  Item(CallbackRecord callback) {
    super(callback, Item.factory);
  }
}
