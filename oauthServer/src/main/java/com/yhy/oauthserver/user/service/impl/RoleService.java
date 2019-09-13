package com.yhy.oauthserver.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.oauthserver.config.security.MyUserPrincipal;
import com.yhy.oauthserver.user.dao.RoleMapper;
import com.yhy.oauthserver.user.dao.RoleRelModelMapper;
import com.yhy.oauthserver.user.dao.UserInfoAllMapper;
import com.yhy.oauthserver.user.pojo.Role;
import com.yhy.oauthserver.user.pojo.RoleRelModel;
import com.yhy.oauthserver.user.service.IRoleService;
import com.yhy.oauthserver.util.IdGenerator;

@Service
@Transactional
public class RoleService implements IRoleService{

	@Autowired
	private UserInfoAllMapper userInfoAllDao;
	
	@Autowired
	private RoleRelModelMapper roleRelModelDao;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private RoleMapper roleDao;
	
	@Override
	public Map<String, Object> getModelListByParame(String parame) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("modelList", userInfoAllDao.getModelList(parame));
		resMap.put("modelListByRole", userInfoAllDao.getModelListByRoleId(parame));
		return resMap;
	}

	@Override
	public int saveModelInfo(String roleId, String modelIds) {
		String[] modelIdArray = modelIds.split(";");
		int resNum = 0;
		roleRelModelDao.deleteInfoByParameter(roleId, modelIdArray);
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		for (String modelId : modelIdArray) {
			RoleRelModel roleRelModel = new RoleRelModel();
			String roleRelModelId = IdGenerator.get().nextId();
			roleRelModel.setRoleId(roleId);
			roleRelModel.setModelId(modelId);
			roleRelModel.setRoleRelModelId(roleRelModelId);
			roleRelModel.setCreateTime(new Date());
			roleRelModel.setCreateUser(myUserPrincipal.getUserInfo().get("id"));
			resNum += roleRelModelDao.insertSelective(roleRelModel);
		}
		return resNum;
	}

	@Override
	public int updateRoleInfo(String roleId, String roleName) {
		HashMap<String, String> parameterMap = new HashMap<>();
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		parameterMap.put("roleId", roleId);
		parameterMap.put("roleName", roleName);
		parameterMap.put("userId", myUserPrincipal.getUserInfo().get("id"));
		return roleDao.updateRoleName(parameterMap);
	}

	@Override
	public int saveRoleInfo(Role role) {
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authenticationService.getAuthentication().getPrincipal();
		role.setRoleId(IdGenerator.get().nextId());
		role.setCreateUser(myUserPrincipal.getUserInfo().get("id"));
		return roleDao.insertSelective(role);
	}

	@Override
	public int deleteRoleByid(String roleId) {
		roleDao.deleteRoleByid(roleId);
		roleDao.deleteRoleUserInfoByid(roleId);
		return roleDao.deleteRoleInfoByid(roleId);
	}

	
}
