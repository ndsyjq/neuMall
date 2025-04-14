package flyway;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCTemplateSingleton {
    private static volatile JDBCTemplateSingleton instance ;
    private JdbcTemplate jdbcTemplate;

    private JDBCTemplateSingleton() {
        this.jdbcTemplate = DataSourceConfig.getJdbcTemplate();
    }
    public static JDBCTemplateSingleton getInstance() {
        if (instance == null) {
            synchronized (JDBCTemplateSingleton.class) {
                if (instance == null) {
                    instance = new JDBCTemplateSingleton();
                }
            }
        }
        return instance;
    }
}
