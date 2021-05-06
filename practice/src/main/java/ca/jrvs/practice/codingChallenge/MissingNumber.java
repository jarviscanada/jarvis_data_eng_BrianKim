package ca.jrvs.practice.codingChallenge;

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
}
