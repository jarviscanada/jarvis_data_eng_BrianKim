#!/bin/bash

# Check for correct number of arguments, give error and exit if not satisfied
if [ $# -ne 5 ]; then
    echo "Error: Please provide the correct number of arguments"
    echo "Args: psql_host psql_port db_name psql_user psql_password"
    exit 1
fi

# Save arguments into variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
export PGPASSWORD=$5

# Save memory usage cmd stout into variables
mem_info=`vmstat -S M`
mem_info_time=`vmstat -t`
disk_info=`vmstat -d`
disk_space=`df -BM /`

# Parse host hardware specifications into variables
hostname=$(hostname -f)
memory_free=$(echo "$mem_info" | awk '{ if(NR == "3") print $4;}')
cpu_idle=$(echo "$mem_info" | awk '{ if(NR == "3") print $15;}')
cpu_kernel=$(echo "$mem_info" | awk '{ if(NR == "3") print $14;}')
disk_io=$(echo "$disk_info" | awk '{ if(NR == "3") print $10;}')
disk_available=$(echo "$disk_space" | awk '{ if(NR == "2") print $4;}')
timestamp=$(echo "$mem_info_time" | awk '{ if(NR == "3") print $18, $19;}')

# Construct an INSERT statement which takes all the cpu/memory info as a single tuple for the host_usage table
# Execute insert sql statement using psql CLI tool
host_usage_insert="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) SELECT '$timestamp', id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available FROM host_info WHERE hostname = '$hostname';"
psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$host_usage_insert"