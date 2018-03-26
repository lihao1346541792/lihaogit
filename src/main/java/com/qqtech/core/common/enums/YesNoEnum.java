package com.qqtech.core.common.enums;

public enum YesNoEnum {
	YES(1),
    NO(2);
    int code;

    YesNoEnum (int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
