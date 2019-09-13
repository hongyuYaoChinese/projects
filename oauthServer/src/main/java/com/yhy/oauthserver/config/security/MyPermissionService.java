package com.yhy.oauthserver.config.security;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.yhy.oauthserver.user.service.IUserService;

@Component("myPermissionService")
public class MyPermissionService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Autowired
	private IUserService userService;

	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		boolean hasPermission = false;
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			String requestUrl = request.getRequestURI();
			List<Map<String, Object>> userPermissionList = userService.getUserPermissionByName(userName);
			String path = request.getContextPath() ;
			for (Map<String, Object> permissionMap : userPermissionList) {
				String systemUrl = null == permissionMap.get("url")?"":(String) permissionMap.get("url");
				String url = path+permissionMap.get("model_url")+systemUrl;
				String shortUrl = path+permissionMap.get("model_url");
				if (antPathMatcher.match(requestUrl, url)||antPathMatcher.match(requestUrl, shortUrl)) {
					hasPermission = true;
					break;
				}
			}
		}
		return hasPermission;
	}
}
