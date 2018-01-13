package org.zfun.leetcode;

public class ReverseNodesKGroup {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        // validate all inputs: head and k
        if (k < 2)
            return head;

        ListNode curr = head, tail = null;
        ListNode[] nodes = new ListNode[k];
        int numReverses = 0;
        int count = 0;
        while (curr != null) {
            nodes[count] = curr;
            count++;
            curr = curr.next;
            if (count == k) {
                // reverse
                for (int i = k - 1; i > 0; i--) {
                    nodes[i].next = nodes[i - 1];
                }
                nodes[0].next = curr;

                if (numReverses == 0)
                    head = nodes[k - 1];
                else
                    tail.next = nodes[k - 1];
                tail = nodes[0];

                count = 0;
                numReverses++;
            }
        }

        return head;
    }

    public ListNode constructNodes(int[] ints) {
        ListNode head = null, curr = null;
        int count = 0;
        for (int x : ints) {
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
        ReverseNodesKGroup obj = new ReverseNodesKGroup();

        ListNode head = obj.constructNodes(new int[] { 1, 2 });
        head = obj.reverseKGroup(head, 2);
        obj.printNodes(head);

        head = obj.constructNodes(new int[] { 1, 2, 3, 4 });
        head = obj.reverseKGroup(head, 2);
        obj.printNodes(head);
    }
}
