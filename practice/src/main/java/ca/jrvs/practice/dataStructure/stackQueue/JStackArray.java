package ca.jrvs.practice.dataStructure.stackQueue;

import java.util.NoSuchElementException;

public class JStackArray<E> implements JStack<E> {
  transient Object[] elementData;
  private int top = -1;
  private static final int DEFAULT_SIZE = 10;

  JStackArray(int size) {
    elementData = new Object[size];
  }

  JStackArray() {
    this(DEFAULT_SIZE);
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
    if (top == -1)
      throw new NoSuchElementException("This stack is empty");

    E element = (E) elementData[top];
    top--;
    return element;
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
    if (top == elementData.length-1)
      throw new IndexOutOfBoundsException("The stack has already reached its capacity");

    elementData[++top] = e;
  }

  /**
   * Retrieves, but does not remove, the head of the queue represented by this deque (in other
   * words, the first element of this deque), or returns {@code null} if this deque is empty.
   *
   * @return the head of the queue represented by this deque, or {@code null} if this deque is empty
   */
  @Override
  public E peek() {
    return (E) elementData[top];
  }
}
