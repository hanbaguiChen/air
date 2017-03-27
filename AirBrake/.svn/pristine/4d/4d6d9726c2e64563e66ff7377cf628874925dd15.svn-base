package com.cnmts.system;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.AssertTrue;

import org.junit.Assert;
import org.junit.Test;

import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.util.CiphertextUtil;
import com.cnmts.common.util.HttpClientUtils;
import com.cnmts.common.util.JsonUtil;
import com.cnmts.system.bean.User;
import com.fasterxml.jackson.core.type.TypeReference;

public class UserTest {

	@Test
	public void userLogin() {
		String userName = "admin";
		String passWord = CiphertextUtil.passAlgorithmsCiphering("123456", CiphertextUtil.MD5);
		String url = "http://localhost:8087/AirBrake/user/userLogin";
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginName", userName);
		map.put("loginPassword", passWord);
		String doPost = HttpClientUtils.doPost(url, map);
		JsonResult<User> obj = JsonUtil.toObj(doPost, new TypeReference<JsonResult<User>>() {
		});

		String format = JsonUtil.format(doPost);
		System.out.println(format);

		Assert.assertTrue(obj != null);
		Assert.assertTrue(obj.getCode() == 0);
		Assert.assertTrue(obj.getObj() != null);
	}

}
