package footprintCalculator;

import database.RecordManager;
import database.RecordQuery;
import database.DatabaseConfiguration;
import database.MonthlyReport;
import database.Room;
import database.Item;

// carbon footprint per watt == kilograms of CO2 equivalent per kilowatt-hour (kg CO2e/kWh)
// 0.758 kg CO2e == 1kWh
public class CarbonFootprintCalculator {
  private final double carbonCoef = 0.758;

  public CarbonFootprintCalculator() {
    
  }

  public double calculateCarbon(Item item) {
    return item.getQuantity() * item.getAverageHours() * item.getUsage() * carbonCoef;
  }

  public double trendComparison(MonthlyReport current, MonthlyReport previous) {
    return ((current.getTotalCarbonFootprint() / previous.getTotalCarbonFootprint()) - 1) * 100;//percentge
  }
  
  //total carbon emission of all items
  public void calculateTotal() {
    DatabaseConfiguration.configure();
    RecordManager<Room> roomManager = RecordManager.get("room");
    RecordQuery<Room> queryAll = (Room room) -> {return true;};

    double totalCarbon = 0.0;
    for(Room room : roomManager.query(queryAll)) {
      for(Item item : room.getItems()) {
        totalCarbon += calculateCarbon(item);
      }
    }
    System.out.printf("Your monthly electricity usage accounts to: %.2f kg CO2e/kWh\n", totalCarbon);
  }
}
