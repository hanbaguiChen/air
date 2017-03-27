package com.cnmts.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

/**
 * 
 * word转换html的工具类
 * 
 * @author xiaoming
 * 
 * @time 2016年11月29日 下午3:53:08
 */
public class Word2HtmlUtil {

	private static String word2003 = "application/msword";
	private static String word2007 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	public static File convert2Html(String wordFilePath, String htmlFilePath, String relativePath) throws FileNotFoundException,
			IOException, ParserConfigurationException, TransformerException {
		String probeContentType = FileUtil.getFileContentType(new File(wordFilePath));
		if (word2003.equals(probeContentType)) {
			// word 2003：
			return word20032Html(wordFilePath, htmlFilePath, relativePath);
		} else if (word2007.equals(probeContentType)) {
			// word 2007
			return word20072Html(wordFilePath, htmlFilePath, relativePath);
		} else {
			return null;
		}

	}

	private static File word20032Html(String wordFilePath, String htmlFilePath, String relativePath) throws FileNotFoundException,
			IOException, ParserConfigurationException, TransformerException {
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(wordFilePath));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {

			@Override
			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
				return "docimg/" + suggestedName;
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		// save pictures
		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				Picture pic = pics.get(i);
				try {
					File file = new File(relativePath + "docimg/" + pic.suggestFullFileName());
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					pic.writeImageContent(new FileOutputStream(relativePath + "docimg/" + pic.suggestFullFileName()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		if (SystemUtils.IS_OS_WINDOWS) {
			serializer.setOutputProperty(OutputKeys.ENCODING, "GBK");
		}
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		return writeFile(new String(out.toByteArray()), htmlFilePath);

	}

	private static File word20072Html(String wordFilePath, String htmlFilePath, String relativePath) throws XWPFConverterException,
			IOException {
		OutputStreamWriter outputStreamWriter = null;
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(wordFilePath));
			XHTMLOptions options = XHTMLOptions.create();
			// 存放图片的文件夹
			options.setExtractor(new FileImageExtractor(new File(relativePath + "docimg")));
			// html中图片的路径
			options.URIResolver(new BasicURIResolver("docimg"));
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlFilePath), "utf-8");
			XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
			xhtmlConverter.convert(document, outputStreamWriter, options);
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}
		return new File(htmlFilePath);

	}

	public static File writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			bw.write(content);
			return file;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
		return null;
	}

}
