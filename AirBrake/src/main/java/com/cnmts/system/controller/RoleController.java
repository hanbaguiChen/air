package com.cnmts.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnmts.common.bean.JsonResult;
import com.cnmts.system.bean.Privilege;
import com.cnmts.system.bean.Role;
import com.cnmts.system.service.RoleService;
import com.github.pagehelper.PageInfo;

/**
 * 角色控制器
 * 
 * @author 王璞
 * @date 2016年11月4日 下午2:31:44
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 分页查询橘色<br />
	 * 不包含权限
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:06:07
	 */
	@RequestMapping(value = { "/getRoleListByPage" })
	@ResponseBody
	public JsonResult<PageInfo<Role>> getUserListByPage(PageInfo<Role> page, String roleName) {
		Map<String, String> search = new HashMap<String, String>();
		if (roleName != null && !roleName.equals("")) {
			search.put("roleName", roleName);
		}
		JsonResult<PageInfo<Role>> jsonResult = new JsonResult<PageInfo<Role>>();
		PageInfo<Role> pageInfo = roleService.getRoleListByPage(page, search);
		jsonResult.setObj(pageInfo);
		return jsonResult;
	}

	/**
	 * 编辑角色
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:08:53
	 */
	@RequestMapping(value = { "/editRole" })
	@ResponseBody
	public JsonResult<Boolean> editRole(Role role) {
		if (role.getRoleId() > 0) {
			roleService.updateRole(role);
		} else {
			roleService.addRole(role);
		}
		return new JsonResult<Boolean>(true);
	}

	/**
	 * 根据角色id查询角色信息
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:09:22
	 */
	@RequestMapping(value = { "/getRoleById" })
	@ResponseBody
	public JsonResult<Role> getRoleById(int roleId) {
		Role role = roleService.getRoleById(roleId);
		return new JsonResult<Role>(role);
	}

	/**
	 * 删除角色
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日上午11:10:06
	 */
	@RequestMapping(value = { "/deleteRole" })
	@ResponseBody
	public JsonResult<Boolean> delete(int roleId) {
		return roleService.deleteRole(roleId);
	}

	/**
	 * 查询权限
	 * 
	 * @author 韩有琦
	 * @date 2016年7月22日下午1:42:26
	 */
	@RequestMapping(value = { "/getPrivilegeByRoleId" })
	@ResponseBody
	public JsonResult<List<Privilege>> getPrivilegeByRoleId(int roleId) {
		JsonResult<List<Privilege>> jsonResult = new JsonResult<List<Privilege>>();
		List<Privilege> list = roleService.getPrivilegeByRoleId(roleId, true);
		jsonResult.setObj(list);
		return jsonResult;
	}

	/**
	 * 授权
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午2:57:19
	 */
	@RequestMapping(value = { "/assignPrivileges" })
	@ResponseBody
	public JsonResult<Boolean> assignPrivileges(@RequestBody Role role) {
		roleService.assignPrivileges(role);
		return new JsonResult<Boolean>(true);
	}

	/**
	 * 查询所有角色
	 * 
	 * @author 王璞
	 * @date 2016年11月4日 下午5:13:48
	 * @param roleName
	 * @return
	 */
	@RequestMapping(value = { "/getAllRole" })
	@ResponseBody
	public JsonResult<List<Role>> getAllRole(String roleName) {
		List<Role> roles = roleService.getAllRole(roleName);
		return new JsonResult<List<Role>>(roles);
	}

}
