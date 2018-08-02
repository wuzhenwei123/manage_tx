package com.tx.txBusinessType.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.base.utils.ConfigConstants;
import com.base.utils.HttpUtils;
import com.base.utils.SessionName;
import com.base.utils.Tinput;
import com.tx.txBusinessType.dao.TxBusinessTypeDAO;
import com.tx.txBusinessType.model.TxBusinessType;

/**
 * @author	wzw
 * @time	2018-06-02 11:36:09
 */
@Service
@Transactional
public class TxBusinessTypeServiceImpl implements TxBusinessTypeService{
	
	private static Logger logger = Logger.getLogger(TxBusinessTypeServiceImpl.class);
	
	@Resource
    private TxBusinessTypeDAO txBusinessTypeDAO;
	
    public List<TxBusinessType> getTxBusinessTypeList(TxBusinessType txBusinessType) {
        return txBusinessTypeDAO.getTxBusinessTypeList(txBusinessType);
    }
    
    public List<TxBusinessType> getTxBusinessTypeListGroup(TxBusinessType txBusinessType) {
    	return txBusinessTypeDAO.getTxBusinessTypeListGroup(txBusinessType);
    }

    public TxBusinessType getTxBusinessTypeById(int id) { 
        return txBusinessTypeDAO.getTxBusinessTypeById(id);
    }
    
    public TxBusinessType getTxBusinessType(TxBusinessType txBusinessType){
    	return txBusinessTypeDAO.getTxBusinessType(txBusinessType);
    }
    
    public int insertTxBusinessType(TxBusinessType txBusinessType){
        return txBusinessTypeDAO.insertTxBusinessType(txBusinessType);
    }

    public int updateTxBusinessTypeById(TxBusinessType txBusinessType){
        return txBusinessTypeDAO.updateTxBusinessTypeById(txBusinessType);
    }
    
    public int getTxBusinessTypeCount(TxBusinessType txBusinessType){
    	return txBusinessTypeDAO.getTxBusinessTypeCount(txBusinessType);
    }
    
    public int deleteTxBusinessTypeById(int id){
        return txBusinessTypeDAO.deleteTxBusinessTypeById(id);
    }
    
    /**
     * 查询
     * @param cityCode      城市代码
     * @param serviceType   业务代码
     * @param payNo         用户缴费编号
     * @param preTotalFee   预存金额,单位：分真针对可以预存的业务；其他不可预存业务填“0”。
     * @param plotNo        小区号,格式 G0001 IsNeedPoltNo 决定是否需要小区号默认 0 不需要 1 需要
     * @param accountPeriod 上送账期,格式 YYYYMM （ 根据 开 通 查 询 中IsNeedDate 决定是否需要输入，IsNeedDat 为 0 时补足空格IsNeedDat 为 1 时左补 足 空 格 即“ YYYYMM”IsNeedDat 为 2 时右补 足 0 即“YYYYMM00”）
     * @param bankCardNo    付款卡号
     * @return
     */
    public String queryBusinessMsg(String cityCode,String serviceType,String payNo,Integer preTotalFee,Integer plotNo,String accountPeriod,String bankCardNo){
    	try{
    		Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Query");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", this.getTraceNo());
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		
    		param.put("CityCode", cityCode);
    		param.put("ServiceType", serviceType);
    		param.put("PayNo", payNo);
    		param.put("PreTotalFee", preTotalFee);
    		param.put("CustomerType", 0);
    		param.put("PlotNo", plotNo);
    		param.put("AccountPeriod", accountPeriod);
    		param.put("BankCardNo", bankCardNo);
    		
    		
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		String result = HttpUtils.sendPost(param);
    		return result;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 缴费
     * @param cityCode      城市代码
     * @param serviceType   业务代码
     * @param payNo         用户缴费编号
     * @param hostSerialNo  银行交易流水号
     * @param loopID        循环项缴费 ID
     * @param settlementDate银行清算日
     * @param centerSeria   中心流水号
     * @param bankCardNo    付款卡号
     * @param totalFee      缴费金额
     * @return
     */
    public String payBusinessMsg(String cityCode,String serviceType,String payNo,String hostSerialNo,String loopID,String settlementDate,String bankCardNo,String centerSeria,Integer totalFee){
    	try{
    		Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Pay");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", this.getTraceNo());
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		
    		param.put("CenterSeria", centerSeria);
    		
    		param.put("CityCode", cityCode);
    		param.put("ServiceType", serviceType);
    		param.put("PayNo", payNo);
    		param.put("HostSerialNo", hostSerialNo);
    		param.put("SettlementDate", settlementDate);
    		param.put("LoopID", loopID);
    		param.put("BankCardNo", bankCardNo);
    		param.put("TotalFee", totalFee);
    		
    		
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		String result = HttpUtils.sendPost(param);
    		return result;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 缴费结果查询
     * @param OrgCenterSerial      原交易中心流水号
     * @return
     */
    public String resultBusinessMsg(String orgCenterSerial){
    	try{
    		Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Result");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", this.getTraceNo());
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		
    		param.put("OrgCenterSerial", orgCenterSerial);
    		
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		String result = HttpUtils.sendPost(param);
    		return result;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
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
	public Map<String, Object> pay(Integer totalFee,String shopCode,String userCode,String traceNo,String transaction_id,String IPAddress,String centerInfo,String settlementDate) {
		
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
	 * 商户信息查询
	 * @time : 2015年8月25日 下午4:13:45
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description: TODO
	 */
	public Map<String, Object> queryAccountUser(String shopCode,String userCode,Integer TotalFee,String ip) {

		if (shopCode.equals("3102")) {

		} else if (shopCode.equals("3202")) {
			userCode = userCode + "$G00";
		} 
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("TerminalID", ConfigConstants.TERMINALID);
		param.put("KeyID", ConfigConstants.KEYID);
		param.put("UserID", "");
		param.put("Account", "");
		param.put("EMail", "");
		param.put("CardNo", "");
		param.put("TotalFee", TotalFee);
		param.put("ShopCode", shopCode);
		param.put("PaymentInfo", userCode);
		param.put("IPAddress", ip);
		param.put("TraceNo", "");
		param.put("MCode", Tinput.getMcode(param));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			String result = Tinput.query(param);
			logger.info(sf.format(new Date())+"----------------result-------------"+result);
			String resultInfo =Tinput.getVal(result, "ResultInfo");
			String PaymentInfo =Tinput.getVal(result, "PaymentInfo");
			if(StringUtils.isNotBlank(result)){
				String resultCode =Tinput.getVal(result, "ResultCode");// 00 成功
				if("00".equals(resultCode)){
					String rshopCode =Tinput.getVal(result, "ShopCode");
					String QFee = Tinput.getVal(result, "TotalFee");
					map.put("resultInfo", resultInfo);
					map.put("PaymentInfo", PaymentInfo);
					map.put("shopCode", rshopCode);
					map.put("QFee", QFee);
					map.put("message", "ok");
				}else if(resultCode.trim().equals("CS")||!StringUtils.isNotBlank(resultCode)){
					map.put("message", "fail");
					map.put("resultCode", resultCode);
					map.put("resultInfo", "通讯超时，请确认您的互联网连接状态是否良好，如网络状态良好，请拨打010-96199咨询。");
				}else{
					map.put("message", "fail");
					map.put("resultCode", resultCode);
					map.put("resultInfo", resultInfo);
				}
			}else{
				map.put("message", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 支付前查询
	 * @time : 2015年8月25日 下午4:13:45
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description: TODO
	 */
	public Map<String, Object> queryPayMessage(String shopCode,String userCode,Integer TotalFee,String ip) {

		//判断是不是5元试充
		if (shopCode.equals("3102")) {
			shopCode = "4100";
		} else if (shopCode.equals("3202")) {
			shopCode = "4200";
			userCode = userCode + "$G00$03";
		} 
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("TerminalID", ConfigConstants.TERMINALID);
		param.put("KeyID", ConfigConstants.KEYID);
		param.put("UserID", "");
		param.put("Account", "");
		param.put("EMail", "");
		param.put("CardNo", "");
		param.put("TotalFee", TotalFee);
		param.put("ShopCode", shopCode);
		param.put("PaymentInfo", userCode);
		param.put("IPAddress", ip);
		param.put("TraceNo", "");
		param.put("MCode", Tinput.getMcode(param));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String result = Tinput.query(param);
			logger.info(sf.format(new Date())+"----------------result-------------"+result);
			if(StringUtils.isNotBlank(result)){
				if("CS".equals(result)){
					map.put("message", "fail");
					map.put("resultCode", "CS");
					map.put("resultInfo", "通讯超时，请确认您的互联网连接状态是否良好，如网络状态良好，请拨打010-96199咨询。");
				}else{
					String resultCode =Tinput.getVal(result, "ResultCode");// 00 成功
					String resultInfo =Tinput.getVal(result, "ResultInfo");// 00 成功
					if("00".equals(resultCode)){
						String PaymentInfo =Tinput.getVal(result, "PaymentInfo");
						String[] strs = PaymentInfo.split("\\u0024");
						map.put("PaymentInfo", PaymentInfo.split("\\u0024")[0]);
						if(shopCode.equals("4200")){
							if(strs.length>1){
								map.put("centerInfo", PaymentInfo.split("\\u0024")[1]);
							}else{
								map.put("centerInfo", "");
							}
						}else{
							map.put("centerInfo", "");
						}
						String QFee = Tinput.getVal(result, "TotalFee");
						map.put("QFee", QFee);
						map.put("resultInfo", resultInfo);
						map.put("message", "ok");
					}else{
						map.put("message", "fail");
						map.put("resultCode", resultCode);
						map.put("resultInfo", resultInfo);
					}
				}
			}else{
				map.put("message", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 解析智能充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsg(String customerNumber,String resultInfo,Integer TotalFee){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] resultInfos = resultInfo.split(",");
			String userAddress = resultInfos[1];
			String username = resultInfos[0];
			String bujia = resultInfos[2];
			String koujian = resultInfos[3];
			if(TotalFee>0){//有欠费
				String chajia = resultInfos[4];
				String chajiayue = resultInfos[5];
				map.put("chajia", chajia.split(":")[1]);
				map.put("chajiayue", chajiayue.split(":")[1]);
				String TotalFeestr = String.valueOf(TotalFee);
				if(TotalFeestr.length()>2){
					int fen = TotalFee%100;
					if(fen>0&&fen<10){
						TotalFeestr = TotalFee/100 + ".0" + fen;
					}else if(fen>=10){
						TotalFeestr = TotalFee/100 + "." + fen;
					}else{
						TotalFeestr = TotalFee/100 + ".00";
					}
					map.put("qianfei", TotalFeestr);
				}else if(TotalFeestr.length()==1){
					map.put("qianfei", "0.0"+TotalFeestr);
				}else{
					map.put("qianfei", "0."+TotalFeestr);
				}
			}else{
				map.put("qianfei", "0.00");
				map.put("chajia", "0.00");
			}
			map.put("customerNumber", customerNumber);
			map.put("userAddress", userAddress.split(":")[1]);
			String str = username.split(":")[1];
			if(StringUtils.isNotBlank(str)){
				map.put("username1", str);
				if(str.length()>2){
					str = str.substring(str.length()-2, str.length());
				}else{
					map.put("username", "*");
				}
				map.put("username", "*"+str);
			}else{
				map.put("username", "*");
			}
			map.put("bujia", bujia.split(":")[1]);
			map.put("koujian", koujian.split(":")[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 解析智能充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsg1(String customerNumber,String resultInfo,Integer TotalFee){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] resultInfos = resultInfo.split(",");
			String userAddress = resultInfos[1];
			String username = resultInfos[0];
			String bujia = resultInfos[2];
			String koujian = resultInfos[3];
			if(TotalFee>0){//有欠费
//				String chajia = resultInfos[4];
//				String chajiayue = resultInfos[5];
//				map.put("chajia", chajia.split(":")[1]);
//				map.put("chajiayue", chajiayue.split(":")[1]);
				String TotalFeestr = String.valueOf(TotalFee);
				if(TotalFeestr.length()>2){
					int fen = TotalFee%100;
					if(fen>0&&fen<10){
						TotalFeestr = TotalFee/100 + ".0" + fen;
					}else if(fen>=10){
						TotalFeestr = TotalFee/100 + "." + fen;
					}else{
						TotalFeestr = TotalFee/100 + ".00";
					}
					map.put("qianfei", TotalFeestr);
				}else if(TotalFeestr.length()==1){
					map.put("qianfei", "0.0"+TotalFeestr);
				}else{
					map.put("qianfei", "0."+TotalFeestr);
				}
			}else{
				map.put("qianfei", "0.00");
				map.put("chajia", "0.00");
			}
			map.put("customerNumber", customerNumber);
			if(userAddress.indexOf("。")!=-1){
				map.put("userAddress", userAddress.split(":")[1].subSequence(0, userAddress.split(":")[1].indexOf("。")));
			}else{
				map.put("userAddress", userAddress.split(":")[1]);
			}
			String str = username.split(":")[1];
			if(StringUtils.isNotBlank(str)){
				map.put("username1", str);
				if(str.length()>2){
					str = str.substring(str.length()-2, str.length());
				}else{
					map.put("username", "*");
				}
				map.put("username", "*"+str);
			}else{
				map.put("username", "*");
			}
			map.put("bujia", bujia.split(":")[1]);
			map.put("koujian", koujian.split(":")[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 解析智能充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsg2(String customerNumber,String resultInfo,Integer TotalFee){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] resultInfos = resultInfo.split(",");
			String userAddress = resultInfos[2];
			String username = resultInfos[1];
			String bujia = resultInfos[5];
			String koujian = resultInfos[6];
			if(TotalFee>0){//有欠费
				String chajia = resultInfos[4];
//				String chajiayue = resultInfos[5];
				map.put("chajia", chajia.split(":")[1]);
//				map.put("chajiayue", chajiayue.split(":")[1]);
				String TotalFeestr = String.valueOf(TotalFee);
				if(TotalFeestr.length()>2){
					int fen = TotalFee%100;
					if(fen>0&&fen<10){
						TotalFeestr = TotalFee/100 + ".0" + fen;
					}else if(fen>=10){
						TotalFeestr = TotalFee/100 + "." + fen;
					}else{
						TotalFeestr = TotalFee/100 + ".00";
					}
					map.put("qianfei", TotalFeestr);
				}else if(TotalFeestr.length()==1){
					map.put("qianfei", "0.0"+TotalFeestr);
				}else{
					map.put("qianfei", "0."+TotalFeestr);
				}
			}else{
				map.put("qianfei", "0.00");
				map.put("chajia", "0.00");
			}
			map.put("customerNumber", customerNumber);
			if(userAddress.indexOf("。")!=-1){
				map.put("userAddress", userAddress.split(":")[1].subSequence(0, userAddress.split(":")[1].indexOf("。")));
			}else{
				map.put("userAddress", userAddress.split(":")[1]);
			}
			String str = username.split(":")[1];
			if(StringUtils.isNotBlank(str)){
				map.put("username1", str);
				if(str.length()>2){
					str = str.substring(str.length()-2, str.length());
				}else{
					map.put("username", "*");
				}
				map.put("username", "*"+str);
			}else{
				map.put("username", "*");
			}
			map.put("bujia", bujia.split(":")[1]);
			map.put("koujian", koujian.split(":")[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 解析抄表充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsgCB(String customerNumber,String resultInfo,Integer TotalFee){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			resultInfo = resultInfo.replaceAll("：", ":").replaceAll("；", ";");
			String[] resultInfos = resultInfo.split(";");
			String userAddress = resultInfos[1];
			String username = resultInfos[0];
			String zhangdan = resultInfos[3];
			String zhina = resultInfos[4];
			if(TotalFee>0){//有欠费
				String TotalFeestr = String.valueOf(TotalFee);
				if(TotalFeestr.length()>2){
					int fen = TotalFee%100;
					if(fen>0&&fen<10){
						TotalFeestr = TotalFee/100 + ".0" + fen;
					}else if(fen>=10){
						TotalFeestr = TotalFee/100 + "." + fen;
					}else{
						TotalFeestr = TotalFee/100 + ".00";
					}
					map.put("yingjiao", TotalFeestr);
				}else if(TotalFeestr.length()==1){
					map.put("yingjiao", "0.0"+TotalFeestr);
				}else{
					map.put("yingjiao", "0."+TotalFeestr);
				}
			}else{
				map.put("yingjiao", "0.00");
			}
			map.put("customerNumber", customerNumber);
			map.put("userAddress", userAddress.split(":")[1]);
			String str = username.split(":")[1];
			if(StringUtils.isNotBlank(str)){
				map.put("username1", str);
				if(str.length()>2){
					str = str.substring(str.length()-2, str.length());
				}else{
					map.put("username", "*");
				}
				map.put("username", "*"+str);
			}else{
				map.put("username", "*");
			}
			map.put("zhangdan", zhangdan.split(":")[1]);
			map.put("zhina", zhina.split(":")[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
    
    public String getTraceNo(){
    	int max_count = 999999;
    	if(SessionName.TRACENO_COUNT>=max_count){
    		SessionName.TRACENO_COUNT = 1;
    		return "000001";
    	}else{
    		SessionName.TRACENO_COUNT = SessionName.TRACENO_COUNT + 1;
    		String str = SessionName.TRACENO_COUNT + "";
    		if(str.length()==6){
    			return str;
    		}else if(str.length()<6){
    			int len = 6-str.length();
    			for(int i=0;i<len;i++){
    				str = "0" + str;
    			}
    			return str;
    		}else{
    			SessionName.TRACENO_COUNT = 1;
        		return "000001";
    		}
    	}
    }
}
