package com.cnmts.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.bean.ResultType;
import com.cnmts.system.bean.Privilege;
import com.cnmts.system.bean.Role;
import com.cnmts.system.dao.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true)
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserService userService;

	/**
	 * 获得角色列表
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:06:46
	 */
	public PageInfo<Role> getRoleListByPage(PageInfo<Role> page, Map<String, String> search) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Role> roleList = roleMapper.getRoleListByPage(search);
		page = new PageInfo<Role>(roleList);
		return page;
	}

	/**
	 * 修改角色
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:12:41
	 */
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updateRole(Role role) {
		roleMapper.updateRole(role);
	}

	/**
	 * 增加 角色
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:12:32
	 */
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void addRole(Role role) {
		roleMapper.addRole(role);
	}

	/**
	 * 获得角色信息
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:12:17
	 */
	public Role getRoleById(int roleId) {
		return roleMapper.getRoleById(roleId);
	}

	/**
	 * 删除橘色
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:12:55
	 */
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public JsonResult<Boolean> deleteRole(int roleId) {
		JsonResult<Boolean> jsonResult = new JsonResult<Boolean>();
		//校验
		if(roleMapper.checkRoleUser(roleId) > 0){
			jsonResult.setCode(ResultType.ROLE_DELETE_WRONG);
			jsonResult.setObj(false);
			return jsonResult;
		}else{
			roleMapper.deleteRole(roleId);
			jsonResult.setObj(true);
			return jsonResult;
		}
	}

	/**
	 * 根据角色获得权限
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午2:57:19
	 * @param showNotHave
	 *            是否显示未拥有的菜单
	 */
	public List<Privilege> getPrivilegeByRoleId(int roleId, boolean showNotHave) {
		List<Privilege> privilegeList = roleMapper.getPrivilegeByRoleId(roleId);
		List<Privilege> firstPrivileges = new ArrayList<Privilege>();
		if (privilegeList != null && privilegeList.size() > 0) {
			for (int i = 0; i < privilegeList.size();) {
				Privilege privilege = privilegeList.get(i);
				if (!showNotHave) {// 不显示未拥有的菜单
					if (privilege.getIsHave() == 0) {
						privilegeList.remove(i);
						continue;
					}
				}

				if (privilege.getParentId() == 0) {
					firstPrivileges.add(privilege);
					privilegeList.remove(i);
				} else {
					i++;
				}
			}
		}
		for (int i = 0; i < firstPrivileges.size(); i++) {
			Privilege privilege = firstPrivileges.get(i);
			// 寻找其儿子
			recursive(privilege, privilegeList);
		}

		return firstPrivileges;
	}

	/*** 递归寻找菜单的子菜单 **/
	private void recursive(Privilege privilege, List<Privilege> privilegeList) {
		for (int j = 0; j < privilegeList.size(); ) {
			Privilege childPrivilege = privilegeList.get(j);
			if (childPrivilege.getParentId() == privilege.getPrivilegeId()) {
				privilege.getChildPrivileges().add(childPrivilege);
				privilegeList.remove(j);

				recursive(childPrivilege, privilegeList);
			} else {
				j++;
			}
		}
	}

	/**
	 * 给角色授权
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午2:57:19
	 * @param role
	 */
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void assignPrivileges(Role role) {
		roleMapper.deleteRolePrivilege(role.getRoleId());
		List<Privilege> privileges = role.getPrivileges();
		addPrivileges(privileges, role);
	}
	private void addPrivileges(List<Privilege> privileges,Role role){
		if(privileges != null && privileges.size() > 0){
			for(Privilege p:privileges){
				if(p.getIsHave() == 1){
					roleMapper.saveRolePrivilege(p.getPrivilegeId(),role.getRoleId());
				}
				addPrivileges(p.getChildPrivileges(), role);
			}
		}
	}

	/**
	 * 查询所有角色
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午5:11:42
	 * @param roleName
	 * @return
	 */
	public List<Role> getAllRole(String roleName) {
		return roleMapper.getAllRole(roleName);
	}

}
