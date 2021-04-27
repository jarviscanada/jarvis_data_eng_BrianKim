package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-c8178d271ee64ddd85e45d556b136c35
 */
public class FibonacciNumber {

  public static void main(String[] args) {
    FibonacciNumber solution = new FibonacciNumber();
    System.out.println(solution.fibDP(40));
  }

  /**
   * Big O: O(2 ^ n)
   * Exponential because within recursion tree, all sub trees and their nodes visited and
   * are computed recursively. This is inefficient. Because it will calculate the value again.
   */
  public int fib(int n) {
    if (n == 0)
      return 0;
    if (n == 1)
      return 1;
    return fib(n - 2) + fib(n - 1);
  }

  /**
   * Big O: O(n)
   * This is because using memo table and bottom-up approach, once solved fib numbers are saved in table and
   * used sequentially. Using this will achieve n number of iterations only.
   */
  public int fibDP(int n) {
    int[] memo = new int[n + 1];
    memo[0] = 0; memo[1] = 1;

    for (int i = 2; i <= n; i++) {
      memo[i] = memo[i - 2] + memo[i - 1];
    }
    return memo[n];
  }
}
