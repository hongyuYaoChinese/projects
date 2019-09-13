package com.yhy.oauthserver.config.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class MyUserPrincipal extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> userInfo;

	public MyUserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,Map<String, String> userInfo) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userInfo = userInfo;
	}

	public Map<String, String> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map<String, String> userInfo) {
		this.userInfo = userInfo;
	}

	
	

}
