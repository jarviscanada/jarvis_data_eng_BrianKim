package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-834bb4f3892447b38e12df46a4a906aa
 */
public class StackUsingTwoQueue<T> {
  private Queue<T> q1;
  private Queue<T> q2;
  private boolean bool;

  public static void main(String[] args) {
    StackUsingTwoQueue<Integer> stack = new StackUsingTwoQueue<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    System.out.println(stack.empty());
    System.out.println(stack.top());
    System.out.println(stack.pop());
    System.out.println(stack.top());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
  }

  public StackUsingTwoQueue() {
    q1 = new LinkedList<>();
    q2 = new LinkedList<>();
    bool = true;
  }

  /**
   * Big O: O(1)
   */
  public void push(T element) {
    if (bool)
      q1.add(element);
    else
      q2.add(element);
  }

  /**
   * Big O: O(n)
   */
  public T pop() {
    return removeLast();
  }

  /**
   * Big O: O(n)
   */
  public T top() {
    T element = removeLast();

    if (bool)
      q1.add(element);
    else
      q2.add(element);

    return element;
  }

  /**
   * Big O: O(n)
   */
  private T removeLast() {
    T element = null;
    if (bool) {
      while (q1.size() > 0) {
        element = q1.remove();
        if (q1.size() != 0) {
          q2.add(element);
        }
      }
      bool = false;
    } else {
      while (q2.size() > 0) {
        element = q2.remove();
        if (q2.size() != 0)
          q1.add(element);
      }
      bool = true;
    }
    return element;
  }

  /**
   * Big O: O(1)
   */
  public boolean empty() {
    if (bool)
      return q1.size() == 0;
    else
      return q2.size() == 0;
  }
}
