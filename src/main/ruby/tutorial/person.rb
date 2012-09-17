class Person
  attr_accessor :firstName, :lastName, :age
  def initialize(firstName, lastName, age)
    @firstName, @lastName, @age = firstName, lastName, age
  end
  def full_name
    @firstName + " " + @lastName
  end
end

class Stats
  attr_accessor :total, :count
  def initialize()
    @total, @count = 0, 0
  end
end

bandStats = { "0_50" => Stats.new, "51_100" => Stats.new, "101_200" => Stats.new, "201_inf" => Stats.new}
bandStats["0_50"].total = 1
bandStats["0_50"].count = 1

bandStats["51_100"].total = 1
bandStats["51_100"].count = 1

bandStats["101_200"].total = 101
bandStats["101_200"].count = 101

bandStats["201_inf"].total += 200
bandStats["201_inf"].count += 200

bandStats.each { |name,stats|
  puts "#{name}, #{stats.total}, #{stats.count}"
}
