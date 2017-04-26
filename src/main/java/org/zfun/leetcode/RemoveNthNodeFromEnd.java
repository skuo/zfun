package org.zfun.leetcode;

/** Given a linked list, remove the nth node from the end of list and return its head.

For example,

   Given linked list: 1->2->3->4->5, and n = 2.

   After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:
Given n will always be valid.
Try to do this in one pass.
*/
public class RemoveNthNodeFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode curr = head;
        ListNode targetPrev = head;
        int i = 1;

        while (curr.next != null) {
            // move to the next node
            curr = curr.next;
            i++;
            if (i > n+1) {
                targetPrev = targetPrev.next;
            }
        }
        
        // at the last node
        if (i == n) {
            // removing the head node
            head = head.next;
        } else {
            // removing a node in the middle
            targetPrev.next = targetPrev.next.next;
        }
        
        return head;
    }

    public ListNode constructNodes(int[] ints) {
        ListNode head = null, curr = null;
        int count = 0;
        for (int x: ints) {
            ListNode node = new ListNode(x);
            if (count == 0) {
                head = node;
            } else {
                curr.next = node;
            }
            curr = node;
            count++;
        }
        return head;
    }
    
    public void printNodes(ListNode head) {
        ListNode curr = head;
        System.out.print("[");
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        RemoveNthNodeFromEnd obj = new RemoveNthNodeFromEnd();
        ListNode head = obj.constructNodes(new int[] {1,2,3,4,5});
        obj.printNodes(head);        
        head = obj.removeNthFromEnd(head, 2);
        obj.printNodes(head);
        System.out.println();

        head = obj.constructNodes(new int[] {1});
        obj.printNodes(head);        
        head = obj.removeNthFromEnd(head, 1);
        obj.printNodes(head);
        System.out.println();

        head = obj.constructNodes(new int[] {1,2,3,4,5});
        obj.printNodes(head);        
        head = obj.removeNthFromEnd(head, 1);
        obj.printNodes(head);
        System.out.println();

        head = obj.constructNodes(new int[] {1,2,3,4,5});
        obj.printNodes(head);        
        head = obj.removeNthFromEnd(head, 5);
        obj.printNodes(head);
        System.out.println();
    }
    
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
