[1, 2, 3, 4].each { |x| puts x }
squared = [1, 2, 3, 4].collect { |x| x**2 }
puts squared.inspect
puts squared.collect! { |x| x + 1 }.inspect

array = %w{1 a 2 b 3 c}
(0..array.length-1).step(2) do |i|
  puts "Letter #{array[i]} is #{array[i+1]}"
end

# sort_by
a1 = [ [1,2,3], [100], [10,20] ]
a1_sorted = a1.sort_by { |x| x.size }
puts a1_sorted.inspect
# sort
b1_sorted = [1, 100, 42, 23, 26, 10000].sort do |x,y|
  x == 42 ? 1 : x <=> y
end
puts b1_sorted.inspect
# sort_by
class Animal
  attr_reader :name, :eyes, :appendages
  def initialize(name, eyes, appendages)
    @name, @eyes, @appendages = name, eyes, appendages
  end
  def inspect
    @name
  end
end
animals = [Animal.new("octopus", 2, 8), Animal.new("spiders", 6, 8), Animal.new("bee", 5, 8), Animal.new("elephant", 2, 4), Animal.new("crab", 2, 10)]
animals_sorted_by_eyes = animals.sort_by { |x| x.eyes }
animals_sorted_by_appendages = animals.sort_by { |x| x.appendages }
puts animals_sorted_by_eyes.inspect
puts animals_sorted_by_appendages.inspect

collection = [1, 2, 3, 4, 5]
total = collection.inject(0) { |sum, i| sum + i}
puts total

# add to Enumerable
module Enumerable
  def sort_by_frequency
    histogram = inject(Hash.new(0)) { |hash, x| hash[x] += 1; hash }
    sort_by { |x| [histogram[x],x] } # sort key is an array
  end
end
puts [1,2,3,4,1,2,4,8, 1,4,9,16].sort_by_frequency.inspect

a2 = ("a".."h").to_a
puts a2.find_all { |x| x < "e" }.inspect
puts a2.reject { |x| x < "e" }.inspect
a2_1 = a2.grep /[aeiou]/
puts a2_1.inspect
a2_2 = a2.grep /[^g]/
puts a2_2.inspect

