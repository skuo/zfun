* log in to skynet and type "hive" at command line

hive> show databases;
hive> use default;
hive> show tables;

hive> show tables 'raw.*';
Lists all tables that start with 'raw'.
hive> show tables '.*s';
Lists all tables that end with 's'.

hive> describe invites;
hive> describe extended invites;
hive> show partitions invites;

hive> LOAD DATA INPATH '%s' OVERWRITE INTO TABLE access_log PARTITION (type='%s', ondate='%s');

hive> select * from access_log limit 10;
hive> select count(*) from access_log;

[bash] /home/t/hive_current/bin/hive -e "describe adsvc_imp_clk;"
[bash] hive -e "describe extended adsvc_imp_clk;"
[bash] hive -e "select count(*) from access_log where method = 'GET'";
[bash] hive -e "describe extended skuo_clicks partition (yyyy='2011', mm='11', dd='30')"
[bash] hive -f test.sql > test.out | tee test.outhive
[bash] hive -e "ALTER TABLE test_resptimeband_reduced ADD COLUMNS(count BIGINT)";

# These commands work
[bash] hive -f map.hsql
[bash] hive -f map1.hsql
[bash] hive -f mapred.hsql
[bash] hive -f resptimeband_mapred.hsql

# debugging hive ruby streaming
cat access_log_small.data | ruby resptimeband_map.rb | ruby resptimeband_red.rb