package database;

public class Item extends BaseRecord {
  private int quantity;
  private int averageHours;

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

  public void setQuantity(int quantity) {
    super.callback.update(this);
    this.quantity = quantity;
  }

  public void setAverageHours(int averageHours) {
    super.callback.update(this);
    this.averageHours = averageHours;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public int getAverageHours() {
    return this.averageHours;
  }
}
