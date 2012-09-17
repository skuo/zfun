#!/usr/bin/ruby -w
#
# Ruby class the parses Java-style properties files and cache the configurations.
#
# @author xshi
#

$: << File.dirname( __FILE__)

# representing a Java-style properties file
# TODO: 1) better to keep the order of the property keys?
# TODO: 2) read-only mode to prevent accidental modifications
class JavaProps
  attr :file, :properties
  
  # Takes a file and loads the properties in that file
  def initialize(file)
    @file = file
    @properties = {}
    IO.foreach(file) do |line|
      # skip the commented lines
      next if line =~ /^\s*?#/
      @properties[$1.strip] = $2 if line =~ /([^=]*)=(.*)\/\/(.*)/ || line =~ /([^=]*)=(.*)/
    end
  end

  # Convert to string
  def to_s
    output = "#File Name #{@file} \n"
    @properties.each {|key,value|
      output += "#{key}= #{value} \n"
    }
    return output
  end

  # Write a property
  def write(key,value)
    @properties[key] = value
  end

  # Retrieve a property
  def get(key)
    return @properties[key]
  end

  # Save the properties back to file
  def save
    file = File.new(@file,"w+")
    @properties.each {|key,value| file.puts "#{key}=#{value}\n" }
    file.close
  end
end