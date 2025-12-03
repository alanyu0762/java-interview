# Candidate Interview Tasks

## Project Overview
This Spring Boot project has been created for candidate interviews. It contains a working e-commerce system with some intentionally incomplete methods for candidates to implement.

## Tasks for Candidates

### Task 1: Implement User Search Method
**File**: `src/main/java/com/interview/candidateproject/service/UserService.java`
**Method**: `searchUsersByName(String name)`

**Requirements**:
- Implement case-insensitive search for users by first name or last name
- Support partial matches (e.g., "john" should match "John Doe")
- Use the existing repository method `findByNameContaining`
- Return a list of matching users

**Testing**:
- Endpoint: `GET /api/users/search?name={searchTerm}`
- Example: `curl "http://localhost:8080/api/users/search?name=john"`

### Task 2: Implement User Validation Method
**File**: `src/main/java/com/interview/candidateproject/service/UserService.java`
**Method**: `validateUser(User user)`

**Requirements**:
- Username must be at least 3 characters long
- Email must be valid format (consider using regex or Spring validation)
- First name and last name must not be empty or null
- Username must be unique in the database
- Email must be unique in the database
- Return `true` if all validations pass, `false` otherwise

**Testing**:
- Endpoint: `POST /api/users/validate`
- Example: `curl -X POST http://localhost:8080/api/users/validate -H "Content-Type: application/json" -d '{"username":"ab","email":"invalid","firstName":"","lastName":"Test"}'`

### Task 3: Review and Improve Connection Pool Configuration
**File**: `src/main/java/com/interview/candidateproject/config/DatabaseConfig.java`

**Issues to identify and address**:
1. **Pool Sizing**: Are the current pool sizes appropriate for production?
2. **Connection Validation**: Missing connection test queries
3. **MySQL Optimizations**: Missing MySQL-specific settings
4. **Monitoring**: No metrics or health checks configured
5. **Statement Caching**: Missing prepared statement cache configuration
6. **Timeouts**: Review if timeout values are appropriate
7. **Connection Leak Detection**: Could be improved

**Specific improvements to implement**:
- Add connection test query (`SELECT 1`)
- Configure statement caching (`cachePrepStmts`, `prepStmtCacheSize`)
- Add MySQL-specific optimizations (`useServerPrepStmts`, `rewriteBatchedStatements`)
- Configure appropriate validation timeout
- Add monitoring capabilities
- Review and justify pool size settings

---

## Product Management Tasks

### Task 4: Implement Product Search Method
**File**: `src/main/java/com/interview/candidateproject/service/ProductService.java`
**Method**: `searchProductsByName(String name)`

**Requirements**:
- Implement case-insensitive search for products by name
- Support partial matches (e.g., "phone" should match "iPhone 15")
- Use the existing repository method `findByNameContainingIgnoreCase`
- Return a list of matching products

**Testing**:
- Endpoint: `GET /api/products/search?name={searchTerm}`
- Example: `curl "http://localhost:8080/api/products/search?name=phone"`

### Task 5: Implement Price Range Filter Method
**File**: `src/main/java/com/interview/candidateproject/service/ProductService.java`
**Method**: `getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice)`

**Requirements**:
- Validate that minPrice is not negative
- Validate that maxPrice is not negative
- Validate that minPrice is less than or equal to maxPrice
- Throw `IllegalArgumentException` with descriptive message if validation fails
- Use the existing repository method `findByPriceBetween`
- Return a list of products within the price range

**Testing**:
- Endpoint: `GET /api/products/price-range?minPrice={min}&maxPrice={max}`
- Example: `curl "http://localhost:8080/api/products/price-range?minPrice=10&maxPrice=100"`
- Invalid: `curl "http://localhost:8080/api/products/price-range?minPrice=100&maxPrice=10"` (should return 400)

### Task 6: Implement Stock Update Method
**File**: `src/main/java/com/interview/candidateproject/service/ProductService.java`
**Method**: `updateStockQuantity(Long productId, Integer newQuantity)`

**Requirements**:
- Find the product by ID (throw `RuntimeException` if not found)
- Validate that newQuantity is not negative (throw `IllegalArgumentException`)
- Update the stock quantity and save the product
- Return the updated product

**Testing**:
- Endpoint: `PATCH /api/products/{id}/stock?quantity={newQuantity}`
- Example: `curl -X PATCH "http://localhost:8080/api/products/1/stock?quantity=50"`
- Invalid: `curl -X PATCH "http://localhost:8080/api/products/1/stock?quantity=-5"` (should return 400)

---

## Order Management Tasks

### Task 7: Implement Cancel Order Method
**File**: `src/main/java/com/interview/candidateproject/service/OrderService.java`
**Method**: `cancelOrder(Long orderId)`

