package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Middle-of-the-Linked-List-9182931f59164bdf9fbdadeb81e2edb1
 */
public class MiddleOfLinkedList {

  protected static class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

  public static void main(String[] args) {
    MiddleOfLinkedList solution = new MiddleOfLinkedList();
    ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, null))))));
    ListNode middle = solution.middleNodePointer(head);
    System.out.println(middle.val);
  }

  /**
   * Big O: O(n + n / 2) -> O(n)
   * Because it iterates whole list once and again by n/2 times
   */
  public ListNode middleNode(ListNode head) {
    int count = 0;
    ListNode curr = head;
    while (curr != null) {
      count++;
      curr = curr.next;
    }

    curr = head;
    int m = count / 2 + 1;
    for (int  i = 0; i < m - 1; i++) {
      curr = curr.next;
    }
    return curr;
  }

  /**
   * Big O: O(n)
   * Because you only use two pointers across the list. O(n/2) -> O(n)
   */
  public ListNode middleNodePointer(ListNode head) {
    ListNode slow, fast;
    slow = fast = head;

    while (true) {
      if (fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
      }
      if (fast == null || fast.next == null)
        return slow;
    }
  }
}
