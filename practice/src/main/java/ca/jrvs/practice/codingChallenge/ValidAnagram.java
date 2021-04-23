package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Anagram-e2073fa53c824a9290db64732397b99b
 */
public class ValidAnagram {

  public static void main(String[] args) {
    ValidAnagram solution = new ValidAnagram();
    System.out.println(solution.isAnagramTwo("listen", "silent"));
  }

  /**
   * Big O: O(nlogn)
   * Because for the two arrays, they are sorted and compared.
   */
  public boolean isAnagramOne(String s, String t) {
    char[] sArr = s.toCharArray();
    char[] tArr = t.toCharArray();
    Arrays.sort(sArr);
    Arrays.sort(tArr);
    return Arrays.equals(sArr, tArr);
  }

  /**
   * Big O: O(n)
   * Because it is only iterated by length of s. And counter table is of constant size
   */
  public boolean isAnagramTwo(String s, String t) {
    if (s.length() != t.length())
      return false;
    int[] counter = new int[26];

    for (int i = 0; i < s.length(); i++) {
      counter[s.charAt(i) - 'a']++;
      counter[t.charAt(i) - 'a']--;
    }
    for (int count : counter) {
      if (count != 0)
        return false;
    }
    return true;
  }
}
