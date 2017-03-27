package com.cnmts.common.schedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cnmts.common.util.DateUtil;

@Component
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DatabaseBackupSchedule {

	@Value("${database.mysqlpath}")
	public String mysqlPath;

	@Value("${database.host}")
	private String mysqlHost;

	@Value("${jdbc.username}")
	private String dbUserName;

	@Value("${jdbc.password}")
	private String dbPassword;

	@Value("${database.dbname}")
	private String dbName;

	/**
	 * 备份存储路径
	 */
	@Value("${database.backupDes}")
	private String backupDes;

	private static final Logger logger = Logger.getLogger(DatabaseBackupSchedule.class);

	/**
	 * 夜间1点备份
	 */
	@Scheduled(cron = "${database.backupTime}")
	public void backup() {
		String sysName = System.getProperty("os.name");
		logger.info("备份开始    os.name=" + sysName);

		StringBuilder cmdBuilder = new StringBuilder(mysqlPath + "/mysqldump -h " + mysqlHost + " -u" + dbUserName);
		if (dbPassword != null) {
			cmdBuilder.append(" -p" + dbPassword);
		}
		cmdBuilder.append(" " + dbName);

		Runtime runtime = Runtime.getRuntime();
		Process exec;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		long start = DateUtil.getCurrentMilliseconds();
		try {
			exec = runtime.exec(cmdBuilder.toString());
			// logger.info("执行命令:" + cmdBuilder.toString());

			String fileName = "mysqlbackup_" + DateUtil.date2String(DateUtil.getCurrentDate(), "yyyy-MM-dd_HHmmss") + ".sql";

			File outFile = new File(backupDes, fileName);

			if (!outFile.getParentFile().exists()) {// 不存在
				outFile.setWritable(true, false);
				if (!outFile.getParentFile().mkdirs()) {
					// 创建文件夹失败
					logger.error("创建文件夹失败");
					return;
				}
			}
			logger.info("备份文件存放目录为:" + outFile.getAbsolutePath());

			reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "utf-8"));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			logger.error("backup()", e);

			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("backup()", e);

					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("backup()", e);

					e.printStackTrace();
				}
			}
		}
		logger.info("备份结束  共计用时：" + (DateUtil.getCurrentMilliseconds() - start) + "毫秒");
	}
}
