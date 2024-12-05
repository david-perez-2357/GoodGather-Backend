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

      | Enviroment variable      | Description                                        |
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

## 🗃️ Database
The name of the database **must** be `goodgather` and the lenguage is `PostgreSQL`

```SQL
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS cause;
DROP TABLE IF EXISTS appuser;

-- Appuser table
CREATE TABLE IF NOT EXISTS appuser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Cause table
CREATE TABLE IF NOT EXISTS cause (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    image VARCHAR(500) NOT NULL,
    scope INT NOT NULL DEFAULT 0,
    deleted INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_owner INT NOT NULL,
    CONSTRAINT fk_cause_owner FOREIGN KEY (id_owner) REFERENCES appuser (id)
);

-- Client table
CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birthdate DATE NOT NULL,
    country VARCHAR(50) NOT NULL,
    province VARCHAR(50) NOT NULL,
    id_user INT NOT NULL,
    CONSTRAINT fk_client_user FOREIGN KEY (id_user) REFERENCES appuser (id)
);

-- Event table
CREATE TABLE IF NOT EXISTS event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    image VARCHAR(500) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    capacity INT NOT NULL DEFAULT 100,
    address VARCHAR(100) NOT NULL,
    province VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    ticket_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    deleted INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_owner INT NOT NULL,
    id_cause INT NOT NULL,
    CONSTRAINT fk_event_user FOREIGN KEY (id_owner) REFERENCES appuser (id),
    CONSTRAINT fk_event_cause FOREIGN KEY (id_cause) REFERENCES cause (id)
);

-- Ticket table
CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    amount INT NOT NULL DEFAULT 1,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_event INT NOT NULL,
    id_user INT NOT NULL,
    CONSTRAINT fk_ticket_event FOREIGN KEY (id_event) REFERENCES event(id),
    CONSTRAINT fk_ticket_user FOREIGN KEY (id_user) REFERENCES appuser (id)
);
```

## 👷 Developers
- [@MarioCastroRamirez](https://github.com/MarioCastroRamirez)
- [@david-perez-2357](https://github.com/david-perez-2357)
- [@jorgeariasmartin](https://github.com/jorgeariasmartin)
- [@Pablo-R-B](https://github.com/Pablo-R-B)
