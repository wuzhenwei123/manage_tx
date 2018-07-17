package com.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.controller.BaseController;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.utils.HttpUtils;
import com.github.pagehelper.PageHelper;
import com.tx.task.service.ApplyCutter;
import com.tx.task.service.SendCodeCutter;
import com.tx.txApplay.model.TxApplay;
import com.tx.txApplay.service.TxApplayService;
import com.tx.txBanner.model.TxBanner;
import com.tx.txBanner.service.TxBannerService;
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txPayOrder.model.TxPayOrder;
import com.tx.txPayOrder.service.TxPayOrderService;
import com.tx.txPaynumberMsg.service.TxPaynumberMsgService;
import com.tx.txRecord.model.TxRecord;
import com.tx.txRecord.service.TxRecordService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxOrder.service.TxWxOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.model.YongJinModel;
import com.wx.service.IndexService;
import com.wx.service.OtherService;
import com.wx.service.WeiXinService;

@Controller
@RequestMapping("/other")
public class OtherController extends BaseController{
		
	@Autowired
	private TxWxUserService txWxUserService = null;	
	@Autowired
	private WeiXinService weiXinService = null;
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxBusinessTypeService txBusinessTypeService = null;
	@Autowired
	private TxBannerService txBannerService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxPaynumberMsgService txPaynumberMsgService = null;
	@Autowired
	private SendCodeCutter sendCodeCutter = null;
	@Autowired
	private ApplyCutter applyCutter = null;
	@Autowired
	private IndexService indexService = null;
	@Autowired
	private TxWxOrderService txWxOrderService = null;
	@Autowired
	private OtherService otherService = null;
	@Autowired
	private TxPayOrderService txPayOrderService = null;
	@Autowired
	private TxRecordService txRecordService = null;
	@Autowired
	private TxApplayService txApplayService = null;
	
	/**
	 * 展业二维码
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toMyEmq")
	public String toMyEmq(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			if(!StringUtils.isNotBlank(wxUser.getScanTicket())){
				//生成二维码
				Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, wxUser.getOpenId());
				wxUser.setQrCodeUrl(map.get("imgurl"));
				wxUser.setScanTicket(map.get("ticket"));
				txWxUserService.updateTxWxUserById(wxUser);
			}
			model.addAttribute("wxUser", wxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/share";
	}
	/**
	 * 测试
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response, Model model){
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
		String ResultInfo = HttpUtils.getVal(result, "ResultInfo");
		
		try {
			System.out.println(URLDecoder.decode(ResultInfo,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 批电明细
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSellOrder")
	public String toSellOrder(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			model.addAttribute("wxUser", wxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/orderlist";
	}
	/**
	 * 批电明细
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSellOrderDetail")
	public String toSellOrderDetail(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		Long id = RequestHandler.getLong(request, "id");
		try{
			TxSellingOrder txSellingOrder = txSellingOrderService.getTxSellingOrderById(id);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.addAttribute("order", txSellingOrder);
			model.addAttribute("wxUser", wxUser);
			if(txSellingOrder.getRefundState().intValue()==1){
				if(txSellingOrder.getRefundTime().before(txSellingOrder.getEndTime())){
					model.addAttribute("state", "已提前退款");
				}else{
					model.addAttribute("state", "到期退款");
				}
			}else{
				if(txSellingOrder.getEndTime().after(new Date())){
					model.addAttribute("state", "赚钱中");
				}else{
					model.addAttribute("state", "已到期");
				}
			}
			model.addAttribute("time", sf.format(txSellingOrder.getCreateTime()));
			model.addAttribute("fee", super.getMoney(txSellingOrder.getMoney()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/orderDetail";
	}
	/**
	 * 选择缴费地区
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPlace")
	public String toPlace(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		String name = RequestHandler.getString(request, "name");
		try{
			TxBusinessType txBusinessType = new TxBusinessType();
			txBusinessType.setBillArea(name);
			txBusinessType.setSort("bigLetters");
			txBusinessType.setOrder("asc");
			txBusinessType.setGroup("cityCode");
			TreeMap<String,List<TxBusinessType>> map = new TreeMap<String,List<TxBusinessType>>();
			List<TxBusinessType> list = txBusinessTypeService.getTxBusinessTypeListGroup(txBusinessType);
			Set<String> set = new TreeSet<String>();
			for(TxBusinessType t:list){
				set.add(t.getBigLetters());
			}
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
				String key = it.next();
				List<TxBusinessType> listSub = new ArrayList<TxBusinessType>();
				for(TxBusinessType t:list){
					if(t.getBigLetters().equals(key)){
						listSub.add(t);
					}
				}
				map.put(key, listSub);
			}
			model.addAttribute("map", map);
			model.addAttribute("name", name);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/index/place";
	}
	/**
	 * 根据缴费地区进入首页
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		String cityCode = RequestHandler.getString(request, "cityCode");
		try{
			
			TxBanner txBanner = new TxBanner();
			txBanner.setState(1);
			List<TxBanner> list = txBannerService.getTxBannerList(txBanner);
			model.addAttribute("list", list);
			
			TxBusinessType txBusinessType = new TxBusinessType();
			txBusinessType.setCityCode(cityCode);
			txBusinessType.setGroup("billType");
			List<TxBusinessType> listType = txBusinessTypeService.getTxBusinessTypeListGroup(txBusinessType);
			model.addAttribute("listType", listType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/index/index";
	}
	/**
	 * 根据缴费地区进入首页
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toFirstPay")
	public String toFirstPay(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		Integer id = RequestHandler.getInteger(request, "id");
		try{
			TxBusinessType txBusinessType = txBusinessTypeService.getTxBusinessTypeById(id);
			
			if(txBusinessType.getIsNeedFee().intValue()==1){//需要预存金额
				String feeRule = txBusinessType.getFeeRule();
				if(StringUtils.isNotBlank(feeRule)){
					String[] rules =  feeRule.split("&");
					String gz = rules[0].substring(0, 1);
					if("M".equals(gz)){//规则型
						model.addAttribute("maxfee", super.getMoney(Long.valueOf(rules[2])) );
						model.addAttribute("minfee", super.getMoney(Long.valueOf(rules[1])));
						model.addAttribute("beishu", super.getMoney(Long.valueOf(rules[0].substring(1, rules[0].length()))));
					}else if("E".equals(gz)){//枚举型
						List<String> strFeeList = new ArrayList<String>();
						for(int i=0;i<rules.length;i++){
							if(i==0){
								strFeeList.add(super.getMoney(Long.valueOf(rules[i].substring(1, rules[i].length()))));
							}else{
								strFeeList.add(super.getMoney(Long.valueOf(rules[i])));
							}
						}
						model.addAttribute("strFee", strFeeList);
					}
					model.addAttribute("gz", gz);
				}
			}
			model.addAttribute("txBusinessType", txBusinessType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/firstPayElectricOther";
	}
	/**
	 * 查询其他地区缴费信息
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPayNumberMsg")
	public String queryPayNumberMsg(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		String cityCode = RequestHandler.getString(request, "cityCode");
		String paynumber = RequestHandler.getString(request, "paynumber");
		Integer typeId = RequestHandler.getInteger(request, "typeId");
		String fee = RequestHandler.getString(request, "fee");
		Integer isNeedFee = RequestHandler.getInteger(request, "isNeedFee");
		try{
			TxBusinessType txBusinessType = txBusinessTypeService.getTxBusinessTypeById(typeId);
			model.addAttribute("txBusinessType", txBusinessType);
			model.addAttribute("ServiceType", txBusinessType.getServiceType());
			int money2 = 0;
			if(StringUtils.isNotBlank(fee)){
				Double money1 = Double.valueOf(fee);
				BigDecimal bg = new BigDecimal(money1);
				BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
				money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			}else{
				model.addAttribute("resultCode", "E100");
				model.addAttribute("errmessage", "金额输入有误");
				return "/wx/queryfail";
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
			Map<String, Object> param = new LinkedHashMap<String, Object>();
    		param.put("MsgType", "Query");
    		param.put("TerminalID", ConfigConstants.NATIONAL_UNITY_TERMINALID);
    		param.put("TraceNo", txBusinessTypeService.getTraceNo());
    		param.put("Source", ConfigConstants.NATIONAL_UNITY_SOURCE);
    		param.put("Channel", ConfigConstants.NATIONAL_UNITY_CHANNEL);
    		param.put("CityCode", cityCode);
    		param.put("ServiceType", txBusinessType.getServiceType());
    		param.put("PayNo", paynumber);
    		param.put("PreTotalFee", money2+"");
    		param.put("BankCardNo", "");
    		if(txBusinessType.getIsNeedDate().intValue()==0){
    			param.put("AccountPeriod", "        ");
    		}else if(txBusinessType.getIsNeedDate().intValue()==1){
    			param.put("AccountPeriod", "  "+sf.format(new Date()));
    		}else if(txBusinessType.getIsNeedDate().intValue()==2){
    			param.put("AccountPeriod", sf.format(new Date())+"00");
    		}
    		param.put("CustomerType", "0");
    		param.put("KeyID", ConfigConstants.NATIONAL_UNITY_KEYID);
    		param.put("MCode", HttpUtils.getMcode(param));
    		String result = HttpUtils.sendPost(param);
    		String ResultCode = HttpUtils.getVal(result, "ResultCode");
    		String ResultInfo = HttpUtils.getVal(result, "ResultInfo");
    		System.out.println(URLDecoder.decode(ResultInfo,"UTF-8"));
    		if("00".equals(ResultCode)){
    			String DisplayInfo = HttpUtils.getVal(result, "DisplayInfo");
    			String CenterSerial = HttpUtils.getVal(result, "CenterSerial");
    			String LoopInfo = HttpUtils.getVal(result, "LoopInfo"); 
    			System.out.println(URLDecoder.decode(LoopInfo,"UTF-8"));
    			model.addAttribute("displayInfo", URLDecoder.decode(DisplayInfo,"UTF-8"));
        		model.addAttribute("loopID", URLDecoder.decode(LoopInfo,"UTF-8").split(",")[0]);
        		model.addAttribute("qf", super.getMoney(Long.valueOf(URLDecoder.decode(LoopInfo,"UTF-8").split(",")[3])));
        		model.addAttribute("lx", URLDecoder.decode(LoopInfo,"UTF-8").split(",")[2]);
        		model.addAttribute("fee", fee);
        		model.addAttribute("money", money2);
        		model.addAttribute("centerSerial", CenterSerial);
    		}else{
    			model.addAttribute("msg", URLDecoder.decode(ResultInfo,"UTF-8"));
    			model.addAttribute("resultCode", ResultCode);
    			return  "/wx/index/payFail";
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("paynumber", paynumber);
		model.addAttribute("cityCode", cityCode);
		if(isNeedFee.intValue()==0){
			return  "/wx/index/secondPayElectricOther";
		}else{
			return  "/wx/index/thirdPayElectricOther";
		}
	}
	
	/**
	 * 进入选择支付方式界面
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSelPayWayOther", method = RequestMethod.GET)
	public String toSelPayWayOther(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String PaymentInfo = RequestHandler.getString(request, "PaymentInfo");
		String fee = RequestHandler.getString(request, "fee");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String loopID = RequestHandler.getString(request, "loopID");
		String ServiceType = RequestHandler.getString(request, "ServiceType");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			int money2 = 0;
			if(StringUtils.isNotBlank(fee)){
				Double money1 = Double.valueOf(fee);
				BigDecimal bg = new BigDecimal(money1);
				BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
				money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			}else{
				model.addAttribute("resultCode", "E100");
				model.addAttribute("errmessage", "金额输入有误");
				return "/wx/queryfail";
			}
			model.addAttribute("fee", money2);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("PaymentInfo", PaymentInfo);
		model.addAttribute("paynumber", paynumber);
		model.addAttribute("cityCode", cityCode);
		model.addAttribute("loopID", loopID);
		model.addAttribute("centerSerial", centerSerial);
		model.addAttribute("ServiceType", ServiceType);
		return "/wx/index/payWayOther";
	}
	
	
	/**
	 * 银行卡列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getListCard", method = RequestMethod.GET)
	public String getListCard(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String PaymentInfo = RequestHandler.getString(request, "PaymentInfo");
		String fee = RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String loopID = RequestHandler.getString(request, "loopID");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		String ServiceType = RequestHandler.getString(request, "ServiceType");
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(txWxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("PaymentInfo", PaymentInfo);
			model.addAttribute("paynumber", paynumber);
			model.addAttribute("fee", fee);
			model.addAttribute("cityCode", cityCode);
			model.addAttribute("loopID", loopID);
			model.addAttribute("ServiceType", ServiceType);
			model.addAttribute("centerSerial", centerSerial);
			if(list!=null&&list.size()>0){
				model.addAttribute("list", list);
				return  "/wx/index/cardListOther";
			}else{//获取token
				return  "/wx/index/addCardOther";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/** 进入支付界面**/
	@RequestMapping(value = "/toPay", method = RequestMethod.GET)
	public String toPay(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		super.getJsticket(request);
		String accNo =  RequestHandler.getString(request, "accNo");
		String orderfee =  RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String loopID = RequestHandler.getString(request, "loopID");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		String serviceType = RequestHandler.getString(request, "ServiceType");
		try{
			
			
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
			String ordercode =  txPaynumberMsgService.getOrderNo().get("orderNo");
			
			otherService.setOrderMsgToSession(serviceType, accNo, txWxUser, cityCode, Integer.valueOf(orderfee), loopID, ordercode, paynumber,centerSerial,null);
			
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setAccNo(accNo);
			txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			model.addAttribute("txWxUserBankNo", txWxUserBankNo);
			model.addAttribute("ordercode", ordercode);
			model.addAttribute("token", txWxUserBankNo.getToken());
			model.addAttribute("txnTime", orderNoTime);
			model.addAttribute("orderfee", orderfee);
			model.addAttribute("accNo", accNo);
			model.addAttribute("orderfee", orderfee);
			model.addAttribute("paynumber", paynumber);
			model.addAttribute("centerSerial", centerSerial);
			if(SessionName.xzOrder.get(ordercode)==null){
				sendCodeCutter.filesMng(ordercode, orderNoTime, txWxUserBankNo, orderfee, txWxUser);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/payOther";
	}
	
	/**
	 * 获取token后台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String accNo = RequestHandler.getString(request, "accNo");
		String smsCode = RequestHandler.getString(request, "smsCode");
		String ordercode = RequestHandler.getString(request, "ordercode");
		String txnTime = RequestHandler.getString(request, "txnTime");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			if(txWxUserBankNo!=null&&StringUtils.isNotBlank(ordercode)&&StringUtils.isNotBlank(smsCode)){
				
				Map<String,String> mapsss = SessionName.maporder.get(ordercode);
				
				
				if(SessionName.xzOrder.get(ordercode)==null){
					SessionName.xzOrder.put(ordercode, ordercode);
					Long id = otherService.createOrderOther(mapsss, null, txWxUser, null,txWxUserBankNo.getAccNo(),3,"002",null);
					
					Map<String, String> rspData = otherService.pay(ordercode, txWxUserBankNo, smsCode, txWxUser, txnTime, "002",id);
					if(("00").equals(rspData.get("respCode"))){
						writeSuccessMsg("成功", "", response);
					}else{
						
						writeErrorMsg("fail", rspData.get("respMsg"), response);
					}
				}
			}else{
				writeErrorMsg("error", "支付失败，数据异常，，请联系客服：010-96199", response);
			}
		}catch(Exception e){
			writeErrorMsg("error", "支付失败，系统异常，请联系客服：010-96199", response);
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 添加银行卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddCard", method = RequestMethod.GET)
	public String toAddCard(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String ordercode = RequestHandler.getString(request, "ordercode");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String loopID = RequestHandler.getString(request, "loopID");
		String ServiceType = RequestHandler.getString(request, "ServiceType");
		String fee = RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		
		model.addAttribute("centerSerial", centerSerial);
		model.addAttribute("PaymentInfo", ordercode);
		model.addAttribute("fee", fee);
		model.addAttribute("paynumber", paynumber);
		model.addAttribute("cityCode", cityCode);
		model.addAttribute("loopID", loopID);
		model.addAttribute("ServiceType", ServiceType);
		return  "/wx/index/addCardOther";
	}
	
	
	/**
	 * 添加信用卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCard", method = RequestMethod.GET)
	public String addCard(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String accNo = RequestHandler.getString(request, "accNo");
		String zfOrderNo = RequestHandler.getString(request, "zfOrderNo");
		String orderfee = RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String loopID = RequestHandler.getString(request, "loopID");
		String serviceType = RequestHandler.getString(request, "ServiceType");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			Map<String,String> map = txPaynumberMsgService.getOrderNo();
			
			if(!StringUtils.isNotBlank(zfOrderNo)){
				zfOrderNo = map.get("orderNo");
			}
			//先写入库
			TxWxOrder txWxOrder = new TxWxOrder();
			txWxOrder.setAccNo(accNo);
			txWxOrder.setWxUserId(txWxUser.getId());
			txWxOrder.setCreateTime(new Date());
			txWxOrder.setOrderCode(map.get("orderNo"));
			txWxOrder.setStyle(0);
			txWxOrder.setMoney(0*100L);
			txWxOrder.setTwoPromoterId(txWxUser.getTwoPromoterId());
			txWxOrder.setTwoPromoterName(txWxUser.getTwoPromoterName());
			txWxOrder.setWxUserName(txWxUser.getRealName());
			txWxOrder.setPromoterId(txWxUser.getPromoterId());
			txWxOrder.setZfOrderNo(zfOrderNo);
			txWxOrder.setZfOrderFee(Integer.valueOf(orderfee));
			txWxOrderService.insertTxWxOrder(txWxOrder);
			otherService.setOrderMsgToSession(serviceType, accNo, txWxUser, cityCode, Integer.valueOf(orderfee), loopID, zfOrderNo, paynumber,centerSerial,null);
			String html = otherService.getUnionPayToken(map.get("orderNo"), map.get("orderNoTime"), accNo, txWxUser);
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.print(html);
			pw.flush();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 获取token前台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_fronturl_token_other")
	public String union_fronturl_token_other(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		try{
			String orderId = indexService.frontTransUrl(request);
			if(StringUtils.isNotBlank(orderId)){
				TxWxOrder txWxOrder = txWxOrderService.getTxWxOrderByCode(orderId);
    			if(txWxOrder!=null&&txWxOrder.getId()>0){
    				model.addAttribute("ordercode", txWxOrder.getZfOrderNo());
    				model.addAttribute("txnTime", txWxOrder.getTxnTime());
    				model.addAttribute("orderfee", txWxOrder.getZfOrderFee());
    				model.addAttribute("accNo", txWxOrder.getAccNo());
    				TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(txWxOrder.getAccNo());
    				model.addAttribute("endCode", txWxUserBankNo.getEndCode());
    				model.addAttribute("token", txWxUserBankNo.getToken());
    				
    				TxWxUser wxUser = txWxUserService.getTxWxUserById(txWxOrder.getWxUserId());
    				request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, wxUser.getRealName());
					request.getSession().setAttribute(SessionName.ADMIN_USER_ID, wxUser.getId());
					request.getSession().setAttribute(SessionName.ADMIN_USER, wxUser);
					if(SessionName.xzOrder.get(orderId)==null){
						sendCodeCutter.filesMng(txWxOrder.getZfOrderNo(), txWxOrder.getTxnTime(), txWxUserBankNo, txWxOrder.getZfOrderFee()+"", wxUser);
					}
    				return "/wx/index/payOther";
    			}
			}else{
				logger.info("异常====");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	/**
	 * 支付回调（消费）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay_backurl_other")
	public String pay_backurl_other(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		otherService.paybackUrlOther(request,txBusinessTypeService.getTraceNo());
		return  null;
	}
	
	/**
	 * 佣金
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyYongjin")
	public String toMyYongjin(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		try{
			Long money = 0L;
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//获取售电佣金
			TxPayOrder txPayOrder = new TxPayOrder();
			txPayOrder.setPromoterId(txWxUser.getId());
			txPayOrder.setState(1);
			txPayOrder = txPayOrderService.getTxPayOrderSumMoney(txPayOrder);
			money = money + txPayOrder.getOneRate();
			//获取提现佣金
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			txSellingOrder.setPromoterId(txWxUser.getId());
			txSellingOrder.setState(1);
			txSellingOrder.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getOneRate();
			
			TxSellingOrder txSellingOrder1 = new TxSellingOrder();
			txSellingOrder1.setTwoPromoterId(txWxUser.getId());
			txSellingOrder1.setState(1);
			txSellingOrder1.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getTwoRate();
			
			model.addAttribute("totalFee", super.getMoney(money));
			//获取已经提取金额
			TxRecord txRecord = new TxRecord();
			txRecord.setState(1);
			txRecord.setUserId(txWxUser.getId());
			txRecord = txRecordService.getTxRecordMoney(txRecord);
			
			model.addAttribute("recordFee", super.getMoney(txRecord.getFee()));
			
			//申请金额
			TxApplay txApplay = new TxApplay();
			txApplay.setUserId(txWxUser.getId());
			txApplay.setState(0);
			txApplay = txApplayService.getTxApplayMoney(txApplay);
			
			model.addAttribute("applyFee", super.getMoney(txApplay.getFee()));
			model.addAttribute("yuFee", super.getMoney((money-txRecord.getFee()-txApplay.getFee())));
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			model.addAttribute("week", cal.get(Calendar.DAY_OF_WEEK)-1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/index/yongjin";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUnionpay", method = RequestMethod.GET)
	public String toUnionpay(HttpServletRequest request, HttpServletResponse response, Model model){
		String fee = RequestHandler.getString(request, "fee");
		String orderfee =  RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		String cityCode = RequestHandler.getString(request, "cityCode");
		String loopID = RequestHandler.getString(request, "loopID");
		String centerSerial = RequestHandler.getString(request, "centerSerial");
		String serviceType = RequestHandler.getString(request, "ServiceType");
		try{
			
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			
			String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
			String ordercode =  txPaynumberMsgService.getOrderNo().get("orderNo");
			
			otherService.setOrderMsgToSession(serviceType, null, txWxUser, cityCode, Integer.valueOf(orderfee), loopID, ordercode, paynumber,centerSerial,null);
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String html = otherService.toUnionpay(ordercode, fee,orderNoTime,txWxUser);
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.print(html);
			pw.flush();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * wep支付前台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_fronturl_other")
	public String union_fronturl_wap(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		try{
			//获取订单流水号
			String ordercode = otherService.union_fronturl_other(request,txBusinessTypeService.getTraceNo());
			if(StringUtils.isNotBlank(ordercode)){
				
				TxPayOrder hOrder = txPayOrderService.getTxPayOrderByOrderNumber(ordercode);
				
				Map<String,String> mapssss = SessionName.maporder.get(ordercode);
				model.addAttribute("shopCode", mapssss.get("shopCode"));
				
//				SimpleDateFormat sf11 = new SimpleDateFormat("yyyyMMddHHmmss");
//				Map<String, String> rspData = indexService.queryPay_wap(ordercode, sf11.format(hOrder.getCreateTime()));
//				System.out.println(rspData.get("respCode"));
//				System.out.println(rspData.get("origRespCode"));
//				if(rspData!=null&&"00".equals(rspData.get("respCode"))&&"00".equals(rspData.get("origRespCode"))){
//					String transaction_id = rspData.get("queryId");
					model.addAttribute("transaction_id", hOrder.getQueryNumber());
//				}else{
//					model.addAttribute("msg", "交易失败");
//					model.addAttribute("resultCode", rspData.get("origRespCode"));
//					return "/wx/index/payFail";
//				}
				
				request.setAttribute("customerNumber", hOrder.getOrderNumber());
				if("3100".equals(mapssss.get("shopCode"))){
					request.setAttribute("shopCode", "3102");
				}else{
					request.setAttribute("shopCode", "3202");
				}
				request.setAttribute("money", super.getMoney(hOrder.getRealFee()));
				request.setAttribute("realmoney", super.getMoney(hOrder.getRealFee()));
				request.setAttribute("orderId", ordercode);
			
			}else{
				model.addAttribute("msg", "交易失败");
				return "/wx/index/payFail";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/wx/index/paysuccess";
	}
	
	/**
	 * 银联跳转支付回调（无token）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_backurl_other")
	public String union_backurl_wap(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		otherService.union_backurl_other(request,txBusinessTypeService.getTraceNo());
		return  null;
	}
	/**
	 * 分润提现回到
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fenrun_back_url")
	public String fenrun_back_url(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		otherService.fenrun_back_url(request,txBusinessTypeService.getTraceNo());
		return  null;
	}
	
	@RequestMapping(value = "/toApply", method = RequestMethod.GET)
	public String toApply(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String yuFee = RequestHandler.getString(request, "yuFee");
		model.addAttribute("yuFee", yuFee);
		return "/wx/index/apply";
	}
	
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public String apply(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String fee = RequestHandler.getString(request, "fee");
		try{
			Long money = 0L;
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//获取售电佣金
			TxPayOrder txPayOrder = new TxPayOrder();
			txPayOrder.setPromoterId(txWxUser.getId());
			txPayOrder.setState(1);
			txPayOrder = txPayOrderService.getTxPayOrderSumMoney(txPayOrder);
			money = money + txPayOrder.getOneRate();
			//获取提现佣金
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			txSellingOrder.setPromoterId(txWxUser.getId());
			txSellingOrder.setState(1);
			txSellingOrder.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getOneRate();
			
			TxSellingOrder txSellingOrder1 = new TxSellingOrder();
			txSellingOrder1.setTwoPromoterId(txWxUser.getId());
			txSellingOrder1.setState(1);
			txSellingOrder1.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getTwoRate();
			
			model.addAttribute("totalFee", super.getMoney(money));
			//获取已经提取金额
			TxRecord txRecord = new TxRecord();
			txRecord.setState(1);
			txRecord.setUserId(txWxUser.getId());
			txRecord = txRecordService.getTxRecordMoney(txRecord);
			
			model.addAttribute("recordFee", super.getMoney(txRecord.getFee()));
			
			//申请金额
			TxApplay txApplay = new TxApplay();
			txApplay.setUserId(txWxUser.getId());
			txApplay.setState(0);
			txApplay = txApplayService.getTxApplayMoney(txApplay);
			
			Long yu = money - txRecord.getFee() - txApplay.getFee();
			
			Double money1 = Double.valueOf(fee);
			BigDecimal bg = new BigDecimal(money1);
			BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
			Long money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			if(yu>=money2){
				String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
				String ordercode =  txPaynumberMsgService.getOrderNo().get("orderNo");
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
				TxApplay txApplay2 = new TxApplay();
				txApplay2.setUserId(txWxUser.getId());
				txApplay2.setBatch(sf.format(new Date()));
				txApplay2.setCreateTime(new Date());
				txApplay2.setFee(money2);
				txApplay2.setName(txWxUser.getRealName());
				txApplay2.setState(0);
				txApplay2.setOrderCode(ordercode);
				txApplayService.insertTxApplay(txApplay2);
				//开始提现
				if(txApplay2.getId().intValue()>0){
					applyCutter.filesMng(money2, txWxUser,ordercode,orderNoTime);
				}
			}
			writeSuccessMsg("成功", "", response);
		}catch(Exception e){
			writeErrorMsg("error", "提现失败，系统异常，请联系客服：010-96199", response);
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/toApplyDetail", method = RequestMethod.GET)
	public String toApplyDetail(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			//售电佣金
			TxPayOrder txPayOrder = new TxPayOrder();
			txPayOrder.setPromoterId(txWxUser.getId());
			txPayOrder.setState(1);
			txPayOrder.setOneRateFlag("0");
			txPayOrder.setSort("createTime");
			txPayOrder.setOrder("desc");
			List<TxPayOrder> listPay = txPayOrderService.getTxPayOrderListMsg(txPayOrder);
			//获取提现佣金
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			txSellingOrder.setPromoterId(txWxUser.getId());
			txSellingOrder.setState(1);
			txSellingOrder.setRefundState(1);
			txSellingOrder.setSort("createTime");
			txSellingOrder.setOrder("desc");
			List<TxSellingOrder> listSell1 = txSellingOrderService.getTxSellingOrderListMsg(txSellingOrder);
			
			TxSellingOrder txSellingOrder1 = new TxSellingOrder();
			txSellingOrder1.setTwoPromoterId(txWxUser.getId());
			txSellingOrder1.setState(1);
			txSellingOrder1.setRefundState(1);
			txSellingOrder1.setSort("createTime");
			txSellingOrder1.setOrder("desc");
			List<TxSellingOrder> listSell2 = txSellingOrderService.getTxSellingOrderListMsg(txSellingOrder1);
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<YongJinModel> list = new ArrayList<YongJinModel>();
			if(listPay!=null&&listPay.size()>0){
				for(TxPayOrder pay:listPay){
					YongJinModel yongJinModel = new YongJinModel();
					yongJinModel.setFee(super.getMoney(pay.getFee()));
					yongJinModel.setNickName(pay.getNickName());
					yongJinModel.setTime(sf.format(pay.getCreateTime()));
					yongJinModel.setYongjin(super.getMoney(Long.valueOf(pay.getOneRate())));
					list.add(yongJinModel);
				}
			}
			if(listSell1!=null&&listSell1.size()>0){
				for(TxSellingOrder sell:listSell1){
					YongJinModel yongJinModel = new YongJinModel();
					yongJinModel.setFee(super.getMoney(sell.getMoney()));
					yongJinModel.setNickName(sell.getNickName());
					yongJinModel.setTime(sf.format(sell.getCreateTime()));
					yongJinModel.setYongjin(super.getMoney(Long.valueOf(sell.getOneRate())));
					list.add(yongJinModel);
				}
			}
			if(listSell2!=null&&listSell2.size()>0){
				for(TxSellingOrder sell:listSell2){
					YongJinModel yongJinModel = new YongJinModel();
					yongJinModel.setFee(super.getMoney(sell.getMoney()));
					yongJinModel.setNickName(sell.getNickName());
					yongJinModel.setTime(sf.format(sell.getCreateTime()));
					yongJinModel.setYongjin(super.getMoney(Long.valueOf(sell.getTwoRate())));
					list.add(yongJinModel);
				}
			}
			model.addAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/applyDetail";
	}
	@RequestMapping(value = "/toMyTeam", method = RequestMethod.GET)
	public String toMyTeam(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			Long money = 0L;
			Long moneyAll = 0L;
			Long chengjiao = 0L;
			//查粉丝
			TxWxUser txWxUserF = new TxWxUser();
			txWxUserF.setPromoterId(txWxUser.getId());
			txWxUserF.setState(1);
			txWxUserF.setCheckState(1);
			int countF = txWxUserService.getTxWxUserListCount(txWxUserF);
			//获取售电佣金
			TxPayOrder txPayOrder = new TxPayOrder();
			txPayOrder.setPromoterId(txWxUser.getId());
			txPayOrder.setState(1);
			txPayOrder = txPayOrderService.getTxPayOrderSumMoney(txPayOrder);
			money = money + txPayOrder.getOneRate();
			moneyAll = moneyAll + txPayOrder.getFee();
			//获取提现佣金
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			txSellingOrder.setPromoterId(txWxUser.getId());
			txSellingOrder.setState(1);
			txSellingOrder.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getOneRate();
			moneyAll = moneyAll + txSellingOrder.getMoney();
			
			model.addAttribute("moneySelf", super.getMoney(money));
			
			TxSellingOrder txSellingOrder1 = new TxSellingOrder();
			txSellingOrder1.setTwoPromoterId(txWxUser.getId());
			txSellingOrder1.setState(1);
			txSellingOrder1.setRefundState(1);
			txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder);
			money = money + txSellingOrder.getTwoRate();
			moneyAll = moneyAll + txSellingOrder.getMoney();
			
			
			model.addAttribute("money", super.getMoney(money));
			model.addAttribute("countF", countF);
			model.addAttribute("moneyAll",super.getMoney(moneyAll));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/myTeam";
	}
	@RequestMapping(value = "/toMyFans", method = RequestMethod.GET)
	public String toMyFans(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			//查粉丝
			TxWxUser txWxUserF = requst2Bean(request,TxWxUser.class);;
			txWxUserF.setPromoterId(txWxUser.getId());
			txWxUserF.setState(1);
			txWxUserF.setCheckState(1);
			int allCount = txWxUserService.getTxWxUserListCount(txWxUserF);
			PageHelper.offsetPage(txWxUserF.getOffset(), 10);
			PageHelper.orderBy("a.create_time desc ");
			List<TxWxUser> list = txWxUserService.getTxWxUserList(txWxUserF);
			
			
			model.addAttribute("list",list);
			model.addAttribute("page",txWxUserF.getOffset());
			model.addAttribute("allCount",allCount);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/myTeam";
	}
}
