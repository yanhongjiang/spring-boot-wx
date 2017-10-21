package com.yhj.wx.Util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.yhj.wx.menu.Button;
import com.yhj.wx.menu.ClickButton;
import com.yhj.wx.menu.Menu;
import com.yhj.wx.menu.ViewButton;
import com.yhj.wx.po.AccessToken;
import com.yhj.wx.trans.Data;
import com.yhj.wx.trans.Parts;
import com.yhj.wx.trans.Symbols;
import com.yhj.wx.trans.TransResult;
import com.yhj.wx.translate.demo.TransApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * 微信工具类
 * @author Stephen
 *
 */
public class WeixinUtil {
	/*private static final String APPID = "wx64deacfa61bd4565";
	private static final String APPSECRET = "e05242fce5650d7b74f765c966501afc";*/

	private static final String APPID = "wxc5d25b23f0cf5b9e";
	private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//百度账号的id
	private static final String BD_APP_ID ="20171021000089956";
	//百度账号的秘钥
	private static final String BD_SECURITY_KEY ="2iiIX_7CuhsceFqYolQm";
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}

	/**
	 * 获取accessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		//获取资源文件
		Properties properties = PropertiesUtil.gitProperties();
		//获取保存的票据
		String access_token = properties.getProperty("ACCESS_TOKEN");
		//获取票据失效时间
		long token_lose_time = Long.parseLong(StringUtil.isEmpty(properties.getProperty("TOKEN_LOSE_TIME"))?"0":properties.getProperty("TOKEN_LOSE_TIME"));
		//当前时间
		long curren_time= new Date().getTime()/1000;
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

		//判断是否有存过票据
		if(!StringUtil.isEmpty(access_token) &&!StringUtil.isEmpty(token_lose_time)&& token_lose_time>curren_time){
			token.setToken(access_token);
			token.setExpiresIn(Integer.valueOf((token_lose_time-curren_time)+""));
		}else{
			JSONObject jsonObject = doGetStr(url);
			if(jsonObject!=null){
				//保存的票据
				properties.setProperty("ACCESS_TOKEN",jsonObject.getString("access_token"));
				//保存票据失效时间 （ACCESS_TOKEN生成时间+7100）一个有效期为7200秒，少100秒留给程序保证每次获取的都是有效的
				properties.setProperty("TOKEN_LOSE_TIME",curren_time+7100+"");
				//更新资源对像
				PropertiesUtil.storeProperties(properties);
				token.setToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}
		}

		return token;
	}

	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");

		ViewButton button21 = new ViewButton();
		button21.setName("view菜单");
		button21.setType("view");
		button21.setUrl("http://www.baidu.com");

		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");

		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");

		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});

		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}

	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}

	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	public static String translate(String source) throws ParseException, IOException{
		TransApi api = new TransApi(BD_APP_ID, BD_SECURITY_KEY);
		//结果语音
		String toLanguage ="auto";
		System.out.println(" @的索引"+source.indexOf("@"));
		String sourceNew = "";
		if(source.indexOf("JP")>0){
			sourceNew =source.replaceAll("JP","");
			toLanguage = "jp";
		}else{
			sourceNew = source;
		}
		JSONObject jsonObject = api.getTransResult(sourceNew, "auto", toLanguage);
		System.out.println(jsonObject.toString());
		StringBuffer dstBuff = new StringBuffer();
		if(!"[]".equals(jsonObject.toString())){
			String from  = jsonObject.getString("from");
			String to  = jsonObject.getString("to");
			JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
			dstBuff.append("翻译结果： ");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				TransResult transResult = (TransResult) JSONObject.toBean(jsonObj, TransResult.class);
				String src = transResult.getSrc();
				String dst = transResult.getDst();
				if("zh".equals(from)&&"en".equals(to)){
					dstBuff.append("中文“"+src+"” 的英文翻译为“"+dst+"“");
				}else if("zh".equals(to)&&"en".equals(from)){
					dstBuff.append("英文“"+src+"” 的中文翻译为“"+dst+"“");
				}else if("jp".equals(to)&&"zh".equals(from)){
					dstBuff.append("中文“"+src+"” 的日文翻译为“"+dst+"“");
				}else if("zh".equals(to)&&"jp".equals(from)){
					dstBuff.append("日文“"+src+"” 的中文翻译为“"+dst+"“");
				}else{
					dstBuff.append(""+src+"” 的翻译为“"+dst+"“");
				}

			}
		}else{
			//dst.append(translateFull(source));
		}
		System.out.println("翻译结果："+dstBuff.toString());
		return dstBuff.toString();
	}

	public static String translateFull(String source) throws ParseException, IOException{
		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=mMlyQp8aGeYLkDxf5GtKbTmD&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = doGetStr(url);
		StringBuffer dst = new StringBuffer();
		List<Map> list = (List<Map>) jsonObject.get("trans_result");
		for(Map map : list){
			dst.append(map.get("dst"));
		}
		return dst.toString();
	}
}
