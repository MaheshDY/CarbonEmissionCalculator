package com.co2calculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class City {
  private String name;
  private double lat;
  private double lon;
}