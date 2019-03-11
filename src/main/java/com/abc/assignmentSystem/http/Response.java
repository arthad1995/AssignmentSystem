package com.abc.assignmentSystem.http;

import java.util.List;



import lombok.Data;

@Data
public class Response<T> {

	private boolean success;
	private int code;
	private String message;
	private T payload;
	

	public Response() {
	
		super();
	}
	
	public Response(boolean success) {
		super();
		this.success = success;
		this.code = success ? 200 : 400;
		this.message = "";
	}
	
	public Response(boolean success, String message,T payload) {
		super();
		this.success = success;
		this.code = success ? 200 : 400;
		this.message = message;
		this.payload = payload;
	}

	public Response(boolean success, int code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	

	public boolean isSuccess() {
		return success;
	}

	public Response(boolean success, int code, String message, T payload) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.payload = payload;
	}

	public Response(boolean success, T payload) {
		super();
		this.success = success;
		this.code = success ? 200 : 400;
		this.payload = payload;
	}
}

