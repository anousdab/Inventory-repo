# Abstract

This is a simple API made with Java Spring Boot and postgreSQL for an inventory tracking web application.
It supports 1 inventory.

# Features

- Basic CRUD Functionalities
- Create inventory items
-  Edit Them
- Delete Them
- View a list of them

- When deleting, allow deletion comments and undeletion
- Export inventory items data to a CSV for one or many items.

# Routes

-http://localhost:8080/ for the web application
- GET http://localhost:8080/inventory to get all inventory items
- GET http://localhost:8080/inventory?is_deleted=true to get all deleted inventory items
- GET http://localhost:8080/inventory/download to download all inventory items into a CSV
- GET http://localhost:8080/inventory/itemID/download to download an item into a CSV
- PUT http://localhost:8080/inventory/itemID to update an item
- POST http://localhost:8080/inventory/itemID to create an item
- DELETE http://localhost:8080/inventory/itemID to delete an item



# Deployed App

https://blooming-depths-69566.herokuapp.com/

# How to run it with docker

CD src/main/docker 

docker-compose up

# How to run it with maven

mvn spring-boot:run


# Possible improvement
- Add other inventory
- Unit tests
- Api documentation


