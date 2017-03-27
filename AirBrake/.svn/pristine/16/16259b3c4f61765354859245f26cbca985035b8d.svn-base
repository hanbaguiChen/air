package com.cnmts.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cnmts.common.bean.SystemConfig;

public class VideoUtil {

	private static Logger logger = Logger.getLogger(VideoUtil.class);

	private static String command = " -i #srcFilePath# -ss 1 -y -f image2 -s #resolution# #desFilePath#";

	public static void genThumb(String videoFilePath, String thumbFilePath) {
		logger.info("genThumb() START videoFilePath=" + videoFilePath + "   thumbFilePath=" + thumbFilePath);

		Runtime runtime = Runtime.getRuntime();
		Process exec;
		long start = DateUtil.getCurrentMilliseconds();
		BufferedReader reader = null;
		try {
			String t_command = SystemConfig.ffmpegPath + command;
			t_command = t_command.replace("#srcFilePath#", videoFilePath);
			t_command = t_command.replace("#desFilePath#", thumbFilePath);
			if (StringUtils.isEmpty(SystemConfig.ffmpegResolution)) {
				t_command = t_command.replace(" #resolution#", "");
			} else {
				t_command = t_command.replace("#resolution#", SystemConfig.ffmpegResolution);
			}

			exec = runtime.exec(t_command);
			logger.info("执行命令:" + t_command);
			reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "utf-8"));

			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			logger.info("执行结果：" + builder.toString());

			exec.destroy();
		} catch (IOException e) {
			thumbFilePath = null;
			e.printStackTrace();
			logger.error("genThumb() ERROR", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		long end = DateUtil.getCurrentMilliseconds();
		logger.info("genThumb() END  用时：" + (end - start) + " 毫秒");
	}
}
