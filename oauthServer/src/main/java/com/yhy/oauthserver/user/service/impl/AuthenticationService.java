package com.yhy.oauthserver.user.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yhy.oauthserver.user.service.IAuthenticationService;

/**获取当前登录用户信息
 * @author YaoHongYu
 *
 */
@Service
public class AuthenticationService implements IAuthenticationService {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
