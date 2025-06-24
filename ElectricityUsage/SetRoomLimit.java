package ElectricityUsage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import database.RecordManager;
import database.Room;

public class SetRoomLimit {
  private RecordManager<Room> roomManager;
  private Scanner input;

  public void displayCurrentRoom(ArrayList<Room> rooms) {
    int count = 1;
    for (Room room : rooms) {
      System.out.printf("[%d] %s\n", count++, room.getName());
    }
  }

  public void main() {
    this.roomManager = RecordManager.get("room");
    this.input = new Scanner(System.in);
    int userInput;

    ArrayList<Room> allRoom = roomManager.query(r -> true);

    while (true) {
      this.displayCurrentRoom(allRoom);
      System.out.printf("Please input your option [1 - %d]: ", allRoom.size());
      String temp = input.nextLine();
      try {
        userInput = Integer.parseInt(temp);
        if (userInput < 1 || userInput > allRoom.size()) {
          throw new IOException("Invalid Option");
        }
        break;
      } catch (NumberFormatException e) {
        System.out.println("Please input the correct input");
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    new ElectricityUsageMonitor().limitRoom(allRoom.get(userInput - 1));
  }
}
