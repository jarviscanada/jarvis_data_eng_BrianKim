Table of contents
* [Introduction](#Introduction)
* [Quick Start](#Quick Start)
* [Implementation](#Implementation)
* [Test](#Test)
* [Deployment](#Deployment)
* [Improvements](#Improvements)

# Introduction
- This is a SpringBoot Trading Application for Jarvis which provides a new trading platform that replaces the legacy trading app. Because the previous app had monolithic architecture, 
which was hard to maintain and scale, this new system will provide microservice architecture and SpringBoot framework for efficiency.
- This application is a RESTful API and can be consumed by various clients such as web/mobile applications. Http end points are used to help manage trader profile, accounts, and trade securities.
- **Technologies Used**: Java, SpringBoot, REST API, Apache Tomcat, PostgreSQL, MVP, Docker, Maven, Swagger-Ui, Postman

# Quick Start
- Prequiresites: Docker, CentOS 7
- Docker scripts with description
	- build images
  - create a docker network
  - start containers
- Try trading-app with SwaggerUI (screenshot)

# Implementation
## Architecture
- ![Diagram](asset/trading.png)
- Components/Serivices
  - Controller layer: In controller layer, user inputs are parsed and appropriate methods are called based on the REST API end point.
  The endpoints are annotated in the class and method-level in order for the Tomcat WebServlet to redirect and call to.
  - Service layer: In service layer, the business logics are encapsulated. User input passed as arguments are validated and exceptions are handled if any error occurs.
  - DAO layer: In dao layer, HttpClient and DataSource(JDBC) objects are managed to connect to Web service or Database. CRUD operations and complex queries are contained. 
  - SpringBoot: SpringBoot groups all controller/service/dao layers into one maintainable system. As part of Springboot framework, Apache Tomcat/WebServlet listens for HTTP Requests
  and calls the appropriate controller method based on the end points. Also, the IoC container maintains and inject dependencies ie. `@Component`, `@Service`, etc
  - PSQL and IEX: PSQL is the persistent data storage which consists of all the data tables. DataStore(JDBC) object communicates and runs CRUD operations on them.
  IEX is a web service that uses REST API to provide stock information in JSON format, so that the users can consume and deserialize for their uses.

## REST API Usage
### Swagger
- Swagger UI help to visualize and interact with the API’s resources. It’s automatically generated from your OpenAPI (formerly known as Swagger) Specification, with the visual documentation making it easy for back end implementation and client side consumption.
### Quote Controller
- Quote Controller is used to get market data from the IEX Cloud as well as updating and persisting them in the PSQL database instance as dailyList.
- Endpoints:
  - GET `/quote/iex/ticker/{ticker}`: Show iexQuote for a given ticker/symbol
  - PUT `/quote/iexMarketData`: Update quote table using iex data
  - PUT `/quote/`: Update a given quote in the quote table
  - POST `/quote/tickerId/{tickerId}`: Add a new ticker to the dailyList (quote table)
  - GET `/quote/dailyList`: Show the dailyList
### Trader Account Controller
- Trader Account Controller is used to manage trader and account information which are persisted in PSQL instance. It can deposit and withdraw fund from a given trader account. 
- Endpoints:
  - POST `/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}`: Create a trader and an account
  - DELETE `/trader/traderId/{traderId}`: Delete a trader
  - PUT `/trader/deposit/traderId/{traderId}/amount/{amount}`: Deposit a fund
  - PUT `/trader/withdraw/traderId/{traderId}/amount/{amount}`: Withdraw a fund

# Test 
Integration tests were ran against the Dao, and Service layers within the IntelliJ IDE.
Endpoints of the Controller REST APIs are tested via Swagger-ui, Postman and Linux `curl` cmd.

# Deployment
- docker diagram including images, containers, network, and docker hub
e.g. https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- describe each image in details (e.g. how psql initialize tables)

# Improvements
If you have more time, what would you improve?
- at least 3 improvements