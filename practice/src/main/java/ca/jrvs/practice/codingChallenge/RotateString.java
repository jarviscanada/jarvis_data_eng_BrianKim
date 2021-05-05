package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Rotate-String-28e9d399f512410aa32921020ca02ce2
 */
public class RotateString {

  public static void main(String[] args) {
    RotateString solution = new RotateString();
    System.out.println(solution.rotateString("abcde", "cdeab"));
  }


  /**
   * Big O: O(N^2)
   */
  public boolean rotateString(String A, String B) {
    return A.length() == B.length() && A.concat(A).contains(B);
  }
}
