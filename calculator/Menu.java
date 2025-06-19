import java.util.Scanner;

public class Menu {
      public static void displayMenu() {
      System.out.println("---------------------");
      System.out.println("     GreenSteps      ");
      System.out.println("---------------------");
      System.out.println("[1] Calculator");
      System.out.println("[2] ");
      System.out.println("[3] ");
      System.out.println("[4] Exit");
      System.out.print("Your choice: ");
   }

   public static void main(String[] args) {      
      CarbonFootprintCalculator c = new CarbonFootprintCalculator();
      
      displayMenu();
      Scanner scanner = new Scanner(System.in);
      int choice = scanner.nextInt();

      if(choice == 1) {
         Item.displayItem();
         c.display();
         int kWh = scanner.nextInt();
         c.setKWh(kWh);
         c.calculate();
      } else if(choice == 2) {

      } else if(choice == 3) {
         
      } else if(choice == 4) {
         
      } else {
         System.out.println("Please enter a valid choice (1-4).");
      }
      scanner.close();
  }
}
