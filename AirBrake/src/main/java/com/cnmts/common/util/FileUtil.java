package com.cnmts.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

public class FileUtil extends FileUtils {

	/**
	 * 获取文件IMEI类型
	 * 
	 * @author 王璞
	 * @date 2016年12月15日 下午2:08:07
	 * @param file
	 * @return
	 */
	public static String getFileContentType(File file) {
		try {
			return new Tika().detect(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密字符串
	 * 
	 * @param sourceStr
	 *            需要加密目标字符串
	 * @param algorithmsName
	 *            算法名称(如:MD2,MD5,SHA1,SHA256,SHA384,SHA512)
	 * @return
	 */
	public static String getHash(File file, int algorithmsName) {
		String password = "";
		try {
			InputStream inputStream = new FileInputStream(file);
			switch (algorithmsName) {
			case 1:
				password = DigestUtils.md2Hex(inputStream);
				break;
			case 2:
				password = DigestUtils.md5Hex(inputStream);
				break;
			case 3:
				password = DigestUtils.sha1Hex(inputStream);
				break;
			case 4:
				password = DigestUtils.sha256Hex(inputStream);
				break;
			case 5:
				password = DigestUtils.sha384Hex(inputStream);
				break;
			case 6:
				password = DigestUtils.sha512Hex(inputStream);
				break;
			}
		} catch (Exception e) {
		}
		return password;
	}

}
