package DAO;

import DAO.entity.User;

public interface UserDao {
    User findByUsername(String username);
    User findByEmail(String email);
    void updateLoginAttempts(String username, int attempts);
    void lockUser(String username);
    void createUser(User user);
    void resetLoginAttempts(String username);
    void updatePassword(Long userId, String newPassword);
}
