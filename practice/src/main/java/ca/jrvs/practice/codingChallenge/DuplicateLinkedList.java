package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateLinkedList {

  public static void main(String[] args) {
    LinkedJList<Integer> list = new LinkedJList<>();
    list.add(1);
    list.add(2);
    list.add(2);
    list.add(2);
    list.add(6);
    list.add(8);
    list.add(8);
    list.add(1);
    list.add(8);
    System.out.println(Arrays.toString(list.toArray()));
    list.removeDuplicate();
    System.out.println(Arrays.toString(list.toArray()));
  }
}
