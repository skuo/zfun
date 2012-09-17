#!/usr/bin/ruby -w

class ResptimebandMapper
  def map(mapper_in=STDIN)
    mapper_in.each_line { |line|
      line.chomp!()
#      puts "line : #{line}" if $DEBUG
#      $stderr.puts "line : #{line}" 
      fields = line.split("\t")
      resptime = fields[0]
      i = resptime.to_i
      band = "0_50"
      if i <= 50
        band = "0_50"
      elsif i <= 100
	band = "51_100"
      elsif i <= 200
	band = "101_200"
      else
        band = "201_inf"
      end
      puts [band, resptime].join("\t")
    }
  end
end

# Only run the following code when this file is the main file being run
# instead of having been required or loaded by another file
if __FILE__ == $0 
  ResptimebandMapper.new.map()
end