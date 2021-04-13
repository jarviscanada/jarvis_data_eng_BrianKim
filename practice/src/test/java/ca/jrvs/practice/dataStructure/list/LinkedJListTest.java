package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedJListTest {

  @Test
  public void add() {
    JList<String> list = new LinkedJList<>();
    list.add("first");
    list.add("second");
    list.add("third");

    assertEquals(3, list.size());
  }

  @Test
  public void toArray() {
    JList<String> list = new LinkedJList<>();
    list.add("first");
    list.add("second");
    list.add("third");

    assertArrayEquals(new String[]{"first", "second", "third"}, list.toArray());
  }

  @Test
  public void size() {
    JList<Integer> list = new LinkedJList<>();
    list.add(3);
    list.add(5);
    list.add(7);
    list.add(9);
    assertEquals(4, list.size());
  }

  @Test
  public void isEmpty() {
    JList<Integer> list = new LinkedJList<>();
    assertTrue(list.isEmpty());

    list.add(3);
    assertFalse(list.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> list = new LinkedJList<>();
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");

    assertEquals(3, list.indexOf("four"));
    assertEquals(2, list.indexOf("three"));
  }

  @Test
  public void contains() {
    JList<String> list = new LinkedJList<>();
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");

    assertTrue(list.contains("three"));
    assertFalse(list.contains("five"));
  }

  @Test
  public void get() {
    JList<String> list = new LinkedJList<>();
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");

    assertEquals("three", list.get(2));
  }

  @Test
  public void remove() {
    JList<String> list = new LinkedJList<>();
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");

    String removed = list.remove(1);
    assertEquals("two", removed);
    assertEquals(3, list.size());

    String removed2 = list.remove(2);
    list.remove(1);
    list.remove(0);

    assertTrue(list.isEmpty());

    list.add("hello");
    list.remove(0);
    assertTrue(list.isEmpty());
  }

  @Test
  public void clear() {
    JList<String> list = new LinkedJList<>();
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");

    assertEquals(4, list.size());

    list.clear();
    assertEquals(0, list.size());
  }
}