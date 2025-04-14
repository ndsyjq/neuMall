package flyway;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import flyway.FlywayConfig;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataSourceConfig {
    private static DataSource dataSource;

    static {
        try {
            // 手动加载 MySQL 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/neumall");
        config.setUsername("root");
        config.setPassword("as876857");
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);

        FlywayConfig.migrate(dataSource);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}