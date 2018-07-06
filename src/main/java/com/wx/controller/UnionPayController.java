package com.wx.controller;

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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.perm.Permission;
import com.base.utils.ConfigConstants;
import com.base.utils.MakeImei;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.tx.task.service.ManageUserCutter;
import com.tx.txDfRate.model.TxDfRate;
import com.tx.txDfRate.service.TxDfRateService;
import com.tx.txPayRate.model.TxPayRate;
import com.tx.txPayRate.service.TxPayRateService;
import com.tx.txRate.model.TxRate;
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

@Controller
@RequestMapping("/unionpay")
public class UnionPayController extends BaseController{
	
	Logger log = Logger.getLogger(UnionPayController.class);
	
	@Autowired
	private TxPayRateService txPayRateService = null;
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxRefundOrderService txRefundOrderService = null;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;
	@Autowired
	private ManageUserCutter manageUserCutter = null;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private TxRefundFlagService txRefundFlagService = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private TxDfRateService txDfRateService = null;
	
	/**
	 * 转向注册前说明页
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toRegInfo", method = RequestMethod.GET)
	public String toRegInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = (String)request.getSession().getAttribute(SessionName.SESSION_OPENID);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("openId", openId);
		return "/wx/tx/regInfo";
	}
	/**
	 * 缴费选择页面
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPaySecond", method = RequestMethod.GET)
	public String toPaySecond(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = (String)request.getSession().getAttribute(SessionName.SESSION_OPENID);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("openId", openId);
		return "/wx/tx/pay_second";
	}
	
	/**
	 * 展示大额缴费记录页面
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toRecordBigManey", method = RequestMethod.GET)
	public String toRecordBigManey(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/tx/recordBigManey";
	}
	
	/**
	 * 转向订单
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSellOrder", method = RequestMethod.GET)
	public String toSellOrder(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Integer money = RequestHandler.getInteger(request, "money");
		try{
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, sel_time);
			model.addAttribute("endTime", calendar.getTime());
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(wxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("backCard", backCard);
		model.addAttribute("sel_time", sel_time);
		model.addAttribute("money", money);
		return "/wx/tx/order";
	}
	/**
	 * 转向支付方式
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPayWayDF", method = RequestMethod.GET)
	public String toPayWayDF(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		Integer sel_time = RequestHandler.getInteger(request, "sel_time");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		Double money = RequestHandler.getDouble(request, "money");
		model.addAttribute("backCard", backCard);
		model.addAttribute("sel_time", sel_time);
		model.addAttribute("money", money);
		return "/wx/index/payWayDF";
	}
	
	/** 发验证码 **/
	@RequestMapping(value = "/vercode", method = RequestMethod.POST)
	public String vercode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String accNo =  RequestHandler.getString(request, "accNo");
		Double money =  RequestHandler.getDouble(request, "money");
		Integer backCard =  RequestHandler.getInteger(request, "backCard");
		Integer sel_time =  RequestHandler.getInteger(request, "sel_time");
		try{
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
			if(StringUtils.isNotBlank(accNo)&&money!=null){
				BigDecimal bg1 = new BigDecimal(money);
				BigDecimal f = bg1.setScale(2, BigDecimal.ROUND_HALF_UP);
				int money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				
				String orderId = new MakeImei().getCode();
				Date d = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				String txnTime = sf.format(d);
				
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, sel_time);
				model.addAttribute("endTime", calendar.getTime());
				TxSellingOrder txSellingOrder = new TxSellingOrder();
				txSellingOrder.setBackCard(backCard);
				txSellingOrder.setCode(orderId);
				txSellingOrder.setSelTime(sel_time);
				txSellingOrder.setAccNo(accNo);
				txSellingOrder.setCreateTime(new Date());
				txSellingOrder.setEndTime(calendar.getTime());
				txSellingOrder.setMoney(money2*1L);
				
				if(wxUser.getPromoterId()!=null){
					TxWxUser wxUserPromet = txWxUserService.getTxWxUserById(wxUser.getPromoterId());//上级代理
					if(wxUserPromet.getParentId()!=null){
						txSellingOrder.setPromoterId(wxUserPromet.getPromoterId());
						txSellingOrder.setTwoPromoterId(wxUser.getPromoterId());
					}else{
						txSellingOrder.setPromoterId(wxUser.getPromoterId());
					}
				}
				
				txSellingOrder.setWxUserName(wxUser.getRealName());
				txSellingOrder.setWxUserId(wxUser.getId());
//				txSellingOrder.setProfits(new BigDecimal(ConfigConstants.PAY_RATE));
				
//				List<TxPayRate> list = txPayRateService.getTxPayRateList(new TxPayRate());
//				
//				BigDecimal bg = new BigDecimal(money2);
//				if(list!=null&&list.size()>0){
//					TxPayRate rate = list.get(0);
//					if(txSellingOrder.getPromoterId()!=null){
//						int one = (bg.multiply(rate.getOneRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//						txSellingOrder.setOneRate(one);
//					}
//					
//					if(txSellingOrder.getTwoPromoterId()!=null){
//						int two = (bg.multiply(rate.getTwoRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//						txSellingOrder.setTwoRate(two);
//					}
//					int devRate = (bg.multiply(rate.getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//					txSellingOrder.setDevRate(devRate);
//				}
				txSellingOrderService.insertTxSellingOrder(txSellingOrder);
				SessionName.MAPORDERNO.put("C_"+accNo, orderId+"_"+txnTime);
				boolean b = txWxUserBankNoService.vercodeNew(wxUser, orderId, txnTime, txWxUserBankNo, money2+"",backCard);
				if(b){
					writeSuccessMsg("发送成功", orderId, response);
				}else{
					SessionName.MAPORDERNO.remove("C_"+accNo);
					txSellingOrderService.deleteTxSellingOrderByCode(orderId);
					writeErrorMsg("发送失败", "", response);
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
	 * 支付
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(HttpServletRequest request, HttpServletResponse response, Model model){
		String accNo = RequestHandler.getString(request, "accNo");
		String smsCode = RequestHandler.getString(request, "smsCode");
		Double money = RequestHandler.getDouble(request, "money");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		try{
			if(StringUtils.isNotBlank(accNo)&&money!=null&&StringUtils.isNotBlank(smsCode)){
				
				BigDecimal bg = new BigDecimal(money);
				BigDecimal f = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
				int money2 = f.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
				
				TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
				if(wxUser.getState().intValue()==1&&wxUser.getCheckState().intValue()==1){
					TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
					String param = SessionName.MAPORDERNO.get("C_"+accNo);
					if(StringUtils.isNotBlank(param)){
						Map<String, String> map = txWxUserBankNoService.paySell(wxUser, param.split("_")[0], param.split("_")[1], txWxUserBankNo, money2+"", smsCode,backCard);
						if(map!=null&&"00".equals(map.get("respCode"))){
							writeSuccessMsg("交易成功，交易可能存在延时，请耐心等待！", param.split("_")[0], response);
						}else{
							writeErrorMsg(URLDecoder.decode(map.get("respMsg"),"UTF-8"), "", response);
						}
					}else{
						writeErrorMsg("验证码有误，请重新发送", "", response);
					}
				}else{
					writeErrorMsg("支付失败，个人资料未通过审核", "", response);
				}
			}
		}catch(Exception e){
			writeErrorMsg("系统异常", "", response);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 支付回调（代付）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backUrlDF_sell")
	public String backUrlDF(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		logger.info("============售卡代付回调===================");
		return  null;
	}
	/**
	 * 消费回调（消费）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backUrlTrans_sell")
	public String backUrlTrans_sell(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.backUrlTrans_sell(request);
		return  null;
	}
	
	/**
	 * 代付回调（消费）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/xw_backUrl")
	public String xw_backUrl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.xw_backUrl(request);
		return  null;
	}
	
	/**
	 * 支付成功页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPayResult")
	public String toPayResult(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String orderNo = RequestHandler.getString(request, "orderNo");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		try{
			if(StringUtils.isNotBlank(orderNo)){
				TxSellingOrder txSellingOrder = txSellingOrderService.getTxSellingOrderByCode(orderNo);
				model.addAttribute("txSellingOrder", txSellingOrder);
				model.addAttribute("money", super.getMoney(txSellingOrder.getMoney()));
				model.addAttribute("backCard", backCard);
			}else{
				return "/wx/tip";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/tx/paySuccess";
	}
	
	/**
	 * 交易记录
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyOrder", method = RequestMethod.GET)
	public String toMyOrder(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		return  "/wx/tx/orderList";
	}
	
	
	/**
	 * 交易记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getOrderListByCustumer", method = RequestMethod.GET)
	public String getOrderListByCustumer(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try
		{	
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sf1 = new SimpleDateFormat("yy-MM-dd");
			
			
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			
			txSellingOrder.setWxUserId(wxUser.getId());
			
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			
			
			txSellingOrder.setOffset(from);
			txSellingOrder.setLimit(rowCount);
			
			txSellingOrder.setSort("id");
			txSellingOrder.setOrder("desc");
			txSellingOrder.setState(1);
			int totalResults = txSellingOrderService.getTxSellingOrderCount(txSellingOrder);
			
			long allMoney = txSellingOrderService.getTxSellingOrderMoney(txSellingOrder).getProfitManey();
			long money = txSellingOrderService.getTxSellingOrderMoney(txSellingOrder).getMoney();
			List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderListByPage(txSellingOrder);
			
			if(list!=null&&list.size()>0){
				for(TxSellingOrder obj:list){
					JSONObject jsons = new JSONObject();
					jsons.put("money", super.getMoney(obj.getMoney()));
					jsons.put("profitManey", super.getMoney(obj.getProfitManey()));
					if(obj.getBackCard().intValue()==0){
						if(obj.getState().intValue()==1&&obj.getRefundState().intValue()==0){
							jsons.put("state", "未到期");
						}else if(obj.getState().intValue()==1&&obj.getRefundState().intValue()==1){
							jsons.put("state", "已结算");
						}
					}else{
						if(obj.getState().intValue()==1&&obj.getRefundState().intValue()==0){
							jsons.put("state", "可结算");
						}else if(obj.getState().intValue()==1&&obj.getRefundState().intValue()==1){
							jsons.put("state", "已结算");
						}
					}
					jsons.put("createTimeStr", sf1.format(obj.getCreateTime()));
					jsons.put("endTimeStr", sf1.format(obj.getEndTime()));
					jsons.put("id", obj.getId());
					array.add(jsons);
				}
				json.put("message", "ok");
				json.put("allCount", totalResults);
				json.put("allMoney", super.getMoney(allMoney));
				json.put("money", super.getMoney(money));
				json.put("items", array);
			}else{
				json.put("message", "end");
			}
			
		} catch (Exception e)
		{
			json.put("message", "end");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return null;
	}
	
	
	/**
	 * 订单详情
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOrderDetail", method = RequestMethod.GET)
	public String toOrderDetail(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Long id = RequestHandler.getLong(request, "id");
		try{
			TxSellingOrder order = txSellingOrderService.getTxSellingOrderById(id);
			model.addAttribute("order", order);
			model.addAttribute("money", super.getMoney(order.getMoney()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/tx/orderDetail";
	}
	
	/**
	 * 退卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/refundPay", method = RequestMethod.POST)
	public String refundPay(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Long id = RequestHandler.getLong(request, "id");
		Integer backCard = RequestHandler.getInteger(request, "backCard");
		try{
			//判断T0还是T1
			TxRefundFlag txRefundFlag = new TxRefundFlag();
			txRefundFlag.setStyle(backCard);
			List<TxRefundFlag> listT = txRefundFlagService.getTxRefundFlagList(txRefundFlag);
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			TxSellingOrder order = txSellingOrderService.getTxSellingOrderById(id);
			if(wxUser.getId().intValue()==order.getWxUserId().intValue()&&order.getRefundState().intValue()==0){
//				if(order.getBackCard().intValue()==1){
					//调用接口退费
					TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(order.getAccNo());
					
					BigDecimal bg = new BigDecimal(order.getMoney());
					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.RATE));
					int txnAmtDF = (bg.multiply(bgRate)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+300;
					String orderId = new MakeImei().getCode();
					Date d = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
					String merOrderTime = sf.format(d);
					order.setRefundCode(orderId);
					order.setProfitManey(order.getMoney()-txnAmtDF);
					order.setProfits(bgRate.add(new BigDecimal(3)));
					if(backCard.intValue()==1){//立即退
						List<TxDfRate> list = txDfRateService.getTxDfRateList(new TxDfRate());
						TxDfRate rate = list.get(0);
						if(order.getPromoterId()!=null&&order.getTwoPromoterId()!=null){
							int one = (bg.multiply(rate.getOneRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
							order.setOneRate(one);
							int two = (bg.multiply(rate.getTwoRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
							order.setTwoRate(two);
						}else if(order.getPromoterId()!=null&&order.getTwoPromoterId()==null){
							int one = (bg.multiply(rate.getOneRate().add(rate.getTwoRate()))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
							order.setOneRate(one);
						}
						int devRate = (bg.multiply(rate.getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						order.setDevRate(devRate);
					}
					order.setBackCard(backCard);
					txSellingOrderService.updateTxSellingOrderById(order);
					Map<String, String> map = txWxUserBankNoService.xwDF(wxUser, orderId, merOrderTime, txWxUserBankNo, (order.getMoney()-txnAmtDF)+"", null, order.getBackCard(), listT.get(0).getTrem(),txnAmtDF);
					if(map!=null&&"00".equals(map.get("respCode"))){
						writeSuccessMsg("退卡成功！", null, response);
					}else{
						writeErrorMsg(URLDecoder.decode(map.get("respMsg"),"UTF-8"), "", response);
					}
//				}else{
//					//入库，等待管理员操作
//					TxRefundOrder txRefundOrder = new TxRefundOrder();
//					txRefundOrder.setUserId(wxUser.getId());
//					txRefundOrder.setRealName(wxUser.getRealName());
//					txRefundOrder.setCreateTime(new Date());
//					txRefundOrder.setFee(order.getMoney());
//					txRefundOrder.setOrderCode(order.getCode());
//					txRefundOrder.setOrderTime(order.getCreateTime());
//					txRefundOrderService.insertTxRefundOrder(txRefundOrder);
//					writeSuccessMsg("申请成功！", null, response);
//				}
			}else{
				writeSuccessMsg("退卡失败！", null, response);
			}
		}catch(Exception e){
			writeErrorMsg("系统异常", "", response);
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 我的二维码
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyEwm", method = RequestMethod.GET)
	public String toMyEwm(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			ManageAdminUser manageAdminUserDb = new ManageAdminUser();
			manageAdminUserDb.setOpenId(wxUser.getOpenId());
			manageAdminUserDb = manageadminuserService.getManageAdminUser(manageAdminUserDb);
			if(manageAdminUserDb==null){
				//获取默认的一级业务员
				ManageAdminUser manageAdminUser = new ManageAdminUser();
				manageAdminUser.setIs_default_db(1);
				manageAdminUser = manageadminuserService.getManageAdminUser(manageAdminUser);
				
				if(manageAdminUser!=null&&manageAdminUser.getAdminId()>0){
					//生产二级业务员账号
					ManageAdminUser manageAdminUser1 = new ManageAdminUser();
					manageAdminUser1.setOpenId(wxUser.getOpenId());
					manageAdminUser1.setParent_id(manageAdminUser.getAdminId());
					manageAdminUser1.setRealName(wxUser.getRealName());
					manageAdminUser1.setCreateTime(new Date());
					manageAdminUser1.setAdminName(wxUser.getMobile());
					manageAdminUser1.setPasswd(MD5.getMD5ofStr(wxUser.getMobile().substring(wxUser.getMobile().length()-6,wxUser.getMobile().length())));
					manageAdminUser1.setLoginIP(super.getIp(request));
					manageAdminUser1.setState(0);
					manageAdminUser1.setRole_id(Integer.valueOf(ConfigConstants.TWO_DB_ROLE_ID));
					manageadminuserService.insertManageAdminUser(manageAdminUser1);
					manageUserCutter.filesMng(manageAdminUser1, weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
					//生成二维码
					Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, manageAdminUser1.getOpenId());
					
					manageAdminUser1.setQr_code_url(map.get("imgurl"));
					manageadminuserService.updateManageAdminUser(manageAdminUser1);
					
					model.addAttribute("imgurl", map.get("imgurl"));
				}
			}else{
				if(!StringUtils.isNotBlank(manageAdminUserDb.getQr_code_url())){
					//生成二维码
					Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, manageAdminUserDb.getOpenId());
					manageAdminUserDb.setQr_code_url(map.get("imgurl"));
					manageadminuserService.updateManageAdminUser(manageAdminUserDb);
					model.addAttribute("imgurl", map.get("imgurl"));
				}else{
					model.addAttribute("imgurl", manageAdminUserDb.getQr_code_url());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/tx/myewm";
	}
	
	/**
	 * 退卡
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@Permission(value = "/txRefundOrder/refundCard")
	@RequestMapping(value = "/refundCard", method = RequestMethod.POST)
	public String refundCard(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Long id = RequestHandler.getLong(request, "id");
		try{
			
			TxRefundOrder txRefundOrder = txRefundOrderService.getTxRefundOrderById(id);
			if(txRefundOrder!=null&&txRefundOrder.getState().intValue()==0){
				
				//判断T0还是T1
				TxRefundFlag txRefundFlag = new TxRefundFlag();
				txRefundFlag.setStyle(2);
				List<TxRefundFlag> listT = txRefundFlagService.getTxRefundFlagList(txRefundFlag);
				
				TxSellingOrder order = txSellingOrderService.getTxSellingOrderByCode(txRefundOrder.getOrderCode());
				if(order.getState().intValue()==1&&order.getRefundState().intValue()==0){
					
					//调用查询接口
					TxWxUser wxUser = txWxUserService.getTxWxUserById(txRefundOrder.getUserId());
					//调用接口退费
					TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(order.getAccNo());
					
					BigDecimal bg = new BigDecimal(order.getMoney());
					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.RATE));
					
					int txnAmtDF = (bg.multiply(bgRate).divide(new BigDecimal(12).multiply(new BigDecimal(order.getSelTime())))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					
					order.setProfitManey(order.getMoney()+txnAmtDF);
					order.setProfits(bgRate);
					
					String orderId = new MakeImei().getCode();
					Date d = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
					String merOrderTime = sf.format(d);
					order.setRefundCode(orderId);
					
					if(order.getPromoterId()!=null&&order.getTwoPromoterId()!=null){
						int two = (bg.multiply(new BigDecimal(0.0008))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						order.setTwoRate(two);
					}else if(order.getPromoterId()!=null&&order.getTwoPromoterId()==null){
						int one = (bg.multiply(new BigDecimal(0.0008))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						order.setOneRate(one);
					}
					
//					order.setProfitManey(order.getMoney());
//					order.setProfits(new BigDecimal(0));
					txSellingOrderService.updateTxSellingOrderById(order);
					Map<String, String> map = txWxUserBankNoService.xwDF(wxUser, orderId, merOrderTime, txWxUserBankNo, order.getProfitManey()+"", null, order.getBackCard(), listT.get(0).getTrem(),0);
					if(map!=null&&"00".equals(map.get("respCode"))){
						//进行分润
						writeSuccessMsg("退卡成功！", null, response);
					}else{
						writeErrorMsg(URLDecoder.decode(map.get("respMsg"),"UTF-8"), "", response);
					}
				}else{
					writeErrorMsg("退卡失败", "", response);
				}
			}else{
				writeErrorMsg("退卡失败", "", response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
}
