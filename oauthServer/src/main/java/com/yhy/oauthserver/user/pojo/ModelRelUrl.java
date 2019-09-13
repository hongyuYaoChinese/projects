package com.yhy.oauthserver.user.pojo;

import java.util.Date;

public class ModelRelUrl {
    private String modelRelUrlId;

    private String modelId;

    private String urlId;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    public String getModelRelUrlId() {
        return modelRelUrlId;
    }

    public void setModelRelUrlId(String modelRelUrlId) {
        this.modelRelUrlId = modelRelUrlId == null ? null : modelRelUrlId.trim();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId == null ? null : urlId.trim();
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