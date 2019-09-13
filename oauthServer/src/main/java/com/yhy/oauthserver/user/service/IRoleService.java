package com.yhy.oauthserver.user.service;

import java.util.Map;

import com.yhy.oauthserver.user.pojo.Role;

public interface IRoleService {

	Map<String, Object> getModelListByParame(String parame);
	
	int saveModelInfo(String roleId,String modelIds);
	
	int updateRoleInfo(String roleId,String roleName);
	
	int saveRoleInfo(Role role);
	
	int deleteRoleByid(String roleId);
}
