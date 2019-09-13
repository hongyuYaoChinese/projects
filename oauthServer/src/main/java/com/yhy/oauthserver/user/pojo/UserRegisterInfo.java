package com.yhy.oauthserver.user.pojo;

import javax.validation.constraints.NotBlank;

public class UserRegisterInfo {

	@NotBlank(message = "用户名不能为空")
	private String userName;
	
	@NotBlank(message = "密码不能为空")
	private String userPassword;
	
	private String phone;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
