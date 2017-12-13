package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Given a linked list, swap every two adjacent nodes and return its head.

For example,
Given 1->2->3->4, you should return the list as 2->1->4->3.

Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.

 */
public class SwapNodes {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode swapPairs(ListNode head) {
        // handle edge condition
        if (head == null)
            return head;

        ListNode curr = head;
        head = null; // make it easy to set to the swapped node
        ListNode first = null, second = null, lastPair = null;
        // do a pair at a time
        while (curr != null) {
            first = curr;
            second = null;
            curr = curr.next;
            if (curr != null) {
                // got a pair, swap
                second = curr;
                // next node
                curr = curr.next;
                // swap
                first.next = curr;
                second.next = first;
                if (head == null)
                    // second is now first
                    head = second;
                else
                    // link the lastPair with the current pair
                    lastPair.next.next = second;
                lastPair = second;
            } else {
                // only one node, set head if null
                if (head == null)
                    // first is still first
                    head = first;
            }

        }
        return head;
    }

    public ListNode constructNodes(List<Integer> intValues) {
        ListNode head = null;
        ListNode curr = null;
        int i = 0;
        for (Integer intValue : intValues) {
            ListNode node = new ListNode(intValue);
            if (i++ == 0)
                head = node;
            else
                curr.next = node;
            curr = node;
        }
        return head;
    }

    public void printNodes(ListNode head) {
        ListNode curr = head;
        List<Integer> intList = new ArrayList<>();
        while (curr != null) {
            intList.add(curr.val);
            curr = curr.next;
        }
        System.out.println(intList.stream().map(Object::toString).collect(Collectors.joining(",", "[", "]")));
    }

    public static void main(String[] args) {
        SwapNodes obj = new SwapNodes();
        // head == njll
        ListNode head = null;
        head = obj.swapPairs(head);
        obj.printNodes(head);

        // one node
        head = obj.constructNodes(Arrays.asList(1));
        head = obj.swapPairs(head);
        obj.printNodes(head);

        // odd # of nodes
        head = obj.constructNodes(Arrays.asList(1, 2, 3, 4, 5));
        head = obj.swapPairs(head);
        obj.printNodes(head);

        // even # of nodes
        head = obj.constructNodes(Arrays.asList(1, 2, 3, 4, 5, 6));
        head = obj.swapPairs(head);
        obj.printNodes(head);
    }

}
