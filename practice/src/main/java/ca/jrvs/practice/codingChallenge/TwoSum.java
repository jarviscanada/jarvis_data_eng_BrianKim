package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/Two-Sum-d19268837fac445ca06a2c22de4d3b55
 */
public class TwoSum {

  public static void main(String[] args) {
    TwoSum solution = new TwoSum();
    int[] output = solution.twoSumMap(new int[]{2, 7, 11, 15}, 18);
    System.out.println(Arrays.toString(output));
  }

  /**
   * Big O: O(n^2)
   * Because of nested for loop. It will compare each number against every number in brute force way.
   */
  public int[] twoSumBrute(int[] nums, int target) {
    int[] output = new int[2];
    for (int i = 0; i < nums.length; i++) {
      for (int j = i; j < nums.length; j++) {
        if (i != j && nums[i] + nums[j] == target) {
          output[0] = i;
          output[1] = j;
          return output;
        }
      }
    }
    return output;
  }

  /**
   * Big O: O(n)
   * Because of the additional Map space, the search time is reduced to O(1).
   */
  public int[] twoSumMap(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      map.put(nums[i], i);
    }
    for (int i = 0; i < nums.length; i++) {
      int remainder = target - nums[i];
      if (map.containsKey(remainder) && map.get(remainder) != i)
        return new int[]{i, map.get(remainder)};
    }
    throw new IllegalArgumentException("No solution");
  }
}
