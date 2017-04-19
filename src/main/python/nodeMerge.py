#=====================================================================                
# class Node 
#=====================================================================   
class Node:
    def __init__(self, intValue=None, nextNode=None):
        self.value = intValue
        self.next = nextNode
        
#=====================================================================                
# functions 
#=====================================================================   

def constructNodes(intList):
    head = None
    current = None
    for intValue in intList:
        node = Node(intValue)
        if (head == None):
            head = node
        else:
            current.next = node
        current = node
    
    return head

def printNodes(node):
    print "[",
    while(node != None):
        print node.value,
        node = node.next
    print "]"
      
def merge(node1, node2):
    head = None
    current = None
    
    # case 0: node1 != None && node2 != None
    while(node1 != None and node2 != None):
        if (node1.value <= node2.value):
            head, current, node1 = addNode(head, current, node1)
        else:
            head, current, node2 = addNode(head, current, node2)
    
    # case1: node1 == None or Node2 == None.  Loop through node1 xor node2 until it is empty
    while (node1 != None):
        head, current, node1 = addNode(head, current, node1) 
        
    while (node2 != None):
        head, current, node2 = addNode(head, current, node2)
        
    return head
  
def addNode(head, current, node):
    if (head == None):
        head = node
    else:
        current.next = node
    current = node
    node = node.next
    return head, current, node
  
#=====================================================================                
# main 
#=====================================================================   

node1 = constructNodes([1,2,3,4,5])
printNodes(node1)
node2 = constructNodes([2,3,5,8,13,21])
printNodes(node2)

node = merge(node1, node2)
printNodes(node)
