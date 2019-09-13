package com.yhy.oauthserver.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.oauthserver.config.security.MyUserPrincipal;
import com.yhy.oauthserver.user.dao.ModelRelUrlMapper;
import com.yhy.oauthserver.user.dao.SystemModelMapper;
import com.yhy.oauthserver.user.dao.SystemUrlMapper;
import com.yhy.oauthserver.user.pojo.ModelRelUrl;
import com.yhy.oauthserver.user.pojo.SystemModel;
import com.yhy.oauthserver.user.pojo.SystemUrl;
import com.yhy.oauthserver.user.service.ISystemModelService;
import com.yhy.oauthserver.util.IdGenerator;

@Service
@Transactional
public class SystemModelService implements ISystemModelService {

	@Autowired
	private SystemModelMapper systemModelDao;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private SystemUrlMapper systemUrlDao;
	
	@Autowired
	private ModelRelUrlMapper modelRelUrlDao;
	

	
	@Override
	public int saveModel(SystemModel systemModel) {
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		systemModel.setSystemModelId(IdGenerator.get().nextId());
		systemModel.setCreateUser(myUserPrincipal.getUserInfo().get("id"));
		return systemModelDao.insertSelective(systemModel);
	}

	@Override
	public int deleteModelByid(String modelId) {
		systemModelDao.deleteModelByid(modelId);
		systemModelDao.deleteModelInfoByid(modelId);
		return systemModelDao.deleteModelRoleInfoByid(modelId);
	}

	@Override
	public Map<String, Object> getModelAndUrlList(String modelId) {
		HashMap<String, Object> resMap = new HashMap<>();
		resMap.put("urlList", systemUrlDao.getUrlList());
		resMap.put("urlListByMid", systemUrlDao.getUrlListByMid(modelId));
		return resMap;
	}

	@Override
	public int updateModelInfo(Map<String, String> parameterMap) {
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		parameterMap.put("updateUser", myUserPrincipal.getUserInfo().get("id"));
		return systemModelDao.updateModelInfo(parameterMap);
	}

	@Override
	public int saveModelUrlInfo(String urlIds, String modelId) {
		modelRelUrlDao.deleteInfoByParameter(modelId);
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		String[] urlIdArray = urlIds.split(";");
		for (String urlId : urlIdArray) {
			ModelRelUrl modelRelUrl = new ModelRelUrl();
			modelRelUrl.setModelRelUrlId(IdGenerator.get().nextId());
			modelRelUrl.setModelId(modelId);
			modelRelUrl.setUrlId(urlId);
			modelRelUrl.setCreateUser(myUserPrincipal.getUserInfo().get("id"));
			modelRelUrlDao.insertSelective(modelRelUrl);
		}
		return 0;
	}

	
}
