class Duck
  def quack
    puts 'Quack!'
  end
end

class Goose
  def honk
    puts 'Honk!'
  end
  def swin
    puts 'Splash splash splash..'
  end
end

class DuckRecording
  def quack
    play
  end
  def play
    puts 'QuackRecoding!'
  end
end

def make_it_quack(duck)
  duck.quack
end
make_it_quack(Duck.new)
make_it_quack(DuckRecording.new)
#make_it_quack(Goose.new)  # ERROR

class Frog
  attr_reader :name
  attr_accessor :speaks_english
  def initialize(name)
    @name = name
  end
  def speak
    @speaks_english ||= @name.size > 6
    @speaks_english ? "HI. I'm #{@name}, the talking frog." : "Ribbit."
  end
end
puts Frog.new("Leonard").speak()
lucas = Frog.new("Lucas")
puts lucas.speak()
puts lucas.name
puts lucas.speaks_english

class Warning
  @@translations = { :en => "Wet Floor", :es => "Piso Mojado" }
  def initialize (language=:en)
    @language = language
  end
  def warn
    puts @@translations[@language]
  end
end
Warning.new.warn
Warning.new(:es).warn

# reopen String class to add a new method. 
# Only do this if the method is generally helpful and it does not conflict 
# with a method defined by some library you include in the future
class String
  def scramble
    split(//).sort_by { rand }.join
  end
end
puts "I once was a normal string.".scramble

# subclass
class UnpredictableString < String
  def scramble
    split(//).sort_by { rand }.join
  end
  def inspect
    scramble.inspect
  end
end
str = UnpredictableString.new("It was a dark and storming night.")
puts str
puts str.scramble

class Rectangle
  def initialize(*args) # accept variable number of arguments
    case args.size
    when 2
      @top_left, @bottom_right = args
    when 3
      @top_left, length, width = args
      @bottom_right = [@top_left[0] + length, @top_left[1] - width]
    else
      raise ArgumentError, "This method takes either 2 or 3 arguments"
    end
  end
end
Rectangle.new([10,23], [14,13])
Rectangle.new([10,23], 4, 10)
#Rectangle.new

class Name
  attr_reader :first, :last
  def first=(first)
    if first == nil or first.size == 0
      raise ArgumentError.new('Everyone must have a first name')
    end
    first = first.dup
    first[0] = first[0].chr.capitalize
    @first = first
  end
  def last=(last)
    if last == nil or last.size == 0
      riase ArgumentError.new('Everyone must have a last name')
    end
    @last = last
  end
  def full_name
    "#{@first} #{@last}"
  end
  def initialize(first, last)
    self.first = first
    self.last = last
  end
end
jacob = Name.new('Jacob', 'Berendes')
jacob.first = 'Mary Sue'
puts jacob.full_name
john = Name.new('john', 'von Newmann')
puts john.full_name
