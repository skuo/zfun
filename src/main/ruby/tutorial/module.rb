require 'set'

# include for mixin in class
module Taggable
  attr_accessor :tags
  def taggable_setup
    @tags = Set.new
  end
  def add_tag(tag)
    @tags << tag
  end
  def remove_tag(tag)
    @tags.delete(tag)
  end
end

class TaggableString < String
  include Taggable
  def initialize(*args)
    super
    taggable_setup
  end
end

s = TaggableString.new('It was the best of times, it was the worse of time.')
s.add_tag('dickens')
s.add_tag 'quotation'
puts s.tags.inspect

# extends for mixin in object
class Person
  attr_reader :name, :age, :occupation
  def initialize(name, age, occupation)
    @name, @age, @occupation = name, age, occupation
  end
  def mild_manner?
    true
  end
end
jimmy = Person.new("Jimmy Olsen", 21, "cub reporter")
clark = Person.new("Clark Kent", 35, "reporter")
puts jimmy.mild_manner?
puts clark.mild_manner?
module SuperPowers
  def fly
    "Flying"
  end
  def leap(what)
    "Leaping #{what} is a single bound!"
  end
  def mild_manner?
    false
  end
  def superhero_name
    'Superman'
  end
end
clark.extend(SuperPowers)
puts clark.superhero_name
puts jimmy.mild_manner?
puts clark.mild_manner?

# namespace
module StringTheory
  class String
    def initialize(length=10**-3)
      @length = length
    end
  end
end
puts String.new
puts StringTheory::String.new

