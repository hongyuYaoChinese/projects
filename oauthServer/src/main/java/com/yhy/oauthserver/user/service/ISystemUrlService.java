package com.yhy.oauthserver.user.service;

import java.util.Map;

public interface ISystemUrlService {

	int updateUrlInfo(Map<String, String> urlInfo);
	
	int deleteUrlByid(String urlId);
	
	int saveUrlInfo(String urlName,String urlDescribe);
}
