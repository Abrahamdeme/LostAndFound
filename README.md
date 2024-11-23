# Lost and Found Management System
## Presentation Slides
- Description of the Project
The Lost and Found Management System is a RESTful API developed in Java using Spring Boot to manage the reporting and tracking of lost and found items. 
Users can report lost items, mark them as found, and update or delete their reports. 
The project implements secure user authentication and authorization with Spring Security and JWT tokens, ensuring that only authorized users can perform certain operations. 

# Class Diagram
![Classe UML](https://github.com/user-attachments/assets/cb2c1051-4601-4964-90e6-406c0eec54bb)



# Setup
Follow the steps below to set up the project on intellij idea

Clone the Repository

git clone https://github.com/Abrahamdeme/LostAndFound_final_P

Set Up MySQL Database

Create a database named lostandfound.
Update the application.properties file with your database credentials.
Run the Application

The application will run on http://localhost:8080.

# Technologies Used
Java 
Spring Boot 
Spring Security with JWT
Hibernate 
MySQL
Maven (Build Tool)
Lombok (Code Simplification)
Postmap

# Controllers and Routes Structure
## AuthController
- POST /api/auth/register
Register a new user.
- Payload: { "username": "user1", "password": "password123" }

- POST /api/auth/login
Authenticate a user and get a JWT token.
- Payload: { "username": "user1", "password": "password123" }
# ItemController
- POST /api/items
Add a new lost or found item.
- Payload:

json
Copy code
{
  "title": "Lost Phone",
  "description": "Black iPhone",
  "status": "LOST",
  "locationLost": "Park"
}
- GET /api/items
Get all items.

- GET /api/items/{status}
Get items by status (LOST or FOUND).

- PATCH /api/items/{id}
Update an item.
- Payload:

json
Copy code
{
  "title": "Updated Title"
}
- DELETE /api/items/{id}
Delete an item by its ID.



# Future Work
- Frontend Integration: Develop a user-friendly interface using React or Angular.
- Search Functionality: Implement a search feature for items based on keywords

# Team Members
Abraham DEME
