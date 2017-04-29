package org.zfun.leetcode;

public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // reconstitute num1
        int num1 = 0;
        int multiplicant = 1;
        while(l1 != null) {
            num1 = num1 + l1.val * multiplicant; 
            l1 = l1.next;
            multiplicant *= 10;
        };
        // reconstitute num2
        int num2 = 0;
        multiplicant = 1;
        while (l2 != null) {
            num2 = num2 + l2.val * multiplicant;
            l2 = l2.next;
            multiplicant *= 10;
        };
        //
        int sum = num1 + num2;
        int quotient = sum;
        ListNode head = null;
        ListNode tail = null;
        while (quotient > 0 || sum == 0) {
            int remainder = quotient % 10;
            ListNode node = new ListNode(remainder);
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
    
    public static void main(String[] args) {
        AddTwoNumbers s = new AddTwoNumbers();
        //
        ListNode l1 = null;
        ListNode l1Tail = null;
        ListNode l1Node = s.new ListNode(2);
        l1 = l1Node;
        l1Tail = l1Node;
        l1Node = s.new ListNode(4);
        l1Tail.next = l1Node;
        l1Tail = l1Node;
        l1Node = s.new ListNode(3);
        l1Tail.next = l1Node;
        l1Tail = l1Node;
        //
        ListNode l2 = null;
        ListNode l2Tail = null;
        ListNode l2Node = s.new ListNode(5);
        l2 = l2Node;
        l2Tail = l2Node;
        l2Node = s.new ListNode(6);
        l2Tail.next = l2Node;
        l2Tail = l2Node;
        l2Node = s.new ListNode(4);
        l2Tail.next = l2Node;
        l2Tail = l2Node;
        //
        ListNode listNode = s.addTwoNumbers(l1, l2);
        while(listNode != null) {
            System.out.print(listNode.val + " ->");
            listNode = listNode.next;
        }
    }
    
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
