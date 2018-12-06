package com.lemon.api.auto.testCase;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.pojo.Apidetail;
import com.lemon.api.auto.pojo.ExcelObject;
import com.lemon.api.auto.utils.HttpUtils;
import com.lemon.api.auto.utils.ApiUtils;
import com.lemon.api.auto.utils.ExcelUtils;



public class AllTestCase {

	@DataProvider
	public Object[][] datas() {
		List<ExcelObject> objList = (List<ExcelObject>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 2, Apidetail.class);
		int size = objList.size();
		// 创建一个容器-->数据提供者需要的二维数组-->只要获得需要的信息即可
		Object[][] datas = new Object[size][4];
		for (int i = 0; i < size; i++) {
			Apidetail apidetail = (Apidetail) objList.get(i);
			datas[i][0] = apidetail.getCaseId();
			datas[i][1] = apidetail.getApiId();
			datas[i][2] = apidetail.getRequestData();
			datas[i][3] = apidetail.getExpectedReponseData();
		}
		return datas;
	}

	@Test(dataProvider = "datas")
	public void f(String CaseId, String apiId, String requestData, String excepectedResponseData) {
		//1、获取URL地址
		String url = ApiUtils.getUrlByApiId(apiId);
		//2、将json数据转为Map<String,String>
		Map<String, String> paramsMap = (Map<String, String>) JSONObject.parse(requestData);
		// 3、发包
		String result = HttpUtils.request(apiId, url, paramsMap);
		System.out.println(result);
		//4、断言
		//还不如保存起来---》将result写会到api_test_case_01.xlsx的第2个sheet的某一行的某一行
		ExcelUtils.writexcel("target/classes/api_test_case_01.xlsx",2,CaseId,6,result);
		
		System.out.println(result);

	}

}
