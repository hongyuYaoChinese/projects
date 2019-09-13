package com.yhy.oauthserver.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.oauthserver.user.pojo.UserRegisterInfo;
import com.yhy.oauthserver.user.service.IUserService;

@RequestMapping
@Controller
public class LoginAndRegisterController {

	@Autowired
	private IUserService UserService;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@PostMapping("/register")
	public Object register(Model model,HttpServletRequest request,HttpServletResponse response) {
		UserRegisterInfo info = new UserRegisterInfo();
		String userName = request.getParameter("userName");
		info.setUserName(userName);
		info.setPhone(request.getParameter("userPhone"));
		info.setUserPassword(request.getParameter("userPassword"));
		Map<String, String> userInfo = UserService.getUserInfo(userName);
		if (null == userInfo) {
			UserService.createUser(info);
			return "index";
		}else {
			model.addAttribute("errorInfo", "用户名重复！");
			return "register";
		}
	}
}
