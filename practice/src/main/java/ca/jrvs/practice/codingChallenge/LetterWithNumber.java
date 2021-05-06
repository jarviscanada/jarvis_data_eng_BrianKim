package ca.jrvs.practice.codingChallenge;

public class LetterWithNumber {

  public static void main(String[] args) {
    LetterWithNumber solution = new LetterWithNumber();
    System.out.println(solution.printWithNumber("abcdee"));
  }

  public String printWithNumber(String str) {
    StringBuilder strBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++)
      strBuilder.append(str.charAt(i)).append(String.valueOf((int) str.charAt(i)));
    return strBuilder.toString();
  }
}
