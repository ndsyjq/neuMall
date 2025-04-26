package Service;

import DAO.UserDao;
import DAO.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

// UserService.java
public class UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 通过构造函数注入DAO
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public void createUser(User user) {
        // 1. 检查用户名是否已存在
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 2. 检查邮箱是否已存在
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("邮箱已被注册");
        }

        // 3. 加密密码
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 4. 插入数据库
        userDao.createUser(user);
        userDao.saveUserRole(user.getId(), 2L);
    }
    public void createUserByAdmin(User user, List<Long> roleIds) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 2. 检查邮箱是否已存在
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("邮箱已被注册");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.createUserByAdmin(user);
        roleIds.forEach(roleId -> userDao.saveUserRole(user.getId(), roleId));
    }
    public User authenticate(String username, String password) {
        User user = userDao.findByUsername(username);

        if (user == null) {
            // 如果用户名查找不到，尝试通过邮箱查找
            user = userDao.findByEmail(username);
        }
        if (user != null) {
            user.setRoles(userDao.findRolesByUserId(user.getId())); // 加载角色
        }
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            userDao.resetLoginAttempts(username);
            return user;
        } else {
            if (user != null) {
                int attempts = user.getLoginAttempts() + 1;
                userDao.updateLoginAttempts(username, attempts);
                if (attempts >= 5) {
                    userDao.lockUser(username);
                }
            }
            return null;
        }
    }
    public UserDao getUserDao() {
        return userDao;
    }
    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}