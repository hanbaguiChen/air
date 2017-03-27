package com.cnmts.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	private static final String userAgent_firefox = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3";
	private static String cookie = null;

	/**
	 * 发送表单请求<br />
	 * 默认UTF-8编码
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:34:26
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, new HashMap<String, String>(), null, Consts.UTF_8);
	}

	/**
	 * 发送表单请求<br />
	 * 默认UTF-8编码
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:34:26
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params) {
		return doPost(url, params, null, Consts.UTF_8);
	}

	/**
	 * 发送表单请求<br />
	 * 默认UTF-8编码
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:34:26
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @param header
	 *            HTTP表头
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, String> header) {
		return doPost(url, params, header, Consts.UTF_8);
	}

	/**
	 * 发送表单请求
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:33:56
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @param header
	 *            HTTP表头
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String doPost(String url, Map<String, String> params, Map<String, String> header, Charset charset) {
		String result = "";

		HttpClient httpClient = getHttpClient();
		HttpPost httpPost = getHttpPost(url);
		setHeader(header, httpPost);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			Header[] headers = response.getHeaders("Set-Cookie");
			if (headers != null && headers.length > 0) {
				cookie = headers[0].getValue();
			}
			result = EntityUtils.toString(response.getEntity(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送json请求
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:36:56
	 * @param url
	 *            请求地址
	 * @param jsonObject
	 *            json对象
	 * @param clazz
	 * @return T
	 */
	public static <T> T doJson(String url, Object jsonObject, Class<T> clazz) {
		String result = doJson(url, JsonUtil.toJson(jsonObject), null, Consts.UTF_8);
		return JsonUtil.toObj(result, clazz);
	}

	/**
	 * 发送json请求
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:13:39
	 * @param url
	 *            请求地址
	 * @param jsonObject
	 *            json对象
	 * @return
	 */
	public static String doJson(String url, Object jsonObject) {
		return doJson(url, JsonUtil.toJson(jsonObject), null, Consts.UTF_8);
	}

	/**
	 * 发送json请求
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:14:04
	 * @param url
	 *            请求地址
	 * @param jsonContent
	 *            json字符串内容
	 * @return
	 */
	public static String doJson(String url, String jsonContent) {
		return doJson(url, jsonContent, null, Consts.UTF_8);
	}

	/**
	 * 发送json请求
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午10:11:09
	 * @param url
	 * @param jsonContent
	 *            json内容
	 * @param header
	 *            HTTP头部
	 * @param charset
	 *            编码
	 * @return
	 */
	public static String doJson(String url, String jsonContent, Map<String, String> header, Charset charset) {
		String result = "";

		HttpClient httpClient = getHttpClient();
		HttpPost httpPost = getHttpPost(url);
		setHeader(header, httpPost);

		StringEntity entity = new StringEntity(jsonContent, charset);
		entity.setContentType("application/json");

		try {
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			result = EntityUtils.toString(httpResponse.getEntity(), charset);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @author 王璞
	 * @date 2016年11月28日 上午10:23:37
	 * @param url
	 *            请求地址
	 * @param params
	 *            文本参数
	 * @param fileMap
	 *            文件参数
	 * @return
	 */
	public static String uploadFile(String url, Map<String, String> params, Map<String, File> fileMap) {
		return uploadFile(url, params, fileMap, null);
	}

	/**
	 * 上传文件
	 * 
	 * @author 王璞
	 * @date 2016年11月28日 上午10:22:47
	 * @param url
	 *            请求地址
	 * @param params
	 *            文本参数
	 * @param fileMap
	 *            文件参数
	 * @param header
	 *            头部
	 * @return
	 */
	public static String uploadFile(String url, Map<String, String> params, Map<String, File> fileMap, Map<String, String> header) {
		String result = null;

		HttpClient httpClient = getHttpClient();
		HttpPost httpPost = getHttpPost(url);
		setHeader(header, httpPost);

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
		multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		// 设置表单参数
		if (params != null && params.size() > 0) {
			Set<String> keySet = params.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				StringBody stringBody = new StringBody(params.get(key), ContentType.TEXT_PLAIN);
				multipartEntityBuilder.addPart(key, stringBody);
			}

		}
		// 设置上传的文件参数
		if (fileMap != null && fileMap.size() > 0) {
			Set<String> keySet = fileMap.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				FileBody fileBody = new FileBody(fileMap.get(key), ContentType.APPLICATION_OCTET_STREAM);
				multipartEntityBuilder.addPart(key, fileBody);
			}
		}
		httpPost.setEntity(multipartEntityBuilder.build());
		try {
			HttpResponse execute = httpClient.execute(httpPost);
			HttpEntity entity = execute.getEntity();
			result = EntityUtils.toString(entity, Charset.forName("UTF-8"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 下载文件
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午9:58:56
	 * @param url
	 *            下载地址
	 * @param desFilePath
	 *            文件存放路径
	 * @return File
	 */
	public static File downloadFile(String url, String desFilePath, Map<String, String> params) {
		return downloadFile(url, new File(desFilePath), params);
	}

	/**
	 * 下载文件
	 * 
	 * @author 王璞
	 * @date 2016年11月14日 上午9:58:06
	 * @param url
	 *            下载地址
	 * @param file
	 *            存储文件
	 * @return File
	 */
	public static File downloadFile(String url, File file, Map<String, String> params) {
		HttpClient httpClient = getHttpClient();
		HttpPost httpPost = getHttpPost(url);

		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null) {
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			InputStream inputStream = httpClient.execute(httpPost).getEntity().getContent();
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buff = new byte[1024 * 1024];
			int length = 0;
			while ((length = bufferedInputStream.read(buff)) != -1) {
				bufferedOutputStream.write(buff, 0, length);
				bufferedOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return file;
	}

	private static HttpClient getHttpClient() {
		HttpClientBuilder create = HttpClientBuilder.create();
		return create.build();
	}

	private static HttpPost getHttpPost(String url) {
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", userAgent_firefox);
		post.setHeader("Cookie", cookie);
		return post;
	}

	@SuppressWarnings("unused")
	private static HttpGet getHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", userAgent_firefox);
		httpGet.setHeader("Cookie", cookie);
		return httpGet;
	}

	private static void setHeader(Map<String, String> header, Object obj) {
		if (header != null) {// 设置header
			Set<String> headerKey = header.keySet();
			for (Iterator<String> iterator = headerKey.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String value = header.get(key);
				if (obj instanceof HttpPost) {
					((HttpPost) obj).setHeader(key, value);
				}
				if (obj instanceof HttpGet) {
					((HttpPost) obj).setHeader(key, value);
				}
			}
		}
	}
}