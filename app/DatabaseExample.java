package app;

import database.DatabaseConfiguration;
import database.RecordManager;
import database.ItemMetadata;
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


public class DatabaseExample {
  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    SystemInfo.getCurrentDate();
    TestScenario scenario = new TestGenerateRoomDummy();
    scenario.test();
  }
}
