require File.join(File.dirname(__FILE__), '../..', 'main/ruby', 'person')
require 'test/unit'

class PersonTest < Test::Unit::TestCase
  def test_first_name
    person = Person.new('Nathaniel','Talbott',25)
    assert_equal 'Nathaniel', person.firstName
  end
  
  def test_last_name
    person = Person.new('Nathaniel','Talbott',25)
    assert_equal 'Talbott', person.lastName
  end
  
  def test_full_name
    person = Person.new('Nathaniel','Talbott',25)
    assert_equal 'Nathaniel Talbott', person.full_name
  end
  
  def test_age
    person = Person.new('Nathaniel','Talbott',25)
    assert_equal 25, person.age
  end
end