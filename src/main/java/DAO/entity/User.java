package DAO.entity;

import java.security.Timestamp;

// User.java
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean locked;
    private int loginAttempts;
    private String nickname;
    private String gender;
    private String avatar;
    private boolean isAdmin;
    private Timestamp createdAt;


    public User() {
    }

    public User(Long id, String username, String password, String email, String phone, boolean locked, int loginAttempts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.locked = locked;
        this.loginAttempts = loginAttempts;
        this.nickname = "";
    }

    /**
     * 获取
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * 设置
     * @param locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * 获取
     * @return loginAttempts
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * 设置
     * @param loginAttempts
     */
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String toString() {
        return "User{id = " + id + ", username = " + username + ", password = " + password + ", email = " + email + ", phone = " + phone + ", locked = " + locked + ", loginAttempts = " + loginAttempts + "}";
    }
}