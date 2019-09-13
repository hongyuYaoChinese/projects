package com.yhy.oauthserver.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.oauthserver.config.security.MyUserPrincipal;
import com.yhy.oauthserver.user.dao.SystemUrlMapper;
import com.yhy.oauthserver.user.pojo.SystemUrl;
import com.yhy.oauthserver.user.service.ISystemUrlService;
import com.yhy.oauthserver.util.IdGenerator;

@Service
@Transactional
public class SystemUrlService implements ISystemUrlService {

	@Autowired
	private SystemUrlMapper systemUrlDao;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public int updateUrlInfo(Map<String, String> urlInfo) {
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		String userId = myUserPrincipal.getUserInfo().get("id");
		urlInfo.put("updateUser", userId);
		return systemUrlDao.updateUrlInfo(urlInfo);
	}

	@Override
	public int deleteUrlByid(String urlId) {
		systemUrlDao.deleteUrlByid(urlId);
		return systemUrlDao.deleteUrlInfoByid(urlId);
	}

	@Override
	public int saveUrlInfo(String urlName, String urlDescribe) {
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		String urlId = IdGenerator.get().nextId();
		String userId = myUserPrincipal.getUserInfo().get("id");
		SystemUrl systemUrl = new SystemUrl();
		systemUrl.setCreateUser(userId);
		systemUrl.setSystemUrlId(urlId);
		systemUrl.setUrl(urlName);
		systemUrl.setUrlDescribe(urlDescribe);
		return systemUrlDao.insertSelective(systemUrl);
	}
	
	
}
