package com.ls.http.base;

import java.util.Map;

import com.android.volley.VolleyError;

public class ResponseData {
	protected Object data;
	protected Map<String, String> headers;
	protected int statusCode;
	protected VolleyError error;
	protected String responceString;
	
	/**	 
	 * @return Instance of class, specified in response or null if no such class was specified.
	 */
	public Object getData() {
		return data;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public VolleyError getError() {
		return error;
	}

	public String getResponceString()
	{
		return responceString;
	}	
}