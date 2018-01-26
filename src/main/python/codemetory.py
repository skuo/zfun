'''
https://www.codementor.io/sheena/essential-python-interview-questions-du107ozr6
'''
#--------------------                
# question  
# python is interpretive
# python is dynamic typed
# python does not have accessors
# python allow inclusion C based extension to speed up execution
# Function and class are fist class objects
#--------------------                


#--------------------                
# question 2 
# os package allows for platform independent operation
# recursive function 
#--------------------                
def print_directory_contents(sPath):
    import os                                       
    for sChild in os.listdir(sPath):                
        sChildPath = os.path.join(sPath,sChild)
        if os.path.isdir(sChildPath):
            print_directory_contents(sChildPath)
        else:
            print(sChildPath)
          
def question_2():
    print_directory_contents("/Users/terrancekuo/git-wa")
  
#--------------------                
# question 3
# list comprehension
#--------------------                
def question_3():
    A0 = dict(zip(('a','b','c','d','e'),(1,2,3,4,5)))
    print A0 #{'a': 1, 'c': 3, 'b': 2, 'e': 5, 'd': 4}
    A1 = range(10)
    print A1 # [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    A2 = sorted([i for i in A1 if i in A0])
    print A2 # []
    A3 = sorted([A0[s] for s in A0])
    print A3 # [1, 2, 3, 4, 5]
    A4 = [i for i in A1 if i in A3]
    print A4 # [1, 2, 3, 4, 5]
    A5 = {i:i*i for i in A1}
    print A5 # {0: 0, 1: 1, 2: 4, 3: 9, 4: 16, 5: 25, 6: 36, 7: 49, 8: 64, 9: 81}
    A6 = [[i,i*i] for i in A1]
    print A6 # [[0, 0], [1, 1], [2, 4], [3, 9], [4, 16], [5, 25], [6, 36], [7, 49], [8, 64], [9, 81]]

      
#--------------------                
# question 4
# Python's multi-threaded library does not really work.  Python has a construct called the Global Interpreter Lock (GIL). 
# The GIL makes sure that only one of your 'threads' can execute at any one time.  Multi-threading library
# is good for IO wait tasks but it won't use extra CPU cores.
#--------------------                


#--------------------                
# question 6
# variable l is assigned a memory, which is reused
# lesson: don't change variable with default value
#--------------------                
def question_6(x,l=[]):
    for i in range(x):
        l.append(i*i)
    print(l) 


#--------------------                
# question 7
# monkey patch is generally not a good idea except may be in testing
#--------------------       
class Question7:
    def add(self,x,y):         
        return x+y
    
def not_exactly_add(self, x, y):
    return x+y+1

# monkey patch here
Question7.add = not_exactly_add


def question_7(x, y):
    obj = Question7()
    print '{}+{} (not exactly) ={}'.format(x,y,obj.add(x,y))
                           

#--------------------                
# question 8
#--------------------
def f(*args,**kwargs): 
    print(args, kwargs)
    
def f2(arg1,arg2,*args,**kwargs): 
    print(arg1,arg2, args, kwargs)
    
def question_8():
    l = [1,2,3]
    t = (4,5,6)
    d = {'a':7,'b':8,'c':9}
    
    f()                         # (() {})
    f(1,2,3)                    # ((1, 2, 3) {})
    f(1,2,3,"groovy")           # ((1, 2, 3, 'groovy') {})
    f(a=1,b=2,c=3)              # (() {'a': 1, 'c': 3, 'b': 2})
    f(a=1,b=2,c=3,zzz="hi")     # (() {'a': 1, 'c': 3, 'b': 2, 'zzz': 'hi'})
    f(1,2,3,a=1,b=2,c=3)        # ((1, 2, 3) {'a': 1, 'c': 3, 'b': 2})
    
    f(*l,**d)                   # ((1, 2, 3) {'a': 7, 'c': 9, 'b': 8})
    f(*t,**d)                   # ((4, 5, 6) {'a': 7, 'c': 9, 'b': 8})
    f(1,2,*t)                   # ((1, 2, 4, 5, 6) {})
    f(q="winning",**d)          # (() {'a': 7, 'q': 'winning', 'c': 9, 'b': 8})
    f(1,2,*t,q="winning",**d)   # ((1, 2, 4, 5, 6) {'a': 7, 'q': 'winning', 'c': 9, 'b': 8})

    f2(1,2,3)                       # (1 2 (3,) {})
    f2(1,2,3,"groovy")              # (1 2 (3, 'groovy') {})
    f2(arg1=1,arg2=2,c=3)           # (1 2 () {'c': 3})
    f2(arg1=1,arg2=2,c=3,zzz="hi")  # (1 2 () {'c': 3, 'zzz': 'hi'})
    f2(1,2,3,a=1,b=2,c=3)           # (1 2 (3,) {'a': 1, 'c': 3, 'b': 2})
    
    f2(*l,**d)                   # (1 2 (3,) {'a': 7, 'c': 9, 'b': 8})
    f2(*t,**d)                   # (4 5 (6,) {'a': 7, 'c': 9, 'b': 8})
    f2(1,2,*t)                   # (1 2 (4, 5, 6) {})
    f2(1,1,q="winning",**d)      # (1 1 () {'a': 7, 'q': 'winning', 'c': 9, 'b': 8})
    f2(1,2,*t,q="winning",**d)   # (1 2 (4, 5, 6) {'a': 7, 'q': 'winning', 'c': 9, 'b': 8}) 
    
                    
