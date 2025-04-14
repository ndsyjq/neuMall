package DAO;// UserDaoImpl.java
import DAO.UserDao;
import DAO.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setLocked(rs.getBoolean("locked"));
        user.setLoginAttempts(rs.getInt("login_attempts"));
        return user;
    };
    public User findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public User findByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, username);
        } catch (EmptyResultDataAccessException e) {
            return null; // 用户不存在时返回null
        }
    }

    @Override
    public void updateLoginAttempts(String username, int attempts) {
        String sql = "UPDATE users SET login_attempts = ? WHERE username = ?";
        jdbcTemplate.update(sql, attempts, username);
    }

    @Override
    public void lockUser(String username) {
        String sql = "UPDATE users SET locked = TRUE WHERE username = ?";
        jdbcTemplate.update(sql, username);
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone());
    }

    @Override
    public void resetLoginAttempts(String username) {
        String sql = "UPDATE users SET login_attempts = 0 WHERE username = ?";
        jdbcTemplate.update(sql, username);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
    }
}