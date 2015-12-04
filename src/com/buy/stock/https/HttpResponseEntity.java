package com.buy.stock.https;

public class HttpResponseEntity {
	/**
	 * HTTP请求结果码
	 */
	private int httpResponseCode;

	/**
	 * HTTP请求结果
	 */
	private byte[] content;

	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
