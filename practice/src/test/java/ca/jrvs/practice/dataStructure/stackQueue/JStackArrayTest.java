package ca.jrvs.practice.dataStructure.stackQueue;

import static org.junit.Assert.*;

import org.junit.Test;

public class JStackArrayTest {

  @Test
  public void pop() {
    JStack<String> stack = new JStackArray<>();
    stack.push("first");
    stack.push("second");
    stack.push("third");

    String str = stack.pop();
    assertEquals("third", str);

    str = stack.pop();
    assertEquals("second", str);
  }

  @Test
  public void push() {
    JStack<String> stack = new JStackArray<>();
    try {
      stack.push("first");
      stack.push("second");
      stack.push("third");
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void peek() {
    JStack<String> stack = new JStackArray<>();
    stack.push("first");
    stack.push("second");
    stack.push("third");

    String str = stack.peek();
    assertEquals("third", str);

    str = stack.peek();
    assertEquals("third", str);
  }
}