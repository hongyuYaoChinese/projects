package com.yhy.oauthserver.user.service;

import org.springframework.security.core.Authentication;

public interface IAuthenticationService {
	
	Authentication getAuthentication();
}
