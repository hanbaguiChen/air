package com.cnmts.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cnmts.common.bean.SystemConfig;
import com.cnmts.common.exception.InvalidDataException;
import com.cnmts.common.util.DateUtil;
import com.cnmts.system.bean.Privilege;
import com.cnmts.system.bean.User;
import com.cnmts.system.bean.UserStateEnum;
import com.cnmts.system.dao.UserMapper;
import com.cnmts.system.exception.ForbiddenLoginException;
import com.cnmts.system.exception.UserAlreadyExistedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true)
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleService roleService;

	/**
	 * 查询用户
	 * 
	 * @author 王璞
	 * @param platform
	 * @date 2016年11月2日 下午2:38:40
	 * @param loginName
	 * @param loginPassword
	 * @param userToken
	 * @throws ForbiddenLoginException
	 * @throws InvalidDataException
	 */
	@Transactional(readOnly = false, noRollbackFor = { ForbiddenLoginException.class, InvalidDataException.class })
	public User getUserByLogin(int platform, String loginName, String loginPassword, String userToken) throws ForbiddenLoginException,
			InvalidDataException {
		if ((StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPassword)) && StringUtils.isEmpty(userToken)) {
			throw new InvalidDataException();
		}
		User userByLogin = null;

		if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(loginPassword)) {
			userByLogin = userMapper.getUserByLogin(loginName, loginPassword);
		}

		if (userByLogin == null && StringUtils.isNotEmpty(userToken)) {
			userByLogin = getUserByUserToken(userToken);
		}

		if (userByLogin != null && userByLogin.getUserState() != 1) {
			// 非正常的账号不允许登录
			throw new ForbiddenLoginException();
		}
		if (userByLogin != null) {
			// 登录成功，设置token
			String updateUserToken = updateUserToken(userByLogin.getUserId());
			userByLogin.setUserToken(updateUserToken);
			if (platform == 0) {// web平台查询权限
				// 获取权限
				List<Privilege> privilegeByRoleId = roleService.getPrivilegeByRoleId(userByLogin.getRoleId(), false);
				userByLogin.setPrivileges(privilegeByRoleId);
			}
		}
		return userByLogin;
	}

	/**
	 * 添加用户
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午3:21:48
	 * @param user
	 * @throws UserAlreadyExistedException
	 */
	@Transactional(readOnly = false, rollbackFor = { UserAlreadyExistedException.class })
	public void addUser(User user) throws UserAlreadyExistedException {
		User userByLoginName = getUserByLoginName(user.getLoginName());
		if (userByLoginName != null) {
			throw new UserAlreadyExistedException(user.getLoginName());
		}
		userMapper.addUser(user);
	}

	/**
	 * 查询用户信息
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午2:59:16
	 * @param userId
	 *            用户id
	 * @return
	 */
	public User getUserByUserId(int userId) {
		return userMapper.getUserByUserId(userId);
	}

	/**
	 * 查询某一种角色的所有用户
	 * 
	 * @param userId
	 * @return
	 */
	public List<User> getUserByRoleId(int roleId) {
		return userMapper.getUserByRoleId(roleId);
	}

	/**
	 * 通过登录名查询用户
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午2:27:51
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return userMapper.getUserByLoginName(loginName);
	}

	/**
	 * 根据用户token查询用户信息
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午3:15:33
	 * @param userToken
	 * @return
	 */
	public User getUserByUserToken(String userToken) {
		User userByUserToken = userMapper.getUserByUserToken(userToken);
		if (userByUserToken != null) {
			userByUserToken.setLoginPassword(null);
			Date userTokenCreateTime = userByUserToken.getUserTokenCreateTime();
			Date addDays = DateUtils.addDays(userTokenCreateTime, SystemConfig.tokenMaxAge);
			if (addDays.compareTo(DateUtil.getCurrentDate()) < 0) {// token最大有效期
				userByUserToken = null;
			}
		}
		return userMapper.getUserByUserToken(userToken);
	}

	/**
	 * 更新用户信息
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午3:30:30
	 */
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	/**
	 * 更新头像
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午3:30:30
	 * @param currentUser
	 */
	@Transactional(readOnly = false)
	public void updateUserAvatar(int userId, int fileId) {
		userMapper.updateUserAvatar(userId, fileId);
	}

	/**
	 * 删除用户
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午3:41:43
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void deleteUserById(int userId) {
		User user = new User();
		user.setUserId(userId);
		user.setUserState(UserStateEnum.DELETED.getUserStateCode());
		updateUserState(user);
	}

	/**
	 * 分页获取用户列表
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午3:52:18
	 * @param page
	 * @param search
	 * @return
	 */
	public PageInfo<User> getUserListByPage(PageInfo<User> page, Map<String, Object> search) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<User> userList = userMapper.getUserListByPage(search);
		page = new PageInfo<User>(userList);
		return page;
	}

	/**
	 * 重新设置用户密码<br />
	 * 重置密码用户token也会重新生成
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午3:12:59
	 * @param password
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void resetUserPassword(String password, int userId) {
		updateUserToken(userId);
		userMapper.resetUserPassword(password, userId);
	}

	/**
	 * 查询所有用户
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午2:24:23
	 * @return
	 */
	public List<User> getAllUser() {
		return userMapper.selectAllUser();
	}

	/**
	 * 更新用户状态
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午2:24:34
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateUserState(User user) {
		userMapper.updateUserState(user);
	}

	/**
	 * 更新token
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午3:12:44
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public String updateUserToken(int userId) {
		String token = UUID.randomUUID().toString().replace("-", "");
		userMapper.updateUserToken(userId, token, DateUtil.getCurrentDate());
		return token;
	}

	/**
	 * 删除用户token
	 * 
	 * @author 王璞
	 * @date 2017年1月11日 下午4:44:20
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void deleteUserToken(int userId) {
		userMapper.updateUserToken(userId, null, DateUtil.getCurrentDate());
	}
}
