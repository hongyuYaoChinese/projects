package com.yhy.oauthserver.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserInfoAllMapper {

	List<Map<String, Object>> getUserInfoList();

	Map<String, String> getUserInfoByName(String name);
	
	List<Map<String, Object>> getUserPermissionByName(String userName);
	
	List<Map<String, Object>> getUserPermissionById(String userId);
	
	String getUserPermissionStringByName(String userName);
	
	String getUserPassWordByName(String userName);
	
	List<String> getUserRoleById(String userId);
	
	List<Map<String, String>> getModelListByRoleId(String roleId);
	
	List<Map<String, String>> getModelList(String roleId);

	
}
