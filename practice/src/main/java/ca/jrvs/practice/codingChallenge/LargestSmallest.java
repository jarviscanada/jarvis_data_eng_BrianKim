package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ticket: https://www.notion.so/jarvisdev/Find-Largest-Smallest-8cac9db454cb47d0bd930da7fec9aaaf
 */
public class LargestSmallest {
  static int largest = 0;

  /**
   * Big O: O(n)
   * Iterate n times by for loop
   */
  public int largestNum(int[] arr) {
      int largest = arr[0];
      for (int num : arr) {
        if (num > largest)
          largest = num;
      }
      return largest;
  }

  /**
   * Big O: O(n)
   * Iterate n times by stream api
   */
  public int largestNumStream(int[] arr) {
    largest = arr[0];
    Arrays.stream(arr).forEach(num -> {
      if (num > largest)
        largest = num;
    });
    return largest;
  }

  /**
   * Big O: O(n)
   * This method iterates over the entire collection (n)
   */
  public int largestNumJavaAPI(Integer[] arr) {
    List<Integer> list = Arrays.asList(arr);
    return Collections.max(list);
  }
}
