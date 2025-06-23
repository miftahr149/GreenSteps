package footprintCalculator;

import database.*;
import java.util.Scanner;
import java.util.List;

public class ItemSelector {
  public void display() {
    Scanner scanner = new Scanner(System.in);
    DatabaseConfiguration.configure();
    RecordManager<Room> roomManager = RecordManager.get("room");
    RecordQuery<Room> queryAll = (Room room) -> {return true;};
    List<Room> rooms= roomManager.query(queryAll);

    //display room
    System.out.println("List of Rooms:");
    for(Room room : roomManager.query(queryAll)) {
      System.out.println(room.getName());
    }

    //select room
    System.out.println("Select a room");

    //display item
    //
    //select item
    //input quantity
    //input avgHour

    System.out.println("Saved to database");
    scanner.close();
  }
}
