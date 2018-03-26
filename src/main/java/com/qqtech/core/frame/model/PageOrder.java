package com.qqtech.core.frame.model;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * 分页排序类
 * 
 * @author andy
 * 
 *         2015-6-27
 */
@SuppressWarnings("serial")
public class PageOrder extends Order {

	public PageOrder() {
		this(Direction.DESC, "id");
	}

	public PageOrder(Direction direction, String orderField) {
		super(direction, orderField);
	}
}
