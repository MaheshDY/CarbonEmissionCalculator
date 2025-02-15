package com.co2calculator.model;

import lombok.Getter;

@Getter
public enum TransportationMethod {
  DIESEL_CAR_SMALL(142),
  PETROL_CAR_SMALL(154),
  PLUGIN_HYBRID_CAR_SMALL(73),
  ELECTRIC_CAR_SMALL(50),
  DIESEL_CAR_MEDIUM(171),
  PETROL_CAR_MEDIUM(192),
  PLUGIN_HYBRID_CAR_MEDIUM(110),
  ELECTRIC_CAR_MEDIUM(58),
  DIESEL_CAR_LARGE(209),
  PETROL_CAR_LARGE(282),
  PLUGIN_HYBRID_CAR_LARGE(126),
  ELECTRIC_CAR_LARGE(73),
  BUS_DEFAULT(27),
  TRAIN_DEFAULT(6);

  private final double co2PerKm;

  public static double getCO2PerKm(String method) {
    return TransportationMethod.valueOf(method.toUpperCase().replace("-", "_")).getCo2PerKm();
  }

  TransportationMethod(double co2PerKm) {
    this.co2PerKm = co2PerKm;
  }
}