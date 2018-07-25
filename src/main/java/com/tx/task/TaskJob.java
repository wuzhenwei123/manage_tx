package com.tx.task;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.utils.ChineseCharToEn;
import com.base.utils.ConfigConstants;
import com.base.utils.HttpUtils;
import com.base.utils.MD5;
import com.base.utils.MakeImei;
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txCity.model.TxCity;
import com.tx.txCity.service.TxCityService;
import com.tx.txDfOrder.model.TxDfOrder;
import com.tx.txDfOrder.service.TxDfOrderService;
import com.tx.txRefundFlag.model.TxRefundFlag;
import com.tx.txRefundFlag.service.TxRefundFlagService;
import com.tx.txRefundOrder.model.TxRefundOrder;
import com.tx.txRefundOrder.service.TxRefundOrderService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.WeiXinService;
import com.wx.utils.https.HttpRequest;



@Service
public class TaskJob {
	
	Logger log = Logger.getLogger(TaskJob.class);
	private static MD5 MD5 = new MD5();
	
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxCityService txCityService = null;
	@Autowired
	private TxBusinessTypeService txBusinessTypeService = null;
	@Autowired
	private TxRefundOrderService txRefundOrderService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxRefundFlagService txRefundFlagService = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private TxDfOrderService txDfOrderService = null;
	
    public void cut(){
    	try {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    		TxSellingOrder txSellingOrder = new TxSellingOrder();
        	txSellingOrder.setState(1);
        	txSellingOrder.setRefundState(0);
        	
        	Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
        	txSellingOrder.setEndTime(calendar.getTime());
        	
        	List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderListBySY1(txSellingOrder);
        	if(list!=null&&list.size()>0){
        		JSONObject json = new JSONObject();
        		json.put("msgType", "T1XW01");
        		json.put("tranSum", list.size());
        		json.put("merchantID", ConfigConstants.MER_ID);
        		Long fee = 0L;
        		JSONArray array = new JSONArray();
        		String tmpMd5="";
        		for(TxSellingOrder order:list){
        			JSONObject jsonDate = new JSONObject();
        			BigDecimal bg = new BigDecimal(order.getMoney());
					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.RATE));
					int txnAmtDF = (bg.multiply(bgRate).divide(new BigDecimal(12).multiply(new BigDecimal(order.getSelTime())))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        			fee = txnAmtDF + fee;
        			String md5str = MD5.getMD5ofStr(order.getXwMerId()+ txnAmtDF+ order.getMoney());
        			jsonDate.put("xwMerId", order.getXwMerId());
            		jsonDate.put("amt", txnAmtDF);
            		jsonDate.put("orderAmt", order.getMoney());
            		jsonDate.put("dataMd5", md5str);
            		if(tmpMd5==""){
            			tmpMd5 =  md5str;
            		}else{
            			tmpMd5 = MD5.getMD5ofStr(md5str+tmpMd5);
            		}
            		array.add(jsonDate);
        		}
        		json.put("totalFee", fee);
        		json.put("data", array);
        		json.put("md5Str", MD5.getMD5ofStr(tmpMd5));
        		json.put("batchNo", sf.format(new Date())+"0001");
        		
        		String jsonStr = HttpRequest.sendPost(ConfigConstants.SY_URL, json.toString());
        		
//        		String jsonStr = com.base.utils.https.HttpUtils.httpsRequest(ConfigConstants.SY_URL, "POST", json.toString());
        		
                JSONObject result = JSONObject.parseObject(jsonStr);
        		if("0".equals(result.get("respCode"))){
        			for(TxSellingOrder order:list){
            			TxRefundOrder txRefundOrder = new TxRefundOrder();
    					txRefundOrder.setUserId(order.getWxUserId());
    					txRefundOrder.setRealName(order.getWxUserName());
    					txRefundOrder.setCreateTime(new Date());
    					txRefundOrder.setFee(order.getMoney());
    					txRefundOrder.setOrderCode(order.getCode());
    					txRefundOrder.setOrderTime(order.getCreateTime());
    					txRefundOrder.setBatch(sf.format(new Date())+"0001");
    					txRefundOrderService.insertTxRefundOrder(txRefundOrder);
                	}
        		}else{
        			log.info(sf.format(new Date())+"0001" + "---------------收益--------------------"+result.get("respMsg"));
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 业务类型表
     */
    public void job5(){
    	try{
    		Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Open");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", txBusinessTypeService.getTraceNo());
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		String result = HttpUtils.sendPost(param);
    		String ResultCode = HttpUtils.getVal(result, "ResultCode");
    		if("00".equals(ResultCode)){//查询成功
    			TxBusinessType txBusinessType = new TxBusinessType();
    			int count = txBusinessTypeService.getTxBusinessTypeCount(txBusinessType);
    			if(count>0){
    				String items = URLDecoder.decode(HttpUtils.getVal(result, "Items"),"UTF-8");
    				String[] item = items.split("\\|");
    				for(String str:item){
    					String[] strs = str.split(",");
    					TxBusinessType txBusinessType1 = new TxBusinessType();
    					txBusinessType1.setBillType(strs[0]);
    					txBusinessType1.setCityCode(strs[7]);
    					txBusinessType1.setServiceType(strs[8]);
    					txBusinessType1 = txBusinessTypeService.getTxBusinessType(txBusinessType1);
    					if(txBusinessType1!=null){
    						txBusinessType1.setBillType(strs[0]);
    						txBusinessType1.setFeeTypeDesc(strs[1]);
    						txBusinessType1.setBillArea(strs[2]);
    						txBusinessType1.setChargeUnit(strs[3]);
    						txBusinessType1.setIsNeedDate(Integer.valueOf(strs[4]));
    						txBusinessType1.setIsNeedFee(Integer.valueOf(strs[5]));
    						txBusinessType1.setFeeRule(strs[6]);
    						
    						txBusinessType1.setCityCode(strs[7]);
    						txBusinessType1.setServiceType(strs[8]);
        					if(strs.length>9){
        						txBusinessType1.setAddInfo(strs[9]);
        					}
        					txBusinessType1.setState(1);
        					ChineseCharToEn cte = new ChineseCharToEn();
        					String strsssssss = cte.getAllFirstLetter(strs[2]);
        					strsssssss = strsssssss.substring(0, 1);
        					txBusinessType1.setBigLetters(strsssssss.toUpperCase());
        					TxCity txCity = new TxCity();
        					txCity.setCityName(strs[2]);
        					List<TxCity> list = txCityService.getTxCityList(txCity);
        					if(list!=null&&list.size()>0){
        						txCity = list.get(0);
        						txBusinessType1.setRemark1(txCity.getCityCode());
        					}
        					txBusinessTypeService.updateTxBusinessTypeById(txBusinessType1);
    					}else{
    						txBusinessType1 = new TxBusinessType();
    						txBusinessType1.setBillType(strs[0]);
    						txBusinessType1.setFeeTypeDesc(strs[1]);
    						txBusinessType1.setBillArea(strs[2]);
    						txBusinessType1.setChargeUnit(strs[3]);
    						txBusinessType1.setIsNeedDate(Integer.valueOf(strs[4]));
    						txBusinessType1.setIsNeedFee(Integer.valueOf(strs[5]));
    						txBusinessType1.setFeeRule(strs[6]);
    						
    						txBusinessType1.setCityCode(strs[7]);
    						txBusinessType1.setServiceType(strs[8]);
        					if(strs.length>9){
        						txBusinessType1.setAddInfo(strs[9]);
        					}
        					txBusinessType1.setState(1);
        					ChineseCharToEn cte = new ChineseCharToEn();
        					String strsssssss = cte.getAllFirstLetter(strs[2]);
        					strsssssss = strsssssss.substring(0, 1);
        					txBusinessType1.setBigLetters(strsssssss.toUpperCase());
        					TxCity txCity = new TxCity();
        					txCity.setCityName(strs[2]);
        					List<TxCity> list = txCityService.getTxCityList(txCity);
        					if(list!=null&&list.size()>0){
        						txCity = list.get(0);
        						txBusinessType1.setRemark1(txCity.getCityCode());
        					}
        					txBusinessTypeService.insertTxBusinessType(txBusinessType1);
    					}
    				}
    			}else{
    				String items = URLDecoder.decode(HttpUtils.getVal(result, "Items"),"UTF-8");
    				String[] item = items.split("\\|");
    				for(String str:item){
    					String[] strs = str.split(",");
    					TxBusinessType txBusinessType1 = new TxBusinessType();
    					txBusinessType1.setBillType(strs[0]);
    					txBusinessType1.setFeeTypeDesc(strs[1]);
    					txBusinessType1.setBillArea(strs[2]);
    					txBusinessType1.setChargeUnit(strs[3]);
    					txBusinessType1.setIsNeedDate(Integer.valueOf(strs[4]));
    					txBusinessType1.setIsNeedFee(Integer.valueOf(strs[5]));
    					txBusinessType1.setFeeRule(strs[6]);
    					txBusinessType1.setCityCode(strs[7]);
    					txBusinessType1.setServiceType(strs[8]);
    					if(strs.length>9){
    						txBusinessType1.setAddInfo(strs[9]);
    					}
    					txBusinessType1.setState(1);
    					ChineseCharToEn cte = new ChineseCharToEn();
    					String strsssssss = cte.getAllFirstLetter(strs[2]);
    					strsssssss = strsssssss.substring(0, 1);
    					txBusinessType1.setBigLetters(strsssssss.toUpperCase());
    					TxCity txCity = new TxCity();
    					txCity.setCityName(strs[2]);
    					List<TxCity> list = txCityService.getTxCityList(txCity);
    					if(list!=null&&list.size()>0){
    						txCity = list.get(0);
    						txBusinessType1.setRemark1(txCity.getCityCode());
    					}
    					txBusinessTypeService.insertTxBusinessType(txBusinessType1);
    				}
    			}
    		}
    		log.info("Items=" + URLDecoder.decode(HttpUtils.getVal(result, "Items"),"UTF-8"));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    public void cut1(){
    	try {
    		
    		TxDfOrder txDfOrder = new TxDfOrder();
    		txDfOrder.setState(0);
    		List<TxDfOrder> list = txDfOrderService.getTxDfOrderListByDF(txDfOrder);
    		if(list!=null&&list.size()>0){
    			for(TxDfOrder order:list){
    				TxWxUser wxUser = txWxUserService.getTxWxUserById(order.getUserId());
    				String orderId = new MakeImei().getCode();
    				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    				Date d = new Date();
    				String merOrderTime = sf.format(d);
					
					
					//调用接口退费
					TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
					//查询开户行名称
				 	String accNo = wxUser.getCardNumber();
				 	String accName = weiXinService.getKHBankName(accNo);
				 	txWxUserBankNo.setAccName(accName);
				 	txWxUserBankNo.setAccNo(accNo);
    				Map<String, String>	map = txWxUserBankNoService.xwDFTQ(wxUser, orderId, merOrderTime, txWxUserBankNo, order.getFee()+"", order.getOrderCode(), 1, 1,0);
    				if(map!=null&&"00".equals(map.get("respCode"))){
    					log.info(wxUser.getId()+"-----------"+order.getOrderCode()+"---------------"+merOrderTime+"------------成功");
					}else{
						log.info(wxUser.getId()+"-----------"+order.getOrderCode()+"---------------"+merOrderTime+"------------失败"+URLDecoder.decode(map.get("respMsg"),"UTF-8"));
					}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
//    public void cut1(){
//    	try {
//    		
//    		SimpleDateFormat sf111 = new SimpleDateFormat("yyyyMMddHHmmss");
//    		TxSellingOrder txSellingOrder = new TxSellingOrder();
//    		txSellingOrder.setState(1);
//    		txSellingOrder.setRefundState(0);
//    		txSellingOrder.setEndTime(new Date());
//    		//判断T0还是T1
//    		TxRefundFlag txRefundFlag = new TxRefundFlag();
//    		txRefundFlag.setStyle(2);
//    		List<TxRefundFlag> listT = txRefundFlagService.getTxRefundFlagList(txRefundFlag);
//    		List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderListBySY1(txSellingOrder);
//    		if(list!=null&&list.size()>0){
//    			for(TxSellingOrder order:list){
//    				if(order.getState().intValue()==1&&order.getRefundState().intValue()==0){
//    					
//    					TxWxUser wxUser = txWxUserService.getTxWxUserById(order.getWxUserId());
//    					//调用接口退费
////						TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(order.getAccNo());
//    					
//    					//调用接口退费
//    					TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
//    					//查询开户行名称
//    					String accNo = wxUser.getCardNumber();
//    					String accName = weiXinService.getKHBankName(accNo);
//    					txWxUserBankNo.setAccName(accName);
//    					txWxUserBankNo.setAccNo(accNo);
//    					
//    					BigDecimal bg = new BigDecimal(order.getMoney());
//    					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.PAY_RATE));
//    					
//    					int txnAmtDF = (bg.multiply(bgRate).divide(new BigDecimal(12).multiply(new BigDecimal(order.getSelTime())))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//    					
//    					order.setProfitManey(order.getMoney()+txnAmtDF);
//    					order.setProfits(bgRate);
//    					
//    					String orderId = new MakeImei().getCode();
//    					Date d = new Date();
//    					
//    					String merOrderTime = sf111.format(d);
//    					order.setRefundCode(orderId);
//    					
//    					if(order.getPromoterId()!=null&&order.getTwoPromoterId()!=null){
//    						int two = (bg.multiply(new BigDecimal(0.0008))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//    						order.setTwoRate(two);
//    					}else if(order.getPromoterId()!=null&&order.getTwoPromoterId()==null){
//    						int one = (bg.multiply(new BigDecimal(0.0008))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//    						order.setOneRate(one);
//    					}
//    					
////						order.setProfitManey(order.getMoney());
////						order.setProfits(new BigDecimal(0));
//    					txSellingOrderService.updateTxSellingOrderById(order);
//    					txWxUserBankNoService.xwDF(wxUser, orderId, merOrderTime, txWxUserBankNo, order.getProfitManey()+"", null, order.getBackCard(), listT.get(0).getTrem(),0);
//    				}
//    			}
//    		}
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
//    }
}
