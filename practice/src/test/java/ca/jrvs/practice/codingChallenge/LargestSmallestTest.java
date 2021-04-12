package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class LargestSmallestTest {

  @Test
  public void largestNum() {
    LargestSmallest main = new LargestSmallest();
    int result = main.largestNum(new int[]{1, 6, 3, 6, 8, 32, 23, 34, 4, 64, 23});
    assertEquals(64, result);
  }

  @Test
  public void largestNumStream() {
    LargestSmallest main = new LargestSmallest();
    int result = main.largestNumStream(new int[]{1, 6, 3, 6, 8, 32, 23, 34, 4, 64, 23});
    assertEquals(64, result);
  }

  @Test
  public void largestNumJavaAPI() {
    LargestSmallest main = new LargestSmallest();
    int result = main.largestNumJavaAPI(new Integer[]{1, 6, 3, 6, 8, 32, 23, 34, 4, 64, 23});
  }
}