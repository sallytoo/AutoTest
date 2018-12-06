package com.lemon.api.auto.pojo;

public class Apidetail extends ExcelObject{
	
	//用例编号
	private String caseId;
	//接口编号
	private String apiId;
	//是否执行
	private String isExcute;
	//接口请求参数
	private String requestData;
	//期望响应数据
	private String expectedReponseData;
	//实际响应数据
	private String ActualReponseData;
	
	

	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getIsExcute() {
		return isExcute;
	}
	public void setIsExcute(String isExcute) {
		this.isExcute = isExcute;
	}
	public String getRequestData() {
		return requestData;
	}
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getExpectedReponseData() {
		return expectedReponseData;
	}
	public void setExpectedReponseData(String expectedReponseData) {
		this.expectedReponseData = expectedReponseData;
	}
	public String getActualReponseData() {
		return ActualReponseData;
	}
	public void setActualReponseData(String actualReponseData) {
		ActualReponseData = actualReponseData;
	}
	@Override
	public String toString() {
		return "Apidetail [caseId=" + caseId + ", apiId=" + apiId + ", isExcute=" + isExcute + ", requestData="
				+ requestData + ", expectedReponseData=" + expectedReponseData + ", ActualReponseData="
				+ ActualReponseData + ", getRowNum()=" + getRowNum() + "]";
	}
	
	
	
	

}
