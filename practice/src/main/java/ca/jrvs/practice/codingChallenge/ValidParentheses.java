package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-32e31f8b82ea4f0bac1602ec45332af4
 */
public class ValidParentheses {

  public static void main(String[] args) {
    ValidParentheses solution = new ValidParentheses();
    System.out.println(solution.isValid("{[()]({})}"));
  }

  public boolean isValid(String s) {
    Map<Character, Character> pair = new HashMap<>();
    pair.put('}', '{');
    pair.put(']', '[');
    pair.put(')', '(');
    char[] arr = s.toCharArray();
    // {[]} -> }][{ ()[]{} {[]()}
    LinkedList<Character> stack = new LinkedList<>();
    for (char c : arr) {
      stack.add(c);
    }
    while (stack.size() > 0) {
      if (stack.getFirst().equals(pair.get(stack.getLast()))) {
        stack.removeFirst();
        stack.removeLast();
      } else {
        char k = stack.removeLast();
        if (!pair.get(k).equals(stack.getLast()))
          return false;
        stack.removeLast();
      }
    }
    return true;
  }
}
