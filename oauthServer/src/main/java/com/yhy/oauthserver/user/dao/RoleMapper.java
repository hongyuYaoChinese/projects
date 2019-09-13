package com.yhy.oauthserver.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.Role;
@Mapper
public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
    
    List<Map<String, String>>getRoleList();
    
    List<Map<String, String>>getRoleListByUid(String userId);
    
    int updateRoleName(Map<String, String> parameterMap);
    
    int deleteRoleByid(String roleId);
    
    int deleteRoleInfoByid(String roleId);
    
    int deleteRoleUserInfoByid(String roleId);
}