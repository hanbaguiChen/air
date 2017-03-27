package com.cnmts.common.excel;

/**
 * excel一行的格<br />
 * 1,直接设置object为该列数据<br/>
 * 2,提供propertyName，自动获取<br />
 * 
 * @author 王璞
 * @date 2016年11月18日 上午11:09:54
 * @version 5.0
 */
public class ExcelRowFormat {

	/** 直接提供数据 */
	private Object object;

	/** 属性名称 **/
	private String propertyName;
	/** 属性的子属性名称 **/
	private String childPropertyName = null;
	/** 如果是集合/数组，角标 **/
	private int index = -1;
	/** 字符串格式 **/
	private String strFormat = null;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getChildPropertyName() {
		return childPropertyName;
	}

	public void setChildPropertyName(String childPropertyName) {
		this.childPropertyName = childPropertyName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStrFormat() {
		return strFormat;
	}

	public void setStrFormat(String strFormat) {
		this.strFormat = strFormat;
	}

	public Object getObject() {
		return object;
	}

	public ExcelRowFormat setObject(Object object) {
		this.object = object;
		return this;
	}

}
