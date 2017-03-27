package com.cnmts.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cnmts.system.bean.Privilege;
import com.cnmts.system.bean.Role;

public interface RoleMapper {

	List<Role> getRoleListByPage(Map<String, String> search);

	void updateRole(Role role);

	void addRole(Role role);

	Role getRoleById(@Param("roleId") int roleId);

	void deleteRole(@Param("roleId") int roleId);
	
	Integer checkRoleUser(@Param("roleId") int roleId);

	List<Privilege> getPrivilegeByRoleId(@Param("roleId") int roleId);

	void deleteRolePrivilege(@Param("roleId") int roleId);

	void saveRolePrivilege(@Param("pId") int pId,@Param("rId") int roleId);

	List<Role> getAllRole(@Param("roleName") String roleName);

}
