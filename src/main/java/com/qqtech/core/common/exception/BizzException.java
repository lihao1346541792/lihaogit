package com.qqtech.core.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 普通业务异常
 * 
 * @author andy.wangzhh
 *
 */
@SuppressWarnings("serial")
public class BizzException extends RuntimeException {
	public static final Logger log = LoggerFactory.getLogger(BizzException.class);

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BizzException() {
		super();
	}

	public BizzException(String msg) {
		super(msg);
		this.msg = msg;
		log.info("普通业务异常-" + msg);
	}

	public BizzException(Exception e) {
		super(e);
	}

	public BizzException(String msg, Exception e) {
		super(msg, e);
		this.msg = msg;
		log.info("普通业务异常-" + msg);
	}
}
