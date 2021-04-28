package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-96165068131a4ba384caa04db5e31567
 */
public class QueueUsingStack<T> {

  public static void main(String[] args) {
    QueueUsingStack<Integer> queue = new QueueUsingStack<>();

    queue.push(1);
    queue.push(2);
    queue.push(3);
    queue.push(4);
    System.out.println(queue.empty());
    System.out.println(queue.peek());
    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.pop());
    System.out.println(queue.empty());
  }

  private Stack<T> s1;
  private Stack<T> s2;

  private T front;

  public QueueUsingStack() {
    s1 = new Stack<>();
    s2 = new Stack<>();
    front = null;
  }

  /**
   * Big O: O(n)
   * Because you have to pop all element from s1 and push to s2 which is 2n operations.
   */
  public void push(T element) {
    if (s1.isEmpty())
      s1.push(element);
    else {
      while (!s1.isEmpty())
        s2.push(s1.pop());
      s1.push(element);
      while (!s2.isEmpty())
        s1.push(s2.pop());
      front = s1.peek();
    }
  }

  /**
   * Big O: O(1)
   * Because the stack is already reversely organized from push step. Stack pop is O(1)
   */
  public T pop() {
    return s1.pop();
  }

  /**
   * Big O: O(1)
   * Same reason as pop()
   */
  public T peek() {
    return front;
  }

  /**
   * Big O: O(1)
   */
  public boolean empty() {
    return s1.isEmpty();
  }
}
