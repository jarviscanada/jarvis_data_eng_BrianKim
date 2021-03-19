# Linux Cluster Monitoring Agent

# Introduction
This project implements automating monitoring agent which observes a cluster of Linux nodes/servers and record data from the nodes. The users are clusters of Linux servers internally connected by a switch which communicates using internal IPv4 addresses. This monitoring agent will capture the hardware specifications of each nodes and periodically insert node resource usuage into a RDBMS database. Using this monitoring agent and data it collects, you can solve important business problems involving resource planning such as increasing and decreasing number of nodes depending on the relative CPU/Memory usages.<br/>
Technologies used: <br/>
* Linux 
* GitFlow
* Bash Script
* Docker
* Postgresql

# Quick Start
Use markdown code block for your quick start commands
* Provision a docker psql instance on host computer by executing `psql_docker.sh`
```bash
    bash ./scripts/psql_docker.sh create|start|stop [username] [password]
```
* Create tables which will contain the hardware specifications and resource usages using `ddl.sql`
```bash
    psql -h localhost -U [username] -d [database] -f sql/ddl.sql
```
* Collect and insert host device hardware info into the `host_info` table
```bash
    bash ./scripts/host_info.sh localhost 5432 [database] [username] [password]
```
* Collect and insert host device cpu/memory usage into the `host_usage` table
```bash
    bash ./scripts/host_usage.sh localhost 5432 [database] [username] [password]
```
* Automate the host_usage.sh script with `crontab` CLI tool
```
    # Open crontab job file
    crontab -e
    
    # Add this task to the list and save
    * * * * * bash [full_directory_path]/host_usage.sh localhost 5432 [database] [username] [password] > /tmp/host_usage.log
```
# Implementation
- Inside one of the hosts in the Linux cluster, provision and run a PostgreSQL database instance containerizing it with docker using `psql_docker.sh`
- `host_info.sh` will gather the host hardware specification using `vmstat`, `df`, and `lscpu` and insert it into the PostgreSQL instance
- `host_usage.sh` will collect the real time cpu/memory resource usage of the host device and insert it into the PostgreSQL instance
  - using `crontab` the host will automate the insertion of usage data, every 1 minute.
## Architecture
![my image](./assets/linux_sql_architecture.png)

## Scripts
Shell script descirption and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
How did you test your bash scripts and SQL queries? What was the result?

# Improvements
Write at least three things you want to improve 
e.g. 
- handle hardware update 
- blah
- blah
