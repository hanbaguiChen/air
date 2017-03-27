package com.cnmts.common.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cnmts.common.bean.Attachment;
import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.exception.InvalidDataException;
import com.cnmts.common.service.FileService;
import com.cnmts.common.util.SessionUtil;

@Controller
@RequestMapping(value = "/file")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * 上传文件
	 * 
	 * @author 王璞
	 * @date 2016年11月3日 下午2:20:32
	 * @param multipartFile
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/uploadFile" })
	@ResponseBody
	public JsonResult<Attachment> uploadFile(@RequestParam(name = "file") MultipartFile multipartFile, HttpSession session)
			throws Exception {
		if (multipartFile.getSize() <= 0) {
			throw new InvalidDataException("文件大小为0");
		}
		int currentUserId = SessionUtil.getCurrentUserId(session);

		Attachment attachment = fileService.saveFile(multipartFile, currentUserId);

		return new JsonResult<Attachment>(attachment);
	}
}
