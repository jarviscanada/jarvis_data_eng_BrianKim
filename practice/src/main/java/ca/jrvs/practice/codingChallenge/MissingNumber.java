package ca.jrvs.practice.codingChallenge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket: https://www.notion.so/jarvisdev/Missing-Number-76d884e56a284464a986d3da266fcaf9
 */
public class MissingNumber {

  /**
   * Big O: O(n)
   * Because two for loops over n and O(1) space were used.
   */
  public int missingNumber(int[] nums) {
    int sum = 0;
    for (int i = nums.length; i >= 1; i--) {
      sum += i;
    }
    for (int num : nums) {
      sum -= num;
    }
    return sum;
  }

  /**
   * Big O: O(n)
   * Because one for loop over n and one set operations to get complement.
   * Uses O(n) space due to Set space.
   */
  public int missingNumberHashSet(int[] nums) {
    Set<Integer> expectSet = new HashSet<>();
    Set<Integer> actualSet = new HashSet<>();
    int missing = 0;
    for (int i = 0;  i <= nums.length; i++) {
      expectSet.add(i);
      if (i != nums.length)
        actualSet.add(nums[i]);
    }
    expectSet.removeAll(actualSet);
    return (Integer) expectSet.toArray()[0];
  }

  /**
   * Big O: O(n)
   * using Gauss formula for the arithmetic sum saves time. For efficient.
   * One for loop.
   */
  public int missingNumberGauss(int[] nums) {
    int sum = (nums.length)*(nums.length + 1)/2;
    for (int num : nums) {
      sum -= num;
    }
    return sum;
  }
}
