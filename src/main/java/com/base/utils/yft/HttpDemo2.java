package com.base.utils.yft;


import java.util.Random;

import com.base.utils.yft.bean.MerchantRegister;
import com.base.utils.yft.bean.QrCodeTran;
import com.base.utils.yft.bean.Signature;
import com.base.utils.yft.rsa.AESCrypt;
import com.base.utils.yft.rsa.RSAUtils;
import com.base.utils.yft.util.JsonMapper;
import com.base.utils.yft.util.StringUtil;



/**
 * Created by zhy on 2017/3/6.
 */
public class HttpDemo2 {
//    static {
//        //for localhost testing only
//        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//                new javax.net.ssl.HostnameVerifier(){
//
//                    public boolean verify(String hostname,
//                                          javax.net.ssl.SSLSession sslSession) {
//                        if (hostname.equals("localhost")) {
//                            return true;
//                        }else  if (hostname.equals("114.113.238.50")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//    }
	public static void qrcodetest() throws Exception {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNGB1N58aFiXHaAaLXyupbKdiUjgJl9KyEnN5FWq+kjOiefuFiYLw8zodgmXNmGwJHGJDxxN4Os0QXQkbyMqrg+NTH+Nz54PZ5QK87ZgfiNDnWwuuAOuBsIXL1rrg6Jco57/kYky4M9AIjGv0yLrqqH+f+QdZmBQHe8XtswX1hJwIDAQAB";
//      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZHFpXD/RujH5hWtYdAefM43prEicADmxWOma0619Zk3Ow9Pyggp4Dljx+1RJdo01iZ0eFRWmOB8V1MCejn+7PLYxbOIlaD3TgBHiQ3jzr/Rkz8Tni3pWxOX7CIW56DnBbgrmK9Q1AriOwhTo5RpKzebptDsH+kZrkbmKGfROW+QIDAQAB"
      String time = "20170515102330";
      String aes = StringUtil.getRandomString(16);
      Signature signature = new Signature();
      signature.setAes(aes);
      signature.setTime(time);
      String sign = JsonMapper.toJsonString(signature);
      System.out.println("sign: " + sign);
      String signa = RSAUtils.encrypt(publicKey, sign);
      System.out.println("signature: " + signa);
//      signa = URLEncoder.encode(signa, "UTF-8");
//      System.out.println("signature urlencoder: " + signa);

      QrCodeTran merchantRegister = new QrCodeTran();

   
      String cardNo = "";//结算卡号
  
		String idcardno = "";//结算卡身份证号
		String payername = "吕哲";//结算账户开户名
		
		String merchantNo = "";
		String orderNum = "L_00007";
		String amount = "10";
		String goodsName = "订单描述";
		String orderIp = "1.1.1.1";
		String toibkn = "313100000511";
      merchantRegister.setCardNo(AESCrypt.encrypt(cardNo, aes, AESCrypt.iv));
      merchantRegister.setIdCardNo(AESCrypt.encrypt(idcardno, aes, AESCrypt.iv));
      merchantRegister.setPayerName(AESCrypt.encrypt(payername, aes, AESCrypt.iv));
      merchantRegister.setMerchantNo(merchantNo);
      merchantRegister.setOrderNum(orderNum);
      merchantRegister.setTimeOut("20180103120000");
      merchantRegister.setAmount(amount);
      merchantRegister.setGoodsName(goodsName);
      merchantRegister.setOrderIp(orderIp);
      merchantRegister.setToibkn(toibkn);
      String json = JsonMapper.toJsonString(merchantRegister);
      System.out.println("参数为： " + json);
//      Map<String, String> map = new HashMap<>();
//      BeanUtils.populate(merchantRegister, map);
//      String url = "https://www.96299.com.cn:8081/hxt/post?agentId=90008&signature=" + signa;
      String url = "http://39.106.6.224:8080/pay/jhf/qrCodeTran?agentId=90008&signature=" + signa;
      System.out.println(url);
      String s = HttpJsonUtils.httpClientUtils(json, url);
//      String s = httpUtils.sendHttpsRequestByPost(url, map);
//      String s = HttpsRequestUtil.httpRequest(url, json);
      System.out.println(s);
	}
	
	
	public static void dxtest() throws Exception {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNGB1N58aFiXHaAaLXyupbKdiUjgJl9KyEnN5FWq+kjOiefuFiYLw8zodgmXNmGwJHGJDxxN4Os0QXQkbyMqrg+NTH+Nz54PZ5QK87ZgfiNDnWwuuAOuBsIXL1rrg6Jco57/kYky4M9AIjGv0yLrqqH+f+QdZmBQHe8XtswX1hJwIDAQAB";
//      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZHFpXD/RujH5hWtYdAefM43prEicADmxWOma0619Zk3Ow9Pyggp4Dljx+1RJdo01iZ0eFRWmOB8V1MCejn+7PLYxbOIlaD3TgBHiQ3jzr/Rkz8Tni3pWxOX7CIW56DnBbgrmK9Q1AriOwhTo5RpKzebptDsH+kZrkbmKGfROW+QIDAQAB"
      String time = "20170515102330";
      String aes = StringUtil.getRandomString(16);
      Signature signature = new Signature();
      signature.setAes(aes);
      signature.setTime(time);
      String sign = JsonMapper.toJsonString(signature);
      System.out.println("sign: " + sign);
      String signa = RSAUtils.encrypt(publicKey, sign);
      System.out.println("signature: " + signa);
//      signa = URLEncoder.encode(signa, "UTF-8");
//      System.out.println("signature urlencoder: " + signa);

      MerchantRegister merchantRegister = new MerchantRegister();

   
      String telephone = "18810619832";
      merchantRegister.setPhoneNo(AESCrypt.encrypt(telephone, aes, AESCrypt.iv));
    
      String json = JsonMapper.toJsonString(merchantRegister);
      System.out.println("参数为： " + json);
//      Map<String, String> map = new HashMap<>();
//      BeanUtils.populate(merchantRegister, map);
//      String url = "https://www.96299.com.cn:8081/hxt/post?agentId=90008&signature=" + signa;
      String url = "http://114.113.238.50:8020/sysmanage/hxt/postdx?agentId=90008&signature=" + signa;
      System.out.println(url);
      String s = HttpJsonUtils.httpClientUtils(json, url);
//      String s = httpUtils.sendHttpsRequestByPost(url, map);
//      String s = HttpsRequestUtil.httpRequest(url, json);
      System.out.println(s);
	}
	
