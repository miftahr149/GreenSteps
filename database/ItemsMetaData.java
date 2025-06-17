package database;

public class ItemsMetadata extends BaseRecord {
  private String name;
  private double usage;

  public static Factory<ItemsMetadata> factory = new Factory<ItemsMetadata>() {
    @Override
    public ItemsMetadata newInstance(CallbackRecord callback) {
      return new ItemsMetadata(callback);
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

  ItemsMetadata(CallbackRecord callback) {
    super(callback, ItemsMetadata.factory);
  }

  public void setName(String name) {
    super.callback.update(this);
    this.name = name;
  }

  public void setUsage(double usage) {
    super.callback.update(this);
    this.usage = usage;
  }
}
