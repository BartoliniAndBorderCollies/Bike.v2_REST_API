# Bike REST API

This application is a RESTful application for renting bikes, both traditional and electric. The goal was to develop a generic application that can be easily adapted for other types of vehicles as well.

## Features

Application has full functionality to run the renting bike buisness. It can:

- rent a bike
- return a bike
- count rental cost per minute or per days and assign it to the specific user
  

  ### `User actions`
- add, delete, update user
- add feedback how you like the application
- add admin role with permission to get to restricted areas dedicated for admins and be able to:

  ### `Admin actions`
  - ban user
  - delete user
  - find all users
  - find specific user
  

  ### `Bike actions`
- add, delete, update, find a specific bike, find all bikes, find available bikes

  
  ### `Station actions`
- add, delete, update, find, a specific station, find all stations add a bike to a station


## Specification

What this application has?

- MVC pattern
- it is created as generic application with easy to plug in other types of vehicles (not just bikes)
- full documentation in Java Doc
- DTOs
- Spring Security
- Exception handling with a readable response to the client
- integration and unit tests
- Jakarta validation
- mySql database for integration testing and H2 database for the application itself

  ## Authors

- [@BartoliniAndBorderCollies](https://www.github.com/BartoliniAndBorderCollies)

My mentor:
- [Szymon Jasinski](https://github.com/JasinskiSz)

## Stack
- Java 17.0.6
- Spring Boot
- Hibernate
- Junit
- Mockito
- Spring Security
- Jakarta validation
- mySQL
- H2
  
  

