package com.qqtech.core.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登陆异常(用于判断根据用户的token查询返回空的异常处理)
 * 
 * @author andy.wangzhh
 *
 */
@SuppressWarnings("serial")
public class LoginException extends RuntimeException {
	public static final Logger log = LoggerFactory.getLogger(LoginException.class);
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LoginException() {
		super();
	}

	public LoginException(String msg) {
		super(msg);
		this.msg = msg;
		log.info("登陆异常-" + msg);
	}

	public LoginException(Exception e) {
		super(e);
	}

	public LoginException(String msg, Exception e) {
		super(msg, e);
		this.msg = msg;
		log.info("登陆异常-" + msg);
	}
}
