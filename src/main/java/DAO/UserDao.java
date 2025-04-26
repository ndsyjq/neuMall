package DAO;

import DAO.entity.Role;
import DAO.entity.User;

import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    User findByEmail(String email);
    void updateLoginAttempts(String username, int attempts);
    void lockUser(String username);
    void createUser(User user);
    void resetLoginAttempts(String username);
    void updatePassword(Long userId, String newPassword);
    List<User> getAllUsers();
    List<User> searchUsers(String keyword);
    void updateUser(User user);
    void deleteUser(Long userId);
    User getUserById(Long id);
    void createUserByAdmin(User user);
    List<Role> findRolesByUserId(Long userId); // 获取用户角色列表
    void saveUserRole(Long userId, Long roleId); // 保存用户角色关联
}
