package DAO;// UserDaoImpl.java

import DAO.entity.Role;
import DAO.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
    public Long createUserByAdmin(User user) {
        String sql = "INSERT INTO users " +
                "(username, password, email, phone, nickname, gender, avatar, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // 新增字段
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getNickname());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getAvatar());
            ps.setBoolean(8, user.isAdmin());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
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
        List<User> users = jdbcTemplate.query(sql, USER_ROW_MAPPER);
        
        // 为每个用户加载角色
        for (User user : users) {
            user.setRoles(findRolesByUserId(user.getId()));
        }
        
        return users;
    }

    @Override
    public List<User> searchUsers(String keyword) {
        String sql = "SELECT * FROM users WHERE username LIKE ? OR nickname LIKE ?";
        keyword = "%" + keyword + "%";
        List<User> users = jdbcTemplate.query(sql, USER_ROW_MAPPER, keyword, keyword);
        
        // 为每个用户加载角色
        for (User user : users) {
            user.setRoles(findRolesByUserId(user.getId()));
        }
        
        return users;
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
    @Override
    public void deleteUserRoles(Long userId) {
        String sql = "DELETE FROM user_roles WHERE user_id = ?";
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
    // DAO/UserDaoImpl.java
    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) ->
            new Role(rs.getLong("id"), rs.getString("role_name"), rs.getString("description"));

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        String sql = "SELECT r.* FROM roles r " +
                "JOIN user_roles ur ON r.id = ur.role_id " +
                "WHERE ur.user_id = ?";
        return jdbcTemplate.query(sql, ROLE_ROW_MAPPER, userId);
    }

    @Override
    public void saveUserRole(Long userId, Long roleId) {
        String sql = "INSERT INTO user_roles (user_id,role_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, roleId);
    }
    @Override
    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, ROLE_ROW_MAPPER);
    }
}