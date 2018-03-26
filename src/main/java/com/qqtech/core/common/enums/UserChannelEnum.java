package com.qqtech.core.common.enums;
/**
 * 用户账号渠道类型:1-手机;2-Email;3-微信;4-QQ;5-新浪微博
 * @author andy
 *
 */
public enum UserChannelEnum {
	/**1-手机**/
	PHONE(1),
	/**2-Email**/
    EMAIL(2),
    /**3-微信**/
	WEIXIN(3),
	/**4-QQ**/
	QQ(4),
	/**5-新浪微博**/
	XINA(5);
    int code;

    UserChannelEnum (int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
