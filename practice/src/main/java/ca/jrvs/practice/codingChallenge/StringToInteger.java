package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/String-to-Integer-atoi-5091bcd6058343a7b0253b9146ac5067
 */
public class StringToInteger {

  /**
   * Big O: O(n) parseInt method will convert each char from String s into int and there are n
   * number of char
   */
  public int atoiOne(String s) throws NumberFormatException {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Failed to parse String to Int: ", e);
    }
  }

  /**
   * Big O: O(n) this will split String s into an char array, charArr which then is iterated to
   * place modulo of 48 into an int array in reverse. O(n) Then combineDigits method is called which
   * iterates the int array to produce result in O(n)
   */
  public int atoiTwo(String s) throws RuntimeException {
    char[] charArr = s.toCharArray();
    int[] intArr = new int[charArr.length];
    for (int i = 0; i < charArr.length; i++) {
      intArr[charArr.length - 1 - i] = charArr[i] % 48;
      if (charArr[i] < 48 || charArr[i] > 57) {
        throw new RuntimeException("Failed to parse String to Int: Check format");
      }
    }
    return combineDigits(intArr);
  }

  public int combineDigits(int[] arr) {
    int result = 0;
    for (int i = 0; i < arr.length; i++) {
      result += (Math.pow(10, i)) * arr[i];
    }
    return result;
  }
}
