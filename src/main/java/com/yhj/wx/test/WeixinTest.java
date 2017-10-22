package com.yhj.wx.test;


import com.yhj.wx.Util.WeixinUtil;
import com.yhj.wx.po.AccessToken;
import net.sf.json.JSONObject;

import java.util.Date;

public class WeixinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			//WeixinUtil.createMenu(token.getToken(),menu);
			//String path = "D:\\2.jpg";
			//String mediaId = WeixinUtil.upload(path, token.getToken(), "image");
			//System.out.println(mediaId);
			//WeixinUtil.getCode();
			//String result = WeixinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
