#=====
# Main
#=====
if ARGV.length < 1 then
  print "Usage: ruby scpTpkg.rb remoteHost\n"
  Process.exit(1)
end

remoteHost = ARGV[0]
tpkgFile = "tpkgFile"
remoteDestination = "@#{remoteHost}:centos-tpkg/."
puts "remoteDestination=#{remoteDestination}"

# find all the tpkg files
cmd = "find . -name \"*.tpkg\" -print > " << tpkgFile
puts cmd
system(cmd)

# read and copy them to remote server
open(tpkgFile).each { |line|
  line.strip!
  cmd = "scp #{line} #{remoteDestination}"
  puts cmd
  system(cmd)
}