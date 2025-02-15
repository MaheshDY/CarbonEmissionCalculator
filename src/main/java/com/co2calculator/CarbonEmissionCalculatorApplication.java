package com.co2calculator;

import com.co2calculator.exceptions.InvalidInputException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static org.apache.hc.core5.util.TextUtils.isBlank;

@Command(name = "co2-calculator", mixinStandardHelpOptions = true, version = "1.0",
    description = "Calculates CO2 emissions for a trip between two cities.")
public class CarbonEmissionCalculatorApplication implements Runnable {

  @Option(names = {"--start"}, description = "Starting city", required = true)
  private String startCity;

  @Option(names = {"--end"}, description = "Destination city", required = true)
  private String endCity;

  @Option(names = {"--transportation-method"}, description = "Transportation method", required = true)
  private String transportationMethod;

  public static void main(String[] args) {
    int exitCode = new CommandLine(new CarbonEmissionCalculatorApplication()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public void run() {
    try {
      if (isBlank(startCity)) {
        throw new IllegalArgumentException("Error: The '--start' parameter cannot be empty.");
      }
      if (isBlank(endCity)) {
        throw new IllegalArgumentException("Error: The '--end' parameter cannot be empty.");
      }
      if (isBlank(transportationMethod)) {
        throw new IllegalArgumentException("Error: The '--transportation-method' parameter cannot be empty.");
      }

      EmissionCalculator calculator = new EmissionCalculator();
      double co2Emissions = calculator.calculateCO2(startCity, endCity, transportationMethod);
      System.out.printf("Your trip caused %.1fkg of CO2-equivalent.%n", co2Emissions/1000000);

    } catch (InvalidInputException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
      System.exit(1);
    }
  }
}
