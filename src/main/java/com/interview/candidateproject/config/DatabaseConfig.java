package com.interview.candidateproject.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Database Connection Pool Configuration
 * 
 * REVIEW TASK: This configuration needs to be reviewed and improved.
 * 
 * Current Issues (to be identified and fixed by the candidate):
 * 1. Connection pool size might not be optimal for production
 * 2. Some timeout values might be too aggressive or too lenient
 * 3. Missing some important HikariCP configuration options
 * 4. No connection validation configuration
 * 5. Missing monitoring and health check configurations
 * 6. Thread safety concerns in connection handling
 * 7. No failover or retry mechanism
 * 
 * TODO: Review and improve this configuration for production readiness
 */
@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    // TODO: Review these values - are they appropriate for production?
    @Value("${spring.datasource.hikari.maximum-pool-size:10}")
    private int maximumPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle:5}")
    private int minimumIdle;

    @Value("${spring.datasource.hikari.idle-timeout:300000}")
    private long idleTimeout;

    @Value("${spring.datasource.hikari.max-lifetime:1200000}")
    private long maxLifetime;

    @Value("${spring.datasource.hikari.connection-timeout:20000}")
    private long connectionTimeout;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        // Basic configuration
        config.setJdbcUrl(jdbcUrl);

        // SQLite doesn't use username/password, but set them if provided
        if (username != null && !username.trim().isEmpty()) {
            config.setUsername(username);
        }
        if (password != null && !password.trim().isEmpty()) {
            config.setPassword(password);
        }

        config.setDriverClassName(driverClassName);

        // Pool size configuration - TODO: Review these values
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);

        // Timeout configuration - TODO: Review these values
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);

        // TODO: Add connection validation
        // Missing: connectionTestQuery, validationTimeout

        // TODO: Add monitoring and metrics
        // Missing: metricsTrackerFactory, healthCheckRegistry

        // TODO: Add connection leak detection
        // The leak detection threshold is set in application.properties, but could be
        // improved

        // TODO: Add additional HikariCP optimizations
        // Missing: cachePrepStmts, prepStmtCacheSize, prepStmtCacheSqlLimit
        // Missing: useServerPrepStmts, useLocalSessionState, rewriteBatchedStatements

        // SQLite-specific configuration (removed MySQL SSL properties)
        // SQLite is file-based and doesn't need SSL configuration

        // Basic pool name for monitoring
        config.setPoolName("InterviewDB-Pool");

        return new HikariDataSource(config);
    }

    /**
     * TODO: Consider adding a separate read-only datasource for reporting queries
     * This could help with performance and load distribution
     */

    /**
     * TODO: Consider adding connection pool monitoring beans
     * This would help with observability in production
     */
}
