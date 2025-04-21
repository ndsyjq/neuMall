package DAO;

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
}
