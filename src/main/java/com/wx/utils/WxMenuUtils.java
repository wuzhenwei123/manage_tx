package com.wx.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ConfigConstants;
import com.base.utils.https.HttpUtils;
import com.wx.utils.https.HttpClientConnectionManager;
import com.wx.utils.https.HttpRequest;

/**
 * 用于微信自定义菜单的创建，查询和删除。
 * @author GXYHWZ16
 *
 */

public class WxMenuUtils {
	
	// http客户端  
//    public static  CloseableHttpClient httpclient;  
//      
//    static {  
//    	
//    	
//        httpclient = HttpClientBuilder.create().build();
//        httpclient = (CloseableHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端  
//    }  
  
    public static void main(String args[]){
   	 try {  
   		 ConfigConstants.init();
         String accessToken = getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET); 
           
          String MENU = "{\"button\":[{\"type\":\"view\",\"name\":\"生活缴费\",\"url\":\""+getUrl("index")+"\"}," +
			"{\"name\":\"门票和商品\",\"sub_button\":"+
			"[{\"type\":\"view\",\"name\":\"国外门票\",\"url\":\"https://www.tiqets.com/zh/\"},"+
			"{\"type\":\"view\",\"name\":\"精品购物\",\"url\":\"https://ds.mievie.com/\"},"+
			"{\"type\":\"view\",\"name\":\"我要开店\",\"url\":\"https://www.mievie.com/\"}]}," +
			"{\"name\":\"关于我们\",\"sub_button\":"+
			"[{\"type\":\"view\",\"name\":\"赚点钱\",\"url\":\""+getUrl("jb")+"\"},"+ 
			"{\"type\":\"view\",\"name\":\"吉云信网\",\"url\":\"http://www.10111.com.cn/\"}]}" +
			"]}";
          
           String res = createMenu(MENU, accessToken);  
       	   System.out.println(res);
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
    }
	/**
	 * 带openId的url
	 * @throws UnsupportedEncodingException 
	 */
	public static String getUrl(String state) throws UnsupportedEncodingException{
		String param = URLEncoder.encode(ConfigConstants.URL_PATH+"/weixin/access?appid="+ConfigConstants.APPID, "utf-8");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConfigConstants.APPID+"&redirect_uri="+param+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		System.out.println(url);
		return url;
	}  
      
    /** 
     * 创建菜单 
     */  
    public static String createMenu(String params, String accessToken) throws Exception {  
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, "POST", params);
        JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("errmsg"); 
    }  
    
    /**
     * 批量获取用户基本信息
     */
    
    public static void getFromUserSMess(String accessToken,String openIDS) throws Exception{
       
        if(StringUtils.isNotBlank(openIDS)){
        	String[] openID = openIDS.split(",");
        	for(int i=0;i<openID.length;i++){
        		String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", "access_token=" + accessToken + "&openid="+openID[i]+"&lang=zh_CN");
        		 JSONObject json = JSONObject.parseObject(jsonStr);
                System.out.println("update manage_admin_user set nickName='"+json.getString("nickname")+"',realName='"+json.getString("nickname")+"',headImg='"+json.getString("headimgurl")+"' where openId = '"+json.getString("openid")+"';");
        	}
        }
        
    }
  
    /** 
     * 获取accessToken 
     */  
    public static String getAccessToken(String appid, String secret) throws Exception {  
        
    	String param = "grant_type=client_credential&appid=" + appid + "&secret=" + secret;
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid=" + appid + "&secret=" + secret);
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);  
//        HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("access_token"); 
    }  
      
      
    /** 
     * 删除自定义菜单 
     */  
    public static String deleteMenu(String accessToken) throws Exception {  
        
    	
    	String param = "access_token=" + accessToken;
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/menu/delete", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/menu/delete", "access_token=" + accessToken);
//    	 HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);  
//         HttpResponse response = httpclient.execute(get);  
//         String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
    	
    	JSONObject json = JSONObject.parseObject(jsonStr);
        return  json.getString("errmsg"); 
    }  
    
    /**
     * 获取用户基本信息
     */
    
    public static String getFromUserMess(String accessToken,String openID) throws Exception{
        
    	
    	String param = "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/user/info", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN");
        
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN" );  
//    	HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
    	
    	return  jsonStr;
    }
    
    /**
     * 获取用户基本信息
     */
    
    public static String getFromUserMessBySQ(String accessToken,String openID) throws Exception{
        
    	
    	String param = "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/sns/userinfo", "GET", param);
    	
//        String jsonStr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/userinfo", "access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN");
//    	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="+openID+"&lang=zh_CN" );  
//    	HttpResponse response = httpclient.execute(get);  
//        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
    	System.out.println("============jsonStr===================="+jsonStr);
        return  jsonStr;
    }
    
    /**
     * 发送客服消息
     */
    
    public static void postMess(String params,String accessToken) throws Exception{
    	
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken, "POST", params);
    	
    	System.out.println("客服消息------------------"+jsonStr);
    }

    
    public static void getLoginMsg(String params)throws Exception{
        String jsonStr = HttpRequest.sendGet("http://203.208.238.200/toeclass/wechat/login.do", "openID=15235sdfs_sssdsfdsf&input_loginName=yijidaifu&input_password=yijidaifu");
        System.out.println(jsonStr);
    }
    
    /** 
     *获取aouth2.0网页认证返回码 
     */  
    public static String getAccessCode(String APPID,String SECRET,String code) throws Exception {
    	String param = "appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/sns/oauth2/access_token", "GET", param);
    	System.out.println("jsonStr------------"+jsonStr);
    	return jsonStr;  
    }  
    
    /* 生成关注二维码
	 * @param appid
	 * @param secret
	 */
	public static String getEWM(String appid, String secret,String userId){
		String imgurl = null;
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"expire_seconds\": 60,\"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":\""+userId+"\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
//			
//			
	    	String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
			
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return imgurl;
	}
	
	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
	
	/**
	 * 发送模板信息
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String sendTempltMsg(String appid, String secret, String template_id, String toOPENID){
		try{
			String accessToken = getAccessToken(appid, secret);
			toOPENID = "ogJyvuC4eTh_jSkW-JrFgpGZeKBY";
			template_id = "Y1tUY-szw7FGbzOBeiSarO2YDjheyjechMWZor_PHp4";
			String json = "{\"touser\": \""+toOPENID+"\","
					+ "\"template_id\": \""+template_id+"\","
					+ "\"url\": \"http://weixin.qq.com/download\","
					+ "\"topcolor\": \"#FF0000\","
					+ "\"data\": {\"first\": {\"value\": \"您好，欢迎使用！\",\"color\": \"#173177\"},"
					+ "\"product\": {\"value\": \"推荐抵用券\",\"color\": \"#173177\"},"
					+ "\"price\": {\"value\": \"2\",\"color\": \"#173177\"},"
					+ "\"time\": {\"value\": \"2015-09-01\",\"color\": \"#173177\"},"
					+ "\"remark\": {\"value\": \"感谢\",\"color\": \"#173177\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
			
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken, "POST", json);
			
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			System.out.println(jsonStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 生成关注二维码(永久)
     * @param appid   公众号ＩＤ
     * @param secret  公众号密码
     * @param scene_str  场景编码
     * @return imgurl  二维码地址
     */
	public static String getEWMYj(String appid, String secret,String scene_str){
		String imgurl = null;
		try{
			String accessToken = getAccessToken(appid, secret);
			String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\""+scene_str+"\"}}}";
			
//			URL url = new URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
//			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//			byte[] buf = json.getBytes();
//
//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			httpConn.setRequestMethod("POST");
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
//
//			byte[] datas = readInputStream(httpConn.getInputStream());
//			String jsonStr = new String(datas,"utf-8");
			String jsonStr = HttpUtils.httpsRequest("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken, "POST", json);
//			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken);  
//	        httpost.setEntity(new StringEntity(json, "UTF-8"));  
//	        HttpResponse response = httpclient.execute(httpost);  
//	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
			
			JSONObject object = JSON.parseObject(jsonStr);
			String ticket = object.getString("ticket");
			if(StringUtils.isNotBlank(ticket)){
				imgurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(ticket,"UTF-8");
			}
			System.out.println(imgurl);
		}catch(Exception e){
			e.printStackTrace();
		}
		return imgurl;
	}
	
}
