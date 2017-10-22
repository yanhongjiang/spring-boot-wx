package com.yhj.wx.po;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/10/21.
 */
@Component
@ConfigurationProperties(prefix = "boy")
public class GlobalProperties {
    //项目的根链接
    private String GlobalProjectRoot;

    public String getGlobalProjectRoot() {
        return GlobalProjectRoot;
    }

    public void setGlobalProjectRoot(String globalProjectRoot) {
        GlobalProjectRoot = globalProjectRoot;
    }
}