	public static void zctest() throws Exception {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNGB1N58aFiXHaAaLXyupbKdiUjgJl9KyEnN5FWq+kjOiefuFiYLw8zodgmXNmGwJHGJDxxN4Os0QXQkbyMqrg+NTH+Nz54PZ5QK87ZgfiNDnWwuuAOuBsIXL1rrg6Jco57/kYky4M9AIjGv0yLrqqH+f+QdZmBQHe8XtswX1hJwIDAQAB";
//      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZHFpXD/RujH5hWtYdAefM43prEicADmxWOma0619Zk3Ow9Pyggp4Dljx+1RJdo01iZ0eFRWmOB8V1MCejn+7PLYxbOIlaD3TgBHiQ3jzr/Rkz8Tni3pWxOX7CIW56DnBbgrmK9Q1AriOwhTo5RpKzebptDsH+kZrkbmKGfROW+QIDAQAB"
      String time = "20170515102330";
      String aes = StringUtil.getRandomString(16);
      Signature signature = new Signature();
      signature.setAes(aes);
      signature.setTime(time);
      String sign = JsonMapper.toJsonString(signature);
      System.out.println("sign: " + sign);
      String signa = RSAUtils.encrypt(publicKey, sign);
      System.out.println("signature: " + signa);
//      signa = URLEncoder.encode(signa, "UTF-8");
//      System.out.println("signature urlencoder: " + signa);

      MerchantRegister merchantRegister = new MerchantRegister();

   
      String telephone = "18510719886";
      String accNo="6215590200002332922";
      String cId="210724199111020218";
      String bankNo="102100020850";
      merchantRegister.setPhoneNo(AESCrypt.encrypt(telephone, aes, AESCrypt.iv));
      merchantRegister.setMerName("测试商户");
      merchantRegister.setAccNo(AESCrypt.encrypt(accNo, aes, AESCrypt.iv));
      merchantRegister.setCertifTp("01");
      merchantRegister.setCertifId(AESCrypt.encrypt(cId, aes, AESCrypt.iv));
      merchantRegister.setCustomerNm("乌拉拉");
      merchantRegister.setBankNo(AESCrypt.encrypt(bankNo, aes, AESCrypt.iv));
     // merchantRegister.setSmsCode("1234");
     // merchantRegister.setSmsId("4873000001712252031522132758728");
      String json = JsonMapper.toJsonString(merchantRegister);
      System.out.println("参数为： " + json);
//      Map<String, String> map = new HashMap<>();
//      BeanUtils.populate(merchantRegister, map);
//      String url = "https://www.96299.com.cn:8081/hxt/post?agentId=90008&signature=" + signa;
    //String url = "http://114.113.238.50:8020/sysmanage/hxt/postxw?agentId=90008&signature=" + signa;
      String url = "http://114.113.238.50:8020/sysmanage/hxt/postxw?agentId=90008&signature=" + signa;
      System.out.println(url);
      String s = HttpJsonUtils.httpClientUtils(json, url);
//      String s = httpUtils.sendHttpsRequestByPost(url, map);
//      String s = HttpsRequestUtil.httpRequest(url, json);
      System.out.println(s);
	}
    public static void main(String[] argus) throws Exception {
//    	dxtest();
    	zctest();
    	//qrcodetest();
    
    		//System.out.println("873"+"90008".substring(2, 5));
    	
        
    }
    public static String getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);

    }
}
