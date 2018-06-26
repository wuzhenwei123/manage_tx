package com.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wx.utils.EncryptUtil;


public class Tinput {
	
	
	public static void main(String[] args) throws Exception {

		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("TerminalID", ConfigConstants.TERMINALID);
		param.put("KeyID", ConfigConstants.KEYID);
//		param.put("KeyID", "78032017");
		param.put("UserID", "");
		param.put("Account", "");
		param.put("EMail", "");
		param.put("CardNo", "");
		param.put("TotalFee", "100");
		param.put("ShopCode", "3200");
		param.put("PaymentInfo", "2800251800$G00");
		param.put("IPAddress", "127.0.0.1");
		param.put("TraceNo", "");
		param.put("MCode", getMcode(param));
		// param.put("MCode", "7cb55df7d52caf3e49bc7aec9e95fd4c");
		String result = Tinput.query(param);

		System.out.println(result);
		System.out.println("ResultCode=" + getVal(result, "ResultCode"));
		System.out.println("ResultInfo=" + getVal(result, "ResultInfo"));
		System.out.println("ShopCode=" + getVal(result, "ShopCode"));
		System.out.println("TotalFee=" + getVal(result, "TotalFee"));
		System.out.println("PaymentInfo=" + getVal(result, "PaymentInfo"));

	}

	public static String getMcode(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			sb.append(entry.getValue());
		}
		sb.append(EncryptUtil.md5(ConfigConstants.PRIKEY));
		String md5 = EncryptUtil.md5(sb.toString());
		return md5;
	}

	/**
	 * 包装协议
	 * 
	 * @param param
	 * @return
	 */
	private static String getXml(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"");
		sb.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sb.append("	<soap:Body>");
		sb.append("		<HXTServiceQuery xmlns=\"http://www.chinaepay.com/\">");

		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				sb.append("			<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
			}
		}
		sb.append("		</HXTServiceQuery>");
		sb.append("	</soap:Body>");
		sb.append("</soap:Envelope>");
		return sb.toString();
	}

	/**
	 * 包装协议
	 * 
	 * @param param
	 * @return
	 */
	private static String getPayXml(Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"");
		sb.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sb.append("	<soap:Body>");
		sb.append("		<HXTServicePay xmlns=\"http://www.chinaepay.com/\">");

		if (param != null) {
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				sb.append("			<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
			}
		}
		sb.append("		</HXTServicePay>");
		sb.append("	</soap:Body>");
		sb.append("</soap:Envelope>");
		return sb.toString();
	}

	public static String query(Map<String, Object> param) {
		String result = null;
		try {
			URL url = new URL(ConfigConstants.ENDPOINT);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			byte[] buf = getXml(param).getBytes();

			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("soapActionString", ConfigConstants.QUERYACTIONSTRING);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(25000);
			httpConn.setReadTimeout(25000);
			OutputStream out = httpConn.getOutputStream();
			out.write(buf);
			out.close();

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

	public static String pay(Map<String, Object> param) {
		String result = null;
		try {
			URL url = new URL(ConfigConstants.ENDPOINT);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			byte[] buf = getPayXml(param).getBytes();

			httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("soapActionString", ConfigConstants.PAYACTIONSTRING);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(35000);
			httpConn.setReadTimeout(35000);
			OutputStream out = httpConn.getOutputStream();
			out.write(buf);
			out.close();

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

	public static String getVal(String content, String key) {
		final String regex = "<" + key + ">([\\S\\s]*?)</" + key + ">";
		String result = null;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find()) {
			result = m.group(1);
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
}
