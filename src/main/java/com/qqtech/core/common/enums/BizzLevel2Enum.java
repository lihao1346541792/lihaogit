package com.qqtech.core.common.enums;
/**
 * 系统业务模块第二层:BA-圈子话题;CA-社区大喇叭;EA-家庭幸福窝窝
 * @author andy
 *
 */
public enum BizzLevel2Enum {
	/**AA-用户话题 (友事)**/
    USER_TOPIC("AA"),
	/**BA-圈子话题 **/
    CIRCLE_TOPIC("BA"),
    /**CA-社区大喇叭 **/
	COMMUNITY_MESSAGE("CA"),
	/**EA-家庭幸福窝窝 **/
	HOME_MESSAGE("EA"),
	/**HA-商城商品 **/
	MALL_PRODUCT("HA"),
	/**FA-交易信息 **/
	IDLE_MESSAGE("FA"),
	/**IA-圈商-店铺 **/
	QSHOP_SHOP("IA");

    String code;

    BizzLevel2Enum (String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
