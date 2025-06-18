package database;

import java.util.ArrayList;

public class Room extends BaseRecord {
  private String name;
  private double limit;

  public static Factory<Room> factory = new Factory<Room>() {

    @Override
    public Room newInstance(CallbackRecord callback) {
      Room returnValue = new Room(callback);
      RecordManager<ItemMetadata> metadataManager =
          RecordManager.get(ItemMetadata.factory.getFilename());
      RecordManager<Item> itemManager = RecordManager.get(Item.factory.getFilename());
      ArrayList<ItemMetadata> metadataList = metadataManager.query((_record) -> {
        return true;
      });
      for (ItemMetadata metadata : metadataList) {
        Item obj = itemManager.create();
        obj.setRoom(returnValue);
        obj.setItemMetadata(metadata);
        obj.save();
      }

      return returnValue;
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"name", "limit"};
    }

    @Override
    public String getFilename() {
      return "room";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(getFilename());
    }

    @Override
    public Room load(String strRecord, int page, CallbackRecord callback) {
      Room returnValue = new Room(callback);
      returnValue.decode(strRecord, page);
      return returnValue;
    }
  };

  Room(CallbackRecord callback) {
    super(callback, Room.factory);
  }

  public void setName(String name) {
    super.callback.update(this);
    this.name = name;
  }

  public void setLimit(double limit) {
    super.callback.update(this);
    this.limit = limit;
  }

  public String getName() {
    return this.name;
  }

  public double getLimit() {
    return this.limit;
  }

  public ArrayList<Item> getItems() {
    return super.referenceSubscriber.get(Item.class);
  }
}
