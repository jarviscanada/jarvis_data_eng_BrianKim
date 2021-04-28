package ca.jrvs.practice.dataStructure.stackQueue;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedJListDeque<E> extends LinkedJList<E> implements JDeque<E> {
  /**
   * This is equivalent enqueue operation in Queue ADT
   * <p>
   * Inserts the specified element into the queue represented by this deque (in other words, at the
   * tail of this deque) if it is possible to do so immediately without violating capacity
   * restrictions, returning {@code true} upon success and throwing an {@code IllegalStateException}
   * if no space is currently available.
   *
   * @param e the element to add
   * @return {@code true} (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this deque does not permit
   *                              null elements
   */
  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("This deque does not permit null elements");

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
   * This is equivalent dequeue operation in Queue ADT
   * <p>
   * Retrieves and removes the head of the queue represented by this deque (in other words, the
   * first element of this deque).
   *
   * @return the head of the queue represented by this deque
   * @throws NoSuchElementException if this deque is empty
   */
  @Override
  public E remove() {
    if (this.isEmpty())
      throw new NoSuchElementException("This deque is empty");

    E removed = first.item;

    // one element
    if (first.next == null) {
      first = null;
      last = null;
    } else {
      first.next.prev = null;
      first = first.next;
    }

    size--;
    return removed;
  }

  /**
   * Pops an element from the stack represented by this deque. In other words, removes and returns
   * the first element of this deque.
   *
   * @return the element at the front of this deque (which is the top of the stack represented by
   * this deque)
   * @throws NoSuchElementException if this deque is empty
   */
  @Override
  public E pop() {
    return remove();
  }

  /**
   * Pushes an element onto the stack represented by this deque (in other words, at the head of this
   * deque) if it is possible to do so immediately without violating capacity restrictions
   *
   * @param e the element to push
   * @throws NullPointerException if the specified element is null and this deque does not permit
   *                              null elements
   */
  @Override
  public void push(E e) {
    if (e == null)
      throw new NullPointerException("This stack does not permit null elements");

    if (this.isEmpty()) {
      Node<E> newNode = new Node<>(null, e, null);
      first = newNode;
      last = newNode;
    } else {
      Node<E> newNode = new Node<>(null, e, first);
      first.prev = newNode;
      first = newNode;
    }
    size++;
  }

  /**
   * Retrieves, but does not remove, the head of the queue represented by this deque (in other
   * words, the first element of this deque), or returns {@code null} if this deque is empty.
   *
   * @return the head of the queue represented by this deque, or {@code null} if this deque is empty
   */
  @Override
  public E peek() {
    return isEmpty() ?
        null : first.item;
  }
}
