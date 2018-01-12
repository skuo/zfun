package org.zfun.leetcode;

import java.util.Arrays;
import java.util.List;

public class MergeKSortedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
    
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;
        
        // has at least 2 lists, divide and conquer
        int length = lists.length;
        int step = 1;
        while (step < length) {
            for (int i=0; i<length; i=i+2*step) {
                if (i+step < length)
                    lists[i] = merge2Lists(lists[i], lists[i+step]);
            }
            step *= 2;
        }
        return lists[0];
    }

    public ListNode merge2Lists(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        else if (l2 == null)
            return l1;
        
        if (l1.val <= l2.val) {
            l1.next = merge2Lists(l1.next, l2);
            return l1;
        } else {
            l2.next = merge2Lists(l1, l2.next);
            return l2;
        }
    }
    
    public ListNode constructNodes(List<Integer> intValues) {
        ListNode current = null, head = null;
        
        for (Integer intValue : intValues) {
            ListNode node = new ListNode(intValue);
            if (head == null) {
                head = node;
            } else
                current.next = node;
            current = node;
        }
        return head;
    }

    public void printListNode(ListNode node) {
        System.out.print("[");
        int index = 0;
        while(node != null) {
            if (index++ > 0)
                System.out.print(",");
            System.out.print(node.val);
            node = node.next;
        }
        System.out.println("]");
    }

    public void testMerge2Lists(MergeKSortedList obj) {
        ListNode l1 = obj.constructNodes(Arrays.asList(1, 2, 3, 4, 5));
        //ListNode l1 = obj.constructNodes(Arrays.asList(1));
        obj.printListNode(l1);
        ListNode l2 = obj.constructNodes(Arrays.asList(2, 3, 5, 8, 13, 21));
        obj.printListNode(l2);
        
        System.out.println();
        ListNode mergedNode = obj.merge2Lists(l1, l2);
        obj.printListNode(mergedNode);

        l1 = obj.constructNodes(Arrays.asList(2, 3, 5, 8, 13, 21));
        l2 = obj.constructNodes(Arrays.asList(1));
        mergedNode = obj.merge2Lists(l1, l2);
        obj.printListNode(mergedNode);

        l1 = obj.constructNodes(Arrays.asList(2, 3, 5, 8, 13, 21));
        mergedNode = obj.merge2Lists(l1, null);
        obj.printListNode(mergedNode);
    }

    public void testMergeKLists(MergeKSortedList obj) {
        ListNode l1 = obj.constructNodes(Arrays.asList(1, 2, 3, 4, 5));
        //ListNode l1 = obj.constructNodes(Arrays.asList(1));
        ListNode l2 = obj.constructNodes(Arrays.asList(2, 3, 5, 8, 13, 21));        
        //ListNode l2 = obj.constructNodes(Arrays.asList(5));        
        ListNode l3 = obj.constructNodes(Arrays.asList(3, 6, 9, 12));        

        ListNode[] lists = {l1, l2, l3};
        ListNode mergedNode = obj.mergeKLists(lists);
        obj.printListNode(mergedNode);

    }
    
    public static void main(String[] args) {
        MergeKSortedList obj = new MergeKSortedList();
        //testMerge2Lists(obj);
        obj.testMergeKLists(obj);

    }

}
