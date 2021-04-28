package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-834bb4f3892447b38e12df46a4a906aa
 */
public class StackUsingOneQueue<T> {

  public static void main(String[] args) {
    StackUsingOneQueue<Integer> stack = new StackUsingOneQueue<>();
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
    System.out.println(stack.empty());
  }

  private Queue<T> queue;
  private T top = null;

  public StackUsingOneQueue() {
    queue = new LinkedList<>();
  }

  /**
   * Big O: O(n)
   */
  public void push(T element) {
    queue.add(element);
    for (int i = 0; i < queue.size() - 1; i++)
      queue.add(queue.remove());
    top = element;
  }

  /**
   * Big O: O(1)
   */
  public T pop() {
    return queue.remove();
  }

  /**
   * Big O: O(1)
   */
  public T top() {
    return top;
  }

  /**
   * Big O: O(1)
   */
  public boolean empty() {
    return queue.size() == 0;
  }
}
