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

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        // Basic configuration
        config.setJdbcUrl(jdbcUrl);

        config.setDriverClassName(driverClassName);

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
