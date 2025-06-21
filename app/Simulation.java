package app;

import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

import database.DatabaseConfiguration;
import database.MonthlyUsage;
import database.Room;
import database.SystemInfo;
import database.RecordManager;
import database.RecordQuery;
import database.MonthlyReport;
import database.Item;

public class Simulation {
  private static final RecordManager<Room> roomManager = RecordManager.get("room");
  private static final RecordManager<MonthlyReport> reportManager =
      RecordManager.get("monthlyReport");
  private static final RecordManager<MonthlyUsage> usageManager = RecordManager.get("monthlyUsage");
  private static final int minAverageHours = 2;
  private static final int maxAverageHours = 10;

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

  private static int getRandomNumber() {
    double result = Math.random() * (maxAverageHours - minAverageHours);
    return (int) result;
  }

  private static double getMonthlyUsage(Item item) {
    return item.getUsage() * item.getAverageHours() * item.getQuantity();
  }

  private static Date incrementDateMonth(Date date, int month) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.MONTH, month);
    return c.getTime();
  }

  private static MonthlyUsage generateMonthlyUsage(Room room) {
    double totalElectricityUsage = 0;

    for (Item item : room.getItems()) {
      int averageHours = getRandomNumber();
      item.setAverageHours(averageHours);
      totalElectricityUsage += getMonthlyUsage(item);
      item.save();
    }

    double carbonFootprint = 0.758 * totalElectricityUsage;

    MonthlyUsage returnValue = usageManager.create();
    returnValue.setElectricityUsage(totalElectricityUsage);
    returnValue.setCarbonFootprint(carbonFootprint);
    returnValue.setRoom(room);
    returnValue.setDate(SystemInfo.getCurrentDate());

    return returnValue;
  }

  private static void generateMonthlyReport() {
    RecordQuery<Room> roomQuery = (Room recordQuery) -> {
      return true;
    };

    double totalCarbonFootprint = 0;
    MonthlyReport report = reportManager.create();

    for (Room room : roomManager.query(roomQuery)) {
      MonthlyUsage monthlyUsage = generateMonthlyUsage(room);
      monthlyUsage.setMonthlyReport(report);
      totalCarbonFootprint += monthlyUsage.getCarbonFootprint();
    }
    usageManager.save();

    report.setDate(SystemInfo.getCurrentDate());
    report.setTotalCarbonFootprint(totalCarbonFootprint);
    report.save();
  }

  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    Scanner input = new Scanner(System.in);

    int month = getNumMonth(input);
    for (int i = 0; i < month; i++) {
      generateMonthlyReport();
      Date newDate = incrementDateMonth(SystemInfo.getCurrentDate(), 1);
      SystemInfo.setCurrentDate(newDate);
    }
    input.close();
  }
}
