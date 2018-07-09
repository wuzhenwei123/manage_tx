package com.wx.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.utils.https.HttpUtils;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.tx.txBanner.model.TxBanner;
import com.tx.txBanner.service.TxBannerService;
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.wx.service.KefuService;
import com.wx.service.WeiXinService;
import com.wx.utils.WxMenuUtils;
import com.wx.x0001.vo.send.WxSendTextMsg;

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
	
}
