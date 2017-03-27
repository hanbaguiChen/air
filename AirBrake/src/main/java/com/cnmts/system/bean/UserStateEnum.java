package com.cnmts.system.bean;

/**
 * 用户状态枚举
 * 
 * @author 王璞
 * @date 2017年1月7日 下午2:31:51
 * @version 1.0
 */
public enum UserStateEnum {

	NORMAL(1, "正常"), //
	DISABLE(2, "停用"), //
	DELETED(3, "已删除"), //
	;

	private UserStateEnum(int userStateCode, String userStateName) {
		this.userStateCode = userStateCode;
		this.userStateName = userStateName;
	}

	private int userStateCode;
	private String userStateName;

	public int getUserStateCode() {
		return userStateCode;
	}

	public void setUserStateCode(int userStateCode) {
		this.userStateCode = userStateCode;
	}

	public String getUserStateName() {
		return userStateName;
	}

	public void setUserStateName(String userStateName) {
		this.userStateName = userStateName;
	}

}
