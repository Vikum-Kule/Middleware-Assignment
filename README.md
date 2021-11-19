### Vikum Kulathunga
#### 18000835
#### SCS3203 / IS3108 Middleware Architecture
# Pet Store

## To build and deploy the API

Run the following commands and up the swagger and refer the End points

##### step 1:
run the command on terminal

    ./gradlew build -Dquarkus.package.type=uber-jar

##### step 2
run the command on terminal

    java -jar build/petStore-runner.jar

##### step 3
to find the End-points on here

    http://localhost:8080/swagger-ui/

## Run Test Suite
run the command on terminal

    ./gradlew test

## CURL/WGET commands

### Pets
Get the all the pets

    curl -X GET "http://localhost:8080/pets" -H  "accept: application/json"

Create a new pet

    curl -X POST "http://localhost:8080/pets/add" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petAge\":5,\"petName\":\"Sudda\",\"petType\":\"Dog\"}"

Update an existing pet

    curl -X PUT "http://localhost:8080/pets/update/4" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petAge\":3,\"petName\":\"Kuku\",\"petType\":\"Dog\"}"

Delete an existing pet

    curl -X DELETE "http://localhost:8080/pets/delete/7" -H  "accept: */*"

Search pets with name/type/age

    curl -X GET "http://localhost:8080/pets/search" -H  "accept: application/json" -d "{\"name\":\"Kuku\",\"age\":3,\"type\":\"Dog\"}"

Get pet by ID

    curl -X GET "http://localhost:8080/pets/3" -H  "accept: application/json"

### Pet Types

Get all pet types

    curl -X GET "http://localhost:8080/pets/type/all" -H  "accept: application/json"

Create a new pet type

    curl -X POST "http://localhost:8080/pets/type/add" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petType\":\"Cat\"}"

Update an existing pet type

    curl -X PUT "http://localhost:8080/pets/type/update/1" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"petType\":\"dogs\"}"

Delete an existing  pet type

    curl -X DELETE "http://localhost:8080/pets/type/delete/3" -H  "accept: application/json"


### Open Swagger

###### To launch API Doc,Run the project and route with below url

     http://localhost:8080/swagger-ui/

Exposes the information about your endpoints in the format of the OpenAPI v3 specification. Specification [here](https://microprofile.io/project/eclipse/microprofile-open-api)

The index page contains a link to the OpenAPI information of your endpoints.




#### Schemas
Pet Schema

    {
    "petAge": int,
    "petId": int,
    "petName": "string",
    "petType": "string" 
    }

PetType Schema

    {
    "typeId": int
    "petType": "string"
    }

Pet Request Schema

    {
    "petAge": int,
    "petName": "string",
    "petType": "string"
    }
    