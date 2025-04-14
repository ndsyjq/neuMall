package flyway;
import javax.sql.DataSource;  // 正确导入
// 而不是其他包中的 DataSource（如 com.zaxxer.hikari.DataSource）
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayConfig {
    private static final Logger logger = LoggerFactory.getLogger(FlywayConfig.class);

    public static void migrate(DataSource dataSource) {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .baselineOnMigrate(true)
                    .baselineVersion("0")
                    .locations("classpath:db/migration")
                    .load();

            MigrationInfoService info = flyway.info();
            MigrationVersion currentVersion = info.current() != null
                    ? info.current().getVersion()
                    : MigrationVersion.EMPTY;

            logger.info("Current DB version: {}", currentVersion);  // 使用占位符
            flyway.migrate();
            logger.info("Database migration completed");
        } catch (FlywayException e) {
            logger.error("Database migration failed", e);  // 添加异常参数
            throw new RuntimeException("Database migration failed", e);
        }
    }
}