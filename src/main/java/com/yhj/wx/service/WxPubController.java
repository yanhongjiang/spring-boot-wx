package com.yhj.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/10/17.
 */
@RestController
public class WxPubController {

    //此处TOKEN即我们刚刚所填的token
    private String TOKEN = "yanhongjiang";
    @Autowired
    private WxService wxService;

    /**
     * 接收并校验四个请求参数
     * @return echostr
     */
    @RequestMapping(value = "/checkName",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void checkName(
                            HttpServletRequest request,
                            HttpServletResponse response){
        boolean isGet = request.getMethod().toLowerCase().equals("get");

        try{
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
            response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
            PrintWriter out = response.getWriter();
            //当使用的是GET方法时
            if(isGet){
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串

                System.out.println("-----------------------开始校验------------------------");
                //排序
                String sortString = sort(TOKEN, timestamp, nonce);
                //加密
                String myString = sha1(sortString);
                //校验
                if (myString != null && myString != "" && myString.equals(signature)) {
                    System.out.println("签名校验通过");
                    //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
                    response.getWriter().write(echostr);
                } else {
                    System.out.println("签名校验失败");
                    response.getWriter().write("");
                }
            }else{
                String respMessage = "异常消息！";
                respMessage = wxService.weixinPost(request);
                out.write(respMessage);

            }
        }catch (Exception e){

                }
    }

    /**
     * 排序方法
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
