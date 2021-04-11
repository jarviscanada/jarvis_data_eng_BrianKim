package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-760f2bdd59944d9dbae57cf2e61587a3
 */
public class ValidPalindrome {

  /**
   * Big O: O(n) Because the method runs a for-loop which iterates n/2 times which is asymptotically
   * O(n)
   */
  public boolean isValidPalindromeTwoPointers(String word) {
    int start = 0;
    int end = word.length() - 1;
    while (start < end) {
      if (word.charAt(start) != word.charAt(end)) {
        return false;
      }
      start++;
      end--;
    }
    return true;
  }

  /**
   * Big O: O(n) Because it recursively iterates each time with a smaller string (-2 indexes)
   */
  public boolean isValidPalindromeRecur(String word) {
    return recursiveHelper(word.toCharArray());
  }

  public boolean recursiveHelper(char[] splitWord) {
    // base case
    if (splitWord.length == 1) {
      return true;
    }
    if (splitWord.length == 2) {
      return splitWord[0] == splitWord[1];
    }

    if (splitWord[0] == splitWord[splitWord.length - 1]) {
      char[] newArr = Arrays.copyOfRange(splitWord, 1, splitWord.length - 1);
      return recursiveHelper(newArr);
    } else {
      return false;
    }
  }
}
