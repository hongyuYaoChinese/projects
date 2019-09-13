package com.yhy.oauthserver.user.pojo;

import java.util.Date;

public class RoleRelModel {
    private String roleRelModelId;

    private String roleId;

    private String modelId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public String getRoleRelModelId() {
        return roleRelModelId;
    }

    public void setRoleRelModelId(String roleRelModelId) {
        this.roleRelModelId = roleRelModelId == null ? null : roleRelModelId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}