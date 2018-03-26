package com.qqtech.core.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付业务异常
 * 
 * @author andy.wangzhh
 *
 */
@SuppressWarnings("serial")
public class PayException extends RuntimeException {
	public static final Logger log = LoggerFactory.getLogger(PayException.class);
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public PayException() {
		super();
	}

	public PayException(String msg) {
		super(msg);
		this.msg = msg;
		log.info("支付业务异常-" + msg);
	}

	public PayException(Exception e) {
		super(e);
	}

	public PayException(String msg, Exception e) {
		super(msg, e);
		this.msg = msg;
		log.info("支付业务异常-" + msg);
	}
}
