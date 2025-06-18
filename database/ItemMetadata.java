package database;

public class ItemMetadata extends BaseRecord {
  private String name;
  private double usage;

  public static Factory<ItemMetadata> factory = new Factory<ItemMetadata>() {
    @Override
    public ItemMetadata newInstance(CallbackRecord callback) {
      return new ItemMetadata(callback);
    }

    @Override
    public String[] getSaveAttributes() {
      return new String[] {"name", "usage"};
    }

    @Override
    public String getFilename() {
      return "itemsMetadata";
    }

    @Override
    public String getFileDirname() {
      return DatabaseConfiguration.setFileDirname(getFilename());
    }
  };

  ItemMetadata(CallbackRecord callback) {
    super(callback, ItemMetadata.factory);
  }

  public void setName(String name) {
    super.callback.update(this);
    this.name = name;
  }

  public void setUsage(double usage) {
    super.callback.update(this);
    this.usage = usage;
  }

  public String getName() {
    return this.name;
  }

  public double getUsage() {
    return this.usage;
  }
}