**Requirements**:
- Find the order by ID (throw `RuntimeException` with message "Order not found" if not found)
- Only orders with status PENDING or CONFIRMED can be cancelled
- If order status is SHIPPED, DELIVERED, or already CANCELLED, throw `IllegalStateException` with message "Order cannot be cancelled in current state: {currentStatus}"
- Update the status to CANCELLED and save the order
- Return the updated order

**Testing**:
- Endpoint: `POST /api/orders/{id}/cancel`
- Example: `curl -X POST "http://localhost:8080/api/orders/1/cancel"`
- Invalid (already shipped): Should return 400 with error message

### Task 8: Implement Revenue Calculation Method
**File**: `src/main/java/com/interview/candidateproject/service/OrderService.java`
**Method**: `calculateRevenueInDateRange(LocalDateTime startDate, LocalDateTime endDate)`

**Requirements**:
- Validate that startDate is not null (throw `IllegalArgumentException`)
- Validate that endDate is not null (throw `IllegalArgumentException`)
- Validate that startDate is before or equal to endDate (throw `IllegalArgumentException`)
- Only include orders with status DELIVERED
- Sum up the totalAmount of all matching orders
- Return `BigDecimal.ZERO` if no orders match

**Testing**:
- Endpoint: `GET /api/orders/revenue?startDate={start}&endDate={end}`

**Test Cases with Expected Results** (using sample data):
| Date Range | Expected Revenue | Explanation |
|------------|------------------|-------------|
| Q1 2024 (Jan-Mar) | $2,500.00 | 3 delivered orders |
| Q2 2024 (Apr-Jun) | $3,200.00 | 3 delivered orders |
| Q3 2024 (Jul-Sep) | $4,500.00 | 3 delivered orders |
| Q4 2024 (Oct-Dec) | $5,750.00 | 3 delivered orders |
| Full Year 2024 | $15,950.00 | 12 delivered orders |
| Jan 2025 (no data) | $0.00 | No delivered orders in range |

**Example Requests**:
```bash
# Q1 2024 revenue (expect: 2500.00)
curl "http://localhost:8080/api/orders/revenue?startDate=2024-01-01T00:00:00&endDate=2024-03-31T23:59:59"

# Full year 2024 revenue (expect: 15950.00)
curl "http://localhost:8080/api/orders/revenue?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59"

# Invalid: startDate after endDate (should return 400)
curl "http://localhost:8080/api/orders/revenue?startDate=2024-12-31T00:00:00&endDate=2024-01-01T00:00:00"
```

### Task 9: Implement Recent Orders Method
**File**: `src/main/java/com/interview/candidateproject/service/OrderService.java`
**Method**: `getRecentOrdersByUser(Long userId, int limit)`

**Requirements**:
- Validate that userId is not null (throw `IllegalArgumentException` with message "User ID cannot be null")
- Validate that limit is positive (throw `IllegalArgumentException` with message "Limit must be positive")
- Get all orders for the user
- Sort by orderDate in descending order (most recent first)
- Return only the first 'limit' orders

**Testing**:
- Endpoint: `GET /api/orders/user/{userId}/recent?limit={limit}`

**Test Data Summary** (User 1 - john_doe):
User 1 has **18 total orders** spanning 2024-2025. Most recent orders:

| Order # | Date | Amount | Status |
|---------|------|--------|--------|
| ORD-U1-2025-10 | 2025-04-01 12:00 | $50.00 | CANCELLED |
| ORD-U1-2025-09 | 2025-03-25 09:30 | $899.00 | PENDING |
| ORD-U1-2025-08 | 2025-03-15 15:45 | $175.50 | DELIVERED |
| ORD-U1-2025-07 | 2025-03-01 10:20 | $320.00 | SHIPPED |
| ORD-U1-2025-06 | 2025-02-18 13:00 | $599.99 | CONFIRMED |

**Test Cases**:
| Request | Expected Result |
|---------|-----------------|
| User 1, limit=3 | Returns 3 most recent orders (ORD-U1-2025-10, 09, 08) |
| User 1, limit=5 | Returns 5 most recent orders |
| User 1, limit=100 | Returns all 18 orders (limit exceeds total) |
| User 2, limit=5 | Returns 5 most recent orders for User 2 |
| User 1, limit=0 | Returns 400 Bad Request |
| User 1, limit=-1 | Returns 400 Bad Request |
| userId=null | Returns 400 Bad Request |

**Example Requests**:
```bash
# Get 3 most recent orders for user 1
curl "http://localhost:8080/api/orders/user/1/recent?limit=3"

# Get 5 most recent orders for user 1
curl "http://localhost:8080/api/orders/user/1/recent?limit=5"

# Invalid: limit=0 (should return 400)
curl "http://localhost:8080/api/orders/user/1/recent?limit=0"

# Invalid: negative limit (should return 400)
curl "http://localhost:8080/api/orders/user/1/recent?limit=-1"
```

