package org.zfun.leetcode;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

public class NodeMerge {

    public Node merge(Node node1, Node node2) {
        NodeContext nodeCtx = new NodeContext();
        
        // Case 0: both node1 != null and node2 != null
        while (node1 != null && node2 != null) {
            if (node1.value <= node2.value) {
                addNode(nodeCtx, node1);
                node1 = node1.next;
            } else {
                addNode(nodeCtx, node2);
                node2 = node2.next;
            }
        }
        
        // Case 1: node1 is not null
        while (node1 != null) {
            addNode(nodeCtx, node1);
            node1 = node1.next;
        }
        
        while (node2 != null) {
            addNode(nodeCtx, node2);
            node2 = node2.next;
        }
            
        return nodeCtx.head;
    }
    
    public NodeContext addNode(NodeContext nodeCtx, Node node) {
        Node newNode = new Node(node.value);
        if (nodeCtx.head == null)
            nodeCtx.head = newNode;
        else
            nodeCtx.current.next = newNode;
        nodeCtx.current = newNode;
        return nodeCtx;
    }
    
    public Node constructNodes(List<Integer> intValues) {
        Node current = null, head = null;
        
        for (Integer intValue : intValues) {
            Node node = new Node(intValue);
            if (head == null) {
                head = node;
            } else
                current.next = node;
            current = node;
        }
        return head;
    }

    public void printNode(Node node) {
        System.out.print("[");
        int index = 0;
        while(node != null) {
            if (index++ > 0)
                System.out.print(",");
            System.out.print(node.value);
            node = node.next;
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        NodeMerge obj = new NodeMerge();
        Node node1 = obj.constructNodes(Arrays.asList(1, 2, 3, 4, 5));
        obj.printNode(node1);
        Node node2 = obj.constructNodes(Arrays.asList(2, 3, 5, 8, 13, 21));
        obj.printNode(node2);
        
        System.out.println();
        Node mergedNode = obj.merge(node1, node2);
        obj.printNode(mergedNode);
        mergedNode = obj.merge(node2, node1);
        obj.printNode(mergedNode);
        mergedNode = obj.merge(node2, null);
        obj.printNode(mergedNode);
    }
    
    @Data
    private class Node {
        private Node next;
        private Integer value;
        
        public Node(Integer intValue) {
            this.value = intValue;
        }
    }
    
    @Data
    private class NodeContext {
        private Node head;
        private Node current;
    }
}
