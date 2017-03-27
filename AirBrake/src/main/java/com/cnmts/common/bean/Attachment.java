package com.cnmts.common.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 文件附件
 * @author 王璞
 * @date 2016年1月15日 下午2:42:35
 * @version v1.0
 */
public class Attachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4994525434703513531L;

	/** 文件ID */
	private int fileId;
	/** 真实文件名称 */
	private String fileName;
	/** 文件位于服务器相对路径 */
	private String filePath;
	/** 文件预览图位于服务器相对路径 */
	private String previewFilePath;
	/** 文件大小 单位字节 */
	private long fileLength;
	/** 后缀名 */
	private String fileSuffix;
	/** 文件上传日期 */
	private Date fileUploadDate;
	/** MIME类型 */
	private String fileContentType;
	/** 上传用户ID */
	private int uploadUserId;
	/** 文件MD5值 */
	private String md5;
	/** 文件SHA1值 */
	private String sha1;
	/** 文件实体类 */
	private File file;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Date getFileUploadDate() {
		return fileUploadDate;
	}

	public void setFileUploadDate(Date fileUploadDate) {
		this.fileUploadDate = fileUploadDate;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(int uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getPreviewFilePath() {
		return previewFilePath;
	}

	public void setPreviewFilePath(String previewFilePath) {
		this.previewFilePath = previewFilePath;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

}
