package com.cnmts.common.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cnmts.common.bean.UserOpLog;
import com.cnmts.common.bean.UserOpLog.UserOpLogType;
import com.cnmts.common.dao.UserOpLogMapper;
import com.cnmts.common.util.AccessObjectUtil;
import com.cnmts.common.util.DateUtil;
import com.cnmts.common.util.JsonUtil;
import com.cnmts.common.util.SessionUtil;
import com.cnmts.system.bean.User;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
public class UserOpLogService {

	@Autowired
	private UserOpLogMapper userOpLogMapper;

	/**
	 * 用户登录日志
	 * 
	 * @author 王璞
	 * @date 2016年11月8日 下午4:15:14
	 * @param user
	 */
	public void saveLog4UserLogin(User user) {
		UserOpLog userOpLog = new UserOpLog(user.getLoginName() + " 登录系统", JsonUtil.toJson(user), UserOpLogType.LOGIN);
		saveUserOpLog(userOpLog);
	}

	/**
	 * 用户登出日志
	 * 
	 * @author 王璞
	 * @date 2016年11月8日 下午4:31:47
	 * @param user
	 */
	public void saveLog4UserLogout(User user) {
		String attach = null;
		String loginName = "";
		if (user != null) {
			attach = JsonUtil.toJson(user);
			loginName = user.getLoginName();
		}
		UserOpLog build = new UserOpLog(loginName + " 登录登出", attach, UserOpLogType.LOGOUT);
		saveUserOpLog(build);
	}

	public void saveUserOpLog(UserOpLog userOpLog) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		userOpLog.setCreateTime(DateUtil.getCurrentDate());
		if (request != null) {
			userOpLog.setCreateUserIp(AccessObjectUtil.getIpAddress(request));
			userOpLog.setCreateUserId(SessionUtil.getCurrentUserId(request.getSession()));
		}

		userOpLogMapper.saveUserOpLog(userOpLog);
	}

}
