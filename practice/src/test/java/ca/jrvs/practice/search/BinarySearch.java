package ca.jrvs.practice.search;

import java.util.Optional;

public class BinarySearch {

  /**
   * find the target index in a sorted array
   * @param arr input array is sorted
   * @param target value to be searched
   * @param <E>
   * @return target index or Optional.empty() if not found
   */
  public <E> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
    return binarySearchHelper(0, arr.length-1, arr, target);
  }

  public <E> Optional<Integer> binarySearchHelper(int i, int j, E[] arr, E target) {
    int mid  = (j - i) / 2;

    if (arr[mid].equals(target))
      return Optional.of(mid);

    // if (arr[mid].compareTo(target))

    return null;
  }
}
