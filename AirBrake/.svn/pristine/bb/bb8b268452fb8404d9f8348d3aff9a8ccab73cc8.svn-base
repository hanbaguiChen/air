package com.cnmts.common.bean;

/**
 * json返回结果code
 * 
 * @author 王璞
 * @date 2016年4月5日 下午3:44:53
 * @version 1.0
 */
public enum ResultType {

	SUCCESS(0, "成功"), //
	WRONG(-1, "未知错误"), //
	NOT_LOGIN(-2, "尚未登录"), //
	NO_PRIVILEGE(-3, "您没有此权限"), //
	INVALID_DATA(-4, "数据不合法"), //
	ACCESS_NOT_ALLOW(-5, "禁止访问"), //
	FORBIDDEN_LOGIN(-6, "禁止登陆"), //
	PASSWORD_NOT_EQUAL(-7, "旧密码不一致"), //
	DICT_ALREADY_EXIST(-8, "字典表key已经存在，请更换"), //
	DICT_NOT_ALLOW_NULL(-9, "最少需要有一个字典"), //
	DICT_MOVE_WRONG(-10, "无效操作"), //
	ROLE_DELETE_WRONG(-11, "不允许删除此角色"), //
	USER_ALREAD_EXISTED(-12, "用户已存在"), //
	CARNO_ALREADEXIST(-13, "车厢编号已经存在"), //
	TRAIN_HASNOT_UNITE(-14, "列车尚未解编"), //
	CAR_NOT_EXIST(-15, "车厢不存在"), //
	CAR_ALREAD_PLAIT(-16, "车厢已被编组"), //

	;

	private int code;
	private String info;

	private ResultType(int code, String info) {
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

}
