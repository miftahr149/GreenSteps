package reportGenerator;

import java.util.ArrayList;
import java.util.Scanner;
import footprintCalculator.*;
import database.DatabaseConfiguration;
import database.MonthlyReport;
import database.RecordManager;
import database.RecordQuery;

public class GenerateReport {

    private CarbonFootprintCalculator calc;
    private RecordManager<MonthlyReport> reportList;
    private RecordQuery<MonthlyReport> query;
    private ArrayList<MonthlyReport> all;

    public GenerateReport() {
        DatabaseConfiguration.configure();
        calc = new CarbonFootprintCalculator();
        reportList = RecordManager.get("monthlyReport");
        query = (record) -> {
            return true;
        };
        all = reportList.query(query);
    }

    public void display() {
        int i = 1;
        System.out.println("-----------------------");
        System.out.println("Monthly Report List");
        System.out.println("-----------------------");
        for (MonthlyReport m : all) {
            System.out.println(i++ + ". " + m.getDate());
            System.out.println(m.getTotalCarbonFootprint() + " CO₂e");
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("[Number] = Get getails");
        System.out.println("[0] = Back");
        System.out.printf("Your choice: ");
        int choice = scanner.nextInt();

        if (choice == 0) {

        }
        details(choice);
    }

    public void details(int c) {
        MonthlyReport report1 = all.get(c - 1);
        MonthlyReport report2 = all.get(c - 2);

        System.out.println("");
        System.out.println("-----------------------");
        System.out.println(report1.getDate());
        System.out.println("-----------------------");
        System.out.println("This month has a " + calc.trendComparison(report1, report2)
                + "% change in carbon footprint created compared to the previous month");

    }

    public static void main(String[] args) {
        GenerateReport generate = new GenerateReport();
        generate.display();
    }

}
