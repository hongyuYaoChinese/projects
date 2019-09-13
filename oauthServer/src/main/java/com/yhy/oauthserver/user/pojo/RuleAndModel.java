package com.yhy.oauthserver.user.pojo;

import java.util.ArrayList;
import java.util.Date;

public class RuleAndModel {

	private String roleId;

	private String roleName;

	private Date createTime;

	private Date updateTime;

	private String createUser;

	private String updateUser;

	private ArrayList<SystemModel> models;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public ArrayList<SystemModel> getModels() {
		return models;
	}

	public void setModels(ArrayList<SystemModel> models) {
		this.models = models;
	}

}
