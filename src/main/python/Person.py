#=====================================================================                
# class Person 
#=====================================================================   
class Person:
    def __init__(self, name, address, phone):
        self.name = name
        self.address = address
        self.phone = phone
        self.count = 0
        
#=====================================================================                
# updatePerson 
#=====================================================================   
def updatePerson(person):
    person.count += 1

#=====================================================================                
# main 
#=====================================================================   
        
avatar = Person("I", "me", "myself")
print "name=%s, address=%s, phone=%s, count=%s" % (avatar.name, avatar.address, avatar.phone, avatar.count)
updatePerson(avatar)
print "name=%s, address=%s, phone=%s, count=%s" % (avatar.name, avatar.address, avatar.phone, avatar.count)
