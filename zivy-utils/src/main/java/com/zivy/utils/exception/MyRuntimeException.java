package com.zivy.utils.exception;


public class MyRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3537109711029547957L;

	private Number code;
	private String msg;
	private String logmsg;

	public MyRuntimeException(String msg) {
		super(msg);
		this.code = 99999;// 未知
		this.msg = msg;
	}
	
	public String getLogmsg() {
		return logmsg;
	}

	public void setLogmsg(String logmsg) {
		this.logmsg = logmsg;
	}

	public MyRuntimeException(String msg,Number code) {
		  
		this.code = code;
		this.msg = msg;
	}
	public Number getCode() {
		return code;
	}

	public void setCode(Number code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getLocalizedMessage() {
		return this.msg;
	}

}
