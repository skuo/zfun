#=====
# Main
#=====
mogwaiUrl = "http://mogwai:7700/log.tdb.access.wc1"
date = "20110801"
server = "prod282.wc1"
tmpfile = "/tmp/tmpfile"

cmd = "curl #{mogwaiUrl}?prefix=#{date} > #{tmpfile}"
system(cmd)
# read and download
open(tmpfile).each { |line|
  line.strip!
  tokens = line.split("|")
  out = tokens[0].match(server)
  if (out)
    cmd = "wget #{mogwaiUrl}/#{tokens[0]} -O /tmp/#{tokens[0]}; /bin/gunzip -f /tmp/#{tokens[0]}"
    puts cmd
    system(cmd)
  end
}
