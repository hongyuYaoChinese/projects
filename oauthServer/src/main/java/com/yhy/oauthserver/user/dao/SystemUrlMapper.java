package com.yhy.oauthserver.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yhy.oauthserver.user.pojo.SystemUrl;
@Mapper
public interface SystemUrlMapper {
    int insert(SystemUrl record);

    int insertSelective(SystemUrl record);
    
    List<Map<String, String>> getUrlList();
    
    List<Map<String, String>> getUrlListByMid(String Mid);
    
    int updateUrlInfo(Map<String, String> urlInfo);
    
    int deleteUrlByid(String urlId);
    
    int deleteUrlInfoByid(String urlId);

}