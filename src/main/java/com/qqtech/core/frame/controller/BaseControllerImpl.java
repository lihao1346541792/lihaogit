package com.qqtech.core.frame.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qqtech.core.frame.model.ABaseable;
import com.qqtech.core.frame.service.BaseService;

public abstract class BaseControllerImpl<T extends ABaseable, Q extends T> implements BaseController<T, Q> {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取基础的服务
	 * 
	 * @return BaseService
	 */
	protected abstract BaseService<T> getBaseService();
}
