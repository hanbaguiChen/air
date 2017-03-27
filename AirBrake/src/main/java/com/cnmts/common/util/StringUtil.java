package com.cnmts.common.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

	public static boolean containsBoth(String str, String... strings) {
		boolean flag = true;
		for (String string : strings) {
			if (!contains(str, string)) {
				flag = false;
				continue;
			}
		}
		return flag;
	}

}
