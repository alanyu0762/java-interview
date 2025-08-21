# Spring Boot Candidate Interview Project

This is a Spring Boot project designed for candidate interviews. It contains a RESTful API for managing users, orders, and products with intentional gaps for candidates to fill in.

## Project Overview

The project simulates an e-commerce system with the following entities:
- **Users**: Customer information
- **Orders**: Purchase orders placed by users
- **Products**: Items available for purchase
- **OrderItems**: Individual items within an order

## Database Setup

### Prerequisites
- MySQL 8.0 or higher
- Java 17 or higher
- Maven 3.6 or higher

### Database Installation and Setup

1. **Install MySQL** (if not already installed):
   - Download from [MySQL Official Website](https://dev.mysql.com/downloads/)
   - Install with default settings

2. **Create Database and User**:
   ```sql
   -- Connect to MySQL as root
   mysql -u root -p
   
   -- Run the setup script
   source sql/setup.sql
   ```

3. **Alternative: Run the SQL script directly**:
   ```bash
   mysql -u root -p < sql/setup.sql
   ```

### Database Configuration

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/interview_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### Using Maven
```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Start the application
mvn spring-boot:run
```

### Using IDE
1. Import the project as a Maven project
2. Run `CandidateProjectApplication.java` as a Java application

The application will start on `http://localhost:8080`

## Interview Tasks

### Task 1: Implement User Search Functionality
**Location**: `UserService.searchUsersByName()` method

**Requirements**:
- Implement case-insensitive user search by first name or last name
- Support partial matches
- Use the existing repository method `findByNameContaining`

**Test Endpoint**: `GET /api/users/search?name={searchTerm}`

### Task 2: Implement User Validation
**Location**: `UserService.validateUser()` method

**Requirements**:
- Username must be at least 3 characters long
- Email must be valid format (use regex or existing validation)
- First name and last name must not be empty
- Username and email must be unique in the database
- Return `true` if valid, `false` otherwise

**Test Endpoint**: `POST /api/users/validate`

### Task 3: Review and Improve Connection Pool Configuration
**Location**: `DatabaseConfig.java`

**Issues to identify and fix**:
1. Connection pool sizing for production workloads
2. Timeout configuration optimization
3. Missing connection validation settings
4. Lack of monitoring and health checks
5. Missing MySQL-specific optimizations
6. No connection leak prevention measures

**Areas to improve**:
- Add connection test queries
- Configure statement caching
- Add metrics and monitoring
- Optimize for MySQL specifically
- Add connection validation
- Configure appropriate timeouts for production

## API Endpoints

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `GET /api/users/active` - Get active users only
- `GET /api/users/search?name={name}` - Search users by name (Task 1)
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `POST /api/users/validate` - Validate user data (Task 2)

### Order Management
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by order number
- `GET /api/orders/user/{userId}` - Get orders by user ID
- `GET /api/orders/status/{status}` - Get orders by status
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}/status?status={status}` - Update order status

## Sample API Calls

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "email": "test@example.com",
  "firstName": "Test",
  "lastName": "User"
}'
```

### Search Users (Task 1)
```bash
curl "http://localhost:8080/api/users/search?name=john"
```

### Validate User (Task 2)
```bash
curl -X POST http://localhost:8080/api/users/validate \
-H "Content-Type: application/json" \
-d '{
  "username": "tu",
  "email": "invalid-email",
  "firstName": "",
  "lastName": "User"
}'
```

## Testing

The project includes basic tests that verify the application context loads correctly even with incomplete methods.

Run tests:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/interview/candidateproject/
│   │   ├── config/          # Database configuration (Task 3)
│   │   ├── controller/      # REST controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic (Tasks 1 & 2)
│   │   └── CandidateProjectApplication.java
│   └── resources/
│       └── application.properties
├── test/
└── sql/
    └── setup.sql           # Database setup script
```

## Evaluation Criteria

Candidates will be evaluated on:
1. **Code Quality**: Clean, readable, and well-structured code
2. **Problem-Solving**: Correct implementation of business logic
3. **Best Practices**: Following Spring Boot and Java conventions
4. **Performance**: Efficient database queries and connection pooling
5. **Error Handling**: Proper exception handling and validation
6. **Testing**: Understanding of testing principles (bonus)

## Notes for Interviewers

- The application is designed to run without the candidate implementations
- Incomplete methods throw `UnsupportedOperationException` with clear messages
- API endpoints return appropriate HTTP status codes for unimplemented features
- The connection pool configuration has intentional issues for discussion
- All database operations work correctly except for the candidate tasks

## Common Issues and Solutions

### MySQL Connection Issues
- Ensure MySQL is running on port 3306
- Verify username/password in application.properties
- Check if the database `interview_db` exists

### Port Already in Use
- Change `server.port` in application.properties
- Or stop other applications using port 8080

### Maven Build Issues
- Ensure Java 17 is installed and configured
- Check internet connection for dependency downloads
- Try `mvn clean install -U` to force update dependencies
