package app;

import java.util.Scanner;
import java.util.ArrayList;

import database.DatabaseConfiguration;
import database.MonthlyUsage;
import database.Room;
import database.RecordManager;
import database.RecordQuery;
import database.MonthlyReport;
import database.Item;

public class Simulation {
  private static RecordManager<Room> roomManager = RecordManager.get("room");
  private static RecordManager<MonthlyReport> reportManager = RecordManager.get("monthlyReport");
  private static RecordManager<MonthlyUsage> usageManager = RecordManager.get("monthlyUsage");

  private static int getNumMonth(Scanner input) {
    int month;
    while (true) {
      try {
        String temp = input.nextLine();
        month = Integer.parseInt(temp);
        break;
      } catch (NumberFormatException e) {
        System.out.println("Please input the amount of month in number");
      }
    }
    return month;
  }

  private static MonthlyUsage generateMonthlyUsage(Room room) {
    for (Item item : room.getItems()) {

    }
  }

  private static void generateMonthlyReport() {
    RecordQuery<Room> roomQuery = (Room recordQuery) -> {
      return true;
    };

    for (Room room : roomManager.query(roomQuery)) {
      generateMonthlyUsage(room);
    }
  }

  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    Scanner input = new Scanner(System.in);

    int month = getNumMonth(input);
    for (int i = 0; i < month; i++) {
      generateMonthlyReport();
    }
    input.close();
  }
}
