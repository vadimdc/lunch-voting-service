# Lunch Voting Service v 0.1.0

Simple Rest API Service for lunch voting

## Build this Service

### External requirements

* set JAVA_HOME variable to JDK 1.8 home folder
* add to PATH %JAVA_HOME%\bin
* set GRADLE_HOME variable to gradle 2.6+ home folder
* add to PATH %GRADLE_HOME%\bin
* MySql 5.+ should run on local machine
* create DB: CREATE DATABASE IF NOT EXISTS lunch_voting DEFAULT CHARACTER SET utf8mb4;

### Local deployment

* Checkout the source code.
* You can build this application as a standard Spring Boot application.
* Specify your DB credentials in `lunch-voting-service\src\main\resources\application.properties`
* In the root of the service run command

```
gradle bootRun
```

## Service API

### RESTAURANTS API (only ADMIN role access)

GET    /api/v1/restaurants - list of all restaurants
POST   /api/v1/restaurants - create restaurant
DELETE /api/v1/restaurants - delete all restaurants

GET    /api/v1/restaurants/{restaurantId} - get info about restaurant with specified id
PUT    /api/v1/restaurants/{restaurantId} - update restaurant info
DELETE /api/v1/restaurants/{restaurantId} - delete restaurant with specified id

```
    Curl examples:

        curl "http://localhost:8080/api/v1/restaurants" -u admin:admin
        curl -H "Content-Type: application/json" -X POST -d '{"restaurant": "restaurantName1"}' "http://localhost:8080/api/v1/restaurants" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/restaurants" -u admin:admin

        curl "http://localhost:8080/api/v1/restaurants/{restaurantId}" -u admin:admin
        curl -H "Content-Type: application/json" -X PUT -d '{"restaurant": "restaurantName2"}' "http://localhost:8080/api/v1/restaurants/{restaurantId}" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/restaurants/{restaurantId}" -u admin:admin
```


### DISHES API (only ADMIN role access)

GET    /api/v1/dishes - list of all dishes
POST   /api/v1/dishes - create dish
DELETE /api/v1/dishes - delete all restaurants

GET    /api/v1/dishes/{dishId} - get info about dish with specified id
PUT    /api/v1/dishes/{dishId} - update dish info
DELETE /api/v1/dishes/{dishId} - delete dish with specified id

```
    Curl examples:

        curl "http://localhost:8080/api/v1/dishes" -u admin:admin
        curl -H "Content-Type: application/json" -X POST -d '{"dish": "dishName1"}' "http://localhost:8080/api/v1/dishes" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/dishes" -u admin:admin

        curl "http://localhost:8080/api/v1/dishes/{dishId}" -u admin:admin
        curl -H "Content-Type: application/json" -X PUT -d '{"dish": "dishName2"}' "http://localhost:8080/api/v1/dishes/{dishId}" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/dishes/{dishId}" -u admin:admin
```


### MENU ITEMS API (only ADMIN role access)

GET    /api/v1/menu-items/restaurants/{restaurantId} - get all menu items for restaurant with specified id
POST   /api/v1/menu-items/restaurants/{restaurantId} - create menu item for restaurant with specified id. If dish is not present in system it is also being created

GET    /api/v1/menu-items           - get all menu items
DELETE /api/v1/menu-items           - delete all menu items

GET    /api/v1/menu-items/{itemId}  - get menu item  by specified id
DELETE /api/v1/menu-items/{itemId}  - delete menu item by specified id

```
    Curl examples:

        curl "http://localhost:8080/api/v1/menu-items/restaurants/{restaurantId}" -u admin:admin
        curl -H "Content-Type: application/json" -X POST -d '{"dish": "dishName1", "price": 5.0}' "http://localhost:8080/api/v1/menu-items/restaurants/{restaurantId}" -u admin:admin

        curl "http://localhost:8080/api/v1/menu-items" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/menu-items" -u admin:admin

        curl "http://localhost:8080/api/v1/menu-items/{itemId}" -u admin:admin
        curl -X DELETE "http://localhost:8080/api/v1/menu-items/{itemId}" -u admin:admin
```


### MENU API (any user access)

GET    /api/v1/lunch-menu - get lunch menu

```
    Curl examples:
        сurl "http://localhost:8080/api/v1/lunch-menu"
```


### VOTING API

GET    /api/v1/voting   - get current user vote    - any authorized role access
POST   /api/v1/voting   - vote                     - any authorized role access
DELETE /api/v1/voting   - delete all votes         - ADMIN role access

GET    /voting/results	- get all voting results   - any user access

```
    Curl examples:
        curl "http://localhost:8080/api/v1/voting" -u user1:user1
        curl -H "Content-Type: application/json" -X POST -d '{"restaurant": "restaurantName1"}' "http://localhost:8080/api/v1/voting" -u user1:user1
        curl -X DELETE "http://localhost:8080/api/v1/voting" -u admin:admin

        curl "http://localhost:8080/api/v1/voting/results"
```