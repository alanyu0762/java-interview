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
- Java 17 or higher
- Maven 3.6 or higher

### Database Configuration

This project uses **SQLite** as the database, which requires no installation or setup. The database file `interview_db.db` is automatically created when the application starts.

The SQLite database is pre-configured in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:sqlite:interview_db.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
```

### Initialize Sample Data (Optional)

To populate the database with sample data for testing:
```bash
sqlite3 interview_db.db < sql/setup_sqlite.sql
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

The application will start on `http://localhost:8180`

## Interview Tasks

This project contains **11 candidate tasks** across different services. See `CANDIDATE_TASKS.md` for detailed requirements and test cases.

### User Management Tasks

#### Task 1: Implement User Search Functionality
**Location**: `UserService.searchUsersByName()` method

**Requirements**:
- Implement case-insensitive user search by first name or last name
- Support partial matches
- Use the existing repository method

**Test Endpoint**: `GET /api/users/search?name={searchTerm}`

#### Task 2: Implement User Validation
**Location**: `UserService.validateUser()` method

**Requirements**:
- Username must be at least 3 characters long
- Email must be valid format (use regex or existing validation)
- First name and last name must not be empty
- Username and email must be unique in the database
- Return `true` if valid, `false` otherwise

**Test Endpoint**: `POST /api/users/validate`

#### Task 10: Implement User Deactivation
**Location**: `UserService.deactivateUser()` method

**Requirements**:
- Find user by ID (throw `RuntimeException` if not found)
- Check if user is already inactive (throw `IllegalStateException`)
- Set active status to false and save

**Test Endpoint**: `POST /api/users/{id}/deactivate`

#### Task 11: Implement Email Update
**Location**: `UserService.updateUserEmail()` method

**Requirements**:
- Validate email format using regex
- Check email uniqueness
- Update and return the user

**Test Endpoint**: `PATCH /api/users/{id}/email?email={newEmail}`

### Product Management Tasks

#### Task 4: Implement Product Search
**Location**: `ProductService.searchProductsByName()` method

**Requirements**:
- Case-insensitive search for products by name
- Support partial matches

**Test Endpoint**: `GET /api/products/search?name={searchTerm}`

#### Task 5: Implement Price Range Filter
**Location**: `ProductService.getProductsByPriceRange()` method

**Requirements**:
- Validate price range (non-negative, min <= max)
- Return products within the price range

**Test Endpoint**: `GET /api/products/price-range?minPrice={min}&maxPrice={max}`

#### Task 6: Implement Stock Update
**Location**: `ProductService.updateStockQuantity()` method

**Requirements**:
- Validate quantity is non-negative
- Update and return the product

**Test Endpoint**: `PATCH /api/products/{id}/stock?quantity={newQuantity}`

### Order Management Tasks

#### Task 7: Implement Cancel Order
**Location**: `OrderService.cancelOrder()` method

**Requirements**:
- Only PENDING or CONFIRMED orders can be cancelled
- Throw appropriate exceptions for invalid states

**Test Endpoint**: `POST /api/orders/{id}/cancel`

#### Task 8: Implement Revenue Calculation
**Location**: `OrderService.calculateRevenueInDateRange()` method

**Requirements**:
- Validate date range
- Sum totalAmount of DELIVERED orders only

**Test Endpoint**: `GET /api/orders/revenue?startDate={start}&endDate={end}`

#### Task 9: Implement Recent Orders
**Location**: `OrderService.getRecentOrdersByUser()` method

**Requirements**:
- Sort orders by date descending
- Limit results to specified count

**Test Endpoint**: `GET /api/orders/user/{userId}/recent?limit={limit}`

### Configuration Task

#### Task 3: Review and Improve Connection Pool Configuration
**Location**: `DatabaseConfig.java`

**Issues to identify and fix**:
1. Connection pool sizing for production workloads
2. Timeout configuration optimization
3. Missing connection validation settings
4. Lack of monitoring and health checks
5. No connection leak prevention measures

**Areas to improve**:
- Add connection test queries
- Configure statement caching
- Add metrics and monitoring
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
- `POST /api/users/{id}/deactivate` - Deactivate user (Task 10)
- `PATCH /api/users/{id}/email?email={email}` - Update user email (Task 11)

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/available` - Get available products
- `GET /api/products/low-stock?threshold={n}` - Get low stock products
- `GET /api/products/categories` - Get all categories
- `GET /api/products/search?name={name}` - Search products by name (Task 4)
- `GET /api/products/price-range?minPrice={min}&maxPrice={max}` - Filter by price (Task 5)
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `PATCH /api/products/{id}/stock?quantity={qty}` - Update stock (Task 6)

### Order Management
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/number/{orderNumber}` - Get order by order number
- `GET /api/orders/user/{userId}` - Get orders by user ID
- `GET /api/orders/status/{status}` - Get orders by status
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}/status?status={status}` - Update order status
- `POST /api/orders/{id}/cancel` - Cancel order (Task 7)
- `GET /api/orders/revenue?startDate={start}&endDate={end}` - Calculate revenue (Task 8)
- `GET /api/orders/user/{userId}/recent?limit={n}` - Get recent orders (Task 9)

## Sample API Calls

### Create a User
```bash
curl -X POST http://localhost:8180/api/users \
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
curl "http://localhost:8180/api/users/search?name=john"
```

### Validate User (Task 2)
```bash
curl -X POST http://localhost:8180/api/users/validate \
-H "Content-Type: application/json" \
-d '{
  "username": "tu",
  "email": "invalid-email",
  "firstName": "",
  "lastName": "User"
}'
```

### Search Products (Task 4)
```bash
curl "http://localhost:8180/api/products/search?name=laptop"
```

### Filter Products by Price Range (Task 5)
```bash
curl "http://localhost:8180/api/products/price-range?minPrice=10&maxPrice=100"
```

### Update Stock Quantity (Task 6)
```bash
curl -X PATCH "http://localhost:8180/api/products/1/stock?quantity=50"
```

### Cancel Order (Task 7)
```bash
curl -X POST "http://localhost:8180/api/orders/1/cancel"
```

### Calculate Revenue (Task 8)
```bash
curl "http://localhost:8180/api/orders/revenue?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59"
```

### Get Recent Orders (Task 9)
```bash
curl "http://localhost:8180/api/orders/user/1/recent?limit=5"
```

## Testing

The project includes basic tests that verify the application context loads correctly even with incomplete methods. Tests use H2 in-memory database.

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
│   │   ├── service/         # Business logic (Tasks 1, 2, 4-11)
│   │   └── CandidateProjectApplication.java
│   └── resources/
│       └── application.properties
├── test/
└── sql/
    ├── setup.sql            # MySQL database setup script
    └── setup_sqlite.sql     # SQLite database setup script
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
- The project uses SQLite by default (no external database required)

## Common Issues and Solutions

### Port Already in Use
- Change `server.port` in application.properties (default: 8180)
- Or stop other applications using port 8180

### Maven Build Issues
- Ensure Java 17 is installed and configured
- Check internet connection for dependency downloads
- Try `mvn clean install -U` to force update dependencies

### Database Issues
- SQLite database file `interview_db.db` is created automatically
- To reset data, delete the file and restart the application
- Run `sql/setup_sqlite.sql` to populate with sample data
