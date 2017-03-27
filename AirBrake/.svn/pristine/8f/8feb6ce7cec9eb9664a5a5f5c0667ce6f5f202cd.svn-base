package com.cnmts.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户操作日志
 * 
 * @author 王璞
 * @date 2016年11月8日 下午3:54:43
 * @version 1.0
 */
public class UserOpLog implements Serializable {

	public UserOpLog(String logContent, UserOpLogType userOpLogType) {
		this.logContent = logContent;
		this.logType = userOpLogType.getTypeId();
	}

	public UserOpLog(String logContent, String attach, UserOpLogType userOpLogType) {
		this.logContent = logContent;
		this.attach = attach;
		this.logType = userOpLogType.getTypeId();
	}

	public enum UserOpLogType {
		/** 登录 */
		LOGIN(1), //
		/** 登出 */
		LOGOUT(2), //
		/** 创建项目 */
		CREATE_PROJECT(3), //
		/** 更新项目 */
		UPDATE_PROJECT(4), //
		/** 认领项目 */
		CLAIM_PROJECT(5), //
		/** 启动项目 */
		STARTUP_PROJECT(6), //
		/** 添加项目组成员 */
		APPEND_MEMBER(7), //
		/** 离开项目组 */
		LEAVE_TEAM(8), //
		/** 回到项目组 */
		BACK_TEAM(9), //
		;

		private UserOpLogType(int typeId) {
			this.typeId = typeId;
		}

		private int typeId;

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6079326039568726327L;
	private int logId;
	private String logContent;

	private String attach;

	/***
	 * 日志类型<br />
	 * 1,用户登录<br />
	 * 2,用户登出<br />
	 * 3,用户创建房屋资产信息<br />
	 * 4,用户修改房租资产信息<br />
	 */
	private int logType;
	private Date createTime;
	private int createUserId;
	private String createUserIp;
	private String createUserName;

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCreateUserIp() {
		return createUserIp;
	}

	public void setCreateUserIp(String createUserIp) {
		this.createUserIp = createUserIp;
	}

}
