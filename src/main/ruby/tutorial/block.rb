#
log = lambda { |str| puts "[LOG] #{str}" }
log.call("A test log message.")

def my_lambda(&aBlock)
  aBlock
end

b = my_lambda { puts "Hello World My Way!" }
b.call

def repeat(n)
  if (block_given?)
    n.times { yield }
  else
    raise ArgumentError.new("I can't repeat a block you don't give me!")
  end
end
repeat(4) { puts "hello." }
#repeat(4)
 
def repeat_block(n, &block)
  n.times { yield } if block
end  
repeat_block(2) { puts "hello 2."  }

class Hash
  def find_all
    new_hash = Hash.new
    each { |k,v| new_hash[k] = v if yield(k,v) }
    new_hash
  end
end

squares = {0=>0, 1=>1, 2=>4, 3=>9}
puts squares.find_all { |key, value| key > 1 }
  