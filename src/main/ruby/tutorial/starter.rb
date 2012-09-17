def sayGoodnight(name)
  result = "Goodnight," + name
  return result
end

puts sayGoodnight("John-Boy")
puts sayGoodnight("Mary-Ellen")

#
system("ls > /tmp/ls.ruby")

# string manipulation
number = 5
result = "The number after #{number} is #{number.next}"
puts result

template = 'Oceania has always been at war with %s.'
sentence = template % 'Eurasia'
puts sentence
