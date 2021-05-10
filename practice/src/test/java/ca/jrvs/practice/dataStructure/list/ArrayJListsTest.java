package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;

public class ArrayJListsTest {

  @Test
  public void add() {
    JList<String> list = new ArrayJLists<>();

    assertEquals(0, list.size());
    list.add("one");
    list.add("two");
    list.add("three");
    list.add("four");
    list.add("five");
    list.add("six");
    list.add("seven");
    list.add("eight");
    list.add("nine");
    list.add("ten");
    list.add("eleven");
    list.add("twelve");

    assertEquals("one", list.get(0));
    assertEquals(12, list.size());
  }

  @Test
  public void toArray() {
    JList<Integer> list = new ArrayJLists<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    Integer[] intArr = new Integer[]{1,2,3,4};
    assertArrayEquals(intArr, list.toArray());
  }

  @Test
  public void size() {
    JList<Integer> list = new ArrayJLists<>();
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    assertEquals(list.size(), 4);
  }

  @Test
  public void isEmpty() {
    JList<String> list = new ArrayJLists<>();
    assertTrue(list.isEmpty());

    list.add("first");
    assertFalse(list.isEmpty());
  }

  @Test
  public void indexOf() {
    JList<String> list = new ArrayJLists<>();
    list.add("first");
    list.add("second");
    list.add("third");
    assertEquals(2, list.indexOf("third"));
  }

  @Test
  public void contains() {
    JList<String> list = new ArrayJLists<>();
    list.add("first");
    list.add("second");
    list.add("third");
    assertTrue(list.contains("second"));
    assertFalse(list.contains("fourth"));
  }

  @Test
  public void get() {
    JList<String> list = new ArrayJLists<>();
    list.add("first");
    list.add("second");
    list.add("third");
    assertEquals("second", list.get(1));
  }

  @Test
  public void remove() {
    JList<String> list = new ArrayJLists<>();
    list.add("first");
    list.add("second");
    list.add("third");
    String removed = list.remove(1);
    assertEquals(2, list.size());
    assertEquals("third", list.get(1));
    assertEquals(removed, "second");
  }

  @Test
  public void clear() {
    JList<String> list = new ArrayJLists<>();
    list.add("first");
    list.add("second");
    list.add("third");
    list.clear();

    assertEquals(0, list.size());
  }
}