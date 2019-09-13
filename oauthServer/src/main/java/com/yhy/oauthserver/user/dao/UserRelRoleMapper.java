package com.yhy.oauthserver.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yhy.oauthserver.user.pojo.UserRelRole;
@Mapper
public interface UserRelRoleMapper {
    int insert(UserRelRole record);

    int insertSelective(UserRelRole record);
    
    int deleteInfoByParameter(@Param("userId")String userId,@Param("roleIds")String[] roleIds);
}