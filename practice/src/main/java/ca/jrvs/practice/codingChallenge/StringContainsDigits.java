package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Check-if-a-String-contains-only-digits-bbdf67d8cc924736a2032273601cc81a
 */
public class StringContainsDigits {

  /**
   * Big O: O(N)
   */
  public boolean containsDigitsASCII(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) < 48 || 57 < str.charAt(i))
        return false;
    }
    return true;
  }

  /**
   * Big O: O(N)
   */
  public boolean containsDigitsJavaAPI(String str) {
    try {
      Integer.valueOf(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Big O: O(N)
   */
  public boolean containsDigitsRegex(String str) {
    return str.matches("[0-9]+");
  }
}
