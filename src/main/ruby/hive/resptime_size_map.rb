#!/usr/bin/ruby -w

class Mapper
  def map(mapper_in=STDIN)
    mapper_in.each_line { |line|
      line.chomp!()
#      puts "line : #{line}" if $DEBUG
#      $stderr.puts "line : #{line}" 
      fields = line.split("\t")
      resptime = fields[0]
      size = fields[1]
      puts [resptime, size].join("\t")
    }
  end
end

# Only run the following code when this file is the main file being run
# instead of having been required or loaded by another file
if __FILE__ == $0 
  Mapper.new.map()
end