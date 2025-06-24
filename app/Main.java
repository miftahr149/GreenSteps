package app;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Set;
import ElectricityUsage.ElectricityUsageMonitor;
import ElectricityUsage.SetRoomLimit;
import reportGenerator.GenerateReport;
import java.util.Scanner;
import database.DatabaseConfiguration;
import footprintCalculator.ItemSelector;

@FunctionalInterface
interface OptionFunction {
  void execute();
}


public class Main {

  public static final int maxLength = 40;
  public static Map<String, OptionFunction> optionList;

  public static boolean endProgram = false;

  public static void displayHeading() {
    String title = "GreenSteps";
    int titleLeftPadding = (int) Math.floor((maxLength - title.length()) / 2.0);
    int titleRightPadding = (int) Math.ceil((maxLength - title.length()) / 2.0);
    String border = "-".repeat(maxLength);

    System.out.println(border);
    System.out.println();
    System.out.printf("%s%s%s\n", " ".repeat(titleLeftPadding), title,
        " ".repeat(titleRightPadding));
    System.out.println();
    System.out.println(border);
  }

  public static void displayOption() {
    int count = 1;

    System.out.println("Option: ");

    for (Map.Entry<String, OptionFunction> set : optionList.entrySet()) {
      System.out.printf("[%d] %s\n", count++, set.getKey());
    }

    String border = "-".repeat(maxLength);
    System.out.println(border);
  }

  public static OptionFunction getUserOption(Scanner input)
      throws InputMismatchException, IndexOutOfBoundsException {
    int userInput;

    System.out.printf("Please input your option [1 - %d]: ", optionList.size());
    userInput = input.nextInt();
    input.nextLine();

    if (userInput > optionList.size() || userInput <= 0) {
      throw new IndexOutOfBoundsException("Unavailable option! please choose appropriate option");
    }

    Set<Map.Entry<String, OptionFunction>> optionSet = optionList.entrySet();
    @SuppressWarnings("unchecked")
    Map.Entry<String, OptionFunction> optionEntry =
        (Map.Entry<String, OptionFunction>) optionSet.toArray()[userInput - 1];

    return optionEntry.getValue();
  }

  public static void displayMenu() throws IOException {
    Scanner input = new Scanner(System.in);

    displayHeading();
    displayOption();
    OptionFunction func;

    try {
      func = getUserOption(input);
      System.out.println("\033[H\033[2J");
      func.execute();
    } catch (InputMismatchException e) {
      System.out.println("\033[H\033[2J");
      System.out.println("Invalid format! please input the correct format");
      displayMenu();
    } catch (IndexOutOfBoundsException e) {
      System.out.println("\033[H\033[2J");
      System.out.println(e.getMessage());
      displayMenu();
    }

    while (true && !endProgram) {
      System.out.print("Continue [y/n]: ");
      String userInput = input.nextLine();
      userInput = userInput.toLowerCase();
      if ("y".equals(userInput) || "n".equals(userInput)) {
        System.out.println("\033[H\033[2J");
        if ("y".equals(userInput))
          displayMenu();
        endProgram = false;
        break;
      }
      System.out.println("incorrect option, please use appropriate option");
    }
  }

  public static void main(String[] args) throws IOException {
    DatabaseConfiguration.configure();
    optionList = new HashMap<>() {
      {
        put("Set Room Items", new ItemSelector()::display);
        put("Generate Report", new GenerateReport()::display);
        put("Create new room", new ElectricityUsageMonitor()::addRoom);
        put("Set Room Electricity Limit", new SetRoomLimit()::main);
        put("Add new Item Type", AddItemMetadata::main);
      }
    };
    displayMenu();
  }
}
