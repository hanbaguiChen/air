package com.cnmts.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * 
 * @author 王璞
 * @date 2016年7月15日 下午4:52:32
 * @version 1.0
 */
public class CiphertextUtil {
	public static final int MD2 = 1;
	public static final int MD5 = 2;
	public static final int SHA_1 = 3;
	public static final int SHA_256 = 4;
	public static final int SHA_384 = 5;
	public static final int SHA_512 = 6;

	/**
	 * 加密字符串
	 * 
	 * @param sourceStr
	 *            需要加密目标字符串
	 * @param algorithmsName
	 *            算法名称(如:MD2,MD5,SHA1,SHA256,SHA384,SHA512)
	 * @return
	 */
	public static String passAlgorithmsCiphering(String sourceStr, int algorithmsName) {
		String password = "";
		switch (algorithmsName) {
		case 1:
			password = DigestUtils.md2Hex(sourceStr);
			break;
		case 2:
			password = DigestUtils.md5Hex(sourceStr);
			break;
		case 3:
			password = DigestUtils.sha1Hex(sourceStr);
			break;
		case 4:
			password = DigestUtils.sha256Hex(sourceStr);
			break;
		case 5:
			password = DigestUtils.sha384Hex(sourceStr);
			break;
		case 6:
			password = DigestUtils.sha512Hex(sourceStr);
			break;
		}
		return password;
	}
}
