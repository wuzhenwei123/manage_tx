package com.tx.txWxUserBankNo.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.base.utils.ConfigConstants;
import com.sys.manageAdminUser.dao.ManageAdminUserDao;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.tx.task.service.QuartzJobDFService;
import com.tx.task.service.QuartzJobService;
import com.tx.task.service.QuartzJobSellService;
import com.tx.task.service.QuartzManager;
import com.tx.txBank.dao.TxBankDAO;
import com.tx.txBank.model.TxBank;
import com.tx.txRate.dao.TxRateDAO;
import com.tx.txRate.model.TxRate;
import com.tx.txRefundFlag.dao.TxRefundFlagDAO;
import com.tx.txRefundFlag.model.TxRefundFlag;
import com.tx.txSellingOrder.dao.TxSellingOrderDAO;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txWxOrder.dao.TxWxOrderDAO;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.dao.TxWxUserBankNoDAO;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.wx.service.WeiXinService;
import com.wx.service.WxTemplateMsg;

/**
 * @author	wzw
 * @time	2018-02-05 10:34:13
 */
@Service
@Transactional
public class TxWxUserBankNoServiceImpl implements TxWxUserBankNoService{
	
	private static Logger logger = Logger.getLogger(TxWxUserBankNoServiceImpl.class);
	
	public static Map<String,String> xzOrder = new HashMap<String,String>();
	
	@Resource
    private TxWxUserBankNoDAO txWxUserBankNoDAO;
	@Resource
    private TxWxOrderDAO txWxOrderDAO;
	@Autowired
	private WeiXinService weiXinService;
	@Resource
    private TxBankDAO txBankDAO;
	@Resource
    private TxRateDAO txRateDAO;
	@Resource
    private ManageAdminUserDao manageAdminUserDao;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private WxTemplateMsg wxTemplateMsg;
	@Resource
    private TxSellingOrderDAO txSellingOrderDAO;
	@Resource
    private TxRefundFlagDAO txRefundFlagDAO;
	
    
    public List<TxWxUserBankNo> getTxWxUserBankNoList(TxWxUserBankNo txWxUserBankNo) {
        return txWxUserBankNoDAO.getTxWxUserBankNoList(txWxUserBankNo);
    }

    public TxWxUserBankNo getTxWxUserBankNoById(int id) { 
        return txWxUserBankNoDAO.getTxWxUserBankNoById(id);
    }
    
    public TxWxUserBankNo getTxWxUserBankNoByAccNo(String accNo){
    	return txWxUserBankNoDAO.getTxWxUserBankNoByAccNo(accNo);
    }

    public int insertTxWxUserBankNo(TxWxUserBankNo txWxUserBankNo){
        return txWxUserBankNoDAO.insertTxWxUserBankNo(txWxUserBankNo);
    }

    public int updateTxWxUserBankNoById(TxWxUserBankNo txWxUserBankNo){
        return txWxUserBankNoDAO.updateTxWxUserBankNoById(txWxUserBankNo);
    }
    
    public int deleteTxWxUserBankNoById(int id){
        return txWxUserBankNoDAO.deleteTxWxUserBankNoById(id);
    }
    
