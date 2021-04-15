package ca.jrvs.practice.dataStructure.tree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class JBSTreeTest {

  static class intComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
      return o1 - o2;
    }
  }

  JTree<Integer> tree;

  @Before
  public void setUp() {
    tree = new JBSTree<>(new intComparator());
    tree.insert(50);
    tree.insert(30);
    tree.insert(20);
    tree.insert(40);
    tree.insert(60);
    tree.insert(80);
  }

  @Test
  public void insert() {
    try {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void search() {
    assertEquals(40, tree.search(40).intValue());
    assertEquals(80, tree.search(80).intValue());
  }

  @Test
  public void remove() {

  }

  @Test
  public void preOrder() {
    List<Integer> list = tree.preOrder();

    assertEquals(50, list.get(0).intValue());
    assertEquals(30, list.get(1).intValue());
    assertEquals(20, list.get(2).intValue());
    assertEquals(40, list.get(3).intValue());
    assertEquals(60, list.get(4).intValue());
    assertEquals(80, list.get(5).intValue());
  }

  @Test
  public void inOrder() {
    List<Integer> list = tree.inOrder();

    assertEquals(20, list.get(0).intValue());
    assertEquals(30, list.get(1).intValue());
    assertEquals(40, list.get(2).intValue());
    assertEquals(50, list.get(3).intValue());
    assertEquals(60, list.get(4).intValue());
    assertEquals(80, list.get(5).intValue());
  }

  @Test
  public void postOrder() {
    List<Integer> list = tree.postOrder();

    assertEquals(20, list.get(0).intValue());
    assertEquals(40, list.get(1).intValue());
    assertEquals(30, list.get(2).intValue());
    assertEquals(80, list.get(3).intValue());
    assertEquals(60, list.get(4).intValue());
    assertEquals(50, list.get(5).intValue());
  }

  @Test
  public void findHeight() {
  }
}