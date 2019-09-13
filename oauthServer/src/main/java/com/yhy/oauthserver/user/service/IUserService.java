package com.yhy.oauthserver.user.service;

import java.util.List;
import java.util.Map;

import com.yhy.oauthserver.user.pojo.UserRegisterInfo;

public interface IUserService {

	String createUser(UserRegisterInfo info);
	
	String updateUser(String uid);
	
	String updateUserInfo(String uid);
	
	String deleteUserByUid(String uid);
	
	List<Map<String, Object>> getUserPermissionByName(String userName);

	String getUserPermissionStringByName(String userName);
	
	String getUserPassWordByName(String userName);
	
	Map<String, String> getUserInfo(String name);
	
	Object saveUserRoleInfo(String userId,String roleIds);
	
}