    /**
     * 获取银联token
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    public String getUnionPayToken(TxWxUser wxUser,String orderId,String txnTime,String accNo){
    	String reqMessage = null;
    	try{
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", DemoBase.version);                  //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
    		contentData.put("txnType", "79");                              //交易类型 11-代收
    		contentData.put("txnSubType", "00");                           //交易子类型 00-默认开通
    		contentData.put("bizType", "000902");                          //业务类型 Token支付
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.MER_ID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
//    		contentData.put("accType", "01");                              //账号类型
    		
    		contentData.put("tokenPayData", "{trId="+ConfigConstants.TRID+"&tokenType=01}");
    		
//    		Map<String,String> customerInfoMap = new HashMap<String,String>();
//    		customerInfoMap.put("certifTp", "01");						//证件类型
//    		customerInfoMap.put("certifId", wxUser.getIDNumber());		//证件号码
//    		customerInfoMap.put("customerNm", wxUser.getRealName());					//姓名
//    		customerInfoMap.put("phoneNo", wxUser.getMobile());			    //手机号

    		accNo = AcpService.encryptData(accNo, "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
    		contentData.put("accNo", accNo);
    		contentData.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
//    		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,null,DemoBase.encoding);
//    		contentData.put("customerInfo", customerInfoStr);
    		
    		contentData.put("frontUrl", DemoBase.frontUrl);
    		
    		contentData.put("backUrl", DemoBase.backUrl);
//    		contentData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
    	
    		
    		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		reqMessage = AcpService.createAutoFormHtml(ConfigConstants.FRONTTRANSURL,reqData,DemoBase.encoding);     //生成自动跳转的Html表单
    		logger.info("开通（同步、异步）(后台快捷无此操作)银联侧开通交易-前台请求地址（页面跳转，异步处理）接口：");
    		logger.info("http://114.113.238.50:18123/trans/frontTransTokenURL");
    		logger.info("发送报文");
    		logger.info(reqData);
    		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+reqMessage);
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
    			LogUtil.writeLog("验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			LogUtil.writeLog("验证签名结果[成功].");
    			logger.info("验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String customerInfo = valideData.get("customerInfo");
    			String phone = null;
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				phone = customerInfoMap.get("phoneNo");
    				logger.info("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
    			String accNo = valideData.get("accNo");
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				LogUtil.writeLog("accNo明文: "+ accNo);
    				logger.info("accNo明文: "+ accNo);
    			}
    			
    			String tokenPayData = valideData.get("tokenPayData");
    			if(null!=tokenPayData){
    				Map<String,String> tokenPayDataMap = SDKUtil.parseQString(tokenPayData.substring(1, tokenPayData.length() - 1));
    				String token = tokenPayDataMap.get("token");//这样取
    				LogUtil.writeLog("tokenPayDataMap明文: " + tokenPayDataMap);
    				
    				//写入库
        			TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
        			
        			if(txWxOrder!=null&&txWxOrder.getId()>0){
        				TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
        				txWxUserBankNo.setAccNo(txWxOrder.getAccNo());
        				txWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
        				txWxUserBankNo.setToken(token);
        				txWxUserBankNo.setEndCode(accNo);
        				txWxUserBankNo.setPhone(phone);
        				//获取开户行行号
        		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
        				//获取银行名称
        		     	TxBank txBank = new TxBank();
        		     	txBank.setBankNumber(bankNo);
        		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
        		     	if(listBakn!=null&&listBakn.size()>0){
        		     		txWxUserBankNo.setAccName(listBakn.get(0).getName());
        		     	}
        				txWxUserBankNoDAO.insertTxWxUserBankNo(txWxUserBankNo);
        				txWxOrder.setState(1);
        				txWxOrderDAO.updateTxWxOrderById(txWxOrder);
        			}
    			}
    			
    			String respCode = valideData.get("respCode");
    			logger.info(respCode);
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
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
    				valideData.put(key, value);
    			}
    		}
    		if (!AcpService.validate(valideData, encoding)) {
    			LogUtil.writeLog("验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			LogUtil.writeLog("验证签名结果[成功].");
    			logger.info("验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String customerInfo = valideData.get("customerInfo");
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				LogUtil.writeLog("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
    			String accNo = valideData.get("accNo");
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			if(null!=accNo){
    				accNo = AcpService.decryptData(accNo, "UTF-8");
    				LogUtil.writeLog("accNo明文: "+ accNo);
    				logger.info("accNo明文: "+ accNo);
    			}
    			
    			String tokenPayData = valideData.get("tokenPayData");
    			if(null!=tokenPayData){
    				Map<String,String> tokenPayDataMap = SDKUtil.parseQString(tokenPayData.substring(1, tokenPayData.length() - 1));
    				String token = tokenPayDataMap.get("token");//这样取
    				LogUtil.writeLog("tokenPayDataMap明文: " + tokenPayDataMap);
    				
    				//写入库
        			TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
        			
        			if(txWxOrder!=null&&txWxOrder.getId()>0){
        				TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
        				txWxUserBankNo.setAccNo(txWxOrder.getAccNo());
        				txWxUserBankNo.setWxUserId(txWxOrder.getWxUserId());
        				txWxUserBankNo.setToken(token);
        				txWxUserBankNo.setEndCode(accNo);
        				//获取开户行行号
        		     	String bankNo = weiXinService.getKHBankNo(txWxOrder.getAccNo());
        				//获取银行名称
        		     	TxBank txBank = new TxBank();
        		     	txBank.setBankNumber(bankNo);
        		     	List<TxBank> listBakn = txBankDAO.getTxBankList(txBank);
        		     	if(listBakn!=null&&listBakn.size()>0){
        		     		txWxUserBankNo.setAccName(listBakn.get(0).getName());
        		     	}
        				txWxUserBankNoDAO.insertTxWxUserBankNo(txWxUserBankNo);
        				txWxOrder.setState(1);
        				txWxOrderDAO.updateTxWxOrderById(txWxOrder);
        			}
    			}
    			
    			String respCode = valideData.get("respCode");
    			logger.info(respCode);
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		logger.info("异步前台接收报文");
    		logger.info("--------------开通前台回调-------------->"+valideData);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    /**
     * 支付回调
     * @param request
     * @return
     */
    public String backUrlDF(HttpServletRequest req){
    	String reqMessage = null;
    	try{
    		LogUtil.writeLog("代付接收后台通知开始");
    		logger.info("代付接收后台通知开始");
    		
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
    		logger.info("异步接收报文(代付交易)");
    		logger.info("--------------支付后台回调(代付)-------------->"+valideData);
    		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
    		if (!AcpService.validate(valideData, encoding)) {
    			LogUtil.writeLog("代付验证签名结果[失败].");
    			logger.info("代付支付验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			LogUtil.writeLog("代付验证签名结果[成功].");
    			logger.info("代付验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String customerInfo = valideData.get("customerInfo");
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				LogUtil.writeLog("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
    			String respCode = valideData.get("respCode");
    			logger.info(respCode);
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			if("00".equals(respCode)){
    				
    				if(xzOrder.get(orderId)==null){
    					xzOrder.put(orderId, orderId);
    					//写入库
        				TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
        				
        				if(txWxOrder!=null&&txWxOrder.getId()>0&&txWxOrder.getXfState().intValue()==1){
        					txWxOrder.setState(1);
        					txWxOrder.setCounterFee(Integer.valueOf(valideData.get("counterFee")));
        					txWxOrder.setDfQueryId(valideData.get("queryID"));
        					txWxOrder.setDfFlg(valideData.get("XF_DF_Flg"));
        					txWxOrder.setDfSettleDate(valideData.get("settlementDate"));
        					txWxOrder.setDfState(1);
        					txWxOrder.setOrderAmt(Integer.valueOf(valideData.get("OrderAmt")));
        					txWxOrderDAO.updateTxWxOrderById(txWxOrder);
        					
        					//发送消息
        					this.taskExecutor.execute(new CutFilesThread(txWxOrder));
        					
        				}
    				}
    				
    			}else{
    				TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
    				txWxOrder.setMsgDf(valideData.get("respMsg"));
    				txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    			}
    			
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    /**
     * 支付回调
     * @param request
     * @return
     */
    public String backUrlTrans(HttpServletRequest req){
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
    			LogUtil.writeLog("验证签名结果[失败].");
    			logger.info("支付验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			LogUtil.writeLog("验证签名结果[成功].");
    			logger.info("验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String customerInfo = valideData.get("customerInfo");
    			if(null!=customerInfo){
    				Map<String,String>  customerInfoMap = AcpService.parseCustomerInfo(customerInfo, "UTF-8");
    				LogUtil.writeLog("customerInfoMap明文: "+ customerInfoMap);
    			}
    			
    			String respCode = valideData.get("respCode");
    			logger.info(respCode);
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			if("00".equals(respCode)){
    				
    				//写入库
    				TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
    				
    				if(txWxOrder!=null&&txWxOrder.getId()>0){
    					txWxOrder.setMoney(Long.valueOf(valideData.get("txnAmt")));
//    					txWxOrder.setQueryId(valideData.get("queryId"));
    					txWxOrder.setXfFlg(valideData.get("XF_DF_Flg"));
    					txWxOrder.setTraceNo(valideData.get("traceNo"));
    					txWxOrder.setXfSettleDate(valideData.get("settleDate"));
    					txWxOrder.setXfState(1);
    					
    					List<TxRate> listRate = txRateDAO.getTxRateList(new TxRate());
    					
    					BigDecimal bg1 = new BigDecimal(Long.valueOf(valideData.get("txnAmt")));
    					logger.info("-----------------------getTwoPromoterId-------------"+txWxOrder.getTwoPromoterId());
    					if(txWxOrder.getTwoPromoterId()!=null){
    						long two_rate = (bg1.multiply(listRate.get(0).getTwoPromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    						long one_rate = (bg1.multiply(listRate.get(0).getOnePromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    						long dev_rate = (bg1.multiply(listRate.get(0).getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    						txWxOrder.setTwo_rate(two_rate);
    						txWxOrder.setOne_rate(one_rate);
    						txWxOrder.setDev_rate(dev_rate);
    					}else{
    						long one_rate = (bg1.multiply(listRate.get(0).getOnePromoterRate().add(listRate.get(0).getTwoPromoterRate()))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    						long dev_rate = (bg1.multiply(listRate.get(0).getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    						txWxOrder.setOne_rate(one_rate);
    						txWxOrder.setDev_rate(dev_rate);
    					}
    					
    					
    					txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    				}
    			}else{
    				TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
    				txWxOrder.setMsg(valideData.get("respMsg"));
    				txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    			}
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    
    /**
     * 支付回调售卡
     * @param request
     * @return
     */
    public String backUrlTrans_sell(HttpServletRequest req){
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
    			LogUtil.writeLog("验证签名结果[失败].");
    			logger.info("支付验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			LogUtil.writeLog("验证签名结果[成功].");
    			logger.info("验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String queryId =valideData.get("queryId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String respCode = valideData.get("respCode");
    			logger.info("============售卡支付回调================"+respCode);
    			logger.info("============售卡支付回调================"+URLDecoder.decode(valideData.get("respMsg"),"UTF-8"));
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			TxSellingOrder txSellingOrder = txSellingOrderDAO.getTxSellingOrderByCode(orderId);
    			if("00".equals(respCode)){
    				if(txSellingOrder!=null&&txSellingOrder.getId()>0){
    					txSellingOrder.setState(1);
    					txSellingOrder.setQueryId(queryId);
    					txSellingOrderDAO.updateTxSellingOrderById(txSellingOrder);
    				}
    			}else{
    				logger.info("============售卡支付回调失败=================");
    			}
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return reqMessage;
    }
    /**
     * 支付回调售卡
     * @param request
     * @return
     */
    public String xw_backUrl(HttpServletRequest req){
    	String reqMessage = null;
    	try{
    		LogUtil.writeLog("代付接收后台通知开始");
    		logger.info("代付接收后台通知开始");
    		
    		String encoding = req.getParameter(SDKConstants.param_encoding);
    		// 获取银联通知服务器发送的后台通知参数
    		Map<String, String> reqParam = getAllRequestParam(req);
    		
    		logger.info(reqParam);
    		
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
    		logger.info("异步接收报文(代付交易)");
    		logger.info("--------------代付后台回调-------------->"+valideData);
    		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
    		if (!AcpService.validate(valideData, encoding)) {
    			logger.info("代付验证签名结果[失败].");
    			//验签失败，需解决验签问题
    			
    		} else {
    			logger.info("代付验证签名结果[成功].");
    			//交易成功，更新商户订单状态
    			
    			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String queryId =valideData.get("queryID"); //获取后台通知的数据，其他字段也可用类似方式获取
    			String respCode = valideData.get("respCode");
    			logger.info("============代付回调================"+respCode);
    			logger.info("============代付回调================"+URLDecoder.decode(valideData.get("respMsg"),"UTF-8"));
    			//如果配置了敏感信息加密证书，可以用以下方法解密，如果不加密可以不需要解密
    			TxSellingOrder txSellingOrder = txSellingOrderDAO.getTxSellingOrderByRefundCode(orderId);
    			if("00".equals(respCode)){
    				if(txSellingOrder!=null&&txSellingOrder.getId()>0){
    					txSellingOrder.setRefundState(1);
    					txSellingOrder.setRefundTime(new Date());
    					txSellingOrder.setRefundQueryId(queryId);
    					txSellingOrderDAO.updateTxSellingOrderById(txSellingOrder);
    				}
    			}else{
    				logger.info("============售卡支付回调失败=================");
    			}
    			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
    		}
    		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
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
    public Map<String, String> pay(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode){
    	Map<String, String> rspData = null;
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", DemoBase.version);                  //版本号
    		contentData.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
    		contentData.put("txnType", "01");                              //交易类型 01-消费
    		contentData.put("txnSubType", "01");                           //交易子类型 01-消费
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("backUrlTrans", ConfigConstants.REDIRECT_BACKURLTRANS);
    		contentData.put("backUrlDF", ConfigConstants.REDIRECT_BACKURLDF);
    		contentData.put("merId", ConfigConstants.MER_ID);                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("xwMerId", wxUser.getMerId());                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("txnAmtTrans", AcpService.encryptData(txnAmt, DemoBase.encoding));							   //交易金额，单位分，不要带小数点
    		
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		Map<String,String> customerInfoMap = new HashMap<String,String>();
    		customerInfoMap.put("smsCode", smsCode);			    	//短信验证码
    		//customerInfoMap不送pin的话 该方法可以不送 卡号
    		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding);
    		contentData.put("customerInfo", customerInfoStr);
    		
    		contentData.put("tokenPayData", "{token="+txWxUserBankNo.getToken()+"&trId="+ConfigConstants.TRID+"}");
    		contentData.put("bankNoTrans", AcpService.encryptData(txWxUserBankNo.getAccNo(), DemoBase.encoding));                              //账号类型
    		contentData.put("bankNameTrans", txWxUserBankNo.getAccName());                              //账号类型
    		
    		
    		contentData.put("phone", wxUser.getMobile());
    		contentData.put("settType", "0");                            //账号类型
    		
    		BigDecimal bg = new BigDecimal(txnAmt);
			BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.RATE));
			int txnAmtDF = (bg.multiply(bgRate)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+200;
    		
    		contentData.put("txnAmtDF", AcpService.encryptData((Integer.valueOf(txnAmt)-txnAmtDF)+"", DemoBase.encoding));
    		contentData.put("ppType", "0");

    		
    		

    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);				//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("消费&代付交易");
    		logger.info("http:// 114.113.238.50:54041/XW/Trans");
    		logger.info("发送报文");
    		logger.info("--------支付发送报文------------>"+reqData);
    		rspData = AcpService.post(reqData,ConfigConstants.TRANS,DemoBase.encoding);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("--------支付接收报文------------>"+rspData);
    		logger.info("--------订单号------------>"+orderId);
//    		StringBuffer parseStr = new StringBuffer("");
    		logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
    		if(!rspData.isEmpty()){
    			
    			if(("00").equals(rspData.get("respCode"))){
					//写入库
    				TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
//    				
    				if(txWxOrder!=null&&txWxOrder.getId()>0&&txWxOrder.getMoney().intValue()==Integer.valueOf(rspData.get("txnAmt")).intValue()){
//    					txWxOrder.setState(1);
    					txWxOrder.setQueryId(rspData.get("queryId"));
//    					txWxOrder.setXf_df_flg(rspData.get("XF_DF_Flg"));
//    					txWxOrder.setSettleDate(rspData.get("settleDate"));
    					txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    				}
//    				this.updatePay(orderId, txnTime);
				}else if("S90004".equals(rspData.get("respCode"))){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.SECOND, 60);
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String datastr = sf.format(calendar.getTime());
					String ss = datastr.substring(datastr.length()-2, datastr.length());
					String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
					String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
					String dd = datastr.substring(8, 10);
					String MM = datastr.substring(5, 7);
					QuartzManager.addJob(orderId+"_"+txnTime, QuartzJobService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
				}
    		}else{
//    			//调用查询接口
    			Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, 60);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datastr = sf.format(calendar.getTime());
				String ss = datastr.substring(datastr.length()-2, datastr.length());
				String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
				String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
				String dd = datastr.substring(8, 10);
				String MM = datastr.substring(5, 7);
				QuartzManager.addJob(orderId+"_"+txnTime, QuartzJobService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    		}
//    		reqMessage = DemoBase.genHtmlResult(reqData);
//    		String rspMessage = DemoBase.genHtmlResult(rspData);
//    		logger.info("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+parseStr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return rspData;
    }
    /**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> paySell(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode,Integer backCard){
    	Map<String, String> rspData = null;
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();
    		
    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", DemoBase.version);                  //版本号
    		contentData.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
    		contentData.put("txnType", "01");                              //交易类型 01-消费
    		contentData.put("txnSubType", "01");                           //交易子类型 01-消费
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("backUrlTrans", ConfigConstants.REDIRECT_BACKURLTRANS_SELL);
    		contentData.put("backUrlDF", ConfigConstants.REDIRECT_BACKURLDF_SELL);
    		contentData.put("merId", ConfigConstants.MER_ID);                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		
    		contentData.put("xwMerId", wxUser.getMerId());                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("txnAmtTrans", AcpService.encryptData(txnAmt, DemoBase.encoding));							   //交易金额，单位分，不要带小数点
    		
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		Map<String,String> customerInfoMap = new HashMap<String,String>();
    		customerInfoMap.put("smsCode", smsCode);			    	//短信验证码
    		//customerInfoMap不送pin的话 该方法可以不送 卡号
    		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding);
    		contentData.put("customerInfo", customerInfoStr);
    		
    		contentData.put("tokenPayData", "{token="+txWxUserBankNo.getToken()+"&trId="+ConfigConstants.TRID+"}");
    		contentData.put("bankNoTrans", AcpService.encryptData(txWxUserBankNo.getAccNo(), DemoBase.encoding));                              //账号类型
    		contentData.put("bankNameTrans", txWxUserBankNo.getAccName());                              //账号类型
    		
    		
    		contentData.put("phone", txWxUserBankNo.getPhone());
    		contentData.put("settType", "0");                            //账号类型
    		
    		contentData.put("txnAmtDF", AcpService.encryptData("0", DemoBase.encoding));
    		contentData.put("ppType", "0");
    		contentData.put("hxtXWFee", "0");
    		
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);				//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("消费&代付交易");
    		logger.info("http:// 114.113.238.50:54041/XW/Trans");
    		logger.info("发送报文");
    		logger.info("--------支付发送报文------------>"+reqData);
    		rspData = AcpService.post(reqData,ConfigConstants.TRANS,DemoBase.encoding);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("--------支付接收报文------------>"+rspData);
    		logger.info("--------订单号------------>"+orderId);
//    		StringBuffer parseStr = new StringBuffer("");
    		logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
    		if(!rspData.isEmpty()){
    			
    			if(("00").equals(rspData.get("respCode"))){
    				//写入库
    				TxSellingOrder txSellingOrder = txSellingOrderDAO.getTxSellingOrderByCode(orderId);
    				if(txSellingOrder!=null&&txSellingOrder.getId()>0){
    					txSellingOrder.setQueryId(rspData.get("queryId"));
    					txSellingOrderDAO.updateTxSellingOrderById(txSellingOrder);
    				}
    			}else if("S90004".equals(rspData.get("respCode"))||"E00099".equals(rspData.get("respCode"))){
    				Calendar calendar = Calendar.getInstance();
    				calendar.setTime(new Date());
    				calendar.add(Calendar.SECOND, 30);
    				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    				String datastr = sf.format(calendar.getTime());
    				String ss = datastr.substring(datastr.length()-2, datastr.length());
    				String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
    				String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
    				String dd = datastr.substring(8, 10);
    				String MM = datastr.substring(5, 7);
    				QuartzManager.addJob(orderId+"_"+txnTime+"_"+backCard, QuartzJobSellService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    			}
    		}else{
//    			//调用查询接口
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(new Date());
    			calendar.add(Calendar.SECOND, 60);
    			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String datastr = sf.format(calendar.getTime());
    			String ss = datastr.substring(datastr.length()-2, datastr.length());
    			String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
    			String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
    			String dd = datastr.substring(8, 10);
    			String MM = datastr.substring(5, 7);
    			QuartzManager.addJob(orderId+"_"+txnTime+"_"+backCard, QuartzJobSellService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    		}
//    		reqMessage = DemoBase.genHtmlResult(reqData);
//    		String rspMessage = DemoBase.genHtmlResult(rspData);
//    		logger.info("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+parseStr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return rspData;
    }
    /**
     * 发送验证码
     * @param request
     * @return
     */
    public boolean vercode(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt){
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();

    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", DemoBase.version);                   //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
    		contentData.put("txnType", "77");                              //交易类型 11-代收
    		contentData.put("txnSubType", "02");                           //交易子类型 02-消费短信
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		/***商户接入参数***/
    		contentData.put("merId", ConfigConstants.MER_ID);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		contentData.put("txnAmt", txnAmt);							   //交易金额，单位分，不要带小数点
    		contentData.put("accType", "01");                              //账号类型
    		contentData.put("tokenPayData", "{token="+txWxUserBankNo.getToken()+"&trId="+ConfigConstants.TRID+"}");
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("获取短信验证码");
    		logger.info("http://114.113.238.50:18123/trans/backTransTokenURL");
    		logger.info("发送报文");
    		logger.info("--------验证码发送报文------------>"+reqData);
    		
    		Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.BACKTRANSTOKENURL,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("-------------验证码接收报文-----------------"+rspData);
    		
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
     * 发送验证码(新版)
     * @param request
     * @return
     */
    public boolean vercodeNew(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,Integer backCard){
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();
    		
    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("version", DemoBase.version);                   //版本号
    		contentData.put("encoding", DemoBase.encoding);            //字符集编码 可以使用UTF-8,GBK两种方式
    		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
    		contentData.put("txnType", "77");                              //交易类型 11-代收
    		contentData.put("txnSubType", "02");                           //交易子类型 02-消费短信
    		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
    		contentData.put("channelType", "07");                          //渠道类型07-PC
    		
    		contentData.put("merId", ConfigConstants.MER_ID);    
    		/***商户接入参数***/
    		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
    		contentData.put("txnAmt", txnAmt);							   //交易金额，单位分，不要带小数点
    		contentData.put("accType", "01");                              //账号类型
    		contentData.put("tokenPayData", "{token="+txWxUserBankNo.getToken()+"&trId="+ConfigConstants.TRID+"}");
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("获取短信验证码");
    		logger.info("http://114.113.238.50:18123/trans/backTransTokenURL");
    		logger.info("发送报文");
    		logger.info("--------验证码发送报文------------>"+reqData);
    		
    		Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.BACKTRANSTOKENURL,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("-------------验证码接收报文-----------------"+rspData);
    		
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
//					res.remove(en);
				}
			}
		}
		return res;
	}
	
	public static void main(String args[]){
		BigDecimal bg = new BigDecimal(10000);
		BigDecimal bgRate = new BigDecimal(Double.valueOf(0.0045));
		int txnAmtDF = (bg.multiply(bgRate)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+200;
		logger.info(txnAmtDF);
	}
	
	/**
	 * 交易查询
	 * @param orderId
	 * @param txnTime
	 */
	public void queryPay(String orderId,String txnTime){
		try{
			Map<String, String> data = new HashMap<String, String>();
			
			/***商户接入参数***/
			data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
			data.put("version", DemoBase.version);                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("merId", ConfigConstants.MER_ID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			data.put("orderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("交易状态查询");
			logger.info("http:// 114.113.238.50:54041/XW/Query");
			logger.info("发送报文");
			logger.info("--------交易查询报文------------>"+reqData);
			String url = SDKConfig.getConfig().getSingleQueryUrl();						          //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
			Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易接收结果------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if(StringUtils.isNotBlank(rspData.get("XF_OrderAmt"))){
					TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
					if(txWxOrder!=null&&txWxOrder.getId()>0&&txWxOrder.getMoney().intValue()==Integer.valueOf(rspData.get("XF_OrderAmt")).intValue()){
						TxWxOrder txWxOrder1 = new TxWxOrder();
						if("00".equals(rspData.get("XF_RespCode"))){
							txWxOrder1.setXfState(1);
						}
						if("00".equals(rspData.get("DF_RespCode"))){
							txWxOrder1.setDfState(1);
							txWxOrder1.setOrderAmt(Integer.valueOf(rspData.get("DF_OrderAmt")));
						}
						if("00".equals(rspData.get("XF_RespCode"))&&"00".equals(rspData.get("DF_RespCode"))){
							txWxOrder1.setState(1);
						}
						txWxOrder1.setId(txWxOrder.getId());
						txWxOrderDAO.updateTxWxOrderById(txWxOrder1);
					}
				}
			}else{
				//未返回正确的http状态
				LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 交易查询
	 * @param orderId
	 * @param txnTime
	 */
	public void queryPaySell(String orderId,String txnTime){
		try{
			Map<String, String> data = new HashMap<String, String>();
			
			/***商户接入参数***/
			data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
			data.put("version", DemoBase.version);                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("merId", ConfigConstants.MER_ID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			data.put("orderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			
			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("交易状态查询");
			logger.info("http:// 114.113.238.50:54041/XW/Query");
			logger.info("发送报文");
			logger.info("--------交易查询报文------------>"+reqData);
			String url = SDKConfig.getConfig().getSingleQueryUrl();						          //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
			Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易接收结果------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 交易更新
	 * @param orderId
	 * @param txnTime
	 */
	public boolean updatePay(String orderId,String txnTime,TxWxUser wxUser){
		try{
			Map<String, String> data = new HashMap<String, String>();
			
			/***商户接入参数***/
			data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
			data.put("version", DemoBase.version);                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("settType", "0");
			data.put("merId", ConfigConstants.MER_ID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			
			/***要调通交易以下字段必须修改***/
			data.put("orgOrderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("orgTxnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			data.put("xwMerId", wxUser.getMerId());                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
			
			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			logger.info("更新代付信息");
			logger.info("http:// 114.113.238.50:54041/XW/Update");
			logger.info("发送报文");
			logger.info("--------交易更新发送报文------------>"+reqData);
			Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.UPDATETRANSTOKENURL,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			logger.info("--------交易更新接收报文------------>"+rspData);
			logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if("00".equals(rspData.get("respCode"))){
					TxWxOrder txWxOrder = txWxOrderDAO.getTxWxOrderByCode(orderId);
					TxWxOrder txWxOrder1 = new TxWxOrder();
					txWxOrder1.setState(1);
					txWxOrder1.setId(txWxOrder.getId());
					txWxOrderDAO.updateTxWxOrderById(txWxOrder1);
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
	
	
	private class CutFilesThread implements Runnable {
		private TxWxOrder txWxOrder;

		private CutFilesThread(TxWxOrder txWxOrder) {
			super();
			this.txWxOrder = txWxOrder;
		}

		@Override
		public void run() {
			if(txWxOrder.getPromoterId()!=null){
				ManageAdminUser adminUser = manageAdminUserDao.getManageAdminUserById(txWxOrder.getPromoterId());
				wxTemplateMsg.sendYJTempltMsg(adminUser.getOpenId(), txWxOrder.getOne_rate());
			}
			
			if(txWxOrder.getTwoPromoterId()!=null){
				ManageAdminUser adminUser = manageAdminUserDao.getManageAdminUserById(txWxOrder.getTwoPromoterId());
				wxTemplateMsg.sendYJTempltMsg(adminUser.getOpenId(), txWxOrder.getTwo_rate());
			}
			
			
		}
	}
	
	
	/**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> xwDF(TxWxUser wxUser,String orderId,String merOrderTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode,Integer backCard,Integer flag,Integer xwDFFee){
    	Map<String, String> rspData = null;
    	try{
    		
    		Map<String, String> contentData = new HashMap<String, String>();
    		
    		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    		contentData.put("txnType", "01");                              //交易类型 01-消费
    		contentData.put("settType", "0");                           //交易子类型 01-消费
    		contentData.put("settType", flag+""); 
    		
    		/***商户接入参数***/
    		contentData.put("backUrl", ConfigConstants.XW_BACKURL);
    		contentData.put("merId", ConfigConstants.MER_ID);                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		
    		contentData.put("xwMerId", wxUser.getMerId());                   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
    		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
    		contentData.put("merOrderTime", merOrderTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
    		contentData.put("txnAmt", AcpService.encryptData(txnAmt, DemoBase.encoding));							   //交易金额，单位分，不要带小数点
    		
    		contentData.put("bankNo", AcpService.encryptData(txWxUserBankNo.getAccNo(), DemoBase.encoding));                              //账号类型
    		contentData.put("BankName", txWxUserBankNo.getAccName());                              //账号类型
    		
    		contentData.put("ppType", "0");
    		contentData.put("xwDFFee", xwDFFee+"");//手续费
    		
    		//判断T0还是T1
			TxRefundFlag txRefundFlag = new TxRefundFlag();
			txRefundFlag.setStyle(1);
			List<TxRefundFlag> listT = txRefundFlagDAO.getTxRefundFlagList(txRefundFlag);
			Integer tFlag = 0;
			if(listT!=null&&listT.size()>0){
				tFlag = listT.get(0).getTrem();
			}
			
    		
    		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);				//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
    		logger.info("消费&代付交易");
    		logger.info("http:// 114.113.238.50:54041/XW/Trans");
    		logger.info("发送报文");
    		logger.info("--------支付发送报文------------>"+reqData);
    		rspData = AcpService.post(reqData,ConfigConstants.XW_DF_URL,DemoBase.encoding);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
    		logger.info("同步接收报文");
    		logger.info("--------支付接收报文------------>"+rspData);
    		logger.info("--------订单号------------>"+orderId);
//    		StringBuffer parseStr = new StringBuffer("");
    		logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
    		if(!rspData.isEmpty()){
    			
    			if(("00").equals(rspData.get("respCode"))){
    				//写入库
    				TxSellingOrder txSellingOrder = txSellingOrderDAO.getTxSellingOrderByRefundCode(orderId);
    				if(txSellingOrder!=null&&txSellingOrder.getId()>0){
    					txSellingOrder.setRefundQueryId(rspData.get("queryID"));
    					txSellingOrder.setRefundAccNo(txWxUserBankNo.getAccNo());
    					txSellingOrderDAO.updateTxSellingOrderById(txSellingOrder);
    				}
    			}else if("S90004".equals(rspData.get("respCode"))){
    				
    				Calendar calendar = Calendar.getInstance();
    				calendar.setTime(new Date());
    				calendar.add(Calendar.SECOND, 10);
    				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    				String datastr = sf.format(calendar.getTime());
    				String ss = datastr.substring(datastr.length()-2, datastr.length());
    				String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
    				String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
    				String dd = datastr.substring(8, 10);
    				String MM = datastr.substring(5, 7);
    				QuartzManager.addJob(orderId+"_"+tFlag+"_"+backCard+"_"+wxUser.getMerId(), QuartzJobDFService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    			}else if("E00099".equals(rspData.get("respCode"))){
    				
    				Calendar calendar = Calendar.getInstance();
    				calendar.setTime(new Date());
    				calendar.add(Calendar.SECOND, 10);
    				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    				String datastr = sf.format(calendar.getTime());
    				String ss = datastr.substring(datastr.length()-2, datastr.length());
    				String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
    				String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
    				String dd = datastr.substring(8, 10);
    				String MM = datastr.substring(5, 7);
    				QuartzManager.addJob(orderId+"_"+tFlag+"_"+backCard+"_"+wxUser.getMerId(), QuartzJobDFService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    			}
    		}else{
//    			//调用查询接口
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(new Date());
    			calendar.add(Calendar.SECOND, 30);
    			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			String datastr = sf.format(calendar.getTime());
    			String ss = datastr.substring(datastr.length()-2, datastr.length());
    			String mm = datastr.substring(datastr.length()-5, datastr.length()-3);
    			String hh = datastr.substring(datastr.length()-8, datastr.length()-6);
    			String dd = datastr.substring(8, 10);
    			String MM = datastr.substring(5, 7);
    			QuartzManager.addJob(orderId+"_"+tFlag+"_"+backCard+"_"+wxUser.getMerId(), QuartzJobDFService.class, ss+" "+mm+" "+hh+" "+dd+" "+MM+" ?");  
    		}
//    		reqMessage = DemoBase.genHtmlResult(reqData);
//    		String rspMessage = DemoBase.genHtmlResult(rspData);
//    		logger.info("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+parseStr);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return rspData;
    }
}
