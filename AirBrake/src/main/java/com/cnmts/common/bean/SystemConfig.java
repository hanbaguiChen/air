package com.cnmts.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置
 * 
 * @author 王璞
 * @date 2017年1月7日 上午10:35:17
 * @version 1.0
 */
public class SystemConfig {

	/** ffmpeg路径 */
	public static String ffmpegPath = null;
	/** ffmpeg 截取分辨率 */
	public static String ffmpegResolution = null;
	/** 文件保存路径 */
	public static String fileSavePath = null;
	/** 项目根路径 */
	public static String projectRootPath = null;

	/** token最大有效期天，默认7天 */
	public static int tokenMaxAge = 7;

	public static Map<Integer, String> alarmMap = new HashMap<Integer, String>();
}
