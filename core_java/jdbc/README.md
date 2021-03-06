# Introduction
This is a JDBC App that allows the Java program to create a connection to an RDBMS which stores sales information.
It utilizes the connection pooling to simplify JDBC flow and Data Access Object (DAO) Pattern to abstract the data persistence and the underlying queries.
CustomerDAO and OrdersDAO are implemented to perform CRUD operations against the tables in the database system.<br><br>
**Technologies used:** Java, JDBC, PostgreSQL, Maven, slf4j Logger, DBeaver, Intellij

# Implementation
## ER Diagram
![my image](./assets/er_diagram.png)

## Design Patterns
**DAO (Data Access Object) Pattern:** <br>
- DAO is a pattern used to abstract the data persistence in software that manages connections to databases.
When it's used as an interface, the input and output will be a Data Transfer Object. DTO is a fully encapsulated object which provides a single domain of data.
DAO effectively encapsulates complex joins, pass, and aggregations. As an abstraction layer, it hides the ugly business queries and only provides functionality to the user. 

**Repository Pattern:** <br>
- The Repository pattern focuses on single table access per class. It is a higher abstraction layer that exists over the DAO layer.
  Implementation of repositories might have different DAOs as its members. Repository pattern allows you to shard your database so that accessing a single table is easier than the whole database. Because of this, in distributed database systems, this pattern is helpful as your store and manage shards of database horizontally. 

# Test
The database was set up in a dockerized PostgreSQL instance. Inside the `JDBCExecutor` which is the class acting as the client, manual tests were done inside the `main` method.
Exceptions were logged and handled using the `slf4j` logger. Using CustomerDAO and OrdersDAO, DTO were returned and their outputs were displayed to compare results.
