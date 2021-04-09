package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CompareTwoMapsTest {

  @Test
  public void compareMaps() {
    CompareTwoMaps compareObject = new CompareTwoMaps();
    Map<String, String> m1 = new HashMap<>();
    Map<String, String> m2 = new HashMap<>();

    m1.put("1", "one");
    m1.put("2", "two");
    m1.put("3", "three");
    m1.put("4", "four");

    m2.put("1", "one");
    m2.put("2", "two");
    m2.put("3", "three");

    assertFalse(compareObject.compareMaps(m1, m2));

    m2.put("4", "four");

    assertTrue(compareObject.compareMaps(m1, m2));
  }
}