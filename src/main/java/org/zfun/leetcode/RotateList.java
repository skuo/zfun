package org.zfun.leetcode;

import java.util.ArrayList;
import java.util.List;

/*
Given a list, rotate the list to the right by k places, where k is non-negative.

Example:

Given 1->2->3->4->5->NULL and k = 2,

return 4->5->1->2->3->NULL.
 */
public class RotateList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode rotateRight1(ListNode head, int k) {
        // test cases: k=0, k=1, k=2
        // head=null, numNodes < k, == k, > k
        if (k == 0)
            return head;

        if (head == null || head.next == null)
            return head;

        ListNode[] nodes = new ListNode[k + 1];
        ListNode curr = head;
        int numNodes = 0;
        while (curr != null) {
            numNodes++;
            for (int i = 0; i < k; i++)
                nodes[i] = nodes[i + 1];
            nodes[k] = curr;
            curr = curr.next;
        }
        // rotate
        if (numNodes > k) {
            nodes[k].next = head;
            head = nodes[1];
            nodes[0].next = null;

        } else {
            // reverse the nodes in nodes[]
            head = nodes[k - numNodes + 1];
            ListNode tail = nodes[k];
            int tailIndex = k;
            for (int i = 0; i < k; i++) {
                tail.next = head;
                head = tail;
                if (--tailIndex < k - numNodes + 1)
                    tailIndex = k;
                tail = nodes[tailIndex];
                tail.next = null;
            }
        }
        return head;
    }

    public ListNode rotateRight(ListNode head, int k) {
        // test cases: k=0, k=1, k=2
        // head=null, numNodes < k, == k, > k
        if (k == 0)
            return head;

        if (head == null || head.next == null)
            return head;

        // assume k can be very large and num of nodes are small
        List<ListNode> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr);
            curr = curr.next;
        }

        // rotate
        int size = list.size();
        int effectiveRotations = k % size;
        ListNode tail = list.get(size-1);
        int tailIndex = size-1;
        for (int i = 0; i < effectiveRotations; i++) {
            tail.next = head;
            head = tail;
            if (--tailIndex < 0)
                tailIndex = size-1;
            tail = list.get(tailIndex);
            tail.next = null;
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
        RotateList obj = new RotateList();

        ListNode head = obj.constructNodes(new int[] { 1, 2 });
        head = obj.rotateRight(head, 2);
        obj.printNodes(head); // [1 2]

        head = obj.constructNodes(new int[] { 1, 2, 3, 4, 5 });
        head = obj.rotateRight(head, 2);
        obj.printNodes(head); // [1 2]
    }
}
