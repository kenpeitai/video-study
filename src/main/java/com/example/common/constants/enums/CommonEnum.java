package com.example.common.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonEnum {

    COMMON_ERROR(500, "系统内部错误"),
    COMMON_SUCCESS(200, "操作成功");

    private final int code;
    private final String msg;
}
