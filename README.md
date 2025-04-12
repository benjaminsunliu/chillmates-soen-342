# SOEN 342 

### Contributors:
- **Benjamin LIU**  
  Student ID: 40280899

- **Jordan YEH**  
  Student ID: 40283075

### Design Patterns Used:
- **Singleton**: Used for Catalogs to ensure only one instance of each catalog exists.
- **Data Mapper**: Used to map data between the database and the application.
- **Layered Architecture**: The application is structured into layers, separating concerns and improving maintainability.

### Architecture:
- **Console Application**: The application is a console-based application that interacts with the user through the command line.
- **Domain Layer**: Contains the core business logic and classes from the domain model.
- **Persistence Layer**: Contains the classes responsible for data access and storage, using the Data Mapper pattern.

### Project Details:
This Java-based application provides a console-driven interface for managing:

-  **Service Requests** between Clients and Experts
-  **Institutions** and their Objects of Interest
-  **Auction Houses** and **Auction Scheduling**
-  **Viewings** for scheduled auctions

It supports three roles:

- **Administrator**: Full access to all management features
- **Expert**: Can manage availability and accept/cancel service requests
- **Client**: Can view and create service requests based on availability

#  Features

- **Role-based login & registration**
- **Expert availability & time slot management**
- **Bidirectional linking between clients, experts, and service requests**
- **Auction scheduling with date/time conflict checking**
- **Viewings with start/end datetime validation**
- **Institutional object linking to auctions**

---

##  Technologies Used

- Java 17+
- JPA (Jakarta Persistence API)
- Hibernate ORM
- H2 / other compatible RDBMS (via `persistence.xml`)
- Console-based UI

##  Running the Project

###  Prerequisites

- Java 17+
- Maven

###  Build and Run
- Seed the database with the `src/main/java/app/DBSeeder.java` file.
- Run the application using the `src/main/java/app/Driver.java` file.
