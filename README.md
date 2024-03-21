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