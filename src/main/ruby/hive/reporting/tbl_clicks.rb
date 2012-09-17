# insert November data into skuo_clicks by partition

(1..30).each { |i|
  dd = "%02d" % i
  query = <<-HIVE_QUERY
  "INSERT OVERWRITE TABLE skuo_clicks partition (yyyy=2011, mm=11, dd=#{dd})
  select a.customer_id, a.ypid, a.listing_id, a.tier, ta.uip
  from traffic_adclick ta join advertiser_extract_vw a on ta.lid=a.listing_id 
  where ta.tq='0' and ta.cstm='1' and ta.yyyy=2011 and ta.mm=11 and ta.dd=#{dd} and a.ds=201111#{dd}"
  HIVE_QUERY
  puts query
  result = system("/home/t/hive_current/bin/hive -e #{query}")
  if result 
    puts "SUCCESS!!"
  else
    puts "FAILED"
  end
}

