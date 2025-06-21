package app;

import java.util.Scanner;
import database.DatabaseConfiguration;

public class AddItemMetadata {

  public static int getNumItem(Scanner input) {

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
