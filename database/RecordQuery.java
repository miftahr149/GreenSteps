package database;

@FunctionalInterface
public interface RecordQuery<T extends BaseRecord> {
  public boolean accept(T record);
}
