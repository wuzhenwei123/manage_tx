package com.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.wx.utils.EncryptUtil;
import com.wx.utils.https.HttpRequest;



public class HttpUtils {
	
	
	
	public static void main(String[] args) throws Exception {

		// 3102，4100 测试用户号: 022736,111111
		// 3202，4200 测试用户号: 2800251800, 2800252000

		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("MsgType", "Open");
		param.put("TerminalID", "70030002");
		param.put("TraceNo", "000006");
		param.put("Source", "ACT");
		param.put("Channel", "1111");
		param.put("KeyID", "31174909");
		param.put("MCode", getMcode(param));
		
		
//		String jsonStr = HttpRequest.sendPost("http://interface.chinaepay.com:8766/Service.aspx", getParam(param));
		
		String result = HttpUtils.sendPost(param);

		System.out.println(result);
		
		System.out.println("Items=" + URLDecoder.decode(getVal(result, "Items"),"UTF-8"));

	}
	
	public static String getMcode(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			if(!entry.getKey().equals("MsgType")){
				sb.append(entry.getValue());
			}
		}
		sb.append(ConfigConstants.NATIONAL_UNITY_MKEY);
		String md5 = EncryptUtil.md5(sb.toString());
		return md5;
	}
	
	/**
	 * 封装参数
	 * @param param
	 * @return
	 */
	private static String getParam(Map<String, Object> param) throws Exception{
		StringBuffer sb = new StringBuffer();
		 
		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				if(!StringUtils.isNotBlank(sb.toString())){
					sb.append(entry.getKey() + "=" + URLEncoder.encode((String)entry.getValue(), "utf-8"));
				}else{
					sb.append("&"+entry.getKey() + "=" + URLEncoder.encode((String)entry.getValue(), "utf-8"));
				}
			}
		}
		return sb.toString();
	}
	
	public static String sendPost(Map<String, Object> param) {
		String result = null;
		try {
			URL url = new URL(ConfigConstants.NATIONAL_UNITY_URL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			
			
//			byte[] buf = getParam(param).getBytes();

//			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
//			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("accept", "*/*");
			httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(25000);
			httpConn.setReadTimeout(25000);
//			OutputStream out = httpConn.getOutputStream();
//			out.write(buf);
//			out.close();
			
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
            out.print(getParam(param));
            out.flush();

			byte[] datas = readInputStream(httpConn.getInputStream());
			result = new String(datas, "utf-8");
		} catch (TimeoutException e) {
			return "CS";
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
	
	
	public static String getVal(String content, String key) {
		String result = null;
		String[] str = content.split("&");
		if(str!=null){
			for(String strs:str){
				String[] str1 = strs.split("=");
				if(key.equals(str1[0])){
					result = str1[1];
				}
			}
		}
		return result;
	}

}
