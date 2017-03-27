package com.cnmts.common.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cnmts.common.bean.Attachment;
import com.cnmts.common.bean.SystemConfig;
import com.cnmts.common.dao.FileMapper;
import com.cnmts.common.util.CiphertextUtil;
import com.cnmts.common.util.DateUtil;
import com.cnmts.common.util.FileUtil;
import com.cnmts.common.util.ImageUtil;
import com.cnmts.common.util.VideoUtil;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = true)
public class FileService {

	/** 需要生成缩略图的图片类型 */
	private static final String[] imageArr = new String[] { "image/jpeg", "image/png" };
	/** 能够生成缩略图的视频类型 */
	private static final String[] viderArr = new String[] { "video/mp4" };

	public static final String uploadFolder = "upload";
	public static final String previewFolder = "preview";
	public static final String tempFolder = "temp";

	/**
	 * 生成文件保存路径
	 * 
	 * @author 王璞
	 * @date 2016年12月6日 上午10:55:09
	 * @param fileName
	 * @return
	 */
	public static String genAbsoluteFilePath(String fileName) {
		return getAbsolutePathByRelativePath(FileService.uploadFolder + "/" + DateUtil.getYear() + "/" + DateUtil.getMonth() + "/"
				+ fileName);
	}

	@Autowired
	private FileMapper fileMapper;

