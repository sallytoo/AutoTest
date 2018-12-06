package com.lemon.api.auto.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.ExcelObject;

public class HttpUtils {

	

	/**
	 * 用post方式发送请求
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	private static String post(String url, Map<String, String> paramsMap) {
		String respResult = "";
		try {
			if (paramsMap != null) {
				// 生成post请求
				HttpPost post = new HttpPost(url);
				// 设置post请求参数--请求体中间
				// 创建一个容器，将保存在HashMap中的参数保存到这个容器中
				List<NameValuePair> paramsList = new ArrayList<>();
				Set<String> keySet = paramsMap.keySet();
				for (String key : keySet) {
					String value = paramsMap.get(key);
					paramsList.add(new BasicNameValuePair(key, value));
				}
				// 创建一个原生form表单的请求体
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList);
				// 设置post请求体
				post.setEntity(entity);

				// 创建一个HTTP发送客户端(HTTP发包客户端具备这样的功能：浏览器、postman、jmeter、fiddler、app)
				CloseableHttpClient httpClient = HttpClients.createDefault();

				// 发数据包
				CloseableHttpResponse response = httpClient.execute(post);

				// 获取响应体中的内容
				HttpEntity respEntity = response.getEntity();
				respResult = EntityUtils.toString(respEntity);
				return respResult;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respResult;
	}

	/**
	 * 用get方式发送请求 进一步优化get方法，将设置参数的容器换成HashMap，将所依赖的HttpCLient：List
	 * <NameValuePair>，提取到方法中
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	private static String get(String url, Map<String, String> paramsMap) {
		String respResult = "";
		if (paramsMap != null) {
			try {
				List<NameValuePair> paramsList = new ArrayList<>();

				Set<String> keySet = paramsMap.keySet();
				for (String key : keySet) {
					String value = paramsMap.get(key);
					paramsList.add(new BasicNameValuePair(key, value));
				}

				// 组合拼接参数
				url += ("?" + URLEncodedUtils.format(paramsList, "utf-8"));
				HttpGet get = new HttpGet(url);

				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get);

				HttpEntity respEntity = response.getEntity();
				respResult = EntityUtils.toString(respEntity);
				return respResult;

			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		return respResult;
	}

	public static String get1(String url, List<NameValuePair> parameters) {

		try {
			// 组合拼接参数
			url += ("?" + URLEncodedUtils.format(parameters, "utf-8"));
			HttpGet get = new HttpGet(url);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(get);

			HttpEntity respEntity = response.getEntity();
			// String respResult = EntityUtils.toString(respEntity);
			return EntityUtils.toString(respEntity);

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 通过apiId获得对应的url
	 * 
	 * @param apiId
	 * @return
	 */

/*public static void main(String[] args) {
		Set<String> keySet = apiInfoMap.keySet();
		for (String key : keySet) {
			System.out.println(apiInfoMap.get(key).getUrl());

		}
		// apiInfoMap.get("1");
//	*/

	/**
	 * 发包（分发各种请求）
	 * 
	 * @param apiId
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static String request(String apiId, String url, Map<String, String> paramsMap) {
		String result = "";
		String method = ApiUtils.getRequestMethodByApiId(apiId);
		if ("post".equals(method)) {
			result = post(url, paramsMap);
		} else if ("get".equals(method)) {
			result = HttpUtils.get(url, paramsMap);
		}
		return result;

	}
}
