package com.lemon.api.auto.pojo;

public class ApiInfo extends ExcelObject{
	private String ApiId;
	private String ApiName;
	private String Type;
	private String Url;
	
	
	public String getApiId() {
		return ApiId;
	}
	public void setApiId(String apiId) {
		ApiId = apiId;
	}
	public String getApiName() {
		return ApiName;
	}
	public void setApiName(String apiName) {
		ApiName = apiName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	@Override
	public String toString() {
		return "ApiInfo [ApiId=" + ApiId + ", ApiName=" + ApiName + ", Type=" + Type + ", Url=" + Url + "]";
	}
	
	}
	
	
	


