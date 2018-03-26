package com.qqtech.core.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单处理异常
 * 
 * @author andy.wangzhh
 *
 */
@SuppressWarnings("serial")
public class OrderException extends RuntimeException {
	public static final Logger log = LoggerFactory.getLogger(OrderException.class);
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public OrderException() {
		super();
	}

	public OrderException(String msg) {
		super(msg);
		this.msg = msg;
		log.info("订单异常-" + msg);
	}

	public OrderException(Exception e) {
		super(e);
	}

	public OrderException(String msg, Exception e) {
		super(msg, e);
		this.msg = msg;
		log.info("订单异常-" + msg);
	}
}
