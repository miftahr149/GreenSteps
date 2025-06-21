package app;

import java.util.Scanner;
import database.DatabaseConfiguration;
import database.ItemMetadata;
import database.RecordManager;

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
        System.out.println("Invalid input! please input in integer");
      }
    }

    return returnValue;
  }

  public static ItemMetadata createItemMetadata(Scanner input,
      RecordManager<ItemMetadata> manager) {
    String itemName;
    double itemElectricityUsage;

    System.out.print("Item's name: ");
    itemName = input.nextLine();

    while (true) {
      try {
        System.out.println("Item's electricty usage per hour (kwh): ");
        String temp = input.nextLine();
        itemElectricityUsage = Double.parseDouble(temp);
        break;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input! pelase input in decimal");
      }
    }

    ItemMetadata returnValue = manager.create();
    returnValue.setName(itemName);
    returnValue.setUsage(itemElectricityUsage);
    returnValue.save();

    assert (returnValue.getName().equals(itemName)
        && returnValue.getUsage() == itemElectricityUsage);
    System.out.printf("Create new item with name %s and electricity usage %.2f \n",
        returnValue.getName(), returnValue.getUsage());
    return returnValue;
  }

  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    RecordManager<ItemMetadata> metadataManager = RecordManager.get("itemMetadata");

    Scanner input = new Scanner(System.in);
    int numItem = getNumItem(input);
    for (int iter = 0; iter < numItem; iter++) {
      System.out.printf("Items #%d\n", iter + 1);
      createItemMetadata(input, metadataManager);
    }
  }
}
