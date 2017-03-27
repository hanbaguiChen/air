package com.cnmts.common.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class WordUtil {

	private static String word2003 = "application/msword";
	private static String word2007 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	public static String readWorkFile(String filePath) throws Exception {
		File file = new File(filePath);
		String probeContentType = FileUtil.getFileContentType(file);

		String result = null;
		if (word2003.equals(probeContentType)) {
			// word 2003： 图片不会被读取
			FileInputStream fileInputStream = new FileInputStream(file);
			WordExtractor ex = new WordExtractor(fileInputStream);
			result = ex.getText();
			ex.close();
		} else if (word2007.equals(probeContentType)) {
			// word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
			OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			result = extractor.getText();
			extractor.close();
		}
		return result;
	}

	public static String readWorkFile(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		String probeContentType = FileUtil.getFileContentType(file);
		System.out.println(probeContentType);

		String result = null;
		if (probeContentType.equals(word2003)) {
			// word 2003： 图片不会被读取
			WordExtractor ex = new WordExtractor(fileInputStream);
			result = ex.getText();
			ex.close();
		} else if (probeContentType.equals(word2007)) {
			// word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
			OPCPackage opcPackage = POIXMLDocument.openPackage(file.getAbsolutePath());
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			result = extractor.getText();
			extractor.close();
		}
		return result;
	}
}
