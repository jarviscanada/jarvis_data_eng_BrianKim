package ca.jrvs.practice.dataStructure.stackQueue;

import static org.junit.Assert.*;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import org.junit.Test;

public class LinkedJListDequeTest {

  @Test
  public void add() {
    LinkedJListDeque<String> deque = new LinkedJListDeque<>();
    assertEquals(0, deque.size());

    deque.add("first");
    deque.add("second");
    deque.add("third");
    assertEquals(3, deque.size());
    assertEquals("first", deque.get(0));
    assertEquals("second", deque.get(1));
    assertEquals("third", deque.get(2));
  }

  @Test
  public void remove() {
    JDeque<String> deque = new LinkedJListDeque<>();
    deque.add("first");
    deque.add("second");
    deque.add("third");

    String str = deque.remove();
    assertEquals("first", str);

    str = deque.remove();
    assertEquals("second", str);

    str = deque.remove();
    assertEquals("third", str);

    try {
      deque.remove();
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  @Test
  public void pop() {
    JDeque<String> stack = new LinkedJListDeque<>();
    stack.push("first");

    String removed = stack.pop();
    assertEquals("first", removed);

    stack.push("first");
    stack.push("second");
    stack.push("third");

    removed = stack.pop();
    assertEquals("third", removed);
    removed = stack.pop();
    assertEquals("second", removed);
    removed = stack.pop();
    assertEquals("first", removed);
  }

  @Test
  public void push() {
    JDeque<String> stack = new LinkedJListDeque<>();

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
    JDeque<String> stack = new LinkedJListDeque<>();
    stack.push("first");
    stack.push("second");
    stack.push("third");

    String str = stack.peek();
    assertEquals("third", str);
  }
}