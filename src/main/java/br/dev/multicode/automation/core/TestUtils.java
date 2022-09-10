package br.dev.multicode.automation.core;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

  public static Map<String, Object> createANewBeer() {
    Map<String, Object> beer = new HashMap<>();
    beer.put("name", "Beer Test " + System.nanoTime());
    beer.put("alcoholContent", 5.5);
    beer.put("ingredients", "Wheat and orange peel");
    beer.put("price", 11.90);
    return beer;
  }

  public static Map<String, Object> createAInvalidBeer() {
    Map<String, Object> beer = new HashMap<>();
    beer.put("name", null);
    beer.put("alcoholContent", null);
    beer.put("ingredients", null);
    beer.put("price", null);
    return beer;
  }

  public static Map<String, Object> updateABeer() {
    Map<String, Object> beer = new HashMap<>();
    beer.put("name", "Beer Updated " + System.nanoTime());
    beer.put("alcoholContent", 7.0);
    beer.put("ingredients", "Barley and wheat hops");
    beer.put("price", 19.90);
    return beer;
  }
}
