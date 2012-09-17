adSelUtilLogFile = "/Users/skuo/tmp/AdSelUtil.log"

#=====================================================================                
# class Person 
#=====================================================================   
class AdRankStats:
    def __init__(self):
        self.lastScore = "0.000000"
        self.scoreChanged = 0
        self.scoreNotChanged = 0
        self.scoreNotChangedMissingFields = 0
        self.scoreNotChangedMixedFields = 0
        self.numOfSearches = 0
        self.numOfSearchesWithMissingFields = 0
        self.numOfSearchesWithMixedFields = 0
        self.listingCount = 0
        self.listingMissingFieldsCount = 0
        
    def formatStats(self):
        print "numOfSearches=%s, numOfSearchesWithMissingFields=%s, numOfSearchesWithMixedFields=%s" % (self.numOfSearches, self.numOfSearchesWithMissingFields, self.numOfSearchesWithMixedFields)
        print "scoreChanged=%s, scoreNotchanged=%s, scoreNotChangedMissingFields=%s, scoreNotChangedMixedFields=%s" % (self.scoreChanged, self.scoreNotChanged, self.scoreNotChangedMissingFields, self.scoreNotChangedMixedFields)
        
#=====================================================================                
# countTotalScoreChanges 
#=====================================================================   
def countTotalScoreChanges(line, adRankStats):
    # process score2 line
    if ("score2" in line):
        adRankStats.numOfSearches += 1

        missingFields = True
        if (adRankStats.listingCount == adRankStats.listingMissingFieldsCount):
            adRankStats.numOfSearchesWithMissingFields += 1
            #print line
        else:
            adRankStats.numOfSearchesWithMixedFields += 1
            missingFields = False
        adRankStats.listingCount = 0
        adRankStats.listingMissingFieldsCount = 0

        tokens = line.split(" ")
        kv = tokens[3].split(":")
        if (adRankStats.lastScore != kv[1]):
            adRankStats.scoreChanged += 1
            adRankStats.lastScore = kv[1]
        else:
            #print "<NC> %s" % line
            adRankStats.scoreNotChanged += 1
            if (missingFields):
                adRankStats.scoreNotChangedMissingFields += 1
            else:
                adRankStats.scoreNotChangedMixedFields += 1

#=====================================================================                
# countSearchesWithAllMissingFields 
#=====================================================================   
def countSearchesWithMissingFields(adRankStats):
    infile = open(adSelUtilLogFile,"r")
    line = infile.readline()
    while line:
        if line[-1] == "\n":
            line = line[:-1]
        
        # process result line
        if ("result=" in line):
            adRankStats.listingCount += 1
            if ("missing fields" in line):
                adRankStats.listingMissingFieldsCount += 1
        # process score2 line
        if ("score2" in line):
            countTotalScoreChanges(line, adRankStats)
        #print line
        line = infile.readline()
    
    infile.close()

#=====================================================================                                       
# main                                                                                                       
#=====================================================================

adRankStats = AdRankStats()
#countTotalScoreChanges(adRankStats)
countSearchesWithMissingFields(adRankStats)
adRankStats.formatStats()
