#!/usr/bin/ruby -w

class Stats
  attr_accessor :name, :total, :count
  def initialize(name)
    @name = name
    @total, @count = 0, 0
  end
end

class ResptimebandReducer
  def reduce(reducer_in=STDIN)
    # need to translate key from string to int because band is defined as int in test_resptimeband_reduced
    bandStats = {"0_50" => Stats.new(0), "51_100" => Stats.new(51), "101_200" => Stats.new(101), "201_inf" => Stats.new(201)}
    reducer_in.each_line { |line|
      line.chomp!()
      fields = line.split("\t")
#      $stderr.puts "line : #{line}" 
#      $stderr.puts "f0=#{fields[0]} f1=#{fields[1]}"
      band = fields[0]
      resptime = fields[1]
      bandStats[band].count += 1
      bandStats[band].total += resptime.to_i
    }
    bandStats.each { |key,stats|
      if stats.count > 0
        puts [stats.name, stats.total, stats.count].join("\t")
      end
    }
  end
end

# Only run the following code when this file is the main file being run
# instead of having been required or loaded by another file
if __FILE__ == $0 
  ResptimebandReducer.new.reduce()
end