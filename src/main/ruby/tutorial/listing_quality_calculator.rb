#!/usr/bin/ruby -w
#
# Ruby script that executes the workflow for 1) querying hive traffic tables for all impression
# records that satisfy certain criteria, and 2) calculate the multiplier based on the z-score
# calculated from the statistics of impressions, clicks and predicted probability of clicks.
#
# @author xshi
#

$: << File.dirname( __FILE__)
$: << "/home/t/ypad/conf/listing-quality-calculator-3.3.0.0"

require 'date'
require 'logger'
require 'net/smtp'
require 'java_props.rb'

JAR_FILE_PATH = "/home/t/lib/java/listing-quality-calculator-jar-with-dependencies.jar"
MAIN_CLASS = "com.atti.adsvc.listingquality.App"
LOG_FILE = "/home/t/ypad/logs/listing-quality-calculator-3.3.0.0/listing-quality-calculator.log"

# set HSTACK related env variables
ENV['HADOOP_HOME'] = '/home/t/hadoop_current'
ENV['HIVE_HOME'] = '/home/t/hive_current'
ENV['JAVA_HOME'] = '/home/t/jdk_current'
      
# execute the supplied Hive query through "hive -f" command
def execute_hive_query(query)
  $log.debug "executing hive query: #{query}"

  # temporary hive query file
  hive_sql_file = "/tmp/hive_listing_quality_calculator.sql"

  # write the query to the temp file
  File.open(hive_sql_file, 'w') { |f|
    f.puts query
  }

  # execute the query thru "hive -f" and capture the return status
  return system("/home/t/hive_current/bin/hive -f #{hive_sql_file}")
end

# upload a given file to a remote server via curl
def upload(http_post_url, remote_folder, local_file)
  $log.info "curl -F file=@#{local_file} -F directory=#{remote_folder} #{http_post_url}"
  return system("curl -F file=@#{local_file} -F directory=#{remote_folder} #{http_post_url}")
end

# check if there are valid files in HDFS folder
def hdfs_folder_empty?(hdfs_folder, filename_pattern = nil)
  output = `/home/t/hadoop_current/bin/hadoop fs -count "#{hdfs_folder}/#{filename_pattern.nil? ? "" : filename_pattern}"`
    
    if (output.nil? || output.empty?)
      return true
    else 
      dir_count, file_count, file_size_sum, dir_path = output.split
      if (file_count.to_i <= 0 || file_size_sum.to_i <= 0) then
        return true
      end
    end
    return false
end

# construct date condition
def day_cond(t, start_day, end_day)
  cond = ""
  if (start_day.month == end_day.month) then
  cond = "#{t}.yyyy = #{start_day.year} AND #{t}.mm = #{start_day.month} AND #{t}.dd >= #{start_day.day} AND #{t}.dd <= #{end_day.day}"
  else
    start_day_end_of_month = DateTime.new(start_day.year, start_day.month, -1).day
  cond = 
