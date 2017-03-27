package com.cnmts.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cnmts.common.bean.SessionKey;
import com.cnmts.system.bean.User;

/**
 * session工具类
 * 
 * @author 王璞
 * @date 2016年11月2日 上午11:12:10
 * @version 1.0
 */
public class SessionUtil {

	public static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if (request == null) {
			return null;
		}
		HttpSession session = request.getSession();
		return session;
	}

	/**
	 * 获取当前登录用户
	 * 
	 * @author 王璞
	 * @date 2016年8月5日 下午5:02:00
	 */
	public static User getCurrentUser(HttpSession session) {
		User currentUser = (User) session.getAttribute(SessionKey.CURRENT_USER.getKey());
		return currentUser;
	}

	/**
	 * 通过RequestContextHolder 获取当前登录用户
	 * 
	 * @author 王璞
	 * @date 2016年11月24日 下午3:13:32
	 * @return
	 */
	public static User getCurrentUser() {
		HttpSession session = getSession();
		return getCurrentUser(session);
	}

	/**
	 * 设置key=currentUser
	 * 
	 * @author 王璞
	 * @date 2016年11月2日 下午2:56:20
	 * @param session
	 * @param currentUser
	 */
	public static void setCurrentUser(HttpSession session, User currentUser) {
		session.setAttribute(SessionKey.CURRENT_USER.getKey(), currentUser);
	}

	/**
	 * 获取当前登录用户Id
	 * 
	 * @author 王璞
	 * @date 2016年8月5日 下午5:02:00
	 */
	public static int getCurrentUserId(HttpSession session) {
		if (session == null) {
			return -1;
		}
		User currentUser = getCurrentUser(session);
		if (currentUser == null) {
			return -1;
		}
		return currentUser.getUserId();
	}

	/**
	 * 通过RequestContextHolder 获取userId
	 * 
	 * @author 王璞
	 * @date 2016年11月24日 下午3:13:09
	 * @return
	 */
	public static int getCurrentUserId() {
		User currentUser = getCurrentUser();
		if (currentUser == null) {
			return -1;
		}
		return currentUser.getUserId();
	}

	/**
	 * 设置组网MsgNo
	 * 
	 * @author 王璞
	 * @date 2017年2月6日 上午10:03:34
	 * @param trainId
	 * @param msgNo
	 */
	public static void setBuildNetWorkMsgNo(int trainId, int msgNo) {
		HttpSession session = getSession();
		session.setAttribute(SessionKey.BUILD_NETWORK.getKey() + trainId, msgNo);
	}

	/**
	 * 查询组网MsgNo
	 * 
	 * @author 王璞
	 * @date 2017年2月6日 上午10:10:10
	 * @param trainId
	 * @return
	 */
	public static int getBuildNetWorkMsgNo(int trainId) {
		HttpSession session = getSession();
		return (int) session.getAttribute(SessionKey.BUILD_NETWORK.getKey() + trainId);
	}

	/**
	 * 设置解网MsgNo
	 * 
	 * @author 王璞
	 * @date 2017年2月6日 上午10:11:07
	 * @param trainId
	 * @param msgNo
	 */
	public static void setDestoryNetWorkMsgNo(int trainId, int msgNo) {
		HttpSession session = getSession();
		session.setAttribute(SessionKey.DESTORY_NETWORK.getKey() + trainId, msgNo);
	}

	/**
	 * 查询解网MsgNo
	 * 
	 * @author 王璞
	 * @date 2017年2月6日 上午10:11:45
	 * @param trainId
	 * @param msgNo
	 */
	public static int getDestoryNetWorkMsgNo(int trainId) {
		HttpSession session = getSession();
		return (int) session.getAttribute(SessionKey.DESTORY_NETWORK.getKey() + trainId);
	}
}
