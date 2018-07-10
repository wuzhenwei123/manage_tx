package com.wx.service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.base.utils.HttpUtils;
import com.base.utils.SessionName;
import com.base.utils.Tinput;
import com.tx.task.service.PayLogCutter;
import com.tx.txPayOrder.dao.TxPayOrderDAO;
import com.tx.txPayOrder.model.TxPayOrder;
import com.tx.txWxUser.dao.TxWxUserDAO;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.wx.service.IndexService.ThreadOrderExtends;

@Service
public class OtherService {
	
	private static Logger logger = Logger.getLogger(OtherService.class);
	
	
	@Autowired
	private PayLogCutter payLogCutter;
	@Resource
    private TxPayOrderDAO txPayOrderDAO;
	@Resource
    private TxWxUserDAO txWxUserDAO;
	@Autowired
	private WxTemplateMsg wxTemplateMsg;
	
	/**
	 * 装载预付费订单信息
	 * @param mapresult
	 * @param txWxUser
	 */
	public void setOrderMsgToSession(String serviceType,String bankCardNo,TxWxUser txWxUser,String cityCode,Integer money2,String loopID,String PaymentInfo,String paynumber){
		Map<String,String> mapssss = new HashMap<String,String>();
		mapssss.put("money", money2+"");
		mapssss.put("orderId", PaymentInfo);
		mapssss.put("userId", txWxUser.getId()+"");
		mapssss.put("nickName", txWxUser.getNickName());
		mapssss.put("userName", txWxUser.getRealName());
		mapssss.put("paynumber", paynumber);
		mapssss.put("cityCode",cityCode);
		mapssss.put("loopID",loopID);
		mapssss.put("serviceType",serviceType);
		mapssss.put("bankCardNo",bankCardNo);
		if(txWxUser.getPromoterId()!=null){
			mapssss.put("promoterId", txWxUser.getPromoterId()+"");
		}
		if(txWxUser.getTwoPromoterId()!=null){
			mapssss.put("twoPromoterId", txWxUser.getTwoPromoterId()+"");
		}
		SessionName.maporder.put(PaymentInfo, mapssss);
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
    		
    		contentData.put("frontUrl", ConfigConstants.UNIONPAYTOKEN_FRONTURL_OTHER);
    		
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
    		contentData.put("backUrl", ConfigConstants.PAY_BACKURL_OTHER);
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
     * 支付回调
     * @param request
     * @return
     */
    public String paybackUrlOther(HttpServletRequest req,String traceNo){
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
        				ThreadOrderExtends thread1 = new ThreadOrderExtends(mapsss.get("paynumber"), mapsss.get("cityCode"), mapsss.get("orderId"), mapsss.get("loopID"), mapsss.get("serviceType"), mapsss.get("bankCardNo"), valideData.get("queryId"), traceNo, mapsss.get("money"), settleDate, 3);
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
	
	
	 /**
		 * 调用线程处理缴费
		 * @author ll
		 *
		 */
		class ThreadOrderExtends extends Thread {
	        
	        private String customerNumber;
	        private String cityCode;
	        private String realmoney;
	        private String orderId;//订单号
	        private String loopID;
	        private String serviceType;
	        private String bankCardNo;
	        private String transaction_id;
	        private String traceNo;
	        private String time_end;
	        private Integer payWay;
	        public ThreadOrderExtends(String customerNumber,String cityCode, String orderId,String loopID,String serviceType,String bankCardNo,String transaction_id,String traceNo,String realmoney,String time_end,Integer payWay){ 
	        	this.customerNumber = customerNumber; 
	        	this.cityCode = cityCode;
	        	this.realmoney = realmoney;
	        	this.orderId = orderId;
	        	this.loopID = loopID;
	        	this.serviceType = serviceType;
	        	this.bankCardNo = bankCardNo;
	        	this.transaction_id = transaction_id;
	        	this.traceNo = traceNo;
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
	            			Map<String, Object> mappay = payDF(realmoney, orderId, customerNumber, traceNo, transaction_id, cityCode, bankCardNo, time_end, loopID, serviceType);
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
		 * 缴费
		 * @param shopCode  // 4200/4100
		 * @param userCode  //用户号
		 * @param billCode  //中心流水号
		 * @param traceNo   //终端流水号
		 * @param totalFee  //交费金额
		 * @return
		 */
		public Map<String, Object> payDF(String totalFee,String centerSerial,String paynumber,String traceNo,String hostSerialNo,String cityCode,String bankCardNo,String settlementDate,String loopID,String serviceType) {
			
			Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Pay");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", traceNo);
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		param.put("CenterSerial", centerSerial);
    		param.put("CityCode", cityCode);
    		param.put("ServiceType", serviceType);
    		param.put("PayNo", paynumber);
    		param.put("BankCardNo", bankCardNo);
    		param.put("SettlementDate", settlementDate);
    		param.put("HostSerialNo", hostSerialNo);
    		param.put("LoopID", loopID);
    		param.put("TotalFee", totalFee+"");
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		Map<String, Object> map = new HashMap<String, Object>();
    		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				String result = HttpUtils.sendPost(param);
				logger.info(sf1.format(new Date())+"----------------result-------------"+result);
				if("CS".equals(result)){
					String rshopCode =Tinput.getVal(result, "ShopCode");
					map.put("resultInfo", "交易成功");
					map.put("shopCode", rshopCode);
					map.put("message", "ok");
				}else{
					String resultCode = HttpUtils.getVal(result, "ResultCode");
					String resultInfo = HttpUtils.getVal(result, "ResultInfo");
					System.out.println("--------------------------------------"+URLDecoder.decode(resultInfo,"UTF-8"));
					if("G0".equals(resultCode)||"30".equals(resultCode)){
						map.put("message", "fail");
					}else{
						String rshopCode =Tinput.getVal(result, "ShopCode");
						map.put("resultInfo", resultInfo);
						map.put("shopCode", rshopCode);
						map.put("message", "ok");
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return map;
		}
}
