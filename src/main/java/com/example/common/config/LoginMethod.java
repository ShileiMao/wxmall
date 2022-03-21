package com.example.common.config;

public enum LoginMethod {

    NAME_PWD("pwd"),
    WECHAT("wechat");

    public String value;

    LoginMethod(String value) {
        this.value = value;
    }
}
