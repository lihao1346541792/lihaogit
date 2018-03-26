package com.qqtech.core.frame.model;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * 分页工具类
 * 
 * @author andy
 * 
 *         2015-6-27
 */
@SuppressWarnings("serial")
public class PageKit extends PageRequest {
	public static int PAGE_SIZE = 15;

	public PageKit(Order... orders) {
		this(1, PAGE_SIZE, orders);
	}

	public PageKit(int page, Order... orders) {
		this(page, PAGE_SIZE, orders);
	}

	public PageKit(int page, int size, Order... orders) {
		this(page, size, (ArrayUtils.isEmpty(orders)) ? new Sort(new Order(Direction.DESC, "id")) : new Sort(orders));
	}

	public PageKit(int page, int size, Sort sort) {
		super((page - 1) < 0 ? 0 : page - 1, size < 1 ? PAGE_SIZE : size,
				sort == null ? new Sort(new Order(Direction.DESC, "id")) : sort);
	}
}
