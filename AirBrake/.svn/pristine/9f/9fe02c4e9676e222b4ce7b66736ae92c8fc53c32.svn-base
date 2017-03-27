package com.cnmts.common.view;

import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

import com.cnmts.common.util.JsonUtil;

public class JsonView extends AbstractView {

	private Object object;

	public JsonView(Object object) {
		this.object = object;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		ServletOutputStream outputStream = response.getOutputStream();

		String json = JsonUtil.toJson(this.object);
		System.out.println(json);
		if (object != null) {
			outputStream.write(JsonUtil.toJson(this.object).getBytes(Charset.forName("UTF-8")));
			outputStream.flush();
			outputStream.close();
		}
	}

}
