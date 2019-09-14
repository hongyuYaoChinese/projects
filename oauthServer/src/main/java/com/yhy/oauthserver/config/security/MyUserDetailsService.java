package com.yhy.oauthserver.config.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yhy.oauthserver.user.service.IUserService;

/**用户信息获取
 * @author YaoHongYu
 *
 */
@Component
public class MyUserDetailsService implements UserDetailsService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.info("login name is "+userName);
		Map<String, String> userInfo = userService.getUserInfo(userName);
		return new MyUserPrincipal(userName, userInfo.get("user_password"), true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(userService.getUserPermissionStringByName(userName)),userInfo);
	}
}
