package app;

import database.DatabaseConfiguration;

public class Main {

  public static final int maxLength = 60;

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

  public static void displayMenu() {

  }


  public static void main(String[] args) {
    DatabaseConfiguration.configure();
    displayMenu();
  }
}
