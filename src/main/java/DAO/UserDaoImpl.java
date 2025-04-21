package DAO;// UserDaoImpl.java
import DAO.UserDao;
import DAO.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.util.List;

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
        user.setNickname(rs.getString("nickname"));
        user.setGender(rs.getString("gender"));
        user.setAvatar(rs.getString("avatar"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
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
    public void createUserByAdmin(User user) {
        String sql = "INSERT INTO users " +
                "(username, password, email, phone, nickname, gender, avatar, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // 新增字段
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(), // 已加密，来自UserService
                user.getEmail(),
                user.getPhone(),
                user.getNickname(),
                user.getGender(),
                user.getAvatar(),
                user.isAdmin() // boolean类型，MyBatis会自动处理为1/0
        );
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
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        String sql = "SELECT * FROM users WHERE username LIKE ? OR nickname LIKE ?";
        keyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, keyword, keyword);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ?, locked = ?, login_attempts = ?, nickname = ?, gender = ?, avatar = ?, is_admin = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.isLocked(),
                user.getLoginAttempts(),
                user.getNickname(),
                user.getGender(),
                user.getAvatar(),
                user.isAdmin(),
                user.getId());
    }

    @Override
    public void deleteUser(Long userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
    public User getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, USER_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null; // 用户不存在时返回null
        }
    }
}