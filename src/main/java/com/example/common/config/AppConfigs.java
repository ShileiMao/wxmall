package com.example.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.yml")
public class AppConfigs {
    @Autowired
    private Environment environment;

    @Value("${wechat_app_id}")
    private String wechatAppID;

    public String getWechatAppID() {
        return wechatAppID;
    }

    @Value("${wechat_app_secruit}")
    private String wecahtAppSec;

    public String getWecahtAppSec() {
        return wecahtAppSec;
    }



}
