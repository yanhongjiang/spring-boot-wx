package com.yhj.wx.Util;

import com.yhj.wx.http.HttpsUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/10/17.
 */
public class UserInfoUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String WX_APPID = "wxc5d25b23f0cf5b9e";
    public static final String WX_APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

    // 1.获取code的请求地址
    //public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";

    // 替换字符串
    //public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
    //   return String.format(Get_Code, APPID, REDIRECT_URI, SCOPE);
    //}

    // 2.获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET, String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET, CODE);
    }

    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }

    //获取用户信息
    public static JSONObject getUserInfo(String code, String state) {
        // 1. 用户同意授权,获取code
        //用户信息数据对象
        JSONObject userMessageJsonObject = null;
        // 2. 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            String APPID = WX_APPID;
            String SECRET = WX_APPSECRET;
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName, sex, openid = "";
            String REDIRECT_URI = "http://yhj.tunnel.qydev.com/url";
            String SCOPE = "snsapi_userinfo";

            //第一步:用户授权 获取code
            //String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);

            //第二步:get Access Token URL
            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);

            // 通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

            //请求到的Access Token
            JSONObject jsonObject = JSONObject.fromObject(response);

            if (null != jsonObject) {
                try {

                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");

                    System.out.println("获取access_token成功!");
                    System.out.println("WebAccessToken:{} , openId:{}" + WebAccessToken + openId);

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    System.out.println("第三步:获取用户信息的URL:{}" + userMessageUrl);

                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                    userMessageJsonObject = JSONObject.fromObject(userMessageResponse);

                    System.out.println("用户信息:{}" + userMessageJsonObject.toString());
                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            nickName = userMessageJsonObject.getString("nickname");
                            //用户性别
                            sex = userMessageJsonObject.getString("sex");
                            sex = (sex.equals("1")) ? "男" : "女";
                            //用户唯一标识
                            openid = userMessageJsonObject.getString("openid");

                            System.out.println("用户昵称:{}" + nickName);
                            System.out.println("用户性别:{}" + sex);
                            System.out.println("OpenId:{}" + openid);
                        } catch (JSONException e) {
                            System.out.println("获取用户信息失败");
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("获取Web Access Token失败");
                }
            }
        }
        return userMessageJsonObject;
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
            String REDIRECT_URI = url+"/checkName";
            String SCOPE = "snsapi_login"; // snsapi_userinfo // snsapi_login


            //String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE);
            //System.out.println("getCodeUrl:"+getCodeUrl);
        }catch (Exception e){
            e.getStackTrace();
        }

    }



}
