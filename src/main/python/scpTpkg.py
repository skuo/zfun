import os
import sys

#=====
# Main
#=====
if (len(sys.argv) != 2):
    print "Usage: python scpTpkg.py remoteHost"
    sys.exit(-1)

remoteHost = sys.argv[1]
tpkgFile = "tpkgFile"
remoteDestination = "@%s:centos-tpkg/." % (remoteHost)
print "remoteDestination=%s" % (remoteDestination)

# find all the tpkg files
cmd = "find . -name \"*.tpkg\" -print > %s" % (tpkgFile)
print cmd
os.system(cmd)

# read and copy them to remote server
infile = open(tpkgFile,"r")
line = infile.readline()
while line:
    if line[-1] == "\n":
        line = line[:-1]
    cmd = "scp %s %s" % (line, remoteDestination)
    print cmd
    os.system(cmd)
    line = infile.readline()

infile.close()
