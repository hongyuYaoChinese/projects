package com.yhy.oauthserver.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.SystemModel;
@Mapper
public interface SystemModelMapper {
    int insert(SystemModel record);

    int insertSelective(SystemModel systemModel);
    
    int deleteModelByid(String modelId);
    
    int deleteModelInfoByid(String modelId);
    
    int deleteModelRoleInfoByid(String modelId);
    
	int updateModelInfo(Map<String, String> parameterMap);

}