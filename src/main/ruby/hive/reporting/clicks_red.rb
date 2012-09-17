#!/usr/bin/ruby -w
require 'set'

class Stats
  attr_accessor :customerId, :ypid, :pll, :diamond, :platinum, :gold, :silver, :bronze, :count, :ips
  def initialize(customerId, ypid)
    @customerId = customerId
    @ypid = ypid
    @count = 0
    @pll, @diamond, @platinum, @gold, @silver, @bronze, @ips = Set.new, Set.new, Set.new, Set.new, Set.new, Set.new, Set.new
  end
  
  def countTierUip(listingId, tier, uip)
    @count += 1
    if tier == '20'
      @pll.add(listingId)
    elsif tier == '25'
      @diamond.add(listingId)
    elsif tier == '30' or tier == '35'
      @platinum.add(listingId)
    elsif tier == '40' or tier == '45'
      @gold.add(listingId)
    elsif tier == '50' or tier == '55'
      @silver.add(listingId)
    elsif tier == '60' or tier == '65'
      @bronze.add(listingId)
    end
    @ips.add(uip)
  end
end

class ClicksReducer
  def reduce(reducer_in=STDIN)
    # need to translate key from string to int because band is defined as int in test_resptimeband_reduced
    clickStats = {}
    reducer_in.each_line { |line|
      line.chomp!()
      fields = line.split("\t")
      #$stderr.puts "line : #{line}" 
      #$stderr.puts "f0=#{fields[0]} f1=#{fields[1]}"
      customerId = fields[0]
      ypid = fields[1]
      listingId = fields[2]
      tier = fields[3]
      uip = fields[4]
      key = [customerId, ypid].join("_")
      stats = clickStats[key]
      if stats == nil
        stats = Stats.new(customerId, ypid)
      end
      stats.countTierUip(listingId, tier, uip)
      clickStats[key] = stats
    }
    clickStats.each { |key,stats|
      if stats.count > 0
        puts [stats.customerId, stats.ypid, stats.pll.size, stats.diamond.size, stats.platinum.size, stats.gold.size, stats.silver.size, stats.bronze.size, stats.ips.size, stats.count].join("\t")
      end
    }
  end
end

# Only run the following code when this file is the main file being run
# instead of having been required or loaded by another file
if __FILE__ == $0 
  ClicksReducer.new.reduce()
end