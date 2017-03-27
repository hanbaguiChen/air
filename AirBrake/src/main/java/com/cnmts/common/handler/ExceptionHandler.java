package com.cnmts.common.handler;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.cnmts.common.bean.JsonResult;
import com.cnmts.common.bean.ResultType;
import com.cnmts.common.exception.AccessNotAllowException;
import com.cnmts.common.exception.InvalidDataException;
import com.cnmts.common.util.AccessObjectUtil;
import com.cnmts.common.view.JsonView;
import com.cnmts.system.exception.UserNotLoginException;

/**
 * 统一异常处理器
 * 
 * @author 王璞
 * @date 2016年4月5日 下午3:44:26
 * @version 1.0
 */
public class ExceptionHandler extends ExceptionHandlerExceptionResolver {

	private static Logger logger = Logger.getLogger(ExceptionHandler.class);

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		exception.printStackTrace();
		if (handlerMethod == null) {
			return null;
		}

		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		String ipAddress = AccessObjectUtil.getIpAddress(request);
		StringBuilder builder = new StringBuilder("ipAddress=" + ipAddress + " 访问URL=" + AccessObjectUtil.getAccessUrl(request) + "  ");

		ResponseBody responseBodyAnnotation = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		if (responseBodyAnnotation != null) {// 移动端
			JsonResult<String> result = new JsonResult<String>();
			// 未登录
			if (exception instanceof UserNotLoginException) {
				builder.append("  UserNotLoginException 尚未登录");
				result.setCode(ResultType.NOT_LOGIN);
			} else if (exception instanceof InvalidDataException) {// 数据校验异常
				builder.append("  InvalidDataException  数据校验异常");
				result.setCode(ResultType.INVALID_DATA);
			} else if (exception instanceof AccessNotAllowException) {// 禁止访问
				builder.append("  AccessNotAllowException  禁止访问");
				result.setCode(ResultType.ACCESS_NOT_ALLOW);
			} else {
				builder.append("  未知错误");
				result.setCode(ResultType.WRONG);
			}
			logger.info(builder, exception);
			return new ModelAndView(new JsonView(result));
		} else {// web端
			builder.append("  访问WEB端");
			logger.info(builder);
		}
		return null;
	}
}
