# Getting Started

- System requirements
    - JDK 17
    - Maven
    - graphviz https://graphviz.org


-  Visualize dependencies: Clean and Hexagonal Architecture
  ```
     mvn depgraph:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.db.account.management*:*"
  ```
   target/dependency-graph.png

-  Clean and Hexagonal Architecture
  - account-management-domain - DDD 
  - account-management-dataaccess - output adapter 
  - account-management-application - input  adapter 
  - account-management-container - Spring container 

- H2 console
  http://localhost:8080/h2-console/
  ref to the application.properties 

- Run the application 
  - account-management-container  AccountManagementApplication main class

- Test the api with Postman collection 
  - account-management-application : DB account mangement.postman_collection.json