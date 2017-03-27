package com.cnmts.system.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限
 * 
 * @author 王璞
 * @date 2016年11月4日 下午2:27:36
 * @version 1.0
 */
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2622638297976923781L;

	private int privilegeId;
	/** 权限名称 */
	private String privilegeName;
	/** 上一级id */
	private int parentId;
	/** 路径 */
	private String urlLink;
	/** 类型 */
	private int privilegeType;
	/** 排序 */
	private int privilegeSort;
	/** 0没有，1拥有 */
	private int isHave;

	private List<Privilege> childPrivileges = new ArrayList<Privilege>();

	public int getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public int getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(int privilegeType) {
		this.privilegeType = privilegeType;
	}

	public int getPrivilegeSort() {
		return privilegeSort;
	}

	public void setPrivilegeSort(int privilegeSort) {
		this.privilegeSort = privilegeSort;
	}

	public List<Privilege> getChildPrivileges() {
		return childPrivileges;
	}

	public void setChildPrivileges(List<Privilege> childPrivileges) {
		this.childPrivileges = childPrivileges;
	}

	public int getIsHave() {
		return isHave;
	}

	public void setIsHave(int isHave) {
		this.isHave = isHave;
	}

}
