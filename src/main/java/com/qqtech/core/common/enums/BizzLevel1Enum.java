package com.qqtech.core.common.enums;
/**
 * 系统业务模块第一层:A-用户;B-圈子;C-社区;D-家政;E-家庭;F-闲置品;G-活动
 * @author andy
 *
 */
public enum BizzLevel1Enum {
	/**A-用户 **/
	USER("A"),
	/**B-圈子 **/
    CIRCLE("B"),
    /**C-社区**/
	COMMUNITY("C"),
	/**D-家政 **/
	HOMEMAKING("D"),
	/**E-家庭 **/
	HOME("E"),
	/**F-闲置品 **/
	IDLE("F"),
	/**G-活动 **/
	ACTIVITY("G"),
	/**H-商城 **/
	MALL("H"),
	/**I-圈商 **/
	QSHOP("I");
    String code;

    BizzLevel1Enum (String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
