require 'set'

# failed in eclipse but worked from cmdline
s1 = Set.new 

s1.add("1")
s1.add("2")
s1.add("3")
s1.add("1")

puts s1.inspect
puts s1.size