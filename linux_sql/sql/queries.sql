-- Group hosts by CPU number and sort by their memory size in descending order
-- within each cpu_number group
select
  cpu_number,
  id,
  total_mem
from
  host_info
order by
  cpu_number,
  total_mem desc;

-- Create a function which round up/down each timestamps to its nearest
-- 5 minute interval
create function round_five(ts TIMESTAMP) returns TIMESTAMP as
$$
	begin
		return date_trunc('hour', ts) + date_part('minute', ts):: INT / 5 * interval '5 minutes';
	end;
$$
	language PLPGSQL;

-- Average used memory in percentage over 5 mins intervals for each host
select
  t.host_id,
  hi.hostname,
  t.ts_rounded,
  t.avg_used_percentage
from
  (
    select
      hu.host_id,
      round_five(hu.timestamp) as ts_rounded,
      round(
        avg(
          (
            hi.total_mem - hu.memory_free * 1000
          ) * 1.0 / hi.total_mem * 100
        )
      ) as avg_used_percentage
    from
      host_usage hu
      join host_info hi on hu.host_id = hi.id
    group by
      ts_rounded,
      hu.host_id
  ) t
  join host_info hi on t.host_id = hi.id
order by
  t.ts_rounded;

-- Query that detects host failures during crontab job
-- Assume that server is failed when it inserts less than 3 data entries within 5min interval
select
  *
from
  (
    select
      hu.host_id,
      round_five(hu.timestamp) as ts,
      count(*) as num_data_points
    from
      host_usage hu
    group by
      ts,
      hu.host_id
    order by
      ts
  ) t
where
  t.num_data_points < 3; 