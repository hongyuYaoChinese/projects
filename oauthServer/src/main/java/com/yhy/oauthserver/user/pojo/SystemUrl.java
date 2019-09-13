package com.yhy.oauthserver.user.pojo;

import java.util.Date;

public class SystemUrl {
    private String systemUrlId;

    private String url;

    private String urlDescribe;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    public String getSystemUrlId() {
        return systemUrlId;
    }

    public void setSystemUrlId(String systemUrlId) {
        this.systemUrlId = systemUrlId == null ? null : systemUrlId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrlDescribe() {
        return urlDescribe;
    }

    public void setUrlDescribe(String urlDescribe) {
        this.urlDescribe = urlDescribe == null ? null : urlDescribe.trim();
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