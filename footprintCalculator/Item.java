package footprintCalculator;

public enum Item {
  COMPUTER(300, 0, 0), // general use
  CEILING_UNIVERSAL_AC(3000, 0, 0), PROJECTOR(100, 0, 0);

  private int kWh;
  private int quantity;
  private int avgHours;

  Item(int kWh, int quantity, int avgHours) {
    this.kWh = kWh;
    this.quantity = quantity;
    this.avgHours = avgHours;
  }

  public int getKWh() {
    return kWh;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getAvgHours() {
    return avgHours;
  }

  public void setKWh(int kWh) {
    this.kWh = kWh;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setAvgHours(int avgHours) {
    this.avgHours = avgHours;
  }

  public static void displayItem() {
    for (Item item : Item.values()) {
      System.out.println(item + " " + item.getQuantity() + " " + item.getAvgHours() + "\n");
    }
  }
}
