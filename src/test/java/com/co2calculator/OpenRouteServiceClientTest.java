package com.co2calculator;

import java.io.IOException;

import com.co2calculator.exceptions.ApiException;
import com.co2calculator.model.City;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OpenRouteServiceClientTest {

  private CloseableHttpClient mockHttpClient;
  private CloseableHttpResponse mockResponse;
  private OpenRouteServiceClient orsClient;

  @BeforeEach
  void setUp() {
    mockHttpClient = Mockito.mock(CloseableHttpClient.class);
    mockResponse = Mockito.mock(CloseableHttpResponse.class);
    orsClient = new OpenRouteServiceClient(mockHttpClient);
  }

  @Test
  void testGetCityCoordinates() throws Exception {
    // Arrange-------------------------
    String mockJsonResponse = "{ \"features\": [ { \"geometry\": { \"coordinates\": [ 9.993682, 53.551086 ] } } ] }";
    HttpEntity mockEntity = new StringEntity(mockJsonResponse);
    when(mockResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);
    // Act------------------------------
    City city = orsClient.getCityCoordinates("Hamburg");

    // Assert---------------------------
    assertNotNull(city);
    assertEquals(53.551086, city.getLat());
    assertEquals(9.993682, city.getLon());
  }

  @Test
  void testGetDistance() throws Exception {
    // Arrange-------------------------
    String mockJsonResponse = "{ \"distances\": [ [ 0, 288000 ] ] }"; // Distance in meters
    HttpEntity mockEntity = new StringEntity(mockJsonResponse);
    when(mockResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockResponse);

    City startCity = new City("Hamburg", 53.551086, 9.993682);
    City endCity = new City("Berlin", 52.520008, 13.404954);

    // Act------------------------------
    double distance = orsClient.getDistance(startCity, endCity);

    // Assert---------------------------
    assertEquals(288000, distance); // Distance in meters
  }

  @Test
  void testGetCityCoordinates_ApiFailure() throws Exception {
    // Arrange-----------------
    when(mockHttpClient.execute(any(HttpGet.class))).thenThrow(new IOException("API failure"));
    // Act ---------------------
    Exception exception = assertThrows(
        ApiException.class, () -> {
      orsClient.getCityCoordinates("Hamburg");
    });

    // Assert---------------------------
    assertEquals("API failure", exception.getCause().getMessage());
  }

  @Test
  void testGetDistance_ApiFailure() throws Exception {
    // Arrange ------------------
    when(mockHttpClient.execute(any(HttpPost.class))).thenThrow(new IOException("API failure"));

    City startCity = new City("Hamburg", 53.551086, 9.993682);
    City endCity = new City("Berlin", 52.520008, 13.404954);

    // Act------------------
    Exception exception = assertThrows(ApiException.class, () -> {
      orsClient.getDistance(startCity, endCity);
    });

    // Assert ---------------------
    assertEquals("API failure", exception.getCause().getMessage());
  }
}
