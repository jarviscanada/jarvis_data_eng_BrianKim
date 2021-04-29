package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/LinkedList-Cycle-8ea0e4a976484bcdbb5d136861d274b4
 */
public class LinkedListCycle {
  protected static class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
      val = x;
      next = null;
    }
  }

  /**
   * Big O: O(n)
   * Because only two pointers are used and iterates over n number of nodes in LinkedList.
   */
  public boolean hasCycle(ListNode head) {
    if (head == null)
      return false;

    ListNode slow, fast;
    slow = fast = head;

    while (true) {
      slow = slow.next;

      if (fast.next != null)
        fast = fast.next.next;

      if (slow == null || fast == null)
        return false;

      if (slow == fast)
        return true;
    }
  }
}
