package com.yhy.oauthserver.user.pojo;

import java.util.Date;

public class SystemModel {
	private String systemModelId;

	private String parentModelId;

	private String modelName;

	private String modelDescribe;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;
	
	private String modelUrl;

	
	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getParentModelId() {
		return parentModelId;
	}

	public void setParentModelId(String parentModelId) {
		this.parentModelId = parentModelId;
	}

	public String getSystemModelId() {
		return systemModelId;
	}

	public void setSystemModelId(String systemModelId) {
		this.systemModelId = systemModelId == null ? null : systemModelId.trim();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}

	public String getModelDescribe() {
		return modelDescribe;
	}

	public void setModelDescribe(String modelDescribe) {
		this.modelDescribe = modelDescribe == null ? null : modelDescribe.trim();
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