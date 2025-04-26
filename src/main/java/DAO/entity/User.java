package DAO.entity;

import java.sql.Timestamp;
import java.util.List;

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
    private boolean admin;
    private Timestamp createdAt;
    private List<Role> roles;


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

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public List<Role> getRoles() {return roles;}
    public void setRoles(List<Role> roles) {this.roles = roles;}
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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