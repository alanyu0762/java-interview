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

## Project Structure
```
src/main/java/com/interview/candidateproject/
├── config/
│   └── DatabaseConfig.java        # Task 3: Connection pool config
├── controller/
│   ├── UserController.java        # REST endpoints
│   └── OrderController.java
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
- Task 1: 15-20 minutes
- Task 2: 20-25 minutes  
- Task 3: 25-30 minutes
- **Total**: 60-75 minutes

## Additional Notes
- The application uses H2 database for testing (no setup required for tests)
- All existing functionality works correctly
- Focus on production-ready code, not just working code
- Ask questions if requirements are unclear
