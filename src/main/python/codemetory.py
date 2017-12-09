
#--------------------                
# question 2 
#--------------------                

# os package allows for platform independent operation
# recursive function 
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
# question 2 
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
      
#=====================================================================                
# main 
#=====================================================================   
       
question_3() 
