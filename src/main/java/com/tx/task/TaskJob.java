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

import com.alibaba.fastjson.JSONObject;
import com.base.utils.ChineseCharToEn;
import com.base.utils.ConfigConstants;
import com.base.utils.HttpUtils;
import com.base.utils.MD5;
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txCity.model.TxCity;
import com.tx.txCity.service.TxCityService;
import com.tx.txRefundOrder.model.TxRefundOrder;
import com.tx.txRefundOrder.service.TxRefundOrderService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
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
	
    public void cut(){
    	try {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    		TxSellingOrder txSellingOrder = new TxSellingOrder();
        	txSellingOrder.setState(1);
        	txSellingOrder.setRefundState(0);
        	
        	Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, -1);
        	txSellingOrder.setEndTime(calendar.getTime());
        	
        	List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderListBySY1(txSellingOrder);
        	if(list!=null&&list.size()>0){
        		JSONObject json = new JSONObject();
        		json.put("msgType", "T1XW01");
        		json.put("tranSum", list.size());
        		json.put("merchantID", ConfigConstants.MER_ID);
        		Long fee = 0L;
        		JSONObject jsonDate = new JSONObject();
        		String tmpMd5="";
        		for(TxSellingOrder order:list){
        			
        			BigDecimal bg = new BigDecimal(order.getMoney());
					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.PAY_RATE));
					int txnAmtDF = (bg.multiply(bgRate).divide(new BigDecimal(12).multiply(new BigDecimal(order.getSelTime())))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        			fee = order.getMoney() + txnAmtDF + fee;
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
        		}
        		json.put("totalFee", fee);
        		json.put("data", jsonDate);
        		json.put("md5Str", tmpMd5);
        		json.put("batchNo", sf.format(new Date())+"0001");
        		
        		String jsonStr = HttpRequest.sendPost(ConfigConstants.SY_URL, json.toString());
                JSONObject result = JSONObject.parseObject(jsonStr);
        		if("0".equals(result.get("respCode"))){
        			for(TxSellingOrder order:list){
            			TxRefundOrder txRefundOrder = new TxRefundOrder();
    					txRefundOrder.setUserId(order.getWxUserId());
    					txRefundOrder.setRealName(order.getRealName());
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
    					txBusinessType1.setCityCode(strs[5]);
    					txBusinessType1.setServiceType(strs[6]);
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
}
