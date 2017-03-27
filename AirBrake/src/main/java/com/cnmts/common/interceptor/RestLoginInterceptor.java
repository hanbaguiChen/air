package com.cnmts.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cnmts.common.bean.SessionKey;
import com.cnmts.common.util.CookieUtil;
import com.cnmts.common.util.SessionUtil;
import com.cnmts.system.bean.User;
import com.cnmts.system.exception.UserNotLoginException;
import com.cnmts.system.service.UserService;

/**
 * 移动端登录拦截器
 * 
 * @date 2016年3月12日 下午8:11:51
 */
public class RestLoginInterceptor implements HandlerInterceptor {

	private UserService userService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		User currentUser = SessionUtil.getCurrentUser(session);
		if (currentUser == null) {
			String userToken = CookieUtil.getCookieValueByKey(SessionKey.USER_TOKEN.getKey(), request);
			if (userToken != null) {// 检查是否有token
				User userByUserToken = userService.getUserByUserToken(userToken);
				if (userByUserToken != null) {// 检查token是否有效
					request.getSession().setAttribute(SessionKey.CURRENT_USER.getKey(), userByUserToken);
					return true;
				}
			}
			throw new UserNotLoginException();
		}
		return true;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
