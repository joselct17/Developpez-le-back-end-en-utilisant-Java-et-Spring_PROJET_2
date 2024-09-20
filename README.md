# Estate

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.

# Project Structure
This project is structured in two main parts:

## Front-end: 
>The Angular-based front-end that interacts with the back-end API.

Back-end: 
>The Spring Boot-based back-end that handles business logic and data persistence with MySQL.

# Front-end structure:
src/app: 
>Contains all Angular components, services, and models.

src/environments: 
>Configuration files for different environments (development, production).

# Back-end structure:
src/main/java: 
>Contains the main Java code for the Spring Boot application.

src/main/resources:
>Contains configuration files like application.properties and SQL scripts.

src/main/resources/sql: 
>Folder containing the SQL schema creation script.

## Start the project

Git clone:

> git clone https://github.com/joselct17/Developpez-le-back-end-en-utilisant-Java-et-Spring_PROJET_2

## Front-End
Go inside folder:

> cd P3-Full-Stack-portail-locataire

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

## Back-end

Prerequisites:
Install Java JDK 11 or higher: Download JDK
Install Maven: Download Maven
Install MySQL: Download MySQL


Go inside folder: 

>cd back_end

Install dependencies:

>mvn clean install

Launch Back-End

>mvn spring-boot:run

## Ressources

### Postman collection

For Postman import the collection

> ressources/postman/rental.postman_collection.json 

by following the documentation: 

https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman


### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`


## API Documentation

>http://localhost:8080/swagger-ui/index.html


