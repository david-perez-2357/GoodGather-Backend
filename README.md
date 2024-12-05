![Group 101](https://github.com/user-attachments/assets/afbee22d-5376-4fd9-8c1e-d06c7ab9fe9b)

# GoodGather API
GoodGather is a charitable app where users can discover, create, and attend events for social causes. It connects people with meaningful initiatives, promoting donations and community support.

## 🚀 Getting Started
### Prerequisites
Before getting started, make sure you have the following installed:
- **Java** >= version 17
- **Database:** PostgreSQL or compatible SQL database
- **JDK:** Coretto 21
- **Build tool:** Maven

## Installation
1. **Clone the Repository**

   Clone the project repository to your local machine:

   ```bash
   git clone https://github.com/david-perez-2357/GoodGather-Backend.git
   ```

2. **Set Up Environment Variables**

   To configure the necessary environment variables, follow these steps (example for IntelliJ IDEA):

    - In the top right corner, click on the application name > Edit Configurations.

    - Click on Modify options and enable the "Environment variables" checkbox.

    - In the Environment variables input, click the last icon to open the input field.

    - Add the following environment variables with their name and value:

      | Variable de Entorno      | Descripción                                        |
      |--------------------------|----------------------------------------------------|
      | JDBC_DATABASE_URL        | Url to acces the local database                    |
      | JDBC_DATABASE_USERNAME   | Username of the user to access the database        |
      | JDBC_DATABASE_PASSWORD   | Password of the user to access the database        |

## 🚪 API Endpoints

### Authentication Routes

- **POST** `/api/v1/auth/register`  
  Registers a new user.

- **POST** `/api/v1/auth/authenticate`  
  Authenticates a user and returns a JWT token.

- **POST** `/api/v1/auth/logout`  
  Logs out a user and invalidates the JWT token.

- **GET** `/api/v1/auth/user`  
  Fetches the current authenticated user based on the JWT token.

### Cause Routes

- **GET** `/cause`  
  Fetches a list of all causes.

- **GET** `/cause/{id}`  
  Fetches a cause by its ID.

- **GET** `/cause/{id}/funds`  
  Fetches the funds for a specific cause by its ID.

- **GET** `/cause/{id}/events`  
  Fetches a list of events for a specific cause by its ID.

- **GET** `/cause/user/{userId}`  
  Fetches a list of causes within a user's range by user ID.

- **POST** `/cause`  
  Creates a new cause.

### Client Routes

- **GET** `/client`  
  Fetches a list of all clients.

- **GET** `/client/{id}`  
  Fetches a client by its ID.

### Event Routes

- **GET** `/event`  
  Fetches a list of all events.

- **GET** `/event/all`  
  Fetches a list of all events without any filters.

- **GET** `/event/{id}`  
  Fetches an event by its ID.

- **POST** `/event`  
  Creates a new event.

### Ticket Routes

- **GET** `/ticket/byUser/{userId}`  
  Fetches a list of tickets by user ID.

- **GET** `/ticket/byEvent/{eventId}/boughtInLast/24h`  
  Fetches the number of tickets bought in the last 24 hours for a specific event by event ID.

- **GET** `/ticket/byEvent/{eventId}/byUser/{userId}`  
  Fetches a list of tickets for a specific event by event ID and user ID.

- **POST** `/ticket`  
  Creates a new ticket.