---

## Additional User Management Tasks

### Task 10: Implement User Deactivation Method
**File**: `src/main/java/com/interview/candidateproject/service/UserService.java`
**Method**: `deactivateUser(Long userId)`

**Requirements**:
- Find the user by ID (throw `RuntimeException` with message "User not found" if not found)
- Check if user is already inactive (throw `IllegalStateException` with message "User is already inactive")
- Set the user's active status to false
- Save and return the updated user

**Testing**:
- Endpoint: `POST /api/users/{id}/deactivate`
- Example: `curl -X POST "http://localhost:8080/api/users/1/deactivate"`
- Invalid (already inactive): Should return 400 with error message

### Task 11: Implement Email Update Method
**File**: `src/main/java/com/interview/candidateproject/service/UserService.java`
**Method**: `updateUserEmail(Long userId, String newEmail)`

**Requirements**:
- Find the user by ID (throw `RuntimeException` with message "User not found" if not found)
- Validate that the new email is not null or empty (throw `IllegalArgumentException`)
- Validate that the new email is in valid format using regex (throw `IllegalArgumentException`)
- Check if another user already has this email (throw `IllegalArgumentException` with message "Email already in use")
- Update the email and save the user
- Return the updated user

**Email Regex Hint**: `^[A-Za-z0-9+_.-]+@(.+)$`

**Testing**:
- Endpoint: `PATCH /api/users/{id}/email?email={newEmail}`
- Example: `curl -X PATCH "http://localhost:8080/api/users/1/email?email=newemail@example.com"`
- Invalid format: `curl -X PATCH "http://localhost:8080/api/users/1/email?email=invalid"` (should return 400)
- Already in use: Should return 400 with "Email already in use" message

---

## Project Structure
```
src/main/java/com/interview/candidateproject/
├── config/
│   └── DatabaseConfig.java        # Task 3: Connection pool config
├── controller/
│   ├── UserController.java        # REST endpoints for users
│   ├── ProductController.java     # REST endpoints for products
│   └── OrderController.java       # REST endpoints for orders
├── entity/
│   ├── User.java                  # User entity
│   ├── Order.java                 # Order entity
│   ├── Product.java               # Product entity
│   └── OrderItem.java             # Order item entity
├── repository/
│   ├── UserRepository.java        # User data access
│   ├── OrderRepository.java       # Order data access
│   └── ProductRepository.java     # Product data access
├── service/
│   ├── UserService.java           # Tasks 1 & 2: Incomplete methods
│   ├── ProductService.java        # Tasks 4, 5 & 6: Incomplete methods
│   └── OrderService.java          # Complete service
└── CandidateProjectApplication.java
```

## Running the Application

1. **Setup Database**:
   ```bash
   mysql -u root -p < sql/setup.sql
   ```

2. **Update Configuration**:
   Edit `src/main/resources/application.properties` with your MySQL credentials

3. **Run Application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Test API**:
   Application will be available at `http://localhost:8080`

## Expected Implementation Timeline
- Task 1: 15-20 minutes (User Search)
- Task 2: 20-25 minutes (User Validation)
- Task 3: 25-30 minutes (Connection Pool Review)
- Task 4: 10-15 minutes (Product Search)
- Task 5: 15-20 minutes (Price Range Filter)
- Task 6: 15-20 minutes (Stock Update)
- Task 7: 15-20 minutes (Cancel Order)
- Task 8: 20-25 minutes (Revenue Calculation)
- Task 9: 15-20 minutes (Recent Orders)
- Task 10: 10-15 minutes (User Deactivation)
- Task 11: 20-25 minutes (Email Update)
- **Total**: 160-215 minutes (~3-3.5 hours)

## Task Difficulty Levels
| Task | Difficulty | Skills Tested |
|------|------------|---------------|
| Task 1 | Easy | Repository usage, basic search |
| Task 2 | Medium | Validation, regex, database checks |
| Task 3 | Medium | Configuration, HikariCP, database optimization |
| Task 4 | Easy | Repository usage, basic search |
| Task 5 | Medium | Validation, exception handling |
| Task 6 | Easy | CRUD operations, validation |
| Task 7 | Medium | State machine logic, exception handling |
| Task 8 | Medium | Stream API, filtering, aggregation |
| Task 9 | Medium | Stream API, sorting, limiting |
| Task 10 | Easy | State validation, CRUD operations |
| Task 11 | Medium | Regex validation, uniqueness checks |

## Additional Notes
- The application uses H2 database for testing (no setup required for tests)
- All existing functionality works correctly
- Focus on production-ready code, not just working code
- Ask questions if requirements are unclear
