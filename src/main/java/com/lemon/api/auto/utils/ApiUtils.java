package com.lemon.api.auto.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.Apidetail;
import com.lemon.api.auto.pojo.ExcelObject;

public class ApiUtils {

	static Map<String, ApiInfo> apiInfoMap = new HashMap<String,ApiInfo>();

	static Map<String, Apidetail> apiDetailMap = new HashMap<>();
	
	
	static {
		//把apiinfo信息重新封装成map格式
		List<ApiInfo> objList=(List<ApiInfo>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 1, ApiInfo.class);
		for (ApiInfo obj : objList) {
			ApiInfo apiInfo=(ApiInfo) obj;
			apiInfoMap.put(apiInfo.getApiId(), apiInfo);
		}
		
		//重新把apiDetail信息封装成map
		List<Apidetail> apidetailsList = (List<Apidetail>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx",2,Apidetail.class);
		for (Apidetail apidetail : apidetailsList) {
			apiDetailMap.put(apidetail.getCaseId(), apidetail);
			
			
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
	
	public static void main(String[] args) {
		System.out.println(apiDetailMap.get("10086").getRowNum());
	}
	/**
	 * 通过caseID找到行号
	 * @param caseId
	 * @return
	 */
	public static int getRowNumByCaseId(String caseId){
		return apiDetailMap.get(caseId).getRowNum();
		
	}

}