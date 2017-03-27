package com.cnmts.common.view;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

public class WorkBookView extends AbstractView {

	private Workbook workbook;

	public WorkBookView(Workbook workbook) {
		this.workbook = workbook;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		if (outputStream != null) {
			outputStream.flush();
			outputStream.close();
		}
		if (workbook != null) {
			workbook.close();
		}
	}

}
