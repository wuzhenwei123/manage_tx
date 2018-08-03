package com.wx.controller;

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
import com.tx.txPromoterUser.model.TxPromoterUser;
import com.tx.txPromoterUser.service.TxPromoterUserService;
import com.wx.service.WeiXinService;

@Controller
@RequestMapping("/thirdParty")
public class ThirdPartyContriller extends BaseController{
	
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private TxPromoterUserService txPromoterUserService = null;
	
	/**
	 * login
	 * showShare
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			String openId = RequestHandler.getString(request, "token");
			String promoterToken = RequestHandler.getString(request, "promoterToken");
			if(StringUtils.isNotBlank(promoterToken)){
				TxPromoterUser txPromoterUser = txPromoterUserService.getTxPromoterUserByPromoterToken(promoterToken);
				if(txPromoterUser!=null){
					weiXinService.bindThird(openId, super.getIp(request),txPromoterUser.getUserId(),request);
					return  "redirect:/weixin/dbIndex1?openId="+ openId;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wx/tip";
	}
}
