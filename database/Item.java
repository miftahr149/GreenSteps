package database;

public class Item extends BaseRecord {
  private int quantity;
  private int averageHours;
  private Reference<ItemMetadata> metadata;
  private Reference<Room> room;

  public static Factory<Item> factory = new Factory<Item>() {
    @Override
    public Item newInstance(CallbackRecord callback) {
      return new Item(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"quantity", "averageHours", "metadata"};
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
    this.metadata = new Reference<>(ItemMetadata.factory, this);
    this.room = new Reference<>(Room.factory, this);
  }

  public void setQuantity(int quantity) {
    super.callback.update(this);
    this.quantity = quantity;
  }

  public void setAverageHours(int averageHours) {
    super.callback.update(this);
    this.averageHours = averageHours;
  }

  public void setItemMetadata(ItemMetadata metadata) {
    this.metadata.add(metadata.getID());
  }

  public void setRoom(Room room) {
    this.room.add(room.getID());
  }

  public int getQuantity() {
    return this.quantity;
  }

  public int getAverageHours() {
    return this.averageHours;
  }

  public String getName() {
    return this.metadata.get().getName();
  }

  public double getUsage() {
    return this.metadata.get().getUsage();
  }
}