"(  (#{t}.yyyy = #{start_day.year} AND #{t}.mm = #{start_day.month} AND #{t}.dd >= #{start_day.day} AND #{t}.dd <= #{start_day_end_of_month})
          OR (#{t}.yyyy = #{end_day.year} AND #{t}.mm = #{end_day.month} AND #{t}.dd >= 1 AND #{t}.dd <= #{end_day.day}))"
  end
end

# send alert emails with the sender/receiver/subject/message specified
def send_email(from, from_alias, to, to_alias, subject, message)
  hostname = Socket.gethostname
  msg = <<END_OF_MESSAGE
From: #{from_alias} <#{from}>
To: #{to_alias} <#{to}>
Subject: #{hostname} - #{subject}
        
#{message}
END_OF_MESSAGE

  $log.info "[#{from}] #{hostname} - #{subject}"

  Net::SMTP.start('localhost', 25) do |smtp|
    smtp.send_message(msg, from, to)
  end
end


# get current host name
$hostname = Socket.gethostname

# logger
$log = Logger.new("#{LOG_FILE}", 'weekly')  # TODO: make it rotate by files

# default start/end dates
today = DateTime.now

# pass the options
options = {
  :today => today,
  :min_occurrences => 1000, 
  :sample_size => 0,
  :num_reducers => 50, 
  :skip_hive => false,
  :skip_hadoop_job => false,
  :env => "dev"
}

if __FILE__ == $0
  require 'optparse'

  # parse the options
  ARGV.options do |opts|
    script_name = File.basename($0)
    opts.banner = "Usage: ruby #{script_name} [options]"

    opts.separator ""

    # today
    opts.on("-d", "--today today", String,
    "Specify the today's date (YYYY-MM-DD).",
    "Default: today"
    ) { |d| options[:today] = DateTime.parse(d) }

    opts.separator ""

    # sample size
    opts.on("-n", "--size sample_size", String,
    "Specify the sample size (integer).",
    "Default: 0 (no sampling)"
    ) { |n| options[:sample_size] = n.to_i }
      
    opts.separator ""
    
    # min_occurrences
    opts.on("-l", "--occurrence min_occurrences", String,
    "Specify the minimum occurrences of impressions per listing (integer).",
    "Default: 1000"
    ) { |l| options[:min_occurrences] = l.to_i }
      
    opts.separator ""
    
    # num_reducers
    opts.on("-r", "--reducers num_reducers", String,
    "Specify the number of reducer jobs (integer).",
    "Default: 10"
    ) { |r| options[:num_reducers] = r.to_i }
      
    opts.separator ""

    # environment
    opts.on("-v", "--env environment", String,
    "Specify the environment (string).",
    "Default: dev"
    ) { |options[:env]| }

    opts.separator ""
    
    # skip-hive
    opts.on("--skip-hive", String,
    "Skip hive query stage and use loaded data directly",
    "Default: false"
    ) { |options[:skip_hive]| options[:skip_hive] = true }

    opts.separator ""

    # skip-hadoop-job
    opts.on("--skip-hadoop-job", String,
    "Skip hadoop job stage ",
    "Default: false"
    ) { |options[:skip_hadoop_job]| options[:skip_hadoop_job] = true }

    opts.separator ""
    # help message
    opts.on("-h", "--help", "Show this help message.") { puts opts; exit }

    opts.parse!
  end
end

############################################################
$log.info options.inspect

CONFIG_FILE = "/home/t/ypad/conf/listing-quality-calculator/#{options[:env]}/lqc.properties"
# read the env-specific config properties
$configs = JavaProps.new(CONFIG_FILE)

today_str = options[:today].strftime("%Y_%m_%d") 
start_day = options[:today] - 28
end_day = options[:today] -1
$log.debug "today=#{options[:today]} start_day=#{start_day}; end_day=#{end_day}"
input_dir = "listing_quality/impressions/#{today_str}"
output_dir = "listing_quality/multipliers/#{today_str}"

$log.info "input_dir=#{input_dir}"
$log.info "output_dir=#{output_dir}"

$success = 0
$msg = "The listing quality calculator job finished successfully"


# hive query stage
unless options[:skip_hive]
  # remove and recreate the input and output dir
  $log.info "removing and re-creating input_dir and output_dir"
  system("/home/t/hadoop_current/bin/hadoop fs -rmr #{input_dir}")
  system("/home/t/hadoop_current/bin/hadoop fs -rmr #{output_dir}")
  system("/home/t/hadoop_current/bin/hadoop fs -mkdir #{input_dir}")
  system("/home/t/hadoop_current/bin/hadoop fs -mkdir #{output_dir}")
  
  # load data into hive table
  $success = execute_hive_query( <<END_OF_QUERY
    INSERT OVERWRITE DIRECTORY '#{input_dir}' 
    SELECT i.rid, i.lid, i.ypid, i.yyyy, i.mm, i.dd, i.hh, i.ts, i.s, i.x_app_tg, c.rid, c.tq, c.cstm 
    FROM traffic_search_result i LEFT OUTER JOIN traffic_adclick c ON (i.rid = c.rid AND i.lid = c.lid 
      #{day_cond("c", start_day, end_day)}  
      )
    WHERE 
      #{day_cond("i", start_day, end_day)}
      AND i.aid = 'adsvc' AND (i.tq IS NULL OR i.tq = '0') AND i.cstm = '1';
END_OF_QUERY
  )
  
  if !$success then
    $msg = "Hive query failed"
    $log.error "Hive query failed"
  end
  
  # sleep for a while for it to finish
  sleep 5
end


# check input HDFS folder
if $success then
  # verify that there are valid output data in the input directory
  if (hdfs_folder_empty?("#{input_dir}"))
    $success = 1
    $msg = "Input HDFS folder is empty"
    $log.error "Input HDFS folder is empty"
  end
end
  
# hadoop job stage
unless options[:skip_hadoop_job]
  if $success then
    # passing thuplorough the command-line argument to the java class
    cmd = "/home/t/hadoop_current/bin/hadoop jar #{JAR_FILE_PATH} #{MAIN_CLASS} -i #{input_dir} -o #{output_dir} -l #{options[:min_occurrences]} -r #{options[:num_reducers]}"
    $log.debug "basic cmd: #{cmd}"

    if options[:sample_size] > 0
      $success = system("#{cmd} --sampling -n #{options[:sample_size]}")
    else
      $success = system("#{cmd}")
    end

    if !$success then
      $msg = "Hadoop job failed"
      $log.error "Hadoop job failed"
    end

    # sleep for a while for it to finish
    sleep 5

    # verify that there are valid output data in the output directory
    if (hdfs_folder_empty?("#{output_dir}", "part-*")) then
      $success = 1
      $msg = "No non-empty output files found in HDFS"
      $log.info "No non-empty output files found in HDFS"
    end

  end
end

# file uploading stage
if $success then
  # merge all the hadoop output files into one single local file
  local_merged_file = "/tmp/ctr.properties"
  output = `/home/t/hadoop_current/bin/hadoop fs -cat "#{output_dir}/part-*" | awk '{printf "%s=%s\\n", $1, $2;}' > "#{local_merged_file}"`
  
  # upload the generated file to BDS
  $success = upload($configs.get("bds.upload.path"), "/listing-quality", local_merged_file) 
  if !$success then
    $msg = "Failed to upload file #{local_merged_file} to remote BDS server"
    $log.error "Failed to upload file #{local_merged_file} to remote BDS server"
  end
  
  # verify the uploaded file
  # TODO:
  
  # cleanup temporary files
  if $success then
    #system("rm -r #{local_merged_file}")
  end
end

# send the alert email
if $success then
  subject = "Listing quality calculator job finished"
  message = "Listing quality calculator job has finished today"
else
  subject = "Listing quality calculator job failed"
  message = $msg
end
send_email($configs.get("alert.email.from"), 'Listing Quality Calculator Notification', $configs.get("alert.email.to"), 'Astroman Folks', "#{subject}" , "#{message}" )
