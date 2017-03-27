package com.cnmts.common.dao;

import org.apache.ibatis.annotations.Param;

import com.cnmts.common.bean.Attachment;

public interface FileMapper {

	void saveFile(Attachment attachment);

	void delFileById(@Param("fileId") int id);

	Attachment getFileByFileId(@Param("fileId") int fileId);

}
