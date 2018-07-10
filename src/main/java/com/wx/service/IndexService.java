package com.wx.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.utils.ConfigConstants;
import com.base.utils.SessionName;
import com.base.utils.Tinput;
import com.base.utils.https.HttpUtils;
import com.tx.task.service.PayLogCutter;
import com.tx.txBank.dao.TxBankDAO;
import com.tx.txBank.model.TxBank;
import com.tx.txDfRate.dao.TxDfRateDAO;
import com.tx.txDfRate.model.TxDfRate;
import com.tx.txPayOrder.dao.TxPayOrderDAO;
import com.tx.txPayOrder.model.TxPayOrder;
import com.tx.txWxOrder.dao.TxWxOrderDAO;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxUser.dao.TxWxUserDAO;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.dao.TxWxUserBankNoDAO;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

@Service
public class IndexService {
	
	private static Logger logger = Logger.getLogger(IndexService.class);
	
	@Autowired
	private PayLogCutter payLogCutter;
	@Autowired
	private WxTemplateMsg wxTemplateMsg;
	@Resource
    private TxPayOrderDAO txPayOrderDAO;
	@Resource
    private TxDfRateDAO txDfRateDAO;
	@Resource
    private TxWxOrderDAO txWxOrderDAO;
	@Resource
    private TxWxUserBankNoDAO txWxUserBankNoDAO;
	@Resource
    private TxWxUserDAO txWxUserDAO;
	@Resource
    private TxBankDAO txBankDAO;
	@Autowired
	private WeiXinService weiXinService;
	
	/**
	 * 装载预付费订单信息
	 * @param mapresult
	 * @param txWxUser
	 */
	public void setOrderMsgToSession(Map<String, Object> mapresult,TxWxUser txWxUser,String shopCode,Integer money2,String IpAddr,String PaymentInfo,String paynumber){
		Map<String,String> mapssss = new HashMap<String,String>();
		mapssss.put("bujia", (String)mapresult.get("bujia"));
		mapssss.put("tradeName", (String)mapresult.get("username1"));
		mapssss.put("koujian", (String)mapresult.get("koujian"));
		mapssss.put("chajia", (String)mapresult.get("chajia"));
		mapssss.put("customerNumber", (String)mapresult.get("customerNumber"));
		mapssss.put("centerInfo", (String)mapresult.get("centerInfo"));
	
		mapssss.put("shopCode", shopCode);
		mapssss.put("money", money2+"");
		mapssss.put("orderId", PaymentInfo);
		mapssss.put("IpAddr", IpAddr);
		mapssss.put("userId", txWxUser.getId()+"");
		mapssss.put("nickName", txWxUser.getNickName());
		mapssss.put("userName", txWxUser.getRealName());
		mapssss.put("paynumber", paynumber);
		if(txWxUser.getPromoterId()!=null){
			mapssss.put("promoterId", txWxUser.getPromoterId()+"");
		}
		if(txWxUser.getTwoPromoterId()!=null){
			mapssss.put("twoPromoterId", txWxUser.getTwoPromoterId()+"");
		}
		SessionName.maporder.put(PaymentInfo, mapssss);
	}
	
	/**
     * 发送验证码
     * @param request
     * @return
     */
    public boolean vercode(String orderId,String txnTime,String token,String txnAmt,TxWxUser txWxUser){
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", "5.1.0");                   //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", "01"); //签名方法
    		contentData.put("txnType", "77");                              //交易类型 11-代收
    		contentData.put("txnSubType", "02");                           //交易子类型 02-消费短信
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.PAY_MERID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		contentData.put("txnAmt", txnAmt);							   //交易金额，单位分，不要带小数点
    		contentData.put("tokenPayData", "{token="+token+"&trId="+ConfigConstants.PAY_TRID+"}");
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("获取短信验证码");
    		logger.info("http://114.113.238.50:18123/trans/backTransTokenURL");
    		logger.info("发送报文");
    		logger.info("--------验证码发送报文------------>"+reqData);
    		
    		Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.UNION_SMSCODE_URL,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("-------------验证码接收报文-----------------"+rspData);
    		payLogCutter.filesMng(2, 1, reqData.toString(), txWxUser.getId(), txWxUser.getMobile(), txWxUser.getNickName(), null);
    		payLogCutter.filesMng(2, 2, rspData.toString(), txWxUser.getId(), txWxUser.getMobile(), txWxUser.getNickName(), null);
    		if(!rspData.isEmpty()&&"00".equals(rspData.get("respCode"))){
    			return true;
    		}else{
    			logger.info("-------------respMsg-----------------"+URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
    			//未返回正确的http状态
    			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
    		}
    		String reqMessage = DemoBase.genHtmlResult(reqData);
    		String rspMessage = DemoBase.genHtmlResult(rspData);
    		logger.info("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    
    
    /**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> pay(String orderId,TxWxUserBankNo txWxUserBankNo,String smsCode,TxWxUser wxUser,String txnTime,String orderType,Long id){
    	Map<String, String> rspData = null;
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();
    		
    		Map<String,String> mapsss = SessionName.maporder.get(orderId);
    		
    		logger.info("-------------订单金额-----------"+mapsss.get("realmoney"));

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", "5.1.0");                  //版本号
    		contentData.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", "01"); //签名方法
    		contentData.put("txnType", "01");                              //交易类型 01-消费
    		contentData.put("txnSubType", "01");                           //交易子类型 01-消费
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
//    		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//    		String txnTime = sf1.format(new Date());
    		
    		/***商户接入参数***/
    		contentData.put("backUrl", ConfigConstants.PAY_BACKURL);
    		contentData.put("merId", ConfigConstants.PAY_MERID);                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("txnAmt", mapsss.get("money"));							   //交易金额，单位分，不要带小数点
    		
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		Map<String,String> customerInfoMap = new HashMap<String,String>();
    		customerInfoMap.put("smsCode", smsCode);			    	//短信验证码
    		//customerInfoMap不送pin的话 该方法可以不送 卡号
    		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding);
    		contentData.put("customerInfo", customerInfoStr);
    		
    		contentData.put("tokenPayData", "{token="+txWxUserBankNo.getToken()+"&trId="+ConfigConstants.PAY_TRID+"}");
    		contentData.put("bankNo", AcpService.encryptData(txWxUserBankNo.getAccNo(), DemoBase.encoding));                              //账号类型
    		
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);				//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("消费&代付交易");
    		logger.info("http:// 114.113.238.50:54041/XW/Trans");
    		logger.info("发送报文");
    		logger.info("--------支付发送报文------------>"+reqData);
    		rspData = AcpService.post(reqData,ConfigConstants.UNION_PAY_URL,DemoBase.encoding);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("--------支付接收报文------------>"+rspData);
    		logger.info("--------订单号------------>"+orderId);
    		logger.info("--------123321------------>"+mapsss);
    		logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
    		payLogCutter.filesMng(3, 1, reqData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getNickName(), txWxUserBankNo.getAccNo());
    		payLogCutter.filesMng(3, 2, rspData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getNickName(), txWxUserBankNo.getAccNo());
    		if(!rspData.isEmpty()){
    			if(("00").equals(rspData.get("respCode"))){
					TxPayOrder hOrder = new TxPayOrder();
					hOrder.setId(id);
					SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    		String time_end = rspData.get("txnTime").substring(0, 4) + "-" + rspData.get("txnTime").substring(4, 6) + "-" + rspData.get("txnTime").substring(6, 8) + " " + rspData.get("txnTime").substring(8, 10)+":"+rspData.get("txnTime").substring(10,12)+":"+rspData.get("txnTime").substring(rspData.get("txnTime").length()-2, rspData.get("txnTime").length());
		    		hOrder.setCreateTime(sf1.parse(time_end));
		    		hOrder.setQueryNumber(rspData.get("queryId"));
		    		txPayOrderDAO.updateTxPayOrderById(hOrder);
				}else{
					TxPayOrder hOrder = new TxPayOrder();
					hOrder.setId(id);
					hOrder.setState(0);
					txPayOrderDAO.updateTxPayOrderById(hOrder);
				}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return rspData;
    }
    
