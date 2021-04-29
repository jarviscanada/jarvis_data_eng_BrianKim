package ca.jrvs.practice.dataStructure.list;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LinkedJList<E> implements JList<E> {
  protected static class Node<E> {
    public E item;
    public Node<E> next;
    public Node<E> prev;

    public Node(Node<E> prev, E element, Node<E> next) {
      this.item = element;
      this.prev = prev;
      this.next = next;
    }
  }

  protected Node<E> first;
  protected Node<E> last;
  protected int size;

  /**
   * Appends the specified element to the end of this list (optional operation).
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt> (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("This list does not permit null elements");

    if (isEmpty()) {
      Node<E> newNode = new Node<>(null, e, null);
      first = newNode;
      last = newNode;
    } else {
      Node<E> newNode = new Node<>(last, e, null);
      last.next = newNode;
      last = newNode;
    }
    size++;
    return true;
  }

  /**
   * Returns an array containing all of the elements in this list in proper sequence (from first to
   * last element).
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all of the elements in this list in proper sequence
   */
  @Override
  public Object[] toArray() {
    Object[] listArray = new Object[size];
    Node<E> curr = first;
    int index = 0;
    while (curr != null) {
      listArray[index] = curr.item;
      index++;
      curr = curr.next;
    }
    return listArray;
  }

  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index <tt>i</tt> such
   * that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param o
   */
  @Override
  public int indexOf(Object o) {
    Node<E> curr = first;
    int index = 0;
    while (curr != null) {
      if (curr.item.equals(o))
        return index;
      index++;
      curr = curr.next;
    }
    return -1;
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element. More formally, returns
   * <tt>true</tt> if and only if this list contains at least one element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * @param o element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean contains(Object o) {
    if (o == null)
      throw new NullPointerException("Null pointer exception has occurred: argument cannot be null");

    Node<E> curr = first;
    int index = 0;
    while (curr != null) {
      if (curr.item.equals(o))
        return true;
      index++;
      curr = curr.next;
    }
    return false;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range (<tt>index &lt; 0 || index &gt;=
   *                                   size()</tt>)
   */
  @Override
  public E get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("The index is out of range");

    Node<E> curr = first;
    int i = 0;
    while (curr != null) {
      if (i == index)
        return curr.item;
      i++;
      curr = curr.next;
    }
    return null;
  }

  /**
   * Removes the element at the specified position in this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices).
   *
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public E remove(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("The index is out of range");

    E removed = null;
    Node<E> curr = first;
    int i = 0;
    while (curr != null) {
      if (i == index){
        removed = curr.item;
        if (curr.prev == null)
          first = curr.next;
        else
          curr.prev.next = curr.next;

        if (curr.next == null)
          last = curr.prev;
        else
          curr.next.prev = curr.prev;
      }
      i++;
      curr = curr.next;
    }
    size--;
    return removed;
  }

  public void removeDuplicate() {
    HashSet<Integer> map = new HashSet<>();
    map.add(-1);
    Node<E> curr = first;
    while (curr != null) {
      if (!map.contains(((Integer) curr.item))) {
        map.add((Integer) curr.item);
      } else {
        if (curr.prev == null)
          first = curr.next;
        else
          curr.prev.next = curr.next;

        if (curr.next == null)
          last = curr.prev;
        else
          curr.next.prev = curr.prev;

        size--;
      }
      curr = curr.next;
    }
  }

  /**
   * Removes all of the elements from this list (optional operation). The list will be empty after
   * this call returns.
   */
  @Override
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }
}
