package footprintCalculator;

import database.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ItemSelector {
  public void display() {
    Scanner scanner = new Scanner(System.in);
    RecordManager<Room> roomManager = RecordManager.get("room");
    RecordQuery<Room> queryAll = (Room room) -> {
      return true;
    };
    ArrayList<Room> rooms = roomManager.query(queryAll);

    // display room
    System.out.println("List of Rooms:");
    for (Room room : roomManager.query(queryAll)) {
      System.out.println(room.getName());
    }

    // select room
    System.out.print("Select a room: ");
    int roomInput = scanner.nextInt();
    Room userInputRoom = rooms.get(roomInput - 1);

    // display item
    ArrayList<Item> items = userInputRoom.getItems();
    System.out.println("List of Items: ");
    for (int i = 0; i < items.size(); i++) {
      System.out.printf("%d. %s\n", i + 1, items.get(i).getName());
    }

    // select item
    System.out.print("Select an item: ");
    int itemInput = scanner.nextInt();
    Item userInputItem = items.get(itemInput - 1);

    // input quantity
    System.out.print("Enter quantity: ");
    int quantity = scanner.nextInt();

    // input avgHour
    System.out.print("Enter average hour per month: ");
    int averageHours = scanner.nextInt();

    // set values
    userInputItem.setQuantity(quantity);
    userInputItem.setAverageHours(averageHours);
    userInputItem.save();

    System.out.println("Saved to database");
  }
}
