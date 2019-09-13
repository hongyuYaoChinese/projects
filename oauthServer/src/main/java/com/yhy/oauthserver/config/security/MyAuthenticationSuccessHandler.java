package com.yhy.oauthserver.config.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhy.oauthserver.user.dao.UserInfoAllMapper;

/**
 * 登录成功处理
 * 
 * @author YaoHongYu
 *
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserInfoAllMapper dao;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功");
		RequestCache requestCache = new HttpSessionRequestCache();
		MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
		String id = principal.getUserInfo().get("id");
		List<String> roles = dao.getUserRoleById(id);
		String path = request.getContextPath() ;
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String url = null;
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            url = savedRequest.getRedirectUrl();
        }
        if(url == null){
        	if (roles.contains("admin")) {
    			response.sendRedirect(basePath+"user/control");
    		}else {
    			response.sendRedirect(basePath+"index");
    		}
        }
        
        super.onAuthenticationSuccess(request, response, authentication);
        
	}

}
