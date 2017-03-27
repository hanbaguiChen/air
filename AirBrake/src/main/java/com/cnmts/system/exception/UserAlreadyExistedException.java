package com.cnmts.system.exception;

/**
 * 用户已存在
 * 
 * @author 王璞
 * @date 2017年1月7日 下午2:36:43
 * @version 1.0
 */
public class UserAlreadyExistedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4083146382489955898L;

	private String loginName;

	public UserAlreadyExistedException(String loginName) {
		super(loginName);
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
