package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-763dc27b335d472dbe2085ff8daaf3fe
 */
public class CompareTwoMaps {

  /**
   * Big O: O(n) Because maps take O(1) time to index or get(). And doing that for n total elements
   * takes O(n)
   */
  public <K, V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2) {
    return m1.equals(m2);
  }
}
