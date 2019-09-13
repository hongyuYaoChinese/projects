package com.yhy.oauthserver.user.pojo;

import java.util.ArrayList;
import java.util.Date;

public class ModelAndUrl {

	private String systemModelId;

	private String modelName;

	private String modelDescribe;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;

	private ArrayList<SystemUrl> urls;

	public String getSystemModelId() {
		return systemModelId;
	}

	public void setSystemModelId(String systemModelId) {
		this.systemModelId = systemModelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDescribe() {
		return modelDescribe;
	}

	public void setModelDescribe(String modelDescribe) {
		this.modelDescribe = modelDescribe;
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
		this.createUser = createUser;
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
		this.updateUser = updateUser;
	}

	public ArrayList<SystemUrl> getUrls() {
		return urls;
	}

	public void setUrls(ArrayList<SystemUrl> urls) {
		this.urls = urls;
	}

}
