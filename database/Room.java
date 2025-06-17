package database;

public class Room extends BaseRecord {
  private String name;
  private double limit;

  public static Factory<Room> factory = new Factory<Room>() {
    @Override
    public Room newInstance(CallbackRecord callback) {
      return new Room(callback);
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

}
