package reportGenerator;

import java.util.ArrayList;
import java.util.Scanner;
import footprintCalculator.*;
import database.CustomDate;
import database.DatabaseConfiguration;
import database.MonthlyReport;
import database.MonthlyUsage;
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

    public void getUsage(int c) {
        for (MonthlyUsage m : all.get(c).getMonthlyUsage()) {
            System.out.println(m.getRoomName() + " used " + m.getElectricityUsage() + "KWH of electricity and produced "
                    + m.getCarbonFootprint() + "CO2e carbon footprint this month");
        }
    }

    public void display() {
        int i = 1;
        System.out.println("-----------------------");
        System.out.println("Monthly Report List");
        System.out.println("-----------------------");
        for (MonthlyReport m : all) {
            System.out.println(i++ + ". " + m.getDate());
            System.out.println(m.getTotalCarbonFootprint() + " CO2e");
        }

        System.out.println("");
        System.out.println("The highest production of carbon footprint was produced in: " + getHighest());
        System.out.println(
                "The average carbon footprint produced in the span of " + all.size() + " month(s) is: " + getAverage()
                        + "CO2e");

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("[Number] = Get getails");
        System.out.println("[0] = Back");
        System.out.printf("Your choice: ");
        int choice = scanner.nextInt();

        if (choice == 0) {
            // back to main menu
        }
        details(choice);
    }

    public String getHighest() {
        double highest = 0;
        int index = 0;

        for (MonthlyReport m : all) {
            if (m.getTotalCarbonFootprint() > highest) {
                highest = m.getTotalCarbonFootprint();
                index = all.indexOf(m);
            }
        }
        return all.get(index).getDate().toString();
    }

    public double getAverage() {
        double sum = 0;

        for (MonthlyReport m : all) {
            sum += m.getTotalCarbonFootprint();
        }

        return (sum / all.size());
    }

    public void details(int c) {

        MonthlyReport report1 = all.get(c - 1);

        System.out.println("");
        System.out.println("-----------------------");
        System.out.println(report1.getDate() + " Report");
        System.out.println("-----------------------");
        getUsage(c - 1);

        if (c != 1) {
            MonthlyReport report2 = all.get(c - 2);

            System.out.println("");
            System.out.printf(
                    "This month has a %.2f%%%n change in carbon footprint produced compared to the previous month\n",
                    calc.trendComparison(report1, report2));
        }

        System.out.println("");
        display();

    }

    public static void main(String[] args) {
        GenerateReport generate = new GenerateReport();
        generate.display();
    }

}
