package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;

public class ArrayJLists<E> implements JList<E> {

  /**
   * Default Initial capacity
   */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * The array buffer into which the elements of the ArrayList are stored.
   * The capacity of the ArrayList is the length of this array buffer.
   */
  transient Object[] elementData;

  /**
   * The size of the ArrayList
   */
  private int size;

  public ArrayJLists(int initialCapacity) {
    if (initialCapacity > 0) {
      this.elementData = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
  }

  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public ArrayJLists() {
    this(DEFAULT_CAPACITY);
  }

  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("This list does not allow null elements");
    if (size == elementData.length) {
      Object[] newElementData = new Object[elementData.length * 2];
      for (int i = 0; i < elementData.length; i++) {
        newElementData[i] = elementData[i];
      }
      elementData = newElementData;
    }
    elementData[size] = e;
    size++;
    return true;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(Object o) {
    for (int i = 0; i < size; i++) {
      if (elementData[i].equals(o))
        return i;
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    if (o == null)
      throw new NullPointerException("This method doesn't allow null parameter");

    for (int i = 0; i < size; i++) {
      if (elementData[i].equals(o))
        return true;
    }
    return false;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("The index is out of range");
    return (E) elementData[index];
  }

  @Override
  public E remove(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("The index is out of range");

    E removed = (E) elementData[index];
    for (int i = index; i <= size - 2; i++)
      elementData[i] = elementData[i+1];

    elementData[size-1] = null;
    size--;

    return removed;
  }

  @Override
  public void clear() {
    for (Object element : elementData)
      element = null;
    size = 0;
  }
}
