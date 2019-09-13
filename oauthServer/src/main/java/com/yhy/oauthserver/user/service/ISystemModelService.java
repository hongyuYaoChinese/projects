package com.yhy.oauthserver.user.service;

import java.util.Map;

import com.yhy.oauthserver.user.pojo.SystemModel;

public interface ISystemModelService {

	int saveModel(SystemModel systemModel);
	
	int deleteModelByid(String modelId);
	
	Map<String, Object> getModelAndUrlList(String modelId);
	
	int updateModelInfo(Map<String, String> parameterMap);
	
	int saveModelUrlInfo(String urlIds,String modelId);
}
