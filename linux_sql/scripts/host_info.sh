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

# Save cpu/mem info cmd stout into variables
cpu_info=`lscpu`
mem_info=`vmstat -s`

# Parse host hardware specifications into variables
hostname=$(hostname -f)
cpu_number=$(echo "$cpu_info"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$cpu_info"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$cpu_info"  | egrep "^Model name:" | awk '{print $3,$4,$5}' | xargs)
cpu_mhz=$(echo "$cpu_info"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$cpu_info"  | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(echo "$mem_info" | egrep "total memory$" | awk '{print $1}' | xargs)
timestamp=$(TZ=UTC date +'%Y-%m-%d %H:%M:%S')

# Construct an INSERT statement which takes all the hardware info as a single tuple for the host_info table
# Execute insert sql statement using psql CLI tool
host_info_insert="INSERT INTO host_info VALUES (DEFAULT, '$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, '$l2_cache', $total_mem, '$timestamp');"
psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$host_info_insert"