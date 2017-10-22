package com.yhj.wx.Util;

/**
 * 授权工具类
 * Created by Administrator on 2017/10/22.
 */
public class AuthorizeUtil {
    //免确认授权
    private static String SNSAPI_BASE ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc5d25b23f0cf5b9e&redirect_uri=http://yanhj23.imwork.net:30684%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
    //确认授权
    private static String SNSAPI_USERINFO ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc5d25b23f0cf5b9e&redirect_uri=http://yanhj23.imwork.net:30684%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    //获取免确认的授权URL
    public static String getSnsapiBaseURL(String url){
        return String.format(SNSAPI_BASE,url);
    }
    //获取确认的授权URL
    public static String getSnsapiUserinfoURL(String url){
        return String.format(SNSAPI_USERINFO,url);
    }
}
