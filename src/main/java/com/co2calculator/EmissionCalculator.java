package com.co2calculator;

import com.co2calculator.model.City;
import com.co2calculator.model.TransportationMethod;

public class EmissionCalculator {

  private final OpenRouteServiceClient orsClient;

  public EmissionCalculator() {
    this.orsClient = new OpenRouteServiceClient();
  }

  public double calculateCO2(String startCityName, String endCityName, String transportationMethod) throws Exception {
    City startCity = orsClient.getCityCoordinates(startCityName);
    City endCity = orsClient.getCityCoordinates(endCityName);

    double distance = orsClient.getDistance(startCity, endCity);
    double co2PerKm = TransportationMethod.getCO2PerKm(transportationMethod);

    return distance * co2PerKm;
  }
}
