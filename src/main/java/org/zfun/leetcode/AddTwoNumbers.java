package org.zfun.leetcode;

/*
 You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order 
 and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
 */
public class AddTwoNumbers {
    // 1560 / 1562 test cases passed.
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // reconstitute num1
        long num1 = 0;
        long multiplicant = 1;
        ListNode curr = l1;
        while (curr != null) {
            num1 = num1 + curr.val * multiplicant;
            curr = curr.next;
            multiplicant *= 10;
        }
        ;
        // reconstitute num2
        long num2 = 0;
        multiplicant = 1;
        curr = l2;
        while (curr != null) {
            num2 = num2 + curr.val * multiplicant;
            curr = curr.next;
            multiplicant *= 10;
        }
        ;
        //
        long sum = num1 + num2;
        long quotient = sum;
        ListNode head = null;
        ListNode tail = null;
        while (quotient > 0 || sum == 0) {
            long remainder = quotient % 10;
            ListNode node = new ListNode((int) remainder);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            quotient = quotient / 10;
            // if sum is 0, break;
            if (sum == 0)
                break;
        }
        return head;
    }

    public ListNode constructList(int[] inputs) {
        ListNode head = null, tail = null;

        // inputs is guaranteed to be not empty
        for (int i = 0; i < inputs.length; i++) {
            ListNode node = this.new ListNode(inputs[i]);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = tail.next;
            }
        }

        return head;
    }

    public void print(ListNode l) {
        ListNode curr = l;
        StringBuilder sb = new StringBuilder("[");
        int count = 0;
        while (curr != null) {
            if (count++ > 0)
                sb.append(",");
            sb.append(curr.val);
            curr = curr.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        AddTwoNumbers obj = new AddTwoNumbers();
        //
        ListNode l1 = null;
        ListNode l1Tail = null;
        ListNode l1Node = obj.new ListNode(2);
        l1 = l1Node;
        l1Tail = l1Node;
        l1Node = obj.new ListNode(4);
        l1Tail.next = l1Node;
        l1Tail = l1Node;
        l1Node = obj.new ListNode(3);
        l1Tail.next = l1Node;
        l1Tail = l1Node;
        //
        ListNode l2 = null;
        ListNode l2Tail = null;
        ListNode l2Node = obj.new ListNode(5);
        l2 = l2Node;
        l2Tail = l2Node;
        l2Node = obj.new ListNode(6);
        l2Tail.next = l2Node;
        l2Tail = l2Node;
        l2Node = obj.new ListNode(4);
        l2Tail.next = l2Node;
        l2Tail = l2Node;
        //
        ListNode listNode = obj.addTwoNumbers(l1, l2);
        obj.print(listNode);

        int[] inputs1 = new int[] { 9 };
        l1 = obj.constructList(inputs1);
        int[] inputs2 = new int[] { 1, 9, 9, 9, 9, 9, 9, 9, 9, 9 };
        l2 = obj.constructList(inputs2);
        listNode = obj.addTwoNumbers(l1, l2);
        obj.print(listNode);
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
