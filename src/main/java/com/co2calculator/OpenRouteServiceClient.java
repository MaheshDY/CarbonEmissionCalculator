package com.co2calculator;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.co2calculator.exceptions.ApiException;
import com.co2calculator.model.City;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class OpenRouteServiceClient {

  private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";
  private static final String DISTANCE_URL = "https://api.openrouteservice.org/v2/matrix/driving-car";
  private static final String API_KEY = System.getenv("ORS_TOKEN");

  private final CloseableHttpClient httpClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public OpenRouteServiceClient() {
    this.httpClient = HttpClients.createDefault();
  }

  public OpenRouteServiceClient(CloseableHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public City getCityCoordinates(String cityName) throws Exception {
    String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
    HttpGet request = new HttpGet(GEOCODE_URL + "?api_key=" + API_KEY + "&text=" + encodedCityName + "&layers=locality");
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      JsonNode rootNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
      JsonNode firstResult = rootNode.path("features").get(0);
      double lat = firstResult.path("geometry").path("coordinates").get(1).asDouble();
      double lon = firstResult.path("geometry").path("coordinates").get(0).asDouble();

      return new City(cityName, lat, lon);
    } catch (IOException e) {
      throw new ApiException("Failed to fetch city coordinates", e);
    }
  }

  public double getDistance(City startCity, City endCity) throws Exception {
    HttpPost request = new HttpPost(DISTANCE_URL);
    request.setHeader("Authorization", API_KEY);
    request.setHeader("Content-Type", "application/json");

    String payload = createDistancePayload(startCity, endCity);
    request.setEntity(new StringEntity(payload));

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      JsonNode rootNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
      return rootNode.path("distances").get(0).get(1).asDouble();
    } catch (IOException e) {
      throw new ApiException("Failed to calculate distance", e);
    }
  }

  private String createDistancePayload(City startCity, City endCity) throws Exception {
    ObjectNode payload = objectMapper.createObjectNode();

    ArrayNode locations = objectMapper.createArrayNode();
    locations.add(objectMapper.createArrayNode().add(startCity.getLon()).add(startCity.getLat()));
    locations.add(objectMapper.createArrayNode().add(endCity.getLon()).add(endCity.getLat()));

    payload.set("locations", locations);
    payload.putArray("metrics").add("distance");  // ["distance"]

    return objectMapper.writeValueAsString(payload);
  }
}
