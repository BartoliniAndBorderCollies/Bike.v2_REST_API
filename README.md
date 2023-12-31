# Rent a Bike REST API

## Purpose: 
The application is designed to manage a bike rental business, supporting both traditional and electric bikes. It’s built to be generic, meaning it can be adapted for other types of vehicles.

## Features:

The application supports a range of functionalities, including renting and returning bikes, calculating rental costs, and managing users. It also has specific actions for users, admins, bikes, and stations.

It can:

- rent a bike
- return a bike
- count rental cost per minute or per days and assign it to the specific user
  

  #### `User context actions`:
- add, delete, update user
- add feedback how you like the application
- add admin role with permission to get to restricted areas dedicated for admins and be able to:

  #### `Admin context actions`:
  - ban user
  - delete user
  - find all users
  - find specific user
  

  #### `Bike context actions`:
- add, delete, update, find a specific bike, find all bikes, find available bikes

  
  #### `Station context actions`:
- add, delete, update, find, a specific station, find all stations add a bike to a station


## Specification:

The application follows the MVC pattern and includes Java Doc documentation. It uses DTOs, Spring Security, and handles exceptions with client-readable responses. It also includes integration and unit tests, Jakarta validation, and uses MySQL for integration testing and H2 for the application database.

## Authors:

The application was developed by @BartoliniAndBorderCollies with mentorship from Szymon Jasinski.

- [@BartoliniAndBorderCollies](https://www.github.com/BartoliniAndBorderCollies)

My mentor:
- [Szymon Jasinski](https://github.com/JasinskiSz)

## Installation:

1. Download the project and import it to your IDE.
2. Setup MySQL server and create database for integration testing.
3. In application go to: src -> integration_test -> resources -> application-test.properties and update properties with your own data. This part is responsible for integration testing.
4. In application go to: src -> main -> resources -> application.properties and update properties with your own data. This part is for the application by itself.

## Stack:

The application is built using
- Java 17.0.6
- Spring Boot
- Hibernate
- Junit
- Mockito
- Spring Security
- Jakarta validation
- MySQL
- H2

## License:
I encourage you to use this application for your own purpose. If you decide to do so please contact me:

bartek.klodnicki@gmail.com
  
  

