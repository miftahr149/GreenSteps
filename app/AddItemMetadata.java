package app;

import java.util.Scanner;
import database.DatabaseConfiguration;

public class AddItemMetadata {

  public static int getNumItem(Scanner input) {
    int returnValue;

    while (true) {
      try {
        System.out.print("Amount of items you want to create [in digit]: ");
        String temp = input.nextLine();
        returnValue = Integer.parseInt(temp);
        break;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input! please input in digit");
      }
    }

    return returnValue;
  }

  public static void createItemMetadata(Scanner input) {

  }

  public static void main(String[] args) {
    DatabaseConfiguration.configure();

    Scanner input = new Scanner(System.in);
    int numItem = getNumItem(input);
    for (int iter = 0; iter < numItem; iter++) {
      createItemMetadata(input);
    }
  }
}
