package com.cnmts.common.bean;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = 1717488731978480120L;
	private int code;
	private T obj;

	public JsonResult() {

	}

	public JsonResult(T obj) {
		this.obj = obj;
	}

	public int getCode() {
		return code;
	}

	public JsonResult<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public JsonResult<T> setCode(ResultType resultType) {
		this.code = resultType.getCode();
		return this;
	}

	public T getObj() {
		return obj;
	}

	public JsonResult<T> setObj(T obj) {
		this.obj = obj;
		return this;
	}

}
