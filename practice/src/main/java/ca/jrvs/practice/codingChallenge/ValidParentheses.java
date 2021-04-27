package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-32e31f8b82ea4f0bac1602ec45332af4
 */
public class ValidParentheses {

  public static void main(String[] args) {
    ValidParentheses solution = new ValidParentheses();
    System.out.println(solution.isValid("[]"));
  }

  /**
   * Big O: O(n)
   * Because for loop is used with length of arr.length.
   * Also Map and Stack operations are O(1)
   */
  public boolean isValid(String s) {
    Map<Character, Character> pair = new HashMap<>();
    pair.put('}', '{');
    pair.put(']', '[');
    pair.put(')', '(');
    char[] arr = s.toCharArray();
    // {[]} -> }][{ ()[]{} {[]()}
    Stack<Character> stack = new Stack<>();

    for (int i = 0; i < arr.length; i++) {
      if (pair.containsValue(arr[i]))
        stack.push(arr[i]);
      else if (pair.containsKey(arr[i])) {
        if (stack.empty())
          return false;
        else if (stack.peek() == pair.get(arr[i]))
          stack.pop();
      }
    }
    return stack.size() == 0;
  }
}
