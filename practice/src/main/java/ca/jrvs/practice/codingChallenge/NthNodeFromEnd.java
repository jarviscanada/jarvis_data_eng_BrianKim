package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Nth-Node-From-End-of-LinkedList-8dd36f805e3b41e79c3ee655cf90a125
 */
public class NthNodeFromEnd {

  protected static class ListNode {
    int val;
    ListNode next;
    ListNode() {};
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

  public static void main(String[] args) {
    ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
    NthNodeFromEnd solution = new NthNodeFromEnd();
    ListNode output = solution.removeNthFromEnd(head, 1);
    while (head != null) {
      System.out.println(head.val);
      head = head.next;
    }
  }

  /**
   * Big O: O(n)
   * Because it only uses two Node pointers and one way iteration.
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    int x = -n;
    ListNode nth = null;
    ListNode curr = head;

    while (true) {
      if (x == 0)
        nth = head;
      if (curr.next != null) {
        curr = curr.next; x++;
      } else
        break;
      if (nth != null)
        nth = nth.next;
    }

    ListNode output = nth.next;
    nth.next = nth.next.next;
    return output;
  }
}
