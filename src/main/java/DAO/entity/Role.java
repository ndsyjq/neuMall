package DAO.entity;
public class Role {
    private Long id;
    private String roleName;
    private String description;

    // 构造方法、getter/setter
    public Role(Long id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public Role() {
    }

    /**
     * 获取
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置
     *
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Role{id = " + id + ", roleName = " + roleName + ", description = " + description + "}";
    }
}