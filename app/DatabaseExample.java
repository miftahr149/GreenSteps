package app;

import database.DatabaseConfiguration;
import database.RecordManager;
import database.ItemMetadata;
import database.MonthlyReport;
import database.MonthlyUsage;
import database.Item;
import database.Room;
import database.SystemInfo;

interface TestScenario {
  public void test();
}


class TestGenerateRoomDummy implements TestScenario {

  private void createMetadataItems() {
    RecordManager<ItemMetadata> metadataManager = RecordManager.get("itemMetadata");

    ItemMetadata metadata1 = metadataManager.create();
    metadata1.setName("Computer");
    metadata1.setUsage(0.5);

    ItemMetadata metadata2 = metadataManager.create();
    metadata2.setName("Air Conditioner");
    metadata2.setUsage(3);

    metadataManager.save();
  }

  private void createRoom() {
    RecordManager<Room> roomManager = RecordManager.get("room");

    Room labRoom = roomManager.create();
    labRoom.setName("MPK-1");
    labRoom.setLimit(2000);
    labRoom.save();
  }

  public void test() {
    this.createMetadataItems();
    this.createRoom();
    System.out.println("See item, itemMetadata, and room in storage folder");
  }
}


class TestChangeReference implements TestScenario {
  @Override
  public void test() {
    RecordManager<Room> roomManager = RecordManager.get("room");
    RecordManager<MonthlyUsage> monthlyUsageManager = RecordManager.get("monthlyUsage");

    Room room = roomManager.create();

    MonthlyUsage monthlyUsage = monthlyUsageManager.create();
    monthlyUsage.setDate(SystemInfo.getCurrentDate());

    double totalUsage = 0;
    room.setName("MPK-2");
    for (Item item : room.getItems()) {
      item.setAverageHours(2);
      item.setQuantity(5);
      item.save();
      totalUsage += item.getUsage() * item.getAverageHours() * item.getQuantity() * 30;
    }
    monthlyUsage.setRoom(room);
    monthlyUsage.setElectricityUsage(totalUsage);

    roomManager.save();
    monthlyUsageManager.save();
  }
}


public class DatabaseExample {
  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    SystemInfo.getCurrentDate();
    TestScenario scenario1 = new TestGenerateRoomDummy();
    TestScenario scenario2 = new TestChangeReference();

    scenario1.test();
    scenario2.test();
  }
}
