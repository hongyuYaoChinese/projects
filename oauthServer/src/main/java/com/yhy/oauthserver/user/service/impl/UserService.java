package com.yhy.oauthserver.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yhy.oauthserver.config.security.MyUserPrincipal;
import com.yhy.oauthserver.user.dao.UserInfoAllMapper;
import com.yhy.oauthserver.user.dao.UserInfoMapper;
import com.yhy.oauthserver.user.dao.UserMapper;
import com.yhy.oauthserver.user.dao.UserRelRoleMapper;
import com.yhy.oauthserver.user.pojo.User;
import com.yhy.oauthserver.user.pojo.UserInfo;
import com.yhy.oauthserver.user.pojo.UserRegisterInfo;
import com.yhy.oauthserver.user.pojo.UserRelRole;
import com.yhy.oauthserver.user.service.IUserService;
import com.yhy.oauthserver.util.IdGenerator;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserMapper userDao;

	@Autowired
	private UserInfoMapper userInfoDao;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserInfoAllMapper userInfoAllDao;
	
	@Autowired
	private UserRelRoleMapper userRelRoleDao;

	@Override
	public String createUser(UserRegisterInfo info) {
		String uid = IdGenerator.get().nextId();
		User user = new User();
		user.setId(uid);
		user.setUserName(info.getUserName());
		user.setUserPassword(encoder.encode(info.getUserPassword()));
		user.setCreateTime(new Date());
		user.setCreateUser(uid);
		userDao.insertSelective(user);
		if (!StringUtils.isEmpty(info.getPhone())) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(uid);
			userInfo.setUserInfoId(IdGenerator.get().nextId());
			userInfo.setPhone(info.getPhone());
			userInfoDao.insertSelective(userInfo);
		}
		return uid;
	}

	@Override
	public String updateUser(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserInfo(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUserByUid(String uid) {
		userInfoDao.deleteUser(uid);
		return userInfoDao.deleteUserInfo(uid)+"";
	}

	@Override
	public List<Map<String, Object>> getUserPermissionByName(String userName) {
		return userInfoAllDao.getUserPermissionByName(userName);
	}

	@Override
	public String getUserPermissionStringByName(String userName) {
		return userInfoAllDao.getUserPermissionStringByName(userName);
	}

	@Override
	public String getUserPassWordByName(String userName) {
		return userInfoAllDao.getUserPassWordByName(userName);
	}

	@Override
	public Map<String, String> getUserInfo(String name) {
		return userInfoAllDao.getUserInfoByName(name);
	}

	@Override
	public Object saveUserRoleInfo(String userId, String roleIds) {
		String[] roleIdArray = roleIds.split(";");
		int resNum = 0;
		userRelRoleDao.deleteInfoByParameter(userId, roleIdArray);
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		for (String roleId : roleIdArray) {
			UserRelRole userRelRole = new UserRelRole();
			String userRelRoleId = IdGenerator.get().nextId();
			userRelRole.setRoleId(roleId);
			userRelRole.setUserId(userId);
			userRelRole.setUserRelRoleId(userRelRoleId);
			userRelRole.setCreateTime(new Date());
			userRelRole.setCreateUser(myUserPrincipal.getUserInfo().get("id"));
			resNum += userRelRoleDao.insertSelective(userRelRole);
		}
		return resNum;
	}

}
