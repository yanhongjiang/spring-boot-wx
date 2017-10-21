package com.yhj.wx.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/10/17.
 */
public class UserInfoUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 1.获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";
    // 替换字符串
    public static String getCode(String APPID, String REDIRECT_URI,String SCOPE) {
        return String.format(Get_Code,APPID,REDIRECT_URI,SCOPE);
    }

    // 2.获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET,String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET,CODE);
    }

    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token,openid);
    }


    public static void main(String[] args) {

        try{
            //获取资源文件
            Properties properties = PropertiesUtil.gitProperties();
            //系统链接
            String url = properties.getProperty("api");
            //微信appid
            String appId = properties.getProperty("appid");
            //微信secret
            String secret = properties.getProperty("secret");
            String REDIRECT_URI = url+"/url";
            String SCOPE = "snsapi_login"; // snsapi_userinfo // snsapi_login


            String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE);
            System.out.println("getCodeUrl:"+getCodeUrl);
        }catch (Exception e){
            e.getStackTrace();
        }

    }



}
