package com.lemon.api.auto.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.ExcelObject;

public class ApiUtils {

	static Map<String, ApiInfo> apiInfoMap = new HashMap<String,ApiInfo>();

	static {
		
		List<ExcelObject> objList=(List<ExcelObject>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 1, ApiInfo.class);
		for (Object obj : objList) {
			ApiInfo apiInfo=(ApiInfo) obj;
			apiInfoMap.put(apiInfo.getApiId(), apiInfo);
		
		}
}
	public static String getUrlByApiId(String apiId) {

		return apiInfoMap.get(apiId).getUrl();
	}

	/**
	 * 通过apiId获得请求方法：get or post
	 * 
	 * @param apiId
	 * @return
	 */
	public static String getRequestMethodByApiId(String apiId) {
		return apiInfoMap.get(apiId).getType();
	}

}