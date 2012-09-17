hash = { 1 => 'one', [1,2] => 'two', 'three' => 'three' }
hash.each_pair { |key,value| puts "#{key.inspect} maps to #{value}" }
  
phone_directory = { "Alice" => '555-1212', "Bob" => "555-1313", "Carol" => '555-1313', "Mallory" => '111-111', "Trent" => "555-1212"}
phone_directory_inverted = phone_directory.invert
puts phone_directory_inverted.inspect

# weighted selection
def choose_weighted(weighted)
  sum = weighted.inject(0) do |sum, item_and_weight|
    sum += item_and_weight[1]
  end
  target = rand(sum)
  weighted.each do |item,weight|
    return item if target < weight
    target -= weight
  end
end
marbles = { :block => 51, :white => 17 }
3.times { puts choose_weighted(marbles) }

h = { "apple tree" => "plant", "ficus" => "plant", "shrew" => "animal", "plesiosaur" => "animal" }
keys = h.keys.grep /p/
puts keys.inspect