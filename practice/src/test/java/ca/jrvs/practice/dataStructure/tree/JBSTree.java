package ca.jrvs.practice.dataStructure.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JBSTree<E> implements JTree<E> {

  /**
   * The comparator used to maintain order in this tree map
   * Comparator cannot be null
   */
  private final Comparator<E> comparator;
  private Node<E> root = null;

  public JBSTree(Comparator<E> comparator) {
    this.comparator = comparator;
  }

  /**
   * Insert an object into the tree.
   *
   * @param object item to be inserted
   * @return inserted item
   * @throws IllegalArgumentException if the object already exists
   */
  @Override
  public E insert(E object) {
    return insertRecur(root, object);
  }

  public E insertRecur(Node<E> root, E object) {
    if (this.root == null) {
      this.root = new Node<>(object, null);
      return this.root.getValue();
    }
    if (comparator.compare(root.getValue(), object) > 0) {
      if (root.getLeft() == null) {
        Node<E> newNode = new Node<>(object, root);
        root.setLeft(newNode);
        return root.getValue();
      } else {
        return insertRecur(root.getLeft(), object);
      }
    } else if (comparator.compare(root.getValue(), object) < 0) {
      if (root.getRight() == null) {
        Node<E> newNode = new Node<>(object, root);
        root.setRight(newNode);
        return root.getValue();
      } else {
        return insertRecur(root.getRight(), object);
      }
    } else {
      throw new IllegalArgumentException("This object already exists");
    }
  }

  /**
   * Search and return an object, return null if not found
   *
   * @param object to be found
   * @return the object if exists or null if not
   */
  @Override
  public E search(E object) {
    return searchRecur(root, object);
  }

  public E searchRecur(Node<E> root, E object) {
    if (root == null)
      return null;
    else if (root.value.equals(object))
      return root.value;

    if (comparator.compare(root.value, object) > 0) {
      return searchRecur(root.left, object);
    } else if (comparator.compare(root.value, object) < 0)
      return searchRecur(root.right, object);
    else
      return root.value;
  }

  /**
   * Remove an object from the tree.
   *
   * @param object to be removed
   * @return removed object
   * @throws IllegalArgumentException if the object not exists
   */
  @Override
  public E remove(E object) {
    return null;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in pre-order
   */
  @Override
  public List<E> preOrder() {
    List<E> list = new ArrayList<>();
    preOrderRecur(root, list);
    return list;
  }

  public void preOrderRecur(Node<E> root, List<E> list) {
    if (root != null) {
      list.add(root.value);
      preOrderRecur(root.left, list);
      preOrderRecur(root.right, list);
    }
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in-order
   */
  @Override
  public List<E> inOrder() {
    List<E> list = new ArrayList<>();
    inOrderRecur(root, list);
    return list;
  }

  public void inOrderRecur(Node<E> root, List<E> list) {
    if (root != null) {
      inOrderRecur(root.left, list);
      list.add(root.value);
      inOrderRecur(root.right, list);
    }
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects pre-order
   */
  @Override
  public List<E> postOrder() {
    List<E> list = new ArrayList<>();
    postOrderRecur(root, list);
    return list;
  }

  public void postOrderRecur(Node<E> root, List<E> list) {
    if (root != null) {
      postOrderRecur(root.left, list);
      postOrderRecur(root.right, list);
      list.add(root.value);
    }
  }

  /**
   * traverse through the tree and find out the tree height
   *
   * @return height
   * @throws NullPointerException if the BST is empty
   */
  @Override
  public int findHeight() {
    return 0;
  }

  static final class Node<E> {

    E value;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    public Node(E value, Node<E> parent) {
      this.value = value;
      this.parent = parent;
    }

    public E getValue() {
      return value;
    }

    public void setValue(E value) {
      this.value = value;
    }

    public Node<E> getLeft() {
      return left;
    }

    public void setLeft(Node<E> left) {
      this.left = left;
    }

    public Node<E> getRight() {
      return right;
    }

    public void setRight(Node<E> right) {
      this.right = right;
    }

    public Node<E> getParent() {
      return parent;
    }

    public void setParent(Node<E> parent) {
      this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return Objects.equals(getValue(), node.getValue()) &&
          Objects.equals(getLeft(), node.getLeft()) &&
          Objects.equals(getRight(), node.getRight()) &&
          Objects.equals(getParent(), node.getParent());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue(), getLeft(), getRight(), getParent());
    }
  }
}
