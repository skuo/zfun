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