package com.wx.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


























import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


























import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.CardDistinguish;
import com.base.utils.ConfigConstants;
import com.base.utils.GetOpenIdUntil;
import com.base.utils.MakeImei;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.tx.task.service.UserInfoCutter;
import com.tx.txDfOrder.model.TxDfOrder;
import com.tx.txDfOrder.service.TxDfOrderService;
import com.tx.txPayRate.model.TxPayRate;
import com.tx.txPayRate.service.TxPayRateService;
import com.tx.txRate.model.TxRate;
import com.tx.txRate.service.TxRateService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxOrder.service.TxWxOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.WeiXinService;
import com.wx.service.WxTemplateMsg;
import com.wx.utils.WxMenuUtils;
import com.wx.x0001.WeiXin;
import com.wx.x0001.vo.recv.WxRecvEventMsg;
import com.wx.x0001.vo.recv.WxRecvMsg;
import com.wx.x0001.vo.recv.WxRecvTextMsg;
import com.wx.x0001.vo.send.WxSendMsg;
import com.wx.x0001.vo.send.WxSendNewsMsg;
import com.wx.x0001.vo.send.WxSendTextMsg;

@Controller
@RequestMapping("/weixin")
public class WeiXinController extends BaseController{
	
	Logger log = Logger.getLogger(WeiXinController.class);
	
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;// 用户
	@Autowired
	private WxTemplateMsg wxTemplateMsg = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxWxOrderService txWxOrderService = null;
	@Autowired
	private TxRateService txRateService = null;
	@Autowired
	private UserInfoCutter userInfoCutter;
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxPayRateService txPayRateService = null;
	@Autowired
	private TxDfOrderService txDfOrderService = null;
	
	
	@RequestMapping("/access")
	public String weixin(HttpServletResponse response,
			HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String appid = request.getParameter("appid");
		// 验证接口配置消息的时候会调用
		if (null != timestamp && null != nonce && null != echostr
				&& null != signature) {
			if (WeiXin.access(ConfigConstants.TOKEN, signature, timestamp, nonce)) {
				try {
					PrintWriter writer = response.getWriter();
					writer.print(echostr);
					writer.flush();
					writer.close();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("--------------->"+appid);
		if (StringUtils.isNotBlank(code)) {
			try {
				String access_token = WxMenuUtils.getAccessCode(ConfigConstants.APPID, ConfigConstants.APPSECRET, code);
				JSONObject json = JSONObject.parseObject(access_token);
				String openId = json.getString("openid");
				System.out.println("------------openId--------------"+openId);
				request.setAttribute("openId", openId);
				String states[] = state.split("_");
				boolean b = weiXinService.loginWxCust(openId, request);
				request.getSession().setAttribute(SessionName.SESSION_OPENID, openId);
				if(b){
					if("index".equals(states[0])){//缴费
						return  "redirect:/other/toIndex?cityCode=010";
					}else if("jb".equals(states[0])){//赚点钱
						return  "redirect:/weixin/dbIndex?openId="+ openId;
					}
				}else{
					request.getSession().removeAttribute(SessionName.ADMIN_USER_NAME);
					request.getSession().removeAttribute(SessionName.ADMIN_USER_ID);
					request.getSession().removeAttribute(SessionName.ADMIN_USER);
					if("index".equals(states[0])){//缴费
						return  "redirect:/other/toIndex?cityCode=010";
					}else if("jb".equals(states[0])){//基本信息
						return  "redirect:/weixin/dbIndex?openId="+ openId;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null != timestamp && null != nonce && null != signature) {
			if (WeiXin.access(ConfigConstants.TOKEN, signature, timestamp, nonce)) {// 验证消息的真实性
				try {
					WxRecvMsg msg = WeiXin.recv(request.getInputStream());
					WxSendMsg sendMsg = WeiXin.builderSendByRecv(msg);
					String openId = msg.getFromUser();
					if (msg instanceof WxRecvEventMsg) {
						WxRecvEventMsg m = (WxRecvEventMsg) msg;
						String event = m.getEvent();
						if ("subscribe".equals(event)) {
							String content = "欢迎关注";
							sendMsg = new WxSendTextMsg(sendMsg, content);
							if(StringUtils.isNotBlank(m.getTicket())){
								String ticket = m.getTicket();
								//将推荐人和被推荐人绑定。
								weiXinService.bindMchAndBD(openId,ticket,super.getIp(request));
							}else{
								weiXinService.bind(openId,super.getIp(request));
							}
						}else if("unsubscribe".equals(event)){//取消关注
							//解除绑定
							weiXinService.unBindWx(openId);
						}else if("CLICK".equals(event)){
							System.out.println("---------------"+m.getEventKey());
							if("BM".equals(m.getEventKey())){
								String content = "即将开通此服务，敬请期待。";
								sendMsg = new WxSendTextMsg(sendMsg, content);
							}else if("SC".equals(m.getEventKey())){
								String content = "即将开通此服务，敬请期待。";
								sendMsg = new WxSendTextMsg(sendMsg, content);
							}
						}else if("SCAN".equals(event)){
							String content = "欢迎关注";
							sendMsg = new WxSendTextMsg(sendMsg, content);
							String ticket = m.getTicket();
							weiXinService.bindMchAndBD(openId,ticket,super.getIp(request));
						}else if("LOCATION".equals(event)){
							String str = weiXinService.getBaiDuLocationXY(m.getLatitude(),m.getLongitude());
							
							System.out.println(str);
						}
						WeiXin.send(sendMsg, response.getOutputStream());
					}else if(msg instanceof WxRecvTextMsg){
						WxRecvTextMsg m = (WxRecvTextMsg) msg;
						String content =  m.getContent();
						if(content.contains("合作")){//机构验证
						}
					}
				} catch (Exception e) {
					log.info(e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 测试
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		boolean b = weiXinService.loginWxCust(openId, request);
		return  "redirect:/weixin/dbIndex?openId="+openId;
	}
	/**
	 * 测试
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toReg", method = RequestMethod.GET)
	public String toReg_style(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		model.addAttribute("openId", openId);
		return  "/wx/reg";
	}
	
	
	/**
	 * 验证绑定
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String verify(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String openId = RequestHandler.getString(request, "openId");
		String phone = RequestHandler.getString(request, "phone");
		String pwd = RequestHandler.getString(request, "pwd");
		try{
			if(!StringUtils.isNotBlank(phone)){
				writeSuccessMsg("-1", null, response);
				return null;
			}else if(!StringUtils.isNotBlank(pwd)){
				writeSuccessMsg("-2", null, response);
				return null;
			}else if(!StringUtils.isNotBlank(openId)){
				writeSuccessMsg("-3", null, response);
				return null;
			}
			
			TxWxUser txWxUser = new TxWxUser();
			txWxUser.setMobile(phone);
			txWxUser.setPassword(MD5.getMD5ofStr(pwd));
			int count = txWxUserService.getTxWxUserListCount(txWxUser);
			if(count ==0){
				writeSuccessMsg("-4", null, response);
				return null;
			}else if(count==1){
				List<TxWxUser> list = txWxUserService.getTxWxUserList(txWxUser);
				txWxUser = list.get(0);
//				if(StringUtils.isNotBlank(txWxUser.getOpenId())){
//					writeSuccessMsg("-7", null, response);
//				}else{
//					txWxUser.setOpenId(openId);
					txWxUser.setState(1);
					txWxUserService.updateTxWxUserById(txWxUser);
					
					request.getSession().setAttribute(SessionName.ADMIN_USER, txWxUser);
					request.getSession().setAttribute(SessionName.ADMIN_USER_ID, txWxUser.getId());
					request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, txWxUser.getNickName());
					writeSuccessMsg("1", null, response);
//				}
			}else{
				writeSuccessMsg("-5", null, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 解绑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unbind")
	public String unbind(HttpServletRequest request, HttpServletResponse response, Model model){
		TxWxUser txWxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			txWxUserService.unBindWx(txWxUser.getOpenId());
			writeSuccessMsg("success", null, response);
		}catch(Exception e){
			writeSuccessMsg("error", null, response);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解绑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delMyCard")
	public String delMyCard(HttpServletRequest request, HttpServletResponse response, Model model){
		Integer  id = RequestHandler.getInteger(request, "id");
		try{
			if(id!=null){
				txWxUserBankNoService.deleteTxWxUserBankNoById(id);
			}
			writeSuccessMsg("success", null, response);
		}catch(Exception e){
			writeSuccessMsg("error", null, response);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 保存图片
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/saveImageToDisk", method = RequestMethod.GET)
	public String saveImageToDisk(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String mediaId = RequestHandler.getString(request, "mediaId");
		String accessToken = weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET);
		String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
		String fileToday = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
		File file = new File(saveFilePath + File.separator + fileToday + File.separator);
		if(!file.exists()){
			file.mkdirs();
		}
		JSONObject json = new JSONObject();
		try{
			weiXinService.saveImageToDisk(accessToken, mediaId, mediaId, saveFilePath + File.separator + fileToday + File.separator);
			json.put("c", 0);
			json.put("d", File.separator + fileToday + File.separator + mediaId + ".jpg");
			json.put("m", "上传成功");
		}catch(Exception e){
			json.put("c", -1);
			json.put("d", new JSONObject());
			json.put("m", "上传失败，系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(json.toString());
		return  null;
	}
	
	
	/**
	 * 进入拓展员登录页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dbIndex", method = RequestMethod.GET)
	public String dbIndex(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		model.addAttribute("openId", openId);
		try{
			//判断是否已经验证
			TxWxUser txWxUser = new TxWxUser();
			txWxUser.setOpenId(openId);
			int count = txWxUserService.getTxWxUserListCount(txWxUser);
			if(count==0){//未绑定
				return  "/wx/db_login";
			}else{
				if(count==1){
					txWxUser = txWxUserService.getTxWxUserByOpenId(openId);
					request.getSession().setAttribute(SessionName.ADMIN_USER, txWxUser);
					request.getSession().setAttribute(SessionName.ADMIN_USER_ID, txWxUser.getId());
					request.getSession().setAttribute(SessionName.ADMIN_USER_NAME, txWxUser.getNickName());
					if(txWxUser.getState()==1){
						if(txWxUser.getCheckState()==1){
							return  "/wx/myInfo";
						}else{
							return  "/wx/tx/regInfo";
						}
					}else{
						return  "/wx/db_login";
					}
				}else{
					//账号异常
					return  "/wx/tip";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/db_login";
	}
	
	/**
	 * 我的客户二维码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyQRcode")
	public String toMyQRcode(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		if(StringUtils.isNotBlank(openId)){
			ManageAdminUser manageAdminUser = new ManageAdminUser();
			manageAdminUser.setOpenId(openId);
			manageAdminUser = manageadminuserService.getManageAdminUser(manageAdminUser);
			if(manageAdminUser!=null&&!StringUtils.isNotBlank(manageAdminUser.getScan_ticket())){
				Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, openId);
				manageAdminUser.setScan_ticket(map.get("ticket"));
				manageAdminUser.setQr_code_url(map.get("imgurl"));
				manageadminuserService.updateManageAdminUser(manageAdminUser);
			}
			if(manageAdminUser!=null&&(manageAdminUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.DB_ROLE_ID).intValue()||
					manageAdminUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.TWO_DB_ROLE_ID).intValue())){
				ManageAdminUser manageAdminUser1 = new ManageAdminUser();
				manageAdminUser1.setOpenId(openId);
				manageAdminUser1 = manageadminuserService.getManageAdminUser(manageAdminUser1);
				model.addAttribute("manageAdminUser", manageAdminUser1);
			}
		}
		return "/wx/myQRcode";
	}
	/**
	 * 我的朋友二维码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyQRcodeFriend")
	public String toMyQRcodeFriend(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		if(StringUtils.isNotBlank(openId)){
			TxWxUser wxUser = txWxUserService.getTxWxUserByOpenId(openId);
			if(!StringUtils.isNotBlank(wxUser.getScanTicket())){
				//生成二维码
				Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, wxUser.getOpenId());
				wxUser.setQrCodeUrl(map.get("imgurl"));
				wxUser.setScanTicket(map.get("ticket"));
				txWxUserService.updateTxWxUserById(wxUser);
			}
			model.addAttribute("wxUser", wxUser);
		}
		return "/wx/index/share";
	}
	
	
	/**
	 * 我的客户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyCustomer")
	public String toMyCustomer(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		model.addAttribute("openId", openId);
		return  "/wx/myCustomer";
	}
	
	/**
	 * 我的客户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showInfo")
	public String showInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		super.getJsticket(request);
		Integer id = RequestHandler.getInteger(request, "id");
		try{
			TxWxUser txWxUser = txWxUserService.getTxWxUserById(id);
			model.addAttribute("txWxUser", txWxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/showInfo";
	}
	
	
	/**
	 * 获得绑定的客户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getMyCustomer", method = RequestMethod.GET)
	public String getMyCustomer(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception
	{
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String mobile = RequestHandler.getString(request, "mobile");
		String realName = RequestHandler.getString(request, "realName");
		Integer state = RequestHandler.getInteger(request, "state");
		try
		{	
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			TxWxUser txWxUser = new TxWxUser();
			
			txWxUser.setState(state);
			if(StringUtils.isNotBlank(mobile)){
				txWxUser.setMobile(mobile);
			}
			if(StringUtils.isNotBlank(realName)){
				txWxUser.setRealName(realName);
			}
			
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			
			
			txWxUser.setOffset(from);
			txWxUser.setLimit(rowCount);
			
			txWxUser.setSort("id");
			txWxUser.setOrder("desc");
			
			if(ConfigConstants.DB_ROLE_ID.equals(adminUser.getRole_id()+"")){//一级
				txWxUser.setPromoterId(adminUser.getAdminId());
				txWxUser.setTwoPromoterId(-1);
			}else{
				txWxUser.setTwoPromoterId(adminUser.getAdminId());
			}
			TxWxOrder txWxOrder = new TxWxOrder();
			txWxOrder.setTwoPromoterId(-1);
			txWxOrder.setPromoterId(adminUser.getAdminId());
			txWxOrder.setDfState(1);
			txWxOrder.setXfState(1);
			Long totalMoney = txWxOrderService.getTxWxOrderMoney(txWxOrder);
			Long oneRate = txWxOrderService.getMoneyByRate(txWxOrder);
			int totalResults = txWxUserService.getTxWxUserListCount(txWxUser);
			List<TxWxUser> list = txWxUserService.getTxWxUserListByPage(txWxUser);
			
			
			if(list!=null&&list.size()>0){
				for(TxWxUser obj:list){
					JSONObject jsons = new JSONObject();
					if(obj.getMobile()!=null){
						String mobile1 = obj.getMobile();
						jsons.put("mobile", mobile1.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
					}else{
						jsons.put("mobile", "");
					}
					if(obj.getCheckState()==1){
						jsons.put("checkStateName", "验证通过");
					}else{
						jsons.put("checkStateName", "未通过");
					}
					if(obj.getState().intValue()==1){
						jsons.put("state_name", "已审核");
					}else{
						jsons.put("state_name", "未审核");
					}
					jsons.put("createTime", sf.format(obj.getCreateTime()));
					jsons.put("id", obj.getId());
					jsons.put("msg", obj.getMsg());
					if(StringUtils.isNotBlank(obj.getRealName())){
						jsons.put("realName", weiXinService.chineseName(obj.getRealName()));
					}else{
						jsons.put("realName", "");
					}
					jsons.put("state", obj.getState());
					jsons.put("checkState", obj.getCheckState());
					array.add(jsons);
				}
				json.put("message", "ok");
				json.put("totalResults", totalResults);
				json.put("totalMoney", super.getMoney(totalMoney));
				json.put("oneRate", super.getMoney(oneRate));
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
	 * 获得绑定的客户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getMyCustomerSell", method = RequestMethod.GET)
	public String getMyCustomerSell(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception
	{
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		String mobile = RequestHandler.getString(request, "mobile");
		String realName = RequestHandler.getString(request, "realName");
		Integer state = RequestHandler.getInteger(request, "state");
		try
		{	
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			TxWxUser txWxUser = new TxWxUser();
			
			txWxUser.setState(state);
			if(StringUtils.isNotBlank(mobile)){
				txWxUser.setMobile(mobile);
			}
			if(StringUtils.isNotBlank(realName)){
				txWxUser.setRealName(realName);
			}
			
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			
			
			txWxUser.setOffset(from);
			txWxUser.setLimit(rowCount);
			
			txWxUser.setSort("id");
			txWxUser.setOrder("desc");
			
			if(ConfigConstants.DB_ROLE_ID.equals(adminUser.getRole_id()+"")){//一级
				txWxUser.setPromoterId(adminUser.getAdminId());
				txWxUser.setTwoPromoterId(-1);
			}else{
				txWxUser.setTwoPromoterId(adminUser.getAdminId());
			}
			
			TxSellingOrder txSellingOrder = new TxSellingOrder();
			txSellingOrder.setPromoterId(adminUser.getAdminId());
			txSellingOrder.setState(1);
			txSellingOrder.setTwoPromoterId(-1);
			Long oneRate = txSellingOrderService.getMoneyByRate(txSellingOrder);
			txSellingOrder = txSellingOrderService.getTxSellingOrderMoney(txSellingOrder);
			Long totalMoney = 0L;
			if(txSellingOrder!=null){
				totalMoney = txSellingOrder.getMoney();
			}
			int totalResults = txWxUserService.getTxWxUserListCount(txWxUser);
			List<TxWxUser> list = txWxUserService.getTxWxUserListByPage(txWxUser);
			
			
			if(list!=null&&list.size()>0){
				for(TxWxUser obj:list){
					JSONObject jsons = new JSONObject();
					if(obj.getMobile()!=null){
						String mobile1 = obj.getMobile();
						jsons.put("mobile", mobile1.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
					}else{
						jsons.put("mobile", "");
					}
					if(obj.getCheckState()==1){
						jsons.put("checkStateName", "验证通过");
					}else{
						jsons.put("checkStateName", "未通过");
					}
					if(obj.getState().intValue()==1){
						jsons.put("state_name", "已审核");
					}else{
						jsons.put("state_name", "未审核");
					}
					jsons.put("createTime", sf.format(obj.getCreateTime()));
					jsons.put("id", obj.getId());
					jsons.put("msg", obj.getMsg());
					if(StringUtils.isNotBlank(obj.getRealName())){
						jsons.put("realName", weiXinService.chineseName(obj.getRealName()));
					}else{
						jsons.put("realName", "");
					}
					jsons.put("state", obj.getState());
					jsons.put("checkState", obj.getCheckState());
					array.add(jsons);
				}
				json.put("message", "ok");
				json.put("totalResults", totalResults);
				json.put("totalMoney", super.getMoney(totalMoney));
				json.put("oneRate", super.getMoney(oneRate));
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
	 * 注册
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		String repassword = RequestHandler.getString(request, "repassword");
		JSONObject json = new JSONObject();
		try{
			TxWxUser user1 = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			txWxUser.setOpenId(user1.getOpenId());
			if(!StringUtils.isNotBlank(txWxUser.getPassword())||!StringUtils.isNotBlank(repassword)){
				json.put("c", -1);
				json.put("m", "注册失败,密码不能为空");
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control","no-cache");
				response.getWriter().write(json.toString());
				return  null;
			}
			if(!txWxUser.getPassword().equals(repassword)){
				json.put("c", -1);
				json.put("m", "注册失败,两次密码不一致");
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control","no-cache");
				response.getWriter().write(json.toString());
				return  null;
			}
			
			String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
			
			boolean b = true;
			
			//验证手机是否存在
			TxWxUser user2 = new TxWxUser();
			user2.setMobile(txWxUser.getMobile());
			user2.setState(1);
			int count = txWxUserService.getTxWxUserListCount(user2);
			if(count>0){
				json.put("c", -1);
				json.put("m", "注册失败,手机号已占用,请更换!");
				response.setContentType("text/html;charset=utf-8");
				response.setHeader("Cache-Control","no-cache");
				response.getWriter().write(json.toString());
				return  null;
			}
			
//			String imgUrlFront = txWxUser.getIDUrl().replace("https://file.mievie.com/file", saveFilePath);
//			//进入身份证识别
//			JSONObject jsonFront = new CardDistinguish().idDistinguish(imgUrlFront, "front");
//			if("reversed_side".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "注册失败,未摆正身份证照片面");
//				b = false;
//			}else if("non_idcard".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "注册失败,上传的图片中不包含身份证照片面");
//				b = false;
//			}else if("blurred".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "注册失败,身份证照片面模糊");
//				b = false;
//			}else if("over_exposure".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "注册失败,身份证照片面关键字段反光或过曝");
//				b = false;
//			}else if("unknown".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "注册失败,身份证照片面未知状态");
//				b = false;
//			}else{
//				if(!txWxUser.getIDNumber().equals(jsonFront.getString("number"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,身份证号与上传证件不一致");
//					b = false;
//				}else if(!txWxUser.getRealName().equals(jsonFront.getString("name"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,真实姓名与上传证件不一致");
//					b = false;
//				}
//			}
//			
//			if(b){
//				String imgUrlback = txWxUser.getIDFanUrl().replace("https://file.mievie.com/file", saveFilePath);
//				//进入身份证识别
//				JSONObject jsonBack = new CardDistinguish().idDistinguish(imgUrlback, "back");
//				if("reversed_side".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,未摆正身份证国徽面");
//					b = false;
//				}else if("non_idcard".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,上传的图片中不包含身份证国徽面");
//					b = false;
//				}else if("blurred".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,身份证国徽面模糊");
//					b = false;
//				}else if("over_exposure".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,身份证国徽面关键字段反光或过曝");
//					b = false;
//				}else if("unknown".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "注册失败,身份证国徽面未知状态");
//					b = false;
//				}else{
//					SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//					String today = sf.format(new Date());
//					if(Integer.valueOf(today)>Integer.valueOf(jsonBack.getString("end_time"))){
//						json.put("c", -1);
//						json.put("m", "注册失败,身份证已过期");
//						b = false;
//					}
//				}
//			}
			
//			if(b){
//				String bankUrl = txWxUser.getCardUrl().replace("https://file.mievie.com/file", saveFilePath);
//				JSONObject bankJosn = new CardDistinguish().bankDistinguish(bankUrl);
//				if(0==bankJosn.getIntValue("bank_card_type")){
//					json.put("c", -1);
//					json.put("m", "注册失败,结算卡照片不能识别");
//					b = false;
//				}else{
//					if(!txWxUser.getCardNumber().equals(bankJosn.getString("number").replaceAll("\\s*", ""))){
//						json.put("c", -1);
//						json.put("m", "注册失败,结算卡与上传照片不一致");
//						b = false;
//					}
//				}
//			}
			
			if(b){
				com.base.utils.MD5 md5 = new com.base.utils.MD5();
				txWxUser.setPassword(md5.getMD5ofStr(repassword));
				JSONObject json1 = weiXinService.regHxt(txWxUser);
				if("1000".equals(json1.getString("respCode"))){
					txWxUser.setState(1);
					txWxUser.setCheckState(1);
					txWxUser.setMerId(json1.getString("merId"));
					txWxUser.setMsg("无");
					TxWxUser user = txWxUserService.getTxWxUserByOpenId(txWxUser.getOpenId());
					int flag = 0;
					if(user!=null&&user.getId()>0){
						flag = txWxUserService.updateTxWxUserByOpenId(txWxUser);
					}else{
						txWxUser.setCreateTime(new Date());
						if(txWxUser.getPromoterId()==null){
							ManageAdminUser manageAdminUser = new ManageAdminUser();
							manageAdminUser.setIs_default_db(1);
							manageAdminUser = manageadminuserService.getManageAdminUser(manageAdminUser);
							txWxUser.setPromoterId(manageAdminUser.getAdminId());
						}
						flag = txWxUserService.insertTxWxUser(txWxUser);
						userInfoCutter.filesMng(txWxUser, weiXinService.getAccessToken(ConfigConstants.APPID, ConfigConstants.APPSECRET));
					}
					if(flag>0){
						json.put("c", 0);
						json.put("m", "注册成功");
					}else{
						json.put("c", -1);
						json.put("m", "注册失败");
					}
				}else{
					txWxUser.setCheckState(0);
					txWxUser.setMsg(json1.getString("respMsg"));
					txWxUserService.updateTxWxUserByOpenId(txWxUser);
					json.put("c", -1);
					json.put("m", json1.getString("respMsg"));
				}
			}
		}catch(Exception e){
			json.put("c", -1);
			json.put("d", new JSONObject());
			json.put("m", "注册失败，系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	
	/**
	 * 完善资料
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ws", method = RequestMethod.POST)
	public String ws(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		ManageAdminUser manageAdminUser = requst2Bean(request,ManageAdminUser.class);
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		JSONObject json = new JSONObject();
		try{
			String mobile = manageAdminUser.getMobile();
			manageAdminUser.setAdminName(mobile);
			manageAdminUser.setAdminId(adminUser.getAdminId());
			manageAdminUser.setPasswd(MD5.getMD5ofStr(mobile.substring(mobile.length()-6, mobile.length())));
			int flag = manageadminuserService.updateManageAdminUser(manageAdminUser);
			if(flag>0){
				json.put("c", 0);
				json.put("m", "完善成功");
			}else{
				json.put("c", -1);
				json.put("m", "完善失败");
			}
		}catch(Exception e){
			json.put("c", -1);
			json.put("d", new JSONObject());
			json.put("m", "完善失败，系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	
	/**
	 * 修改信息
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public String updateInfo(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		JSONObject json = new JSONObject();
		try{
			
			String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
			
			boolean b = true;
//			String imgUrlFront = txWxUser.getIDUrl().replace("https://file.mievie.com/file", saveFilePath);
//			//进入身份证识别
//			JSONObject jsonFront = new CardDistinguish().idDistinguish(imgUrlFront, "front");
//			if("reversed_side".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "修改失败,未摆正身份证照片面");
//				b = false;
//			}else if("non_idcard".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "修改失败,上传的图片中不包含身份证照片面");
//				b = false;
//			}else if("blurred".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "修改失败,身份证照片面模糊");
//				b = false;
//			}else if("over_exposure".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "修改失败,身份证照片面关键字段反光或过曝");
//				b = false;
//			}else if("unknown".equals(jsonFront.getString("image_status"))){
//				json.put("c", -1);
//				json.put("m", "修改失败,身份证照片面未知状态");
//				b = false;
//			}else{
//				if(!txWxUser.getIDNumber().equals(jsonFront.getString("number"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,身份证号与上传证件不一致");
//					b = false;
//				}else if(!txWxUser.getRealName().equals(jsonFront.getString("name"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,真实姓名与上传证件不一致");
//					b = false;
//				}
//			}
			
//			if(b){
//				String imgUrlback = txWxUser.getIDFanUrl().replace("https://file.mievie.com/file", saveFilePath);
//				//进入身份证识别
//				JSONObject jsonBack = new CardDistinguish().idDistinguish(imgUrlback, "back");
//				if("reversed_side".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,未摆正身份证国徽面");
//					b = false;
//				}else if("non_idcard".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,上传的图片中不包含身份证国徽面");
//					b = false;
//				}else if("blurred".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,身份证国徽面模糊");
//					b = false;
//				}else if("over_exposure".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,身份证国徽面关键字段反光或过曝");
//					b = false;
//				}else if("unknown".equals(jsonBack.getString("image_status"))){
//					json.put("c", -1);
//					json.put("m", "修改失败,身份证国徽面未知状态");
//					b = false;
//				}else{
//					SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//					String today = sf.format(new Date());
//					if(Integer.valueOf(today)>Integer.valueOf(jsonBack.getString("end_time"))){
//						json.put("c", -1);
//						json.put("m", "修改失败,身份证已过期");
//						b = false;
//					}
//				}
//			}
			
//			if(b){
//				String bankUrl = txWxUser.getCardUrl().replace("https://file.mievie.com/file", saveFilePath);
//				JSONObject bankJosn = new CardDistinguish().bankDistinguish(bankUrl);
//				if(0==bankJosn.getIntValue("bank_card_type")){
//					json.put("c", -1);
//					json.put("m", "修改失败,结算卡照片不能识别");
//					b = false;
//				}else{
//					if(!txWxUser.getCardNumber().equals(bankJosn.getString("number").replaceAll("\\s*", ""))){
//						json.put("c", -1);
//						json.put("m", "修改失败,结算卡与上传照片不一致");
//						b = false;
//					}
//				}
//			}
			
			if(b){
				JSONObject json1 = weiXinService.updateHxt(txWxUser);
				if("1000".equals(json1.getString("respCode"))){
					txWxUser.setCheckState(1);
					txWxUser.setState(1);
					txWxUser.setMerId(json1.getString("merId"));
					txWxUser.setMsg("无");
					int flag = txWxUserService.updateTxWxUserByOpenId(txWxUser);
					if(flag>0){
						json.put("c", 0);
						json.put("m", "修改成功");
						request.getSession().setAttribute(SessionName.ADMIN_USER, txWxUser);
					}else{
						json.put("c", -1);
						json.put("m", "修改失败");
					}
				}else{
//					txWxUser.setCheckState(0);
//					txWxUser.setState(0);
					txWxUser.setMsg(json1.getString("respMsg"));
					txWxUserService.updateTxWxUserByOpenId(txWxUser);
					json.put("c", -1);
					json.put("m", json1.getString("respMsg"));
				}
			}
		}catch(Exception e){
			json.put("c", -1);
			json.put("d", new JSONObject());
			json.put("m", "修改失败，系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	
	
	/**
	 * 支付
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPay", method = RequestMethod.GET)
	public String toPay(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		try{
			Object obj = request.getSession().getAttribute(SessionName.ADMIN_USER);
			if(obj!=null){
				TxWxUser wxUser = (TxWxUser)obj;
				TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
				txWxUserBankNo.setWxUserId(wxUser.getId());
				List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
				if(list!=null&&list.size()==1){
					model.addAttribute("txWxUserBankNo", list.get(0));
				}
				model.addAttribute("list", list);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/tx/pay_one";
	}
	/**
	 * 交易记录
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/showDBOrder", method = RequestMethod.GET)
	public String showDBOrder(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		Integer id = RequestHandler.getInteger(request, "id");
		try{
			TxWxUser wxUser = txWxUserService.getTxWxUserById(id);
			wxUser.setRealName(weiXinService.chineseName(wxUser.getRealName()));
			model.addAttribute("wxUser", wxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/orderlist_db";
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
		String openId = RequestHandler.getString(request, "openId");
		try{
			TxWxUser wxUser = txWxUserService.getTxWxUserByOpenId(openId);
			model.addAttribute("wxUser", wxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/orderlist";
	}
	
	/**
	 * 修改个人信息
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUpdateInfo", method = RequestMethod.GET)
	public String toUpdateInfo(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		String openId = RequestHandler.getString(request, "openId");
		try{
			TxWxUser wxUser = txWxUserService.getTxWxUserByOpenId(openId);
			model.addAttribute("wxUser", wxUser);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/updateInfo";
	}
	
	/**
	 * 开通回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backTransUrl")
	public String backTransUrl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.backTransUrl(request);
		return  null;
	}
	
	/**
	 * 支付回调（代付）
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backUrlDF")
	public String backUrlDF(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.backUrlDF(request);
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
	@RequestMapping(value = "/backUrlTrans")
	public String backUrlTrans(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.backUrlTrans(request);
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
		return  "/wx/addCard";
	}
	
	/**
	 * 开通回调
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/frontTransUrl")
	public String frontTransUrl(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		txWxUserBankNoService.frontTransUrl(request);
		return  "/wx/paySuccess";
	}
	
	/**
	 * 支付
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public String pay(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String accNo = RequestHandler.getString(request, "accNo");
		String smsCode = RequestHandler.getString(request, "smsCode");
		Integer money = RequestHandler.getInteger(request, "money");
		try{
			if(StringUtils.isNotBlank(accNo)&&money!=null&&StringUtils.isNotBlank(smsCode)){
				TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
				if(wxUser.getState().intValue()==1&&wxUser.getCheckState().intValue()==1){
					TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
//					String param = MemcacheUtil.getString("C_"+accNo);
					String param = SessionName.MAPORDERNO.get("C_"+accNo);
					if(StringUtils.isNotBlank(param)){
						Map<String, String> map = txWxUserBankNoService.pay(wxUser, param.split("_")[0], param.split("_")[1], txWxUserBankNo, money*100+"", smsCode);
						if(map!=null&&"00".equals(map.get("respCode"))){
							writeSuccessMsg("交易成功，交易可能存在延时，请耐心等待！", null, response);
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
	@RequestMapping(value = "/addCard", method = RequestMethod.GET)
	public String addCard(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String accNo = RequestHandler.getString(request, "accNo");
		try{
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setAccNo(accNo);
			txWxUserBankNo.setWxUserId(wxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			if(list!=null&&list.size()>0){
				return  "/wx/exitCard";
			}else{//获取token
				String orderId = new MakeImei().getCode();
				Date d = new Date();
				//先写入库
				TxWxOrder txWxOrder = new TxWxOrder();
				txWxOrder.setAccNo(accNo);
				txWxOrder.setWxUserId(wxUser.getId());
				txWxOrder.setCreateTime(d);
				txWxOrder.setOrderCode(orderId);
				txWxOrder.setStyle(0);
				txWxOrder.setMoney(0*100L);
				txWxOrder.setWxUserName(wxUser.getRealName());
				txWxOrder.setPromoterId(wxUser.getPromoterId());
				txWxOrderService.insertTxWxOrder(txWxOrder);
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				String txnTime = sf.format(d);
				String html = txWxUserBankNoService.getUnionPayToken(wxUser, orderId, txnTime,accNo);
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pw.print(html);
				pw.flush();
				pw.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 我的卡列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myCardList", method = RequestMethod.GET)
	public String myCardList(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		try{
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setWxUserId(wxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			model.addAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/cardList";
	}
	
	/** 发验证码 **/
	@RequestMapping(value = "/vercode", method = RequestMethod.POST)
	public String vercode(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String accNo =  RequestHandler.getString(request, "accNo");
		Integer money =  RequestHandler.getInteger(request, "money");
		try{
			if(StringUtils.isNotBlank(accNo)&&money!=null){
				
				List<TxRate> listRate = txRateService.getTxRateList(new TxRate());
				
				TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
				TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoByAccNo(accNo);
				String orderId = new MakeImei().getCode();
				Date d = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				String txnTime = sf.format(d);
				//先写入库
				TxWxOrder txWxOrder = new TxWxOrder();
				txWxOrder.setAccNo(accNo);
				txWxOrder.setWxUserId(wxUser.getId());
				txWxOrder.setCreateTime(d);
				txWxOrder.setOrderCode(orderId);
				txWxOrder.setStyle(1);
				txWxOrder.setTxnTime(txnTime);
				txWxOrder.setWxUserName(wxUser.getRealName());
				txWxOrder.setMoney(money*100L);
				txWxOrder.setPromoterId(wxUser.getPromoterId());
				txWxOrder.setTwoPromoterId(wxUser.getTwoPromoterId());
				
//				BigDecimal bg1 = new BigDecimal(money*100L);
//				if(wxUser.getTwoPromoterId()!=null){
//					long two_rate = (bg1.multiply(listRate.get(0).getTwoPromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//					long one_rate = (bg1.multiply(listRate.get(0).getOnePromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//					long dev_rate = (bg1.multiply(listRate.get(0).getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//					txWxOrder.setTwo_rate(two_rate);
//					txWxOrder.setOne_rate(one_rate);
//					txWxOrder.setDev_rate(dev_rate);
//				}else{
//					long one_rate = (bg1.multiply(listRate.get(0).getOnePromoterRate().add(listRate.get(0).getTwoPromoterRate()))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//					long dev_rate = (bg1.multiply(listRate.get(0).getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//					txWxOrder.setOne_rate(one_rate);
//					txWxOrder.setDev_rate(dev_rate);
//				}
				
				txWxOrderService.insertTxWxOrder(txWxOrder);
				SessionName.MAPORDERNO.put("C_"+accNo, orderId+"_"+txnTime);
//				MemcacheUtil.add("C_"+accNo, 60, orderId+"_"+txnTime);
				boolean b = txWxUserBankNoService.vercode(wxUser, orderId, txnTime, txWxUserBankNo, money*100+"");
				if(b){
					writeSuccessMsg("发送成功", null, response);
				}else{
					SessionName.MAPORDERNO.remove("C_"+accNo);
//					MemcacheUtil.delete("C_"+accNo);
					txWxOrderService.deleteTxWxOrderByOrderCode(orderId);
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
	/** 发验证码 **/
	@RequestMapping(value = "/checkCard", method = RequestMethod.POST)
	public String checkCard(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String accNo = RequestHandler.getString(request, "accNo");
		try{
			TxWxUser wxUser = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
			//查询银行卡是否已经获得token
			TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
			txWxUserBankNo.setAccNo(accNo);
			txWxUserBankNo.setWxUserId(wxUser.getId());
			List<TxWxUserBankNo> list = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
			if(list!=null&&list.size()>0){
				writeErrorMsg("此卡已经添加", "", response);
			}else{
				writeSuccessMsg("不存在，可以添加", null, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 交易记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
	public String getOrderList(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception
	{
		Integer wxUserId = RequestHandler.getInteger(request, "wxUserId");
		String startTime = RequestHandler.getString(request, "startTime");
		String endTime = RequestHandler.getString(request, "endTime");
		String orderCode = RequestHandler.getString(request, "orderCode");
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try
		{	
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sf1 = new SimpleDateFormat("yy-MM-dd");
			
			
			TxWxOrder txWxOrder = new TxWxOrder();
			
			txWxOrder.setWxUserId(wxUserId);
			txWxOrder.setStyle(1);
			
			if(StringUtils.isNotBlank(orderCode)){
				txWxOrder.setOrderCode(orderCode);
			}
			if(StringUtils.isNotBlank(startTime)){
				txWxOrder.setStartTime(sf.parse(startTime+" 00:00:00"));
			}
			if(StringUtils.isNotBlank(endTime)){
				txWxOrder.setEndTime(sf.parse(endTime+" 23:59:59"));
			}
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			
			
			txWxOrder.setOffset(from);
			txWxOrder.setLimit(rowCount);
			
			txWxOrder.setSort("id");
			txWxOrder.setOrder("desc");
			
			int totalResults = txWxOrderService.getTxWxOrderCount(txWxOrder);
			Long totalMoney = txWxOrderService.getTxWxOrderMoney(txWxOrder);
			List<TxWxOrder> list = txWxOrderService.getTxWxOrderListByPage(txWxOrder);
			txWxOrder.setDfState(1);
			txWxOrder.setXfState(1);
			TxWxOrder sum_money = txWxOrderService.getTxWxOrderMoneyRate(txWxOrder);
			if(list!=null&&list.size()>0){
				for(TxWxOrder obj:list){
					JSONObject jsons = new JSONObject();
					jsons.put("orderCode", obj.getOrderCode());
					jsons.put("money", super.getMoney(obj.getMoney()));
					jsons.put("accNo", obj.getAccNo());
					if(obj.getXfState().intValue()==1){
						jsons.put("xfState", "已消费");
					}else{
						jsons.put("xfState", "未消费");
					}
					if(obj.getDfState().intValue()==1){
						jsons.put("dfState", "已代付");
					}else{
						jsons.put("dfState", "未代付");
					}
					if(obj.getXfState().intValue()==1&&obj.getDfState().intValue()==1){
						jsons.put("state", "交易完成");
					}else{
						jsons.put("state", "交易失败");
					}
					if(ConfigConstants.DB_ROLE_ID.equals(adminUser.getRole_id()+"")){//一级
						if(obj.getDfState()==1&&obj.getXfState()==1){
							jsons.put("rate", super.getMoney(obj.getOne_rate()));
						}else{
							jsons.put("rate", 0);
						}
					}else{
						if(obj.getDfState()==1&&obj.getXfState()==1){
							jsons.put("rate", super.getMoney(obj.getTwo_rate()));
						}else{
							jsons.put("rate", 0);
						}
					}
					jsons.put("createTimeStr", sf.format(obj.getCreateTime()));
					jsons.put("createTimeStr1", sf1.format(obj.getCreateTime()));
					jsons.put("id", obj.getId());
					array.add(jsons);
				}
				json.put("message", "ok");
				json.put("totalResults", totalResults);
				json.put("totalMoney", super.getMoney(totalMoney));
				if(ConfigConstants.DB_ROLE_ID.equals(adminUser.getRole_id()+"")){//一级
					json.put("totalMoneyRate", super.getMoney(sum_money.getOne_rate()));
				}else{
					json.put("totalMoneyRate", super.getMoney(sum_money.getTwo_rate()));
				}
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
	 * 交易记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getOrderListByCustumer", method = RequestMethod.GET)
	public String getOrderListByCustumer(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception
	{
		Integer wxUserId = RequestHandler.getInteger(request, "wxUserId");
		String orderCode = RequestHandler.getString(request, "orderCode");
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try
		{	
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sf1 = new SimpleDateFormat("yy-MM-dd");
			
			
			TxSellingOrder txWxOrder = new TxSellingOrder();
			
			txWxOrder.setWxUserId(wxUserId);
			txWxOrder.setState(1);
			
			if(StringUtils.isNotBlank(orderCode)){
				txWxOrder.setCode(orderCode);
			}
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			
			txWxOrder.setState(1);
			txWxOrder.setOffset(from);
			txWxOrder.setLimit(rowCount);
			
			txWxOrder.setSort("id");
			txWxOrder.setOrder("desc");
			
//			int totalResults = txSellingOrderService.getTxSellingOrderCount(txWxOrder);
//			Long totalMoney = txWxOrderService.getTxWxOrderMoney(txWxOrder);
			List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderList(txWxOrder);
			
			if(list!=null&&list.size()>0){
				for(TxSellingOrder obj:list){
					
					TxDfOrder txDfOrder = new TxDfOrder();
					txDfOrder.setOrderCode(obj.getCode());
					List<TxDfOrder> listddd = txDfOrderService.getTxDfOrderList(txDfOrder);
					if(listddd!=null&&listddd.size()>0){
						txDfOrder = listddd.get(0);
					}else{
						txDfOrder = null;
					}
					
					JSONObject jsons = new JSONObject();
//					jsons.put("orderCode", obj.getOrderCode());
					jsons.put("money", super.getMoney(obj.getMoney()));
					jsons.put("accNo", obj.getAccNo());
					if(txDfOrder!=null&&txDfOrder.getId()!=null){
						if(txDfOrder.getState()==0){
							jsons.put("state", "申请退款中");
						}else{
							jsons.put("state", "已提前退款");
						}
					}else{
						if(obj.getRefundState()!=null&&obj.getRefundState().intValue()==1){
							if(obj.getRefundTime().before(obj.getEndTime())){
								jsons.put("state", "已提前退款");
							}else{
								jsons.put("state", "到期退款");
							}
						}else{
							if(obj.getEndTime().after(new Date())){
								jsons.put("state", "赚钱中");
							}else{
								jsons.put("state", "已到期");
							}
						}
					}
					jsons.put("createTimeStr", sf.format(obj.getCreateTime()));
					jsons.put("createTimeStr1", sf1.format(obj.getCreateTime()));
					jsons.put("id", obj.getId());
					array.add(jsons);
				}
				json.put("message", "ok");
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
	 * 我的朋友
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMyFriend", method = RequestMethod.GET)
	public String toMyParent(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		super.getJsticket(request);
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		try{
			if(ConfigConstants.DB_ROLE_ID.equals(String.valueOf(adminUser.getRole_id()))){
				
			}else{
				return  "/wx/tip";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return  "/wx/myFriend";
	}
	/**
	 * 我的朋友
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myFriend", method = RequestMethod.GET)
	public String myFriend(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		ManageAdminUser adminUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		
		Integer state = RequestHandler.getInteger(request, "state");
		String mobile = RequestHandler.getString(request, "mobile");
		String realName = RequestHandler.getString(request, "realName");
		
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try{
			List<TxRate> listRate = txRateService.getTxRateList(new TxRate());
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
			if(ConfigConstants.DB_ROLE_ID.equals(String.valueOf(adminUser.getRole_id()))){
				ManageAdminUser manageAdminUser = new ManageAdminUser();
				manageAdminUser.setParent_id(adminUser.getAdminId());
				manageAdminUser.setOffset(from);
				manageAdminUser.setState(state);
				manageAdminUser.setLimit(rowCount);
				manageAdminUser.setSort("createTime");
				manageAdminUser.setOrder("desc");
				
				if(StringUtils.isNotBlank(mobile)){
					manageAdminUser.setMobile(mobile);
				}
				if(StringUtils.isNotBlank(realName)){
					manageAdminUser.setRealName(realName);
				}
				
				ResponseList<ManageAdminUser> list = manageadminuserService.getManageAdminUserList(manageAdminUser);
				
				
				TxWxOrder txWxOrder1 = new TxWxOrder();
				txWxOrder1.setDfState(1);
				txWxOrder1.setPromoter_state(state);
				txWxOrder1.setXfState(1);
				if(StringUtils.isNotBlank(realName)){
					txWxOrder1.setRealName(realName);
				}
				if(StringUtils.isNotBlank(mobile)){
					txWxOrder1.setMobile(mobile);
				}
				txWxOrder1.setParentId(adminUser.getAdminId());
				Long moneyAll = txWxOrderService.getTxWxOrderMoneyByTwoPromoter(txWxOrder1);
				BigDecimal bg1 = new BigDecimal(moneyAll);
				long two1 = (bg1.multiply(listRate.get(0).getTwoPromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				long two2 = (bg1.multiply(listRate.get(0).getOnePromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				
				if(list!=null){
					Iterator<Object> it = list.iterator();
					while(it.hasNext()){
						ManageAdminUser obj = (ManageAdminUser)it.next();
						JSONObject jsons = new JSONObject();
						
						jsons.put("mobile", obj.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
						jsons.put("realName", weiXinService.chineseName(obj.getRealName()));
						
						TxWxOrder txWxOrder = new TxWxOrder();
						txWxOrder.setDfState(1);
						txWxOrder.setXfState(1);
						txWxOrder.setTwoPromoterId(obj.getAdminId());
						Long money = txWxOrderService.getTxWxOrderMoney(txWxOrder);
						jsons.put("money", super.getMoney(money));
						
						
						BigDecimal bg = new BigDecimal(money);
						long two = (bg.multiply(listRate.get(0).getTwoPromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						jsons.put("profit", super.getMoney(two));
						
						long one = (bg.multiply(listRate.get(0).getOnePromoterRate())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
						jsons.put("profit_one", super.getMoney(one));
						
						array.add(jsons);
					}
					json.put("message", "ok");
					json.put("fansCount", super.getMoney(moneyAll));
					json.put("poneyPCount", super.getMoney(two1));
					json.put("moneyCount", super.getMoney(two2));
					json.put("items", array);
				}else{
					json.put("message", "end");
				}
			}else{
				return  "/wx/tip";
			}
		}catch(Exception e){
			json.put("message", "end");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	/**
	 * 我的朋友
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myFriendSell", method = RequestMethod.GET)
	public String myFriendSell(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		TxWxUser wxUser1 = (TxWxUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		
		Integer state = RequestHandler.getInteger(request, "state");
		String mobile = RequestHandler.getString(request, "mobile");
		String realName = RequestHandler.getString(request, "realName");
		
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		try{
//			List<TxRate> listRate = txRateService.getTxRateList(new TxRate());
//			List<TxPayRate> listPayRate = txPayRateService.getTxPayRateList(new TxPayRate());
			// 分页开始
			Integer pageNo = RequestHandler.getPageNo(request, "pageNo");
			Integer rowCount = RequestHandler.getPageSize(request, "rowCount");
			int from = RequestHandler.getFromByPage(pageNo, rowCount);
				
			TxWxUser wxUser = new TxWxUser();
			wxUser.setPromoterId(wxUser1.getId());
			wxUser.setOffset(from);
			wxUser.setState(state);
			wxUser.setLimit(rowCount);
			wxUser.setSort("createTime");
			wxUser.setOrder("desc");
				
			if(StringUtils.isNotBlank(mobile)){
				wxUser.setMobile(mobile);
			}
			if(StringUtils.isNotBlank(realName)){
				wxUser.setRealName(realName);
			}
			
			List<TxWxUser> list = txWxUserService.getTxWxUserListByPage(wxUser);
				
				
			TxSellingOrder txSellingOrder1 = new TxSellingOrder();
			txSellingOrder1.setState(1);
			txSellingOrder1.setPromoter_state(state);
			if(StringUtils.isNotBlank(realName)){
				txSellingOrder1.setRealName(realName);
			}
			if(StringUtils.isNotBlank(mobile)){
				txSellingOrder1.setMobile(mobile);
			}
			txSellingOrder1.setPromoterId(wxUser1.getId());
			TxSellingOrder txSellingOrder = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder1);
			if(list!=null&&list.size()>0){
				for(TxWxUser obj:list){
					JSONObject jsons = new JSONObject();
					
					jsons.put("mobile", obj.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
					jsons.put("realName", weiXinService.chineseName(obj.getRealName()));
					
					TxSellingOrder txSellingOrder2 = new TxSellingOrder();
					txSellingOrder2.setState(1);
					txSellingOrder2.setParentId(obj.getId());
					TxSellingOrder txSellingOrdersss = txSellingOrderService.getSellingOrderByTwoPromoter(txSellingOrder1);
					jsons.put("money", super.getMoney(txSellingOrdersss.getMoney()));
					
					
					jsons.put("profit", super.getMoney(Long.valueOf(txSellingOrdersss.getOneRate())));
					
					jsons.put("profit_one", super.getMoney(Long.valueOf(txSellingOrdersss.getTwoRate())));
					
					array.add(jsons);
				}
				json.put("message", "ok");
				json.put("fansCount", super.getMoney(txSellingOrder.getMoney()));
				json.put("poneyPCount", super.getMoney(Long.valueOf(txSellingOrder.getOneRate())));
				json.put("moneyCount", super.getMoney(Long.valueOf(txSellingOrder.getTwoRate())));
				json.put("items", array);
			}else{
				json.put("message", "end");
			}
		}catch(Exception e){
			json.put("message", "end");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.getWriter().write(json.toString());
		return  null;
	}
	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/downFile", method = RequestMethod.GET)
	public String downFile(HttpServletResponse response,HttpServletRequest request, Model model) throws Exception{
		String name = RequestHandler.getString(request, "name");
		String link = RequestHandler.getString(request, "link");
		model.addAttribute("name", name);
		model.addAttribute("link", link);
		return  "/common/excelup";
	}

	@RequestMapping("/backUri")
	public String backUri(HttpServletResponse response,HttpServletRequest request) {
		String weixinCode = RequestHandler.getString(request, "code");
		String state = RequestHandler.getString(request, "state");
		System.out.println("----------------state------------------"+state);
		try{
			JSONObject json = GetOpenIdUntil.getOpenId(ConfigConstants.APPID,weixinCode,ConfigConstants.APPSECRET);
			System.out.println("----------------json------------------"+json.toString());
			if(StringUtils.isNotBlank(json.getString("openid"))){
				String[] states = state.split(",");
				if("hezuo".equals(states[0])){//银行卡
					return  "redirect:/weixin/dbIndex?openId="+ json.getString("openid");
				}
				boolean b = weiXinService.loginWxCust(json.getString("openid"), request);
				if(b){
					if("pay".equals(states[0])){//缴费
						return  "redirect:/weixin/toPay";
					}else if("updateinfo".equals(states[0])){//个人中心
						return  "redirect:/weixin/toUpdateInfo?openId="+ json.getString("openid");
					}else if("myorder".equals(states[0])){//我的订单
						return  "redirect:/weixin/toMyOrder?openId="+ json.getString("openid");
					}else if("card".equals(states[0])){//银行卡
						return  "redirect:/weixin/myCardList?openId="+ json.getString("openid");
					} 
				}else{
					boolean b1 = weiXinService.loginWxCust1(json.getString("openid"), request);
					if(b1){
						if("updateinfo".equals(states[0])){//个人中心
							return  "redirect:/weixin/toUpdateInfo?openId="+ json.getString("openid");
						}
					}else{
						return  "/wx/tip";
					}
				}
			}else{
				return  "/wx/tip";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取客户openid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getToken")
	public String getToken(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		String state = RequestHandler.getString(request, "state");
		model.addAttribute("state", state);
		return "/common/index";
	}
	
}
