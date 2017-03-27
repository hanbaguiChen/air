package com.cnmts.common.exception;

/**
 * 数据异常
 * 
 * @author 王璞
 * @date 2016年8月29日 上午10:14:34
 * @version 1.0
 */
public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 71627432679855682L;

	private String eStr;

	public InvalidDataException() {
	}

	public InvalidDataException(String string) {
		super(string);
		this.eStr = string;
	}

	public String geteStr() {
		return eStr;
	}

}
