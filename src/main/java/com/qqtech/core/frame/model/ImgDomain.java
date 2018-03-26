package com.qqtech.core.frame.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 图片封装类
 * 
 * @author andy
 * 
 *         2016-10-14
 */
@SuppressWarnings("serial")
public class ImgDomain implements Serializable {
	/**
	 * 对应图片的数量
	 */
	protected int count;

	/**
	 * 单张图片全路径地址:http://.../.../xxx.jpg
	 */
	protected String source;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
