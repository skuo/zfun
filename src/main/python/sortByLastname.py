#=====================================================================                
# main 
#=====================================================================   
names = ["Jonathan Smith", "Bill O'Reilly", "Thomas Sowell Sr.", "Jonathan Smith", "Rose Friedmann"]
nameSet = set(names)

print ", ".join(str(name) for name in nameSet)

nameTuples = []
for name in nameSet:
    words = name.split()
    nameTuples = nameTuples + [(name, words[1])] 

print nameTuples

# sort by lastName
sortedNameTuples = sorted(nameTuples, key=lambda name: name[1])

print "-----"
print ", ".join(name[0] for name in sortedNameTuples)