#--------------------                
# question 9
# A decorator is a special kind of function that either takes a function and returns a function, 
# or takes a class and returns a class.
#
# https://realpython.com/blog/python/instance-class-and-static-methods-demystified/
#--------------------
class Celsius:
    def __init__(self, temperature = 0):
        self._temperature = temperature

    def to_fahrenheit(self):
        return (self.temperature * 1.8) + 32

    @property
    def temperature(self):
        print("Getting value")
        return self._temperature

    # property setter does not appear to work
    @temperature.setter
    def temperature(self, value):
        print 'in temperature.setter'
        if value < -273:
            raise ValueError("Temperature below -273 is not possible")
        print("Setting value")
        self._temperature = value


#--------------------                
# question 10
# Inheritance and super()
#--------------------
class A(object):
    def go(self):
        print("go A go!")
    def stop(self):
        print("stop A stop!")
    def pause(self):
        raise Exception("Not Implemented")

class B(A):
    def go(self):
        super(B, self).go()
        print("go B go!")

class C(A):
    def go(self):
        super(C, self).go()
        print("go C go!")
    def stop(self):
        super(C, self).stop()
        print("stop C stop!")

class D(B,C):
    def go(self):
        super(D, self).go()
        print("go D go!")
    def stop(self):
        super(D, self).stop()
        print("stop D stop!")
    def pause(self):
        print("wait D wait!")

class E(B,C): 
    pass

def question_10():
    a = A() 
    b = B()
    c = C()
    d = D()
    e = E()
    
    # specify output from here onwards
    
    a.go() # go A go!
    b.go() # go A go!, go B go!
    c.go() # go A go!, go C go!
    d.go() # go A go!, go C go!, go B go!, go D go!
    e.go() # go A go!, go C go!, go B go!

    a.stop() # stop A stop!
    b.stop() # stop A stop!
    c.stop() # stop A stop!, stop C stop!
    d.stop() # stop A stop!, stop C stop!, stop D stop!
    e.stop() # stop A stop!, stop C stop!
    
    a.pause() # ...Exception: Not Implemented
    b.pause() # ...Exception: Not Implemented
    c.pause() # ...Exception: Not Implemented
    d.pause() # wait D wait!
    e.pause() # ...Exception: Not Implemented


#--------------------                
# question 11
# Recursion -- dfs
# Generator -- save memory space
#--------------------
class Node(object):
    def __init__(self,sName):
        self._lChildren = []
        self.sName = sName
    def __repr__(self):
        return "<Node '{}'>".format(self.sName)
    def append(self,*args,**kwargs):
        self._lChildren.append(*args,**kwargs)
    def print_all_1(self):
        print(self)
        for oChild in self._lChildren:
            oChild.print_all_1()
    def print_all_2(self):
        def gen(o):
            lAll = [o,]
            while lAll:
                oNext = lAll.pop(0)
                lAll.extend(oNext._lChildren)
                yield oNext
        for oNode in gen(self):
            print(oNode)

def question_11():            
    oRoot = Node("root")
    oChild1 = Node("child1")
    oChild2 = Node("child2")
    oChild3 = Node("child3")
    oChild4 = Node("child4")
    oChild5 = Node("child5")
    oChild6 = Node("child6")
    oChild7 = Node("child7")
    oChild8 = Node("child8")
    oChild9 = Node("child9")
    oChild10 = Node("child10")
    
    oRoot.append(oChild1)
    oRoot.append(oChild2)
    oRoot.append(oChild3)
    oChild1.append(oChild4)
    oChild1.append(oChild5)
    oChild2.append(oChild6)
    oChild4.append(oChild7)
    oChild3.append(oChild8)
    oChild3.append(oChild9)
    oChild6.append(oChild10)
    
    # specify output from here onwards
    
    oRoot.print_all_1() # dfs.  root, 1, 4, 7, 5, 2, 6, 10, 3, 8, 9
    oRoot.print_all_2() # generator/yield
                        # root, 1, 2, 3, 4, 5, 6, 8, 9, 7, 10

#--------------------
# Use yield to implement iterable
#--------------------
def counter(low, high):
    current = low
    while current <= high:
        yield current
        current += 1

#=====================================================================                
# main 
#=====================================================================   
       
#question_6(2) 
#question_6(3,[3,2,1]) 
#question_6(3) 

#question_7(1,2)

#question_8()

# question 9
'''
man=Celsius(37)
print man.temperature
print man.to_fahrenheit()
print man.temperature
man.temperature = 38
print man.temperature
print man.__dict__
'''
    
#question_10()

#question_11()

for c in counter(3, 8):
    print c
