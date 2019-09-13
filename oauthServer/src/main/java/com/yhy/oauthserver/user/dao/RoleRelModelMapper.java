package com.yhy.oauthserver.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yhy.oauthserver.user.pojo.RoleRelModel;
@Mapper
public interface RoleRelModelMapper {
    int insert(RoleRelModel record);

    int insertSelective(RoleRelModel record);
    
    int deleteInfoByParameter(@Param("roleId")String roleId,@Param("modelIds") String[] modelIdArray);
}