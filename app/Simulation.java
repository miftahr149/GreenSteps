package app;

import java.util.Scanner;
import database.DatabaseConfiguration;

public class Simulation {
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

  private static void generateDummyData() {

  }

  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    Scanner input = new Scanner(System.in);


    int month = getNumMonth(input);
    for (int i = 0; i < month; i++) {
      generateDummyData();
    }
    input.close();
  }
}
