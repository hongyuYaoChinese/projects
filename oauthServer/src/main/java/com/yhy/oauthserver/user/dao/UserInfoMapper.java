package com.yhy.oauthserver.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.UserInfo;
@Mapper
public interface UserInfoMapper {
    int insert(UserInfo record);

    int insertSelective(UserInfo record);
    
    int deleteUser(String userId);
    
    int deleteUserInfo(String userId);
}