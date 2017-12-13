#=====================================================================                
# class Person 
#=====================================================================   
class Person:
    '''Doc string for Person'''
    
    # class variable
    total_count = 0
    
    def __init__(self, name, address, phone):
        self.name = name
        self.address = address
        self.phone = phone
        self.count = 0
        Person.total_count += 1
        
    @classmethod
    def class_total_persons(cls):
        return Person.total_count
    
#=====================================================================                
# updatePerson 
#=====================================================================   
def updatePerson(person):
    person.count += 1

#=====================================================================                
# class Date 
# method, classmethod, static method
#=====================================================================   
class Date:
    
    def __init__(self, day=0, month=0, year=0):
        self.day = day
        self.month = month
        self.year = year
        
    def __str__(self):
        return '{year}/{month}/{day}'.format(year=self.year,month=self.month,day=self.day)
    @classmethod
    def from_string(cls, date_as_string):
        day, month, year = map(int, date_as_string.split('-'))
        # class method has a reference to the class
        date1 = cls(day, month, year)
        return date1
    
    @staticmethod
    def is_date_valid(date_as_string):
        day, month, year = map(int, date_as_string.split('-'))
        return day <= 31 and month <= 12 and year <= 3999
    
            
#=====================================================================                
# Multiple inheritance
''' 
Assuming everything descends from object (you are on your own if it doesn't), Python computes a 
method resolution order (MRO) based on your class inheritance tree. The MRO satisfies 3 properties:

Children of a class come before their parents
Left parents come before right parents
A class only appears once in the MRO
If no such ordering exists, Python errors. The inner workings of this is a C3 Linerization of the classes ancestry. 
Read all about it here: https://www.python.org/download/releases/2.3/mro/

Thus, in both of the examples below, it is:

Child
Left
Right
Parent
When a method is called, the first occurrence of that method in the MRO is the one that is called. Any class that doesn't implement that method is skipped. Any call to super within that method will call the next occurrence of that method in the MRO. Consequently, it matters both what order you place classes in inheritance, and where you put the calls to super in the methods.
'''
#=====================================================================   
class Parent0 (object):
    def __init__(self):
        super(Parent0, self).__init__()
        print "parent0"

class Left0(Parent0):
    def __init__(self):
        super(Left0, self).__init__()
        print "left0"

class Right0(Parent0):
    def __init__(self):
        super(Right0, self).__init__()
        print "right0"

class Child0(Left0, Right0):
    def __init__(self):
        super(Child0, self).__init__()
        print "child0"

class Parent1 (object):
    def __init__(self):
        print "parent1"
        super(Parent1, self).__init__()

class Left1(Parent1):
    def __init__(self):
        print "left1"
        super(Left1, self).__init__()

class Right1(Parent1):
    def __init__(self):
        print "right1"
        super(Right1, self).__init__()

class Child1(Left1, Right1):
    def __init__(self):
        print "child1"
        super(Child1, self).__init__()


#=====================================================================                
# Mixin
# in Python the class hierarchy is defined right to left
# MRO is resolved from left to right (sibling)
#=====================================================================                
class Mixin1(object):
    def test(self):
        print "Mixin1"
    
    def favor(self):
        print 'Anyone named Steve'

class Mixin2(object):
    def test(self):
        print "Mixin2"
        
class BaseClass(object):
    def whoami(self):
        print 'BaseClass'

class MyClass(Mixin2, Mixin1, BaseClass):
    pass


#=====================================================================
# lambda                
#=====================================================================                
# https://www.programiz.com/python-programming/anonymous-function
'''
http://www.bogotobogo.com/python/python_functions_lambda.php


The lambda's general form is :

lambda arg1, arg2, ...argN : expression using arguments
Function objects returned by running lambda expressions work exactly the same as those created and assigned by defs. 
However, there are a few differences that make lambda useful in specialized roles:

lambda is an expression, not a statement.
Because of this, a lambda can appear in places a def is not allowed. For example, places like inside a list literal, 
or a function call's arguments. As an expression, lambda returns a value that can optionally be assigned a name. 
In contrast, the def statement always assigns the new function to the name in the header, 
instead of returning is as a result.

* lambda's body is a single expression, not a block of statements.
* The lambda's body is similar to what we'd put in a def body's return statement. We simply type the result as 
  an expression instead of explicitly returning it. Because it is limited to an expression, a lambda is less general 
  that a def. We can only squeeze design, to limit program nesting. lambda is designed for coding simple functions, 
  and def handles larger tasks.
'''
                    
#=====================================================================                
# main 
#=====================================================================   
        
avatar = Person("I", "me", "myself")
print "name=%s, address=%s, phone=%s, count=%s" % (avatar.name, avatar.address, avatar.phone, avatar.count)
updatePerson(avatar)
print "name=%s, address=%s, phone=%s, count=%s" % (avatar.name, avatar.address, avatar.phone, avatar.count)

print 'Person.class_total_persons()={}'.format(Person.class_total_persons())

second_fiddle = Person("Second", "Fiddle", "duplicated ")

print 'avatar.class_total_person()={}'.format(avatar.class_total_persons())
print 'avatar.__doc__={}'.format(avatar.__doc__)


date1 = Date(9,12,2017)
print date1
date2 = Date.from_string('11-09-2012')
print date2
is_date = Date.is_date_valid('11-09-2012')
print is_date

print "\nsuper() first"
child0 = Child0()

print "\nsuper() last"
child1 = Child1()

print '\nmixin'
obj = MyClass()
obj.test()
obj.whoami()
obj.favor()

print '\nlambda'
double = lambda x: x * 2
# Output: 10
print(double(5))

double = lambda x: x * 2
my_list = [1, 5, 4, 6, 8, 11, 3, 12]
new_list = list(filter(lambda x: (x%2 == 0) , my_list))
# Output: [4, 6, 8, 12]
print(new_list)

new_list = list(map(lambda x: x * 2 , my_list))
# Output: [2, 10, 8, 12, 16, 22, 6, 24]
print(new_list)

