package com.qqtech.core.common.enums;
/**
 * 性别枚举类:1-男;2-女
 * @author andy.wangzhh
 *
 */
public enum SexEnum {
	MALE(1), FEMALE(2);
	int code;

	SexEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
