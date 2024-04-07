# Random Joke Service

## Description
This Spring Boot application provides a single endpoint to fetch a random joke from an external API. The endpoint returns a JSON object containing the id and randomJoke fields.

## Getting Started
To get a copy of this project up and running on your local machine, follow these steps:

### Prerequisites
- Java Development Kit (JDK) version 17 or higher
- Maven

### Installation
1. Clone the repository:
````text
git clone <repository-url>
````
2. Navigate to the project directory:
````text
cd random-joke-service
````
3. Build the project using Maven:
````text
mvn clean install
````
4. Run the application:
````text
mvn spring-boot:run
````

## Usage
Once the application is running, you can access the endpoint via Swagger-UI `http://localhost:8080/swagger-ui/index.html`

### Endpoint
- **GET /api/v1/random-joke**: Fetches a random joke.
- curl http://localhost:8080/api/v1/random-joke

## Testing
This project includes unit and integration tests to ensure the correctness of the functionality. You can run the tests using Maven:
````text
mvn test
````




