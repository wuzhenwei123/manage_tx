package com.base.utils.yft;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhy on 2017/2/27.
 */
public class HttpJsonUtils {
   

    /**
     * 为了扩展 数据库保存 URL  公钥 这里使用传值
     *
     * @param json
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String httpClientUtils(String json, String urlStr) {

        URL url = null;
        HttpURLConnection urlCon = null;
        OutputStreamWriter out = null;
        try {
            url = new URL(urlStr);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestMethod("POST");
            urlCon.setConnectTimeout(60000);
            urlCon.setReadTimeout(60000);
            urlCon.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.connect();
            out = new OutputStreamWriter(urlCon.getOutputStream(), "UTF-8"); // utf-8编码
            out.write(json);
            out.flush();
            out.close();
      
            // 读取响应
            BufferedReader rd = null;
            if (urlCon.getResponseCode() == 200){
                //log.warn("http post请求连接成功");
                rd = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            }else{
                //log.warn("http post请求连接失败！");
                rd = new BufferedReader(new InputStreamReader(urlCon.getErrorStream(), "UTF-8"));
            }
            StringBuffer buffer = new StringBuffer();
            String readLine = "";
            while ((readLine = rd.readLine()) != null){
                buffer.append(readLine);
            }
            rd.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            urlCon.disconnect();
        }
        return null;
    }
}
