package com.webdemo.enums;

public enum ResultEnum {
    USER_NOT_FOUND(404, "用户未找到"),
    PWD_NOT_MATCH(405, "密码不匹配"),
    LOGIN_SUCESS(200, "登陆成功");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
