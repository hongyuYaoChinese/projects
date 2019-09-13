package com.yhy.oauthserver.user.pojo;

import java.util.Date;

public class UserRelRole {
    private String userRelRoleId;

    private String roleId;

    private String userId;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    public String getUserRelRoleId() {
        return userRelRoleId;
    }

    public void setUserRelRoleId(String userRelRoleId) {
        this.userRelRoleId = userRelRoleId == null ? null : userRelRoleId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}