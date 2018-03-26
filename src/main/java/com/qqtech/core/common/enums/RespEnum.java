package com.qqtech.core.common.enums;

/**
 * 访问限制类型
 * 
 * @author andy.wangzhh
 * 
 *         2016-7-1
 */
public enum RespEnum {
	OK(1, "正常"), LOGIN(2, "未登录"), AUTHREAL(3, "未身份认证"), PARAMERROR(4, "参数校验错误"), EXCEPTION(5, "系统异常"), UPGRADE(6,
			"系统急需升级"), AUTHCOMMUNITY(7, "未认证社区住户"), ILLEGAL(9, "非法请求");
	private int code;
	private String desc;

	private RespEnum(int code, String desc) {
		this.desc = desc;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static String getDesc(Integer code) {
		RespEnum statusEnum = getInstance(code);
		if (statusEnum != null) {
			return statusEnum.getDesc();
		}
		return "";
	}

	public static RespEnum getInstance(Integer code) {
		if (code != null) {
			for (RespEnum statusEnum : RespEnum.values()) {
				if (code.equals(statusEnum.getCode())) {
					return statusEnum;
				}
			}
		}
		return null;
	}

}
