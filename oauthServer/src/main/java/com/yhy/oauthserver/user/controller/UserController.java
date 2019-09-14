package com.yhy.oauthserver.user.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhy.oauthserver.config.security.MyUserPrincipal;
import com.yhy.oauthserver.user.dao.RoleMapper;
import com.yhy.oauthserver.user.dao.UserInfoAllMapper;
import com.yhy.oauthserver.user.service.IUserService;
import com.yhy.oauthserver.user.service.impl.AuthenticationService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserInfoAllMapper dao;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private RoleMapper roleDao;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/control")
	public String control(Model model ) {
		model.addAttribute("userList",dao.getUserInfoList());
		model.addAttribute("roleList",roleDao.getRoleList());
		return "userControl";
	}
	
	@ResponseBody
	@RequestMapping("/getUserRoleInfo")
	public Object getUserRoleInfo(String userId) {
		if ("".equals(userId) || null == userId) {
			return roleDao.getRoleList();
		}
		return roleDao.getRoleListByUid(userId);
	}
	
	@ResponseBody
	@RequestMapping("/saveUserRoleInfo")
	public Object saveUserRoleInfo(String userId,String roleIds) {
		return userService.saveUserRoleInfo(userId,roleIds);
	}
	
	@ResponseBody
	@RequestMapping("/deleteUserInfo")
	public Object deleteUserInfo(String userId) {
		return userService.deleteUserByUid(userId);
	}
	
	@ResponseBody
	@RequestMapping("/getMe")
	public Object getMe() {
		MyUserPrincipal principal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		return principal.getUserInfo();
	}

	@ResponseBody
	@GetMapping("/userInfo")
	public Principal user(Principal member) {
		return member;
	}
}