	/**
	 * 保存临时文件
	 * 
	 * @author 王璞
	 * @date 2017年1月17日 上午11:43:52
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public Attachment saveTempFile(MultipartFile multipartFile) throws Exception {
		return saveFile(multipartFile, uploadFolder, -1, false);
	}

	/**
	 * 保存文件
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public Attachment saveFile(MultipartFile multipartFile, int userId) throws Exception {
		return saveFile(multipartFile, uploadFolder, userId, true);
	}

	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	private Attachment saveFile(MultipartFile multipartFile, String folder, int userId, boolean saveInDB) throws Exception {
		// 保存文件
		String fileRealName = multipartFile.getOriginalFilename();
		String fileSuffix = FilenameUtils.getExtension(fileRealName);
		String fileName = UUID.randomUUID().toString() + "." + fileSuffix;
		// 相对路径
		String relatiePath = folder + "/" + DateUtil.getYear() + "/" + DateUtil.getMonth();
		relatiePath = relatiePath + "/" + fileName;

		return saveMultipartFile(multipartFile, relatiePath, userId, saveInDB);
	}

	/**
	 * 保存文件
	 * 
	 * @author 王璞
	 * @date 2016年11月3日 上午9:26:09
	 * @param multipartFile
	 * @param relatiePath
	 *            相对路径
	 * @param userId
	 *            上传用户id
	 * @param saveInDB
	 *            是否保存到数据库
	 * @return
	 * @throws Exception
	 */
	public Attachment saveMultipartFile(MultipartFile multipartFile, String relatiePath, int userId, boolean saveInDB) throws Exception {
		// 保存文件
		String fileRealName = multipartFile.getOriginalFilename();
		String fileSuffix = FilenameUtils.getExtension(fileRealName);

		File file = new File(SystemConfig.fileSavePath, relatiePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		multipartFile.transferTo(file);

		String previewFilePath = null;
		String fileContentType = FileUtil.getFileContentType(file);
		if (ArrayUtils.contains(imageArr, fileContentType)) {// 图片
			previewFilePath = previewFolder + "/" + DateUtil.getYear() + "/" + DateUtil.getMonth();
			previewFilePath = previewFilePath + "/" + UUID.randomUUID().toString() + "." + fileSuffix;
			File previewFile = new File(SystemConfig.fileSavePath, previewFilePath);
			if (!previewFile.getParentFile().exists()) {
				previewFile.getParentFile().mkdirs();
			}
			ImageUtil.genThumbByDefault(file, previewFile);
		} else if (ArrayUtils.contains(viderArr, fileContentType)) {// 视频
			previewFilePath = previewFolder + "/" + DateUtil.getYear() + "/" + DateUtil.getMonth();
			previewFilePath = previewFilePath + "/" + UUID.randomUUID().toString() + ".png";
			File previewFile = new File(SystemConfig.fileSavePath, previewFilePath);
			if (!previewFile.getParentFile().exists()) {
				previewFile.getParentFile().mkdirs();
			}
			VideoUtil.genThumb(file.getAbsolutePath(), previewFile.getAbsolutePath());
		}

		// 保存到数据库
		Attachment attachment = new Attachment();
		attachment.setFileName(fileRealName);
		attachment.setFilePath(relatiePath);
		attachment.setPreviewFilePath(previewFilePath);
		attachment.setFileLength(file.length());
		attachment.setFileSuffix(fileSuffix);
		attachment.setFileContentType(fileContentType);
		attachment.setUploadUserId(userId);
		attachment.setFileUploadDate(DateUtil.getCurrentDate());
		attachment.setMd5(FileUtil.getHash(file, CiphertextUtil.MD5));
		attachment.setSha1(FileUtil.getHash(file, CiphertextUtil.SHA_1));

		attachment.setFile(file);
		if (saveInDB) {
			fileMapper.saveFile(attachment);
		}

		return attachment;
	}

	/**
	 * 保存附件信息
	 * 
	 * @author 王璞
	 * @date 2016年12月5日 下午4:32:18
	 * @param attachment
	 */
	@Transactional(readOnly = false)
	public void saveAttachment(Attachment attachment) {
		fileMapper.saveFile(attachment);
	}

	/**
	 * 通过文件ID查询文件
	 * 
	 * @param fileId
	 *            文件ID
	 * @return
	 */
	public Attachment getFileByFileId(int fileId) {
		return fileMapper.getFileByFileId(fileId);
	}

	/**
	 * 根据相对路径得到文件
	 * 
	 * @author 王璞
	 * @date 2016年12月5日 下午4:33:08
	 * @param relativePath
	 * @return
	 */
	public static File getFileByRelativePath(String relativePath) {
		return new File(SystemConfig.fileSavePath, relativePath);
	}

	/**
	 * 根据相对路径得到绝对路径
	 * 
	 * @author 王璞
	 * @date 2016年12月5日 下午4:41:38
	 * @param relativePath
	 * @return
	 */
	public static String getAbsolutePathByRelativePath(String relativePath) {
		String path = null;
		if ((SystemConfig.fileSavePath.endsWith("/") || SystemConfig.fileSavePath.endsWith("\\"))
				&& (relativePath.startsWith("/") || relativePath.startsWith("\\"))) {
			path = SystemConfig.fileSavePath + relativePath.substring(1, relativePath.length() - 1);
		} else if (!SystemConfig.fileSavePath.endsWith("/") && !SystemConfig.fileSavePath.endsWith("\\") && !relativePath.startsWith("/")
				&& !relativePath.startsWith("\\")) {
			path = SystemConfig.fileSavePath + File.separator + relativePath;
		} else {
			path = SystemConfig.fileSavePath + relativePath;
		}
		return path;
	}

	/**
	 * 文件下载
	 * 
	 * @param attachment
	 * @return
	 * @throws FileNotFoundException
	 */
	public ResponseEntity<byte[]> download(Attachment attachment) throws FileNotFoundException {
		File file = new File(uploadFolder, attachment.getFilePath());
		if (!file.exists()) {
			file = new File(SystemConfig.fileSavePath, attachment.getFilePath());
			if (!file.exists()) {
				throw new FileNotFoundException("文件不存在 fileId=" + attachment.getFileId() + " fileAbsolutePath=" + attachment.getFilePath());
			}
		}
		return download(file, attachment.getFileName());
	}

	/**
	 * 使用相对地址下载
	 * 
	 * @param relativePath
	 *            相对地址
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws FileNotFoundException
	 */
	public ResponseEntity<byte[]> downloadByRelativePath(String relativePath, String fileName) throws FileNotFoundException {
		File file = new File(SystemConfig.fileSavePath, relativePath);
		return download(file, fileName);
	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 *            文件ID
	 * @return
	 * @throws FileNotFoundException
	 */
	public ResponseEntity<byte[]> download(int fileId) throws FileNotFoundException {
		Attachment attachment = getFileByFileId(fileId);
		return download(attachment);
	}

	/**
	 * 文件下载
	 * 
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public ResponseEntity<byte[]> download(File file, String fileName) {
		try {
			return download(FileUtils.readFileToByteArray(file), fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件下载
	 * 
	 * @param data
	 *            文件二进制数据
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public ResponseEntity<byte[]> download(byte[] data, String fileName) {
		HttpHeaders headers = new HttpHeaders();
		try {
			fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		} catch (Exception e) {
		}
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		return responseEntity;
	}

}