    /**
     * 生成订单
     * @param mapsss
     */
    public Long createOrder(Map<String,String> mapsss,String transaction_id,TxWxUser wxUser,String time_end,String accNo,Integer payWay,String orderType,String SettleDate){
    	Long id = null;
    	try{
    		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    		//生成订单
    		TxPayOrder hOrder = new TxPayOrder();
			hOrder.setOrderNumber(mapsss.get("orderId"));
			hOrder.setAccNo(accNo);
			hOrder.setPayNumber(mapsss.get("paynumber"));
			hOrder.setQueryNumber(transaction_id);
			hOrder.setUserName(mapsss.get("userName"));
			hOrder.setCreateTime(new Date());
			logger.info("--------------->"+mapsss.get("money"));
			hOrder.setFee(Long.valueOf(mapsss.get("money")));
			hOrder.setShopCode(mapsss.get("shopCode"));
			hOrder.setRealFee(Long.valueOf(mapsss.get("money")));
			hOrder.setPayWay(payWay);
			SimpleDateFormat sf11 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sf111 = new SimpleDateFormat("yyyy");
			if(StringUtils.isNotBlank(SettleDate)){
				String source = sf111.format(new Date()) + "-" + SettleDate.substring(0,2)+"-"+SettleDate.substring(2,4);
				hOrder.setSettleDate(sf11.parse(source));
			}
			hOrder.setOrderType(orderType);
			
			BigDecimal oneRate = new BigDecimal(0);//智能电
			BigDecimal twoRate = new BigDecimal(0);//抄表电
			BigDecimal devRate = new BigDecimal(0);//运营
			BigDecimal totalRate = new BigDecimal(0);//地区总代
			List<TxDfRate> listRate = txDfRateDAO.getTxDfRateList(new TxDfRate());
			if(listRate!=null&&listRate.size()>0){
				oneRate = listRate.get(0).getOneRate();
				twoRate = listRate.get(0).getTwoRate();
				devRate = listRate.get(0).getDevRate();
				totalRate = listRate.get(0).getTotalRate();
			}
			hOrder.setPromoterId(wxUser.getPromoterId());
			
			BigDecimal bg = new BigDecimal(Integer.valueOf(mapsss.get("money")));
			
			if(wxUser.getPromoterId()!=null){
				if("3102".equals(mapsss.get("shopCode"))){//抄表电
					hOrder.setOneRate((bg.multiply(twoRate)).setScale(1, BigDecimal.ROUND_HALF_UP).intValue());
				}else{
					hOrder.setOneRate((bg.multiply(oneRate)).setScale(1, BigDecimal.ROUND_HALF_UP).intValue());
				}
			}
			hOrder.setDevRate((bg.multiply(devRate)).setScale(1, BigDecimal.ROUND_HALF_UP).intValue());
			hOrder.setTotalRate((bg.multiply(totalRate)).setScale(1, BigDecimal.ROUND_HALF_UP).intValue());
			hOrder.setPromoterName(wxUser.getPromoterName());
			hOrder.setTwoPromoterId(wxUser.getTwoPromoterId());
			hOrder.setTwoPromoterName(wxUser.getTwoPromoterName());
			
			hOrder.setState(1);
			hOrder.setUserId(Integer.valueOf(mapsss.get("userId")));
			id = txPayOrderDAO.insertTxPayOrder(hOrder);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return id;
    }
    
    /**
     * 生成订单
     * @param mapsss
     */
    public Long createOrderOther(Map<String,String> mapsss,String transaction_id,TxWxUser wxUser,String time_end,String accNo,Integer payWay,String orderType,String SettleDate){
    	Long id = null;
    	try{
    		//生成订单
    		TxPayOrder hOrder = new TxPayOrder();
    		hOrder.setOrderNumber(mapsss.get("orderId"));
    		hOrder.setAccNo(accNo);
    		hOrder.setPayNumber(mapsss.get("paynumber"));
    		hOrder.setQueryNumber(transaction_id);
    		hOrder.setUserName(mapsss.get("userName"));
    		hOrder.setCreateTime(new Date());
    		logger.info("--------------->"+mapsss.get("money"));
    		hOrder.setFee(Long.valueOf(mapsss.get("money")));
    		hOrder.setShopCode(mapsss.get("shopCode"));
    		hOrder.setRealFee(Long.valueOf(mapsss.get("money")));
    		hOrder.setPayWay(payWay);
    		SimpleDateFormat sf11 = new SimpleDateFormat("yyyy-MM-dd");
    		SimpleDateFormat sf111 = new SimpleDateFormat("yyyy");
    		if(StringUtils.isNotBlank(SettleDate)){
    			String source = sf111.format(new Date()) + "-" + SettleDate.substring(0,2)+"-"+SettleDate.substring(2,4);
    			hOrder.setSettleDate(sf11.parse(source));
    		}
    		hOrder.setOrderType(orderType);
    		
    		hOrder.setPromoterId(wxUser.getPromoterId());
    		
    		hOrder.setPromoterName(wxUser.getPromoterName());
    		hOrder.setTwoPromoterId(wxUser.getTwoPromoterId());
    		hOrder.setTwoPromoterName(wxUser.getTwoPromoterName());
    		
    		hOrder.setState(1);
    		hOrder.setUserId(Integer.valueOf(mapsss.get("userId")));
    		id = txPayOrderDAO.insertTxPayOrder(hOrder);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return id;
    }
    
    /**
     * 获取银联token
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    public String getUnionPayToken(String orderId,String txnTime,String accNo,TxWxUser wxUser){
    	String reqMessage = null;
    	try{
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", "5.1.0");                  //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", "01"); //签名方法
    		contentData.put("txnType", "79");                              //交易类型 11-代收
    		contentData.put("txnSubType", "00");                           //交易子类型 00-默认开通
    		contentData.put("bizType", "000902");                          //业务类型 Token支付
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.PAY_MERID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("tokenPayData", "{trId="+ConfigConstants.PAY_TRID+"&tokenType=01}");

    		contentData.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
    		
    		contentData.put("frontUrl", ConfigConstants.UNIONPAYTOKEN_FRONTURL);
    		
    		contentData.put("backUrl", ConfigConstants.UNIONPAYTOKEN_BACKURL);
    	
    		accNo = AcpService.encryptData(accNo, "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
    		contentData.put("accNo", accNo);
    		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		reqMessage = AcpService.createAutoFormHtml(ConfigConstants.FRONTTRANSURL,reqData,DemoBase.encoding);     //生成自动跳转的Html表单
    		logger.info("开通（同步、异步）(后台快捷无此操作)银联侧开通交易-前台请求地址（页面跳转，异步处理）接口：");
    		logger.info("http://114.113.238.50:18123/trans/frontTransTokenURL");
    		logger.info("发送报文");
    		logger.info(reqData);
    		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+reqMessage);
    		payLogCutter.filesMng(1, 1, reqData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getNickName(), accNo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    /**
     * 获取银联token
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    public String getUnionPayTokenDF(String orderId,String txnTime,String accNo,TxWxUser wxUser){
    	String reqMessage = null;
    	try{
    		Map<String, String> contentData = new HashMap<String, String>();
    		
    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", "5.1.0");                  //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", "01"); //签名方法
    		contentData.put("txnType", "79");                              //交易类型 11-代收
    		contentData.put("txnSubType", "00");                           //交易子类型 00-默认开通
    		contentData.put("bizType", "000902");                          //业务类型 Token支付
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.MER_ID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("tokenPayData", "{trId="+ConfigConstants.TRID+"&tokenType=01}");
    		
    		contentData.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
    		
    		contentData.put("frontUrl", ConfigConstants.UNIONPAYTOKEN_FRONTURL_DF);
    		
    		contentData.put("backUrl", ConfigConstants.UNIONPAYTOKEN_BACKURL_DF);
    		
    		accNo = AcpService.encryptData(accNo, "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
    		contentData.put("accNo", accNo);
    		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		reqMessage = AcpService.createAutoFormHtml(ConfigConstants.FRONTTRANSURL,reqData,DemoBase.encoding);     //生成自动跳转的Html表单
    		logger.info("开通（同步、异步）(后台快捷无此操作)银联侧开通交易-前台请求地址（页面跳转，异步处理）接口：");
    		logger.info("http://114.113.238.50:18123/trans/frontTransTokenURL");
    		logger.info("发送报文");
    		logger.info(reqData);
    		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+reqMessage);
    		payLogCutter.filesMng(1, 1, reqData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getNickName(), accNo);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    
    /**
     * 开通回调
     * @param request
     * @return
     */
    public String frontTransUrl(HttpServletRequest req){
    	String orderId = null;
    	try{
    		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		LogUtil.printRequestLog(reqParam);
    		
    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				
    				valideData.put(key, value);
    			}
    		}
    		logger.info("--------------开通前台回调-------------->"+valideData);
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("开通token验证失败-------------》");
    			//验签失败，需解决验签问题
    		} else {
    			
    			
    			orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
        		
        		String accNo = valideData.get("accNo");
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				logger.info("accNo明文: "+ accNo);
    			}
    			
    			String customerInfo = valideData.get("customerInfo");
    			String phone = null;
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				phone = customerInfoMap.get("phoneNo");
    				logger.info("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
        		//写入库
    			TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
    			payLogCutter.filesMng(1, 2, valideData.toString(), txWxOrder.getWxUserId(), txWxOrder.getMobile(), txWxOrder.getWxUserName(), accNo);
    			String respCode = valideData.get("respCode");
    			if("00".equals(respCode)){
    				String tokenPayData = valideData.get("tokenPayData");
        			if(null!=tokenPayData){
        				Map<String,String> tokenPayDataMap = SDKUtil.parseQString(tokenPayData.substring(1, tokenPayData.length() - 1));
        				String token = tokenPayDataMap.get("token");//这样取
            			
            			if(txWxOrder!=null&&txWxOrder.getId()>0){
            				TxWxUserBankNo yxWxUserBankNo = txWxUserBankNoDAO.getTxWxUserBankNoByAccNo(txWxOrder.getAccNo());
            				if(yxWxUserBankNo!=null&&yxWxUserBankNo.getId()>0){
            					yxWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
            					yxWxUserBankNo.setToken(token);
            					yxWxUserBankNo.setEndCode(accNo);
            					yxWxUserBankNo.setPhone(phone);
            					//获取开户行行号
                		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
                				//获取银行名称
                		     	TxBank txBank = new TxBank();
                		     	txBank.setBankNumber(bankNo);
                		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
                		     	if(listBakn!=null&&listBakn.size()>0){
                		     		yxWxUserBankNo.setAccName(listBakn.get(0).getName());
                		     	}
            					txWxUserBankNoDAO.updateTxWxUserBankNoById(yxWxUserBankNo);
            				}else{
            					yxWxUserBankNo = new TxWxUserBankNo();
            					yxWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
            					yxWxUserBankNo.setToken(token);
            					yxWxUserBankNo.setEndCode(accNo);
            					yxWxUserBankNo.setPhone(phone);
            					yxWxUserBankNo.setAccNo(txWxOrder.getAccNo());
            					//获取开户行行号
                		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
                				//获取银行名称
                		     	TxBank txBank = new TxBank();
                		     	txBank.setBankNumber(bankNo);
                		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
                		     	if(listBakn!=null&&listBakn.size()>0){
                		     		yxWxUserBankNo.setAccName(listBakn.get(0).getName());
                		     	}
            					txWxUserBankNoDAO.insertTxWxUserBankNo(yxWxUserBankNo);
            				}
            			}
        			}
    			}
    			txWxOrder.setTxnTime(getOrderNo().get("orderNoTime"));
    			txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    		}
    		logger.info("异步前台接收报文");
    		logger.info("--------------开通前台回调-------------->"+valideData);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return orderId;
    }
    
    
    /**
     * 开通回调（后台）
     * @param request
     * @return
     */
    public String backTransUrl(HttpServletRequest req){
    	String reqMessage = null;
    	try{
    		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);

    		LogUtil.printRequestLog(reqParam);

    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				if(!"XF_DF_Flg".equals(key)){
    					valideData.put(key, value);
    				}
    			}
    		}
    		logger.info("异步后台接收报文");
    		logger.info("--------------开通后台回调-------------->"+valideData);
    		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("开通token验证失败-------------》");
    			//验签失败，需解决验签问题
    			
    		} else {
    			//交易成功，更新商户订单状态
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			
    			String accNo = valideData.get("accNo");
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				logger.info("accNo明文: "+ accNo);
    			}
    			String customerInfo = valideData.get("customerInfo");
    			String phone = null;
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				phone = customerInfoMap.get("phoneNo");
    				logger.info("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
    			//写入库
    			TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
    		
				payLogCutter.filesMng(1, 2, valideData.toString(), txWxOrder.getWxUserId(), txWxOrder.getMobile(), txWxOrder.getWxUserName(), accNo);
				
    			logger.info("验证签名结果[成功].");
    			String respCode = valideData.get("respCode");
    			logger.info(respCode);
    			if("00".equals(respCode)){
    				String tokenPayData = valideData.get("tokenPayData");
        			if(null!=tokenPayData){
        				Map<String,String> tokenPayDataMap = SDKUtil.parseQString(tokenPayData.substring(1, tokenPayData.length() - 1));
        				String token = tokenPayDataMap.get("token");//这样取
            			//获取银行名称
            			if(txWxOrder!=null&&txWxOrder.getId()>0){
            				TxWxUserBankNo yxWxUserBankNo = txWxUserBankNoDAO.getTxWxUserBankNoByAccNo(txWxOrder.getAccNo());
            				if(yxWxUserBankNo!=null&&yxWxUserBankNo.getId()>0){
            					yxWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
            					yxWxUserBankNo.setToken(token);
            					yxWxUserBankNo.setEndCode(accNo);
            					yxWxUserBankNo.setPhone(phone);
            					//获取开户行行号
                		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
                				//获取银行名称
                		     	TxBank txBank = new TxBank();
                		     	txBank.setBankNumber(bankNo);
                		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
                		     	if(listBakn!=null&&listBakn.size()>0){
                		     		yxWxUserBankNo.setAccName(listBakn.get(0).getName());
                		     	}
            					txWxUserBankNoDAO.updateTxWxUserBankNoById(yxWxUserBankNo);
            				}else{
            					yxWxUserBankNo = new TxWxUserBankNo();
            					yxWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
            					yxWxUserBankNo.setToken(token);
            					yxWxUserBankNo.setEndCode(accNo);
            					yxWxUserBankNo.setPhone(phone);
            					yxWxUserBankNo.setAccNo(txWxOrder.getAccNo());
            					//获取开户行行号
                		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
                				//获取银行名称
                		     	TxBank txBank = new TxBank();
                		     	txBank.setBankNumber(bankNo);
                		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
                		     	if(listBakn!=null&&listBakn.size()>0){
                		     		yxWxUserBankNo.setAccName(listBakn.get(0).getName());
                		     	}
            					txWxUserBankNoDAO.insertTxWxUserBankNo(yxWxUserBankNo);
            				}
            		     	
            			}
        			}
    			}
    			txWxOrder.setTxnTime(getOrderNo().get("orderNoTime"));
    			txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    /**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//logger.info("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	public Map<String,String> getOrderNo(){
    	Map<String,String> map = new HashMap<String,String>();
    	SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
    	String sceneId = "";
    	for(int i=0;i<6;i++){
			sceneId += (new Random().nextInt(9)+1);
		}
    	String d = sf1.format(new Date());
		String orderNoT = "TK"+d+ sceneId;
		map.put("orderNoTime", d);
		map.put("orderNo", orderNoT);
		return map;
    }
	
	/**
     * 支付回调
     * @param request
     * @return
     */
    public String paybackUrl(HttpServletRequest req){
    	String reqMessage = null;
    	try{
    		LogUtil.writeLog("支付接收后台通知开始");
    		logger.info("支付接收后台通知开始");
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		LogUtil.printRequestLog(reqParam);
    		
    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				
    				valideData.put(key, value);
    			}
    		}
    		logger.info("异步接收报文(消费交易)");
    		logger.info("--------------支付后台回调-------------->"+valideData);
    		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("支付验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			logger.info("验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String respCode = valideData.get("respCode");
    			String settleDate = valideData.get("settleDate");
    			String queryId = valideData.get("queryId");
    			logger.info(respCode);
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			if("00".equals(respCode)){
    				payLogCutter.filesMng(3, 2, valideData.toString(), null, null, null, valideData.get("accNo"));
    				Map<String,String> mapsss = SessionName.maporder.get(orderId);
    				
    				if(SessionName.xzOrder.get(queryId)==null){
    					SessionName.xzOrder.put(queryId, queryId);
    					//启动多线程查单
        				ThreadOrderExtends thread1 = new ThreadOrderExtends(mapsss.get("customerNumber"), mapsss.get("shopCode"), mapsss.get("money"), mapsss.get("orderId"), mapsss.get("centerInfo"), mapsss.get("dyqId"), mapsss.get("hAddressId"), Integer.valueOf(mapsss.get("userId")), mapsss.get("openId"), valideData.get("queryId"), mapsss.get("IpAddr"), mapsss.get("money"), settleDate,3);
    					thread1.start();
    				}
    			}else{
    				TxPayOrder hOrder = txPayOrderDAO.getTxPayOrderByOrderNumber(orderId);
    				if(hOrder!=null&&hOrder.getId()>0){
    					hOrder.setState(0);
    					txPayOrderDAO.updateTxPayOrderById(hOrder);
    				}
    			}
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    /**
	 * 调用线程处理缴费
	 * @author ll
	 *
	 */
	class ThreadOrderExtends extends Thread {
        
        private String customerNumber;
        private String shopCode;
        private String money;
        private String realmoney;
        private String orderId;//订单号
        private String centerInfo;
        private String dyqId;
        private String hAddressId;
        private String openId;
        private String transaction_id;
        private String ipAdd;
        private Integer userId;
        private String time_end;
        private Integer payWay;
        public ThreadOrderExtends(String customerNumber,String shopCode, String money, String orderId,String centerInfo,String dyqId,String hAddressId,Integer userId,String openId,String transaction_id,String ipAdd,String realmoney,String time_end,Integer payWay){ 
        	this.customerNumber = customerNumber; 
        	this.shopCode = shopCode;
        	this.realmoney = realmoney;
        	this.money = money;
        	this.orderId = orderId;
        	this.centerInfo = centerInfo;
        	this.dyqId = dyqId;
        	this.hAddressId = hAddressId;
        	this.userId = userId;
        	this.openId = openId;
        	this.transaction_id = transaction_id;
        	this.ipAdd = ipAdd;
        	this.time_end = time_end;
        	this.payWay = payWay;
        }
        public void run(){
        	synchronized (this) {
        		try{
        			//
        			logger.info("==进入线程====");
        			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        			//查订单状态
        			TxPayOrder hOrder = txPayOrderDAO.getTxPayOrderByOrderNumber(orderId);
    				if(hOrder!=null&&hOrder.getState()==1){
    					SimpleDateFormat sf11 = new SimpleDateFormat("yyyy-MM-dd");
    					SimpleDateFormat sf111 = new SimpleDateFormat("yyyy");
    					if(StringUtils.isNotBlank(time_end)){
    						String source = sf111.format(new Date()) + "-" + time_end.substring(0,2)+"-"+time_end.substring(2,4);
    						hOrder.setSettleDate(sf11.parse(source));
    					}
    					hOrder.setQueryNumber(transaction_id);
    					txPayOrderDAO.updateTxPayOrderById(hOrder);
    					//去充值
    					logger.info("==销账开始====");
            			Map<String, Object> mappay = payDF(money, shopCode, customerNumber, orderId, transaction_id, ipAdd, centerInfo, time_end);
            			logger.info(sf.format(new Date())+"==销账开始===="+customerNumber+"--------------------->"+mappay);
            			if(mappay!=null){
            				String paystatus = mappay.get("message").toString();
            				logger.info("----------paystatus--------"+paystatus);
            				if(paystatus.equals("ok")){
            					
            				}else{//充值失败
            					//生成退费单
            					//修改用户的交易次数
            					TxWxUser txWxUser = txWxUserDAO.getTxWxUserById(hOrder.getUserId());
            					if(payWay==3){
            						refundPay(hOrder.getQueryNumber(), hOrder.getRealFee(), hOrder, txWxUser);
            					}else if(payWay==4){
            						refundPay_TZ(orderId, hOrder.getRealFee(), hOrder);
            					}
            				}
            			}
    				}
                }catch (Exception e){
                        e.printStackTrace();
                }
			}
        }
	}
	
	/**
	 * 缴费
	 * @param shopCode  // 4200/4100
	 * @param userCode  //用户号
	 * @param billCode  //中心流水号
	 * @param traceNo   //终端流水号
	 * @param totalFee  //交费金额
	 * @return
	 */
	public Map<String, Object> payDF(String totalFee,String shopCode,String userCode,String traceNo,String transaction_id,String IPAddress,String centerInfo,String settlementDate) {
		
		if (shopCode.equals("3102")) {//抄表电直充业务
			//1.用户号
			//2.中心流水号
			//3.售电流水号（留空）
			shopCode = "4100";
			userCode += "$" + traceNo + "$$";
		} else if (shopCode.equals("3202")) {//智能电直充业务
			//[1] 用户号(20字节）
			//[2] 中心流水号（20个字节）
			//[3] 售电流水号（14个字节）
			//[4] 电卡类别码（“G00”）
			//[5] 电卡序列号（留空）
			//[6] 随机数（留空）
			shopCode = "4200";
			userCode += "$"+traceNo;
			userCode += "$"+centerInfo;
			userCode += "$G00$$$";
		} 
		
		System.out.println("--------userCode----------"+userCode);
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("TerminalID", ConfigConstants.TERMINALID);
		param.put("KeyID", ConfigConstants.KEYID);
		param.put("UserID", "");//用户ID号
		param.put("Account", "");//帐号
		param.put("EMail", "");//电子邮件地址
		param.put("CardNo", "");//付款卡号
		param.put("SettlementDate", settlementDate);//银行清算日 MMdd
		param.put("HostSerialNo", transaction_id);//银行扣费号
		
		param.put("TotalFee", totalFee);//预购金额
		param.put("ShopCode", shopCode);//业务代码
		param.put("PaymentInfo", userCode);//缴费详情
		param.put("IPAddress", IPAddress);//用户IP地址
		param.put("TraceNo", "");//终端流水号
		param.put("MCode", Tinput.getMcode(param));//校验码
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String result = Tinput.pay(param);
			logger.info(sf1.format(new Date())+"----------------result-------------"+result);
			if(StringUtils.isNotBlank(result)){
				if("CS".equals(result)){
					String rshopCode =Tinput.getVal(result, "ShopCode");
					map.put("resultInfo", "交易成功");
					map.put("shopCode", rshopCode);
					map.put("message", "ok");
				}else{
					String resultCode =Tinput.getVal(result, "ResultCode");// 00 成功
					String resultInfo =Tinput.getVal(result, "ResultInfo");
					if("G0".equals(resultCode)||"30".equals(resultCode)){
						map.put("message", "fail");
					}else{
						String rshopCode =Tinput.getVal(result, "ShopCode");
						map.put("resultInfo", resultInfo);
						map.put("shopCode", rshopCode);
						map.put("message", "ok");
					}
				}
			}else{
				map.put("resultInfo", "交易成功");
				map.put("shopCode", shopCode);
				map.put("message", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 退费
	 * @param orderId
	 * @param txnTime
	 */
	public boolean refundPay(String origQryId,Long txnAmt,TxPayOrder hOrder,TxWxUser txWxUser){
		try{
			Map<String, String> data = new HashMap<String, String>();
			
			/***商户接入参数***/
			data.put("signMethod", "01"); //签名方法
			data.put("version", "5.1.0");                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("txnType", "31");
			data.put("txnSubType", "00");
			data.put("bizType", "000902");
			data.put("accessType", "0");
			data.put("channelType", "07");
			data.put("merId", ConfigConstants.PAY_MERID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			String txnTime = this.getOrderNo().get("orderNoTime");
			data.put("orderId", hOrder.getOrderNumber());
			data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			data.put("backUrl", ConfigConstants.UNION_REFUNDPAY_BACKURL);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			data.put("origQryId", origQryId);                 //****原订单号
			data.put("txnAmt", txnAmt+"");                 //****退费金额
			
			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("更新代付信息");
			logger.info("http:// 114.113.238.50:54041/XW/Update");
			logger.info("发送报文");
			logger.info("--------交易更新发送报文------------>"+reqData);
			Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.UNION_REFUNDPAY_URL,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易更新接收报文------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			
			payLogCutter.filesMng(6, 1, reqData.toString(), txWxUser.getId(), txWxUser.getMobile(), txWxUser.getRealName(), null);
    		payLogCutter.filesMng(6, 2, rspData.toString(), txWxUser.getId(), txWxUser.getMobile(), txWxUser.getRealName(), null);
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if("00".equals(rspData.get("respCode"))){
					hOrder.setState(2);
					hOrder.setRefundNumber(rspData.get("queryId"));
					txPayOrderDAO.updateTxPayOrderById(hOrder);
					TxWxUser user = txWxUserDAO.getTxWxUserById(hOrder.getUserId());
					wxTemplateMsg.sendTFTempltMsg(user.getOpenId(), hOrder.getPayNumber(),hOrder.getRealFee());
					return true;
				}
			}else{
				//未返回正确的http状态
				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
     * 银联退费(有token)回调
     * @param request
     * @return
     */
    public String union_refundpay_backurl(HttpServletRequest req){
    	String orderId = null;
    	try{
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		LogUtil.printRequestLog(reqParam);
    		
    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				
    				valideData.put(key, value);
    			}
    		}
    		logger.info("--------------银联退费(有token)回调-------------->"+valideData);
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("银联退费(有token)回调失败-------------》"+valideData.get("origQryId"));
    			//验签失败，需解决验签问题
    		} else {
    			String respCode = valideData.get("respCode");
    			if("00".equals(respCode)){
    				logger.info("银联退费(有token)回调成功----------------------------------------");
    				String origQryId = valideData.get("merOrderId");
    				TxPayOrder hOrder = txPayOrderDAO.getTxPayOrderByOrderNumber(origQryId);
    				hOrder.setState(2);
    				txPayOrderDAO.updateTxPayOrderById(hOrder);
    			}
    		}
    		logger.info("银联退费(有token)回调");
    		logger.info("--------------银联退费(有token)回调-------------->"+valideData);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return orderId;
    }
    
    
    /**
	 * 查询
	 * @param orderId
	 * @param txnTime
	 */
	public Map<String, String> queryPay(String orderNo,String txnTime){
		Map<String, String> rspData = null;
		try{
			Map<String, String> data = new HashMap<String, String>();
			data.put("signMethod", "01"); //签名方法
			data.put("version", "5.1.0");                   //版本号
			data.put("encoding", DemoBase.encoding);              //版本号
			data.put("txnType", "00");                              //交易类型 11-代收
			data.put("txnSubType", "00");                           //交易子类型 02-消费短信
			data.put("bizType", "000902");                          //业务类型 认证支付2.0
			data.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
			data.put("channelType", "07");                          //渠道类型07-PC
			
			
			data.put("merId",  ConfigConstants.PAY_MERID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			data.put("orderId", orderNo);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,"UTF-8");           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("交易状态查询");
			logger.info("http:// 114.113.238.50:54041/XW/Query");
			logger.info("发送报文");
			logger.info("--------交易查询报文------------>"+reqData);
			rspData = AcpService.post(reqData, ConfigConstants.UNION_QUERY_URL,"UTF-8");     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易接收结果------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			payLogCutter.filesMng(4, 1, reqData.toString(), null, null, null, orderNo);
			payLogCutter.filesMng(4, 2, rspData.toString(),null, null, null, orderNo);
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
//			if(!rspData.isEmpty()){
//			}else{
//				logger.error("未获取到返回报文或返回http状态码非200");
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rspData;
	}
	
	
	/**
     * 跳转银联
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    public String toUnionpay(String merOrderId, String merTxnAmt, String merTxnTime,TxWxUser txWxUser){
    	String reqMessage = null;
    	try{
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", "1.0.0");                  //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", "01"); //签名方法
    		contentData.put("bizType", "000201");                           //交易子类型 00-默认开通
    		contentData.put("accessType", "0");                           //交易子类型 00-默认开通
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.UNION_MERID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("merOrderId", merOrderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("merTxnTime", merTxnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("merTxnAmt", merTxnAmt);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("currencyCode", "156");         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("tokenPayData", "{trId="+ConfigConstants.UNION_TRID+"&tokenType=01}");

    		contentData.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
    		
    		contentData.put("frontUrl", ConfigConstants.UNION_FRONTURL);
    		
    		contentData.put("backUrl", ConfigConstants.UNION_BACKURL);
    	
    		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		reqMessage = AcpService.createAutoFormHtml(ConfigConstants.UNION_FRONTREQ,reqData,DemoBase.encoding);     //生成自动跳转的Html表单
    		logger.info("跳转银联支付接口：");
    		logger.info("http://114.113.238.50:18123/trans/frontReq");
    		logger.info("发送报文");
    		logger.info(reqData);
    		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+reqMessage);
    		payLogCutter.filesMng(11, 1, reqData.toString(), txWxUser.getId(), txWxUser.getMobile(), txWxUser.getRealName(), null);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    
    /**
     * 银联支付(无token)回调
     * @param request
     * @return
     */
    public String union_backurl(HttpServletRequest req){
    	String orderId = null;
    	try{
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		LogUtil.printRequestLog(reqParam);
    		
    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				
    				valideData.put(key, value);
    			}
    		}
    		logger.info("--------------银联支付(无token)后台回调-------------->"+valideData);
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("银联支付(无token)回调验证失败-------------》");
    			//验签失败，需解决验签问题
    		} else {
    			
    			orderId =valideData.get("merOrderId"); //获取后台通知的数据，其他字段也可用类似方式获取
        		
        		String accNo = valideData.get("accNo");
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				logger.info("accNo明文: "+ accNo);
    			}
    			
    			String merTxnTime = valideData.get("merTxnTime");
    			String queryId = valideData.get("queryId");
    			String txnType = valideData.get("txnType");
    			String settleDate = valideData.get("settleDate");
    			Map<String,String> mapsss = SessionName.maporder.get(orderId);
    			
    			String respCode = valideData.get("respCode");
    			if("00".equals(respCode)){
    				if("01".equals(txnType)){
    					String orderIdStr111 = SessionName.maporderNo.get(orderId);
        				System.out.println("-------------orderIdStr111--------------"+orderIdStr111);
    					if(!StringUtils.isNotBlank(orderIdStr111)){
    						SessionName.maporderNo.put(orderId, orderId);
    						logger.info("---------------创建订单---------->");
    						TxWxUser txWxUser = txWxUserDAO.getTxWxUserById(Integer.valueOf(mapsss.get("userId")));
    						Long id = createOrder(mapsss, queryId, txWxUser, merTxnTime, accNo, 4, "002",settleDate);
    						logger.info("---------------创建订单---------->"+id);
    						if(id!=null&&id>0){
    							logger.info("------------------------->"+mapsss);
    	    					//启动多线程查单
    							ThreadOrderExtends thread1 = new ThreadOrderExtends(mapsss.get("customerNumber"), mapsss.get("shopCode"), mapsss.get("money"), mapsss.get("orderId"), mapsss.get("centerInfo"), mapsss.get("dyqId"), mapsss.get("hAddressId"), Integer.valueOf(mapsss.get("userId")), mapsss.get("openId"), valideData.get("queryId"), mapsss.get("IpAddr"), mapsss.get("money"), settleDate,4);
    	    					thread1.start();
    						}
    						payLogCutter.filesMng(11, 2, valideData.toString(), null, null, null, valideData.get("accNo"));
    					}
    				}else if("04".equals(txnType)){
    					logger.info("退费回调-------------》"+valideData);
        				String origQryId = valideData.get("origQryId");
        				TxPayOrder hOrder = txPayOrderDAO.getTxPayOrderByOrderNumber(origQryId);
        				hOrder.setState(2);
        				txPayOrderDAO.updateTxPayOrderById(hOrder);
    				}
    			}else if(!"00".equals(respCode)){
    				if("01".equals(txnType)){
        				TxPayOrder hOrder = txPayOrderDAO.getTxPayOrderByOrderNumber(orderId);
        				if(hOrder!=null&&hOrder.getId()>0){
        					hOrder.setState(0);
            				txPayOrderDAO.updateTxPayOrderById(hOrder);
        				}
    				}
    			}
    		}
    		logger.info("异步银联支付(无token)回调后台接收报文");
    		logger.info("--------------银联支付(无token)回调后台回调-------------->"+valideData);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return orderId;
    }
    
    
    /**
     * 银联支付(无token)前台回调
     * @param request
     * @return
     */
    public String union_fronturl(HttpServletRequest req){
    	String orderId = null;
    	try{
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		LogUtil.printRequestLog(reqParam);
    		
    		Map<String, String> valideData = null;
    		if (null != reqParam && !reqParam.isEmpty()) {
    			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
    			valideData = new HashMap<String, String>(reqParam.size());
    			while (it.hasNext()) {
    				Entry<String, String> e = it.next();
    				String key = (String) e.getKey();
    				String value = (String) e.getValue();
    				
    				valideData.put(key, value);
    			}
    		}
    		logger.info("--------------银联支付(无token)前台回调-------------->"+valideData);
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("银联支付(无token)回调验证失败-------------》");
    			//验签失败，需解决验签问题
    		} else {
    			orderId = valideData.get("merOrderId");
        		
        		String accNo = valideData.get("accNo");
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				logger.info("accNo明文: "+ accNo);
    			}
    			
    			String merTxnTime = valideData.get("merTxnTime");
    			String queryId = valideData.get("queryId");
    			String txnType = valideData.get("txnType");
    			String settleDate = valideData.get("settleDate");
    			Map<String,String> mapsss = SessionName.maporder.get(orderId);
    			
    			String respCode = valideData.get("respCode");
    			if("00".equals(respCode)&&"01".equals(txnType)){
    				String orderIdStr111 = SessionName.maporderNo.get(orderId);
    				System.out.println("-------------orderIdStr111--------------"+orderIdStr111);
					if(!StringUtils.isNotBlank(orderIdStr111)){
						SessionName.maporderNo.put(orderId, orderId);
						TxWxUser txWxUser = txWxUserDAO.getTxWxUserById(Integer.valueOf(mapsss.get("userId")));
						Long id = createOrder(mapsss, queryId, txWxUser, settleDate, accNo, 4, "002",settleDate);
						if(id!=null&&id>0){
							logger.info("------------------------->"+mapsss);
	    					//启动多线程查单
							ThreadOrderExtends thread1 = new ThreadOrderExtends(mapsss.get("customerNumber"), mapsss.get("shopCode"), mapsss.get("money"), mapsss.get("orderId"), mapsss.get("centerInfo"), mapsss.get("dyqId"), mapsss.get("hAddressId"), Integer.valueOf(mapsss.get("userId")), mapsss.get("openId"), valideData.get("queryId"), mapsss.get("IpAddr"), mapsss.get("money"), settleDate,4);
	    					thread1.start();
						}
						payLogCutter.filesMng(11, 2, valideData.toString(), null, null, null, valideData.get("accNo"));
					}
    			}
    		}
    		logger.info("异步银联支付(无token)前台接收报文");
    		logger.info("--------------银联支付(无token)前台回调-------------->"+valideData);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return orderId;
    }
    
    /**
	 * 退费
	 * @param orderId
	 * @param txnTime
	 */
	public boolean refundPay_TZ(String origQryId,Long txnAmt,TxPayOrder hOrder){
		try{
			Map<String, String> data = new HashMap<String, String>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			/***商户接入参数***/
			data.put("signMethod", "01"); //签名方法
			data.put("version", "1.0.0");                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("type", "31");
			data.put("bizType", "000201");
			data.put("accessType", "0");
			data.put("merId", ConfigConstants.UNION_MERID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			String orderId = this.getOrderNo().get("orderNo");
			String txnTime = this.getOrderNo().get("orderNoTime");
			data.put("undoMerTxnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			data.put("backUrl", ConfigConstants.UNION_BACKURL);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			data.put("merOrderId", origQryId);                 //****原订单号
			data.put("merTxnAmt", txnAmt+"");                 //****退费金额
			data.put("merTxnTime", sf.format(hOrder.getCreateTime()));                 //****退费金额
			System.out.println("------------------hOrderqxx.getRemark3()-------------->"+sf.format(hOrder.getCreateTime()));
			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("更新代付信息");
			logger.info("http:// 114.113.238.50:54041/XW/Update");
			logger.info("发送报文");
			logger.info("--------交易更新发送报文------------>"+reqData);
			Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.UNION_REFUNDPAY_BACKTRANS,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易更新接收报文------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if("00".equals(rspData.get("respCode"))){
					return true;
				}
			}else{
				//未返回正确的http状态
				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查询
	 * @param orderId
	 * @param txnTime
	 */
	public Map<String, String> queryPay_wap(String orderNo,String txnTime){
		Map<String, String> rspData = null;
		try{
			Map<String, String> data = new HashMap<String, String>();
			
			data.put("signMethod", "01"); //签名方法
			data.put("version", "1.0.0");                   //版本号
			data.put("encoding", DemoBase.encoding);              //版本号
			data.put("type", "00");                              //交易类型 11-代收
			data.put("bizType", "000000");                          //业务类型 认证支付2.0
			data.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
			data.put("merId",  ConfigConstants.UNION_MERID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			data.put("merOrderId", orderNo);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("merTxnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,"UTF-8");           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			System.out.println("交易状态查询");
			System.out.println("http:// 114.113.238.50:54041/XW/Query");
			System.out.println("发送报文");
			System.out.println("--------交易查询报文------------>"+reqData);
			rspData = AcpService.post(reqData, ConfigConstants.UNION_QUERY_BACKTRANS,"UTF-8");     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			System.out.println("--------交易接收结果------------>"+rspData);
			System.out.println(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		}catch(Exception e){
			e.printStackTrace();
		}
		return rspData;
	}
}
