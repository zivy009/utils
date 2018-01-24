package com.zivy.utils.exception;

/**
 * 正常逻辑异常。只做提醒用。
 * @author zivy
 *
 */
public class MyRuntimeRightException extends RuntimeException {

	private static final long serialVersionUID = -3537109711029547957L;

	private Number code;
	private String msg;

	public MyRuntimeRightException(String msg) {
		super(msg);
		this.code = 99999;// 未知
		this.msg = msg;
	}
	
	public MyRuntimeRightException(String msg,Number code) {
		  
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
