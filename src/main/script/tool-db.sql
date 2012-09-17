# look at tdb_kiwi reporting stat

select * from report_urls;

INSERT INTO lwes_int_stats_daily (partner, colo, ts, rollup, stat_key, stat_value)
    SELECT partner, colo, ts, rollup, stat_key, stat_value
    FROM lwes_int_stats WHERE rollup = 'tdb_kiwi'; 

select * from lwes_int_stats where rollup = 'tdb_kiwi';

select * from lwes_int_stats_daily where rollup = 'tdb_kiwi'