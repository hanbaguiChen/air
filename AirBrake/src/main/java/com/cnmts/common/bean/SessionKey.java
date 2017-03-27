package com.cnmts.common.bean;

public enum SessionKey {

	/** 当前登录用户 */
	CURRENT_USER("currentUser"),
	/** 用户token */
	USER_TOKEN("UserToken"),

	/** 组网 */
	BUILD_NETWORK("buildNetWork_"),
	/** 解网 */
	DESTORY_NETWORK("destoryNetWork_"), ;

	private String key;

	private SessionKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
