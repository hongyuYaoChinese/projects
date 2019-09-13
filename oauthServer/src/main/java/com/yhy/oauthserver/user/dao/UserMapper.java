package com.yhy.oauthserver.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.User;
@Mapper
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}