package ca.jrvs.practice.codingChallenge;


public class ReverseLinkedList {

  protected static class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

  public static void main(String[] args) {
    ReverseLinkedList solution = new ReverseLinkedList();
    ListNode head = new ListNode(1, new ListNode(2, null));
    ListNode curr = solution.reverseListRecur(head);
    while (curr != null) {
      System.out.println(curr.val);
      curr = curr.next;
    }
  }

  public ListNode reverseList(ListNode head) {
    ListNode prev, curr;
    curr = prev = head;
    while (curr != null) {
      if (prev == curr)
        curr = curr.next;
      else {
        prev.next = curr.next;
        curr.next = head;
        head = curr;
        curr = prev.next;
      }
    }
    return head;
  }

  public ListNode reverseListRecur(ListNode head) {
    if (head == null)
      throw new IllegalArgumentException("head of list is empty");
    ListNode prev, curr;
    curr = prev = head;
    return reverseHelper(prev, curr, head);
  }

  public ListNode reverseHelper(ListNode prev, ListNode curr, ListNode head) {
    if (curr == null)
      return head;
    if (prev == curr) {
      curr = curr.next;
    } else {
      prev.next = curr.next;
      curr.next = head;
      head = curr;
      curr = prev.next;
    }
    return reverseHelper(prev, curr, head);
  }
}
