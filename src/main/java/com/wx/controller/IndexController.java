package com.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.base.controller.BaseController;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.tx.task.service.SendCodeCutter;
import com.tx.task.service.SendCodeDFCutter;
import com.tx.txBanner.model.TxBanner;
import com.tx.txBanner.service.TxBannerService;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txPayOrder.model.TxPayOrder;
import com.tx.txPayOrder.service.TxPayOrderService;
import com.tx.txPaynumberMsg.model.TxPaynumberMsg;
import com.tx.txPaynumberMsg.service.TxPaynumberMsgService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxOrder.service.TxWxOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.IndexService;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{
	
	@Autowired
	private TxBannerService txBannerService = null;
	@Autowired
	private IndexService indexService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxPaynumberMsgService txPaynumberMsgService = null;
	@Autowired
	private TxBusinessTypeService txBusinessTypeService = null;
	@Autowired
	private SendCodeCutter sendCodeCutter = null;
	@Autowired
	private TxWxOrderService txWxOrderService = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private TxPayOrderService txPayOrderService = null;
	@Autowired
	private SendCodeDFCutter sendCodeDFCutter = null;
	
	
	
	/**
	 * 首页
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toRegInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = (String)request.getSession().getAttribute(SessionName.SESSION_OPENID);
		try{
			TxBanner txBanner = new TxBanner();
			txBanner.setState(1);
			List<TxBanner> list = txBannerService.getTxBannerList(txBanner);
			model.addAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("openId", openId);
		return "/wx/index/index";
	}
	/**
	 * 首页
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toElectric", method = RequestMethod.GET)
	public String toElectric(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String cityCode = RequestHandler.getString(request, "cityCode");
		String billType = RequestHandler.getString(request, "billType");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			TxPaynumberMsg txPaynumberMsg = new TxPaynumberMsg();
			txPaynumberMsg.setUserId(txWxUser.getId());
			txPaynumberMsg.setBillType(billType);
			txPaynumberMsg.setStatus(1);
			List<TxPaynumberMsg> list = txPaynumberMsgService.getTxPaynumberMsgList(txPaynumberMsg);
			if(list!=null&&list.size()>0){
				model.addAttribute("list", list);
				return "/wx/index/payNumberList";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("billType", billType);
		model.addAttribute("cityCode", cityCode);
		return "/wx/index/firstPayElectric";
	}
	
	/**
	 * 别的地址充电
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOther", method = RequestMethod.GET)
	public String toOther(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String cityCode = RequestHandler.getString(request, "cityCode");
		String billType = RequestHandler.getString(request, "billType");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("billType", billType);
		model.addAttribute("cityCode", cityCode);
		return "/wx/index/firstPayElectric";
	}

	/**
	 * 首次查询缴费信息
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryPayNumberMsg", method = RequestMethod.GET)
	public String queryPayNumberMsg(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String paynumber = RequestHandler.getString(request, "paynumber");
		String shopCode = RequestHandler.getString(request, "shopCode");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			Map<String, Object> map = txBusinessTypeService.queryAccountUser(shopCode, paynumber, 0, super.getIp(request));
			if("ok".equals((String)map.get("message"))){
				Map<String, Object> mapresult = null;
				String resultInfo = (String)map.get("resultInfo");
				Integer QFee = Integer.valueOf((String)map.get("QFee"));
				if("3202".equals(shopCode)){
					mapresult = txBusinessTypeService.getCustomerMsg1(paynumber, resultInfo, QFee);
				}else{
					mapresult = txBusinessTypeService.getCustomerMsgCB(paynumber, resultInfo,QFee);
				}
				TxPaynumberMsg txPaynumberMsg = new TxPaynumberMsg();
				txPaynumberMsg.setUserId(txWxUser.getId());
				txPaynumberMsg.setBillType("002");
				txPaynumberMsg.setPayNumber(paynumber);
				txPaynumberMsg.setStatus(1);
				List<TxPaynumberMsg> list = txPaynumberMsgService.getTxPaynumberMsgList(txPaynumberMsg);
				if(list!=null&&list.size()>0){//修改信息
					txPaynumberMsg = list.get(0);
					txPaynumberMsg.setCreateTime(new Date());
					txPaynumberMsg.setUserAddress((String)mapresult.get("userAddress"));
					txPaynumberMsg.setNickName((String)mapresult.get("username"));//客户名称
		        	txPaynumberMsg.setTradeName((String)mapresult.get("username1"));
		        	txPaynumberMsgService.updateTxPaynumberMsgById(txPaynumberMsg);
				}else{
					if("3202".equals(shopCode)){
						txPaynumberMsg.setRemark1("3202");
		        	}else if("3102".equals(shopCode)){
		        		txPaynumberMsg.setRemark1("3102");
		        	}
					txPaynumberMsg.setCreateTime(new Date());
					txPaynumberMsg.setUserAddress((String)mapresult.get("userAddress"));
					txPaynumberMsg.setNickName((String)mapresult.get("username"));//客户名称
		        	txPaynumberMsg.setTradeName((String)mapresult.get("username1"));
					txPaynumberMsgService.insertTxPaynumberMsg(txPaynumberMsg);
				}
				
				model.addAttribute("shopCode", shopCode);
				model.addAttribute("mapresult", mapresult);
			}else{
				model.addAttribute("shopCode", shopCode);
				model.addAttribute("resultCode", map.get("resultCode"));
				model.addAttribute("errmessage", map.get("resultInfo"));
				return "/wx/index/queryfail";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/wx/index/secondPayElectric";
	}
	/**
	 * 再次查询缴费信息
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryPayNumberMsgTwo", method = RequestMethod.GET)
	public String queryPayNumberMsgTwo(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String paynumber = RequestHandler.getString(request, "paynumber");
		String shopCode = RequestHandler.getString(request, "shopCode");
		String money = RequestHandler.getString(request, "money");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			
			int money2 = 0;
			if(StringUtils.isNotBlank(money)){
				Double money1 = Double.valueOf(money);
				BigDecimal bg = new BigDecimal(money1);
				BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
				money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			}else{
				model.addAttribute("resultCode", "E100");
				model.addAttribute("errmessage", "金额输入有误");
				return "/wx/queryfail";
			}
			
			Map<String, Object> map = txBusinessTypeService.queryPayMessage(shopCode, paynumber,money2,super.getIp(request));
			if("ok".equals((String)map.get("message"))){
				Map<String, Object> mapresult = null;
				String resultInfo = (String)map.get("resultInfo");
				Integer QFee = Integer.valueOf((String)map.get("QFee"));
				if("3202".equals(shopCode)){
					mapresult = txBusinessTypeService.getCustomerMsg1(paynumber, resultInfo, QFee);
				}else{
					mapresult = txBusinessTypeService.getCustomerMsgCB(paynumber, resultInfo,QFee);
				}
				model.addAttribute("mapresult", mapresult);
				model.addAttribute("PaymentInfo", map.get("PaymentInfo"));
				indexService.setOrderMsgToSession(mapresult, txWxUser, shopCode, money2, super.getIp(request), (String)map.get("PaymentInfo"),paynumber);
			}else{
				model.addAttribute("shopCode", shopCode);
				model.addAttribute("resultCode", map.get("resultCode"));
				model.addAttribute("errmessage", map.get("resultInfo"));
				return "/wx/index/queryfail";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("shopCode", shopCode);
		model.addAttribute("money", money);
		return "/wx/index/thirdPayElectric";
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
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("PaymentInfo", PaymentInfo);
		return "/wx/index/payWay";
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
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(txWxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("PaymentInfo", PaymentInfo);
			if(list!=null&&list.size()>0){
				model.addAttribute("list", list);
				return  "/wx/index/cardList";
			}else{//获取token
				return  "/wx/index/addCard";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	/**
	 * 银行卡列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getListCardDF", method = RequestMethod.GET)
	public String getListCardDF(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Double money = RequestHandler.getDouble(request, "money");
		try{
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(txWxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("backCard", backCard);
			model.addAttribute("sel_time", sel_time);
			model.addAttribute("money", money);
			if(list!=null&&list.size()>0){
				model.addAttribute("list", list);
				return  "/wx/index/cardListDF";
			}else{//获取token
				return  "/wx/index/addCardDF";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	/** 进入支付界面**/
	@RequestMapping(value = "toPay", method = RequestMethod.GET)
	public String toPay(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		super.getJsticket(request);
		String accNo =  RequestHandler.getString(request, "accNo");
		String ordercode =  RequestHandler.getString(request, "ordercode");
		try{
			
			Map<String,String> mapssss = SessionName.maporder.get(ordercode);
			String orderfee =  mapssss.get("money");
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
			
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setAccNo(accNo);
			txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			model.addAttribute("txWxUserBankNo", txWxUserBankNo);
			model.addAttribute("ordercode", ordercode);
			model.addAttribute("token", txWxUserBankNo.getToken());
			model.addAttribute("txnTime", orderNoTime);
			model.addAttribute("orderfee", orderfee);
			model.addAttribute("accNo", accNo);
			if(SessionName.xzOrder.get(ordercode)==null){
				sendCodeCutter.filesMng(ordercode, orderNoTime, txWxUserBankNo, orderfee, txWxUser);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/pay";
	}
	/** 进入支付界面**/
	@RequestMapping(value = "toPayDF", method = RequestMethod.GET)
	public String toPayDF(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		super.getJsticket(request);
		String accNo =  RequestHandler.getString(request, "accNo");
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Double money = RequestHandler.getDouble(request, "money");
		try{
			
			BigDecimal bg = new BigDecimal(money);
			BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
			int money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			String orderNoTime = txPaynumberMsgService.getOrderNo().get("orderNoTime");
			
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setAccNo(accNo);
			txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			model.addAttribute("txWxUserBankNo", txWxUserBankNo);
			model.addAttribute("token", txWxUserBankNo.getToken());
			model.addAttribute("txnTime", orderNoTime);
			model.addAttribute("accNo", accNo);
			model.addAttribute("backCard", backCard);
			model.addAttribute("sel_time", sel_time);
			model.addAttribute("money", money);
			//创建订单
			sendCodeDFCutter.filesMng(sel_time, backCard, accNo, txWxUserBankNo, money2, txWxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/payDF";
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
	
	/** 发验证码 **/
	@RequestMapping(value = "/vercode", method = RequestMethod.POST)
	public String vercode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String txnAmt =  RequestHandler.getString(request, "txnAmt");
		String orderId =  RequestHandler.getString(request, "orderId");
		String token =  RequestHandler.getString(request, "token");
		String txnTime =  RequestHandler.getString(request, "txnTime");
		try{
			if(txnAmt!=null){
				TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
				if(SessionName.xzOrder.get(orderId)==null){
					boolean b = indexService.vercode(orderId, txnTime, token, txnAmt, txWxUser);
					if(b){
						writeSuccessMsg("发送成功", null, response);
					}else{
						writeErrorMsg("发送失败", "", response);
					}
				}else{
					writeErrorMsg("重复发送，发送失败", "", response);
				}
			}else{
				writeErrorMsg("发送失败", "", response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
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
		
		model.addAttribute("PaymentInfo", ordercode);
		return  "/wx/index/addCard";
	}
	/**
	 * 添加银行卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddCardDF", method = RequestMethod.GET)
	public String toAddCardDF(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Double money = RequestHandler.getDouble(request, "money");
		model.addAttribute("sel_time", sel_time);
		model.addAttribute("backCard", backCard);
		model.addAttribute("money", money);
		return  "/wx/index/addCard";
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
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			Map<String,String> map = txPaynumberMsgService.getOrderNo();
			
			Map<String,String> mapssss = SessionName.maporder.get(zfOrderNo);
			String orderfee =  mapssss.get("money");
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
			
			String html = indexService.getUnionPayToken(map.get("orderNo"), map.get("orderNoTime"), accNo, txWxUser);
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
	 * 添加信用卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCardDF", method = RequestMethod.GET)
	public String addCardDF(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String accNo = RequestHandler.getString(request, "accNo");
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Double money = RequestHandler.getDouble(request, "money");
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			
			BigDecimal bg = new BigDecimal(money);
			BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
			int money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			
			String orderNo = indexService.getOrderNo().get("orderNo");
			String orderNoTime = indexService.getOrderNo().get("orderNoTime");
			String orderNo1 = indexService.getOrderNo().get("orderNo");
			String orderNoTime1 = indexService.getOrderNo().get("orderNoTime");
			//先写入库
			TxWxOrder txWxOrder = new TxWxOrder();
			txWxOrder.setAccNo(accNo);
			txWxOrder.setWxUserId(txWxUser.getId());
			txWxOrder.setCreateTime(new Date());
			txWxOrder.setOrderCode(orderNo);
			txWxOrder.setStyle(0);
			txWxOrder.setMoney(0*100L);
			txWxOrder.setXfFlg(money+"");
			txWxOrder.setTxnTime(orderNoTime1);
			txWxOrder.setXfSettleDate(sel_time+"");
			txWxOrder.setXfState(backCard);
			txWxOrder.setTwoPromoterId(txWxUser.getTwoPromoterId());
			txWxOrder.setTwoPromoterName(txWxUser.getTwoPromoterName());
			txWxOrder.setWxUserName(txWxUser.getRealName());
			txWxOrder.setPromoterId(txWxUser.getPromoterId());
			txWxOrder.setZfOrderFee(money2);
			txWxOrder.setZfOrderNo(orderNo1);
			txWxOrderService.insertTxWxOrder(txWxOrder);
			
			String html = indexService.getUnionPayTokenDF(orderNo, orderNoTime, accNo, txWxUser);
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
	@RequestMapping(value = "/union_fronturl_token")
	public String unionpaytoken_fronturl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
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
    				return "/wx/index/pay";
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
	 * 获取token前台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_fronturl_token_df")
	public String union_fronturl_token_df(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		try{
			String orderId = indexService.frontTransUrl(request);
			if(StringUtils.isNotBlank(orderId)){
				TxWxOrder txWxOrder = txWxOrderService.getTxWxOrderByCode(orderId);
				if(txWxOrder!=null&&txWxOrder.getId()>0){
					model.addAttribute("txnTime", txWxOrder.getTxnTime());
					model.addAttribute("accNo", txWxOrder.getAccNo());
					model.addAttribute("sel_time", txWxOrder.getXfSettleDate());
					model.addAttribute("money", txWxOrder.getXfFlg());
					model.addAttribute("backCard", txWxOrder.getXfState());
					
					TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(txWxOrder.getAccNo());
					model.addAttribute("endCode", txWxUserBankNo.getEndCode());
					model.addAttribute("token", txWxUserBankNo.getToken());
					model.addAttribute("txWxUserBankNo", txWxUserBankNo);
					
					TxWxUser wxUser = txWxUserService.getTxWxUserById(txWxOrder.getWxUserId());
					request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, wxUser.getRealName());
					request.getSession().setAttribute(SessionName.ADMIN_USER_ID, wxUser.getId());
					request.getSession().setAttribute(SessionName.ADMIN_USER, wxUser);
					//创建订单
					sendCodeDFCutter.filesMng(Integer.valueOf(txWxOrder.getXfSettleDate()), txWxOrder.getXfState(), txWxOrder.getAccNo(), txWxUserBankNo, txWxOrder.getZfOrderFee(), wxUser);
					return "/wx/index/payDF";
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
	 * 获取token后台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_backurl_token")
	public String unionpaytoken_backurl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		indexService.backTransUrl(request);
		return  null;
	}
	
	/**
	 * 获取token后台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_backurl_token_df")
	public String union_backurl_token_df(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		indexService.backTransUrl(request);
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
	@RequestMapping(value = "/pay_backurl")
	public String pay_backurl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		indexService.paybackUrl(request);
		return  null;
	}
	
	
	/**
	 * 银联退费回调（有token）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_refundpay_backurl")
	public String union_refundpay_backurl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		indexService.union_refundpay_backurl(request);
		return  null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/paySuccess", method = RequestMethod.GET)
	public String paySuccess1(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String ordercode =  RequestHandler.getString(request, "ordercode");
		String txnTime =  RequestHandler.getString(request, "txnTime");
		try{
			//获取订单流水号
			
			TxPayOrder hOrder = txPayOrderService.getTxPayOrderByOrderNumber(ordercode);
			
			Map<String,String> mapssss = SessionName.maporder.get(ordercode);
			model.addAttribute("shopCode", mapssss.get("shopCode"));
			Map<String, String> rspData = indexService.queryPay(ordercode, txnTime);
			System.out.println(rspData.get("respCode"));
			System.out.println(rspData.get("origRespCode"));
			if(rspData!=null&&"00".equals(rspData.get("respCode"))&&"00".equals(rspData.get("origRespCode"))){
				String transaction_id = rspData.get("queryId");
				model.addAttribute("transaction_id", transaction_id);
			}else{
				model.addAttribute("msg", "交易失败");
				model.addAttribute("resultCode", rspData.get("origRespCode"));
				//修改交易订单状态
				if(hOrder!=null){
					hOrder.setState(0);
					txPayOrderService.updateTxPayOrderById(hOrder);
				}
				return "/wx/unionpay/payFail";
			}
			
			request.setAttribute("customerNumber", hOrder.getOrderNumber());
			request.setAttribute("money", super.getMoney(hOrder.getRealFee()));
			request.setAttribute("realmoney", super.getMoney(hOrder.getRealFee()));
			request.setAttribute("orderId", ordercode);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/wx/index/paysuccess";
	}
	
	/** 支付失败界面**/
	@RequestMapping(value = "payFail", method = RequestMethod.GET)
	public String payFail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		super.getJsticket(request);
		String msg =  RequestHandler.getString(request, "msg");
		try{
			model.addAttribute("msg", URLDecoder.decode(msg,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/index/payFail";
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
		String merOrderId = RequestHandler.getString(request, "PaymentInfo");
		try{
			
			Map<String,String> mapsss = SessionName.maporder.get(merOrderId);
			
			TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String html = indexService.toUnionpay(merOrderId, mapsss.get("money"),sf.format(new Date()),txWxUser);
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
	 * 获取token前台回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/union_fronturl")
	public String union_fronturl_wap(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		try{
			//获取订单流水号
			String ordercode = indexService.union_fronturl(request);
			if(StringUtils.isNotBlank(ordercode)){
				
				TxPayOrder hOrder = txPayOrderService.getTxPayOrderByOrderNumber(ordercode);
				
				Map<String,String> mapssss = SessionName.maporder.get(ordercode);
				model.addAttribute("shopCode", mapssss.get("shopCode"));
				
				SimpleDateFormat sf11 = new SimpleDateFormat("yyyyMMddHHmmss");
				Map<String, String> rspData = indexService.queryPay_wap(ordercode, sf11.format(hOrder.getCreateTime()));
				System.out.println(rspData.get("respCode"));
				System.out.println(rspData.get("origRespCode"));
				if(rspData!=null&&"00".equals(rspData.get("respCode"))&&"00".equals(rspData.get("origRespCode"))){
					String transaction_id = rspData.get("queryId");
					model.addAttribute("transaction_id", transaction_id);
				}else{
					model.addAttribute("msg", "交易失败");
					model.addAttribute("resultCode", rspData.get("origRespCode"));
					return "/wx/index/payFail";
				}
				
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
	@RequestMapping(value = "/union_backurl")
	public String union_backurl_wap(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		indexService.union_backurl(request);
		return  null;
	}
	
	/**
	 * 去充值
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toRecharge")
	public String toRecharge(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		return  "/wx/index/recharge";
	}
}
