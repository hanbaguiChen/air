package com.cnmts.system.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cnmts.system.bean.User;

public interface UserMapper {

	User getUserByLogin(@Param("loginName") String loginName, @Param("loginPassword") String loginPassword);

	void addUser(User user);

	User getUserByUserId(@Param("userId") int userId);

	User getUserByLoginName(@Param("loginName") String loginName);

	User getUserByUserToken(@Param("userToken") String userToken);

	List<User> getUserByRoleId(@Param("roleId") int roleId);

	void updateUser(User user);

	void updateUserAvatar(@Param("userId") int userId, @Param("fileId") int fileId);

	List<User> getUserListByPage(Map<String, Object> search);

	List<User> selectAllUser();

	void resetUserPassword(@Param("password") String password, @Param("userId") int userId);

	void updateUserState(User user);

	void updateUserToken(@Param("userId") int userId, @Param("userToken") String token, @Param("userTokenCreateTime") Date currentDate);

}
