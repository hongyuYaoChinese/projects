package com.yhy.oauthserver.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.ModelRelUrl;

@Mapper
public interface ModelRelUrlMapper {
    int insert(ModelRelUrl record);

    int insertSelective(ModelRelUrl record);
    
    int deleteInfoByParameter(String Mid);
}