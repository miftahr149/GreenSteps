//carbon footprint per watt == kilograms of CO2 equivalent per kilowatt-hour (kg CO2e/kWh)
//0.758 kg CO2e == 1kWh

import java.util.Scanner;

public class CarbonFootprintCalculator {
  private int kWh;
  private final double emissionFactor = 0.758; //https://sustainability.um.edu.my/ghg-um-2023
  
  public CarbonFootprintCalculator() {
    this.kWh = 0;
  }

  public CarbonFootprintCalculator(int kWh) {
    this.kWh = kWh;
  }

  public int getKWh() {
    return kWh;
  }

  public void setKWh(int kWh) {
    this.kWh = kWh;
  }

  public double calculateCarbon() {
    return kWh*emissionFactor;
  }

  public double calculateCarbonPerYear() {
    return calculateCarbon()*12;
  }

  public String toString() {
    return "Your monthly " + getKWh() + "kWh of electricity usage accounts to: " + calculateCarbon() + "kg CO2e/kWh/month\n" + "In a year, you will produce: " + calculateCarbonPerYear() + "kg CO2e\n";
  }

  public static void main(String[] args) {
    CarbonFootprintCalculator o1 = new CarbonFootprintCalculator();
    
    System.out.printf("Enter the amount of electricity used in (kWh) per month: ");
    Scanner scanner = new Scanner(System.in);
    int c1 = scanner.nextInt();
    scanner.close();

    o1.setKWh(c1);

    System.out.println(o1.toString());

    //Carbon Footprint per year
    if(o1.calculateCarbonPerYear() < 2000.00) {
      System.out.println("Your Lifestyle is Sustainable. Aligned with Global Target for Sustainability. Keep it up");
    } else if(o1.calculateCarbonPerYear() >= 2000.00 && o1.calculateCarbonPerYear() <= 4000.00) {
      System.out.println("Your Lifestyle is Moderate. Better than averag but not yet sustainable. \nCarbon emission below 2 tons CO2e/year or ~219kWh/month is adviced. \nReduce electricity usage");
    } else {
      System.out.println("Carbon emission above 4 tons CO2e/year is Unsustainable. ABOVE PLANETARY BOUNDARIES. \nYou are harming the planet. Reduce electricity usage immediately");
    }
  }
} 