package com.wx.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tx.task.service.SendCodeCutter;
import com.tx.txBanner.model.TxBanner;
import com.tx.txBanner.service.TxBannerService;
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txPaynumberMsg.service.TxPaynumberMsgService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.IndexService;
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
	private IndexService indexService = null;
	
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
		try{
			TxBusinessType txBusinessType = txBusinessTypeService.getTxBusinessTypeById(typeId);
			
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
    		String DisplayInfo = HttpUtils.getVal(result, "DisplayInfo");
    		model.addAttribute("displayInfo", URLDecoder.decode(DisplayInfo,"UTF-8"));
    		model.addAttribute("paynumber", paynumber);
    		model.addAttribute("txBusinessType", txBusinessType);
    		model.addAttribute("fee", fee);
    		model.addAttribute("money", super.getMoney(Long.valueOf(money2)));
    		System.out.println(URLDecoder.decode(ResultInfo,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/index/thirdPayElectricOther";
	}
	
	/**
	 * 进入选择支付方式界面
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSelPayWay", method = RequestMethod.GET)
	public String toSelPayWay(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String PaymentInfo = RequestHandler.getString(request, "PaymentInfo");
		String fee = RequestHandler.getString(request, "fee");
		String paynumber = RequestHandler.getString(request, "paynumber");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("PaymentInfo", PaymentInfo);
		model.addAttribute("fee", fee);
		model.addAttribute("paynumber", paynumber);
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
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(txWxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("PaymentInfo", PaymentInfo);
			model.addAttribute("paynumber", paynumber);
			model.addAttribute("fee", fee);
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
		try{
			
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
			String ordercode =  txPaynumberMsgService.getOrderNo().get("orderNo");
			
			indexService.setOrderMsgToSession(null, txWxUser, null, Integer.valueOf(orderfee), super.getIp(request), ordercode, paynumber);
			
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
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			if(txWxUserBankNo!=null&&StringUtils.isNotBlank(ordercode)&&StringUtils.isNotBlank(smsCode)){
				
				Map<String,String> mapsss = SessionName.maporder.get(ordercode);
				
				
				if(SessionName.xzOrder.get(ordercode)==null){
					SessionName.xzOrder.put(ordercode, ordercode);
					Long id = indexService.createOrder(mapsss, null, txWxUser, null,txWxUserBankNo.getAccNo(),3,"002",null);
					
					Map<String, String> rspData = indexService.pay(ordercode, txWxUserBankNo, smsCode, txWxUser, txnTime, "002",id);
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
}
