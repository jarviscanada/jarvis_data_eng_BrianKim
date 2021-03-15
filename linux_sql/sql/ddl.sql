-- Change the user to 'host_agent' database, if not connected.
\c host_agent;

-- Create a table to store hardware specification, if not created
-- PK is id
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
    (
        id                  SERIAL NOT NULL PRIMARY KEY,
        hostname            VARCHAR NOT NULL UNIQUE,
        cpu_number          INT NOT NULL,
        cpu_architecture    VARCHAR NOT NULL,
        cpu_model           VARCHAR NOT NULL,
        cpu_mhz             FLOAT NOT NULL,
        L2_cache            INT NOT NULL,
        total_mem           INT NOT NULL,
        timestamp           TIMESTAMP NOT NULL
    );

-- Create a table to store resource usage data, if not created
-- PK is timestamp. FK is host_id which refereces the host_info table
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
    (
        timestamp           TIMESTAMP NOT NULL PRIMARY KEY,
        host_id             INT NOT NULL REFERENCES host_info(id),
        memory_free         INT NOT NULL,
        cpu_idle            INT NOT NULL,
        cpu_kernel          INT NOT NULL,
        disk_id             INT NOT NULL,
        disk_available      INT NOT NULL
    );