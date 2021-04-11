package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidPalindromeTest {

  @Test
  public void isValidPalindromeTwoPointers() {
    ValidPalindrome pChecker = new ValidPalindrome();
    assertFalse(pChecker.isValidPalindromeTwoPointers("abcde123"));
    assertTrue(pChecker.isValidPalindromeTwoPointers("kayak"));
    assertTrue(pChecker.isValidPalindromeTwoPointers("321neveroddoreven123"));
  }

  @Test
  public void isValidPalindromeRecur() {
    ValidPalindrome pChecker = new ValidPalindrome();
    assertFalse(pChecker.isValidPalindromeRecur("abdce123"));
    assertTrue(pChecker.isValidPalindromeRecur("kayak"));
    assertTrue(pChecker.isValidPalindromeRecur("321neveroddoreven123"));
  }
}