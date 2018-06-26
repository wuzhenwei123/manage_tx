package com.tx.txWxUser.controller;

import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.perm.Permission;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.wx.service.WxTemplateMsg;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-01 13:47:41
 */
@Controller
@RequestMapping("/txWxUser")
public class TxWxUserController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxWxUserController.class); //Logger
	
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private WxTemplateMsg wxTemplateMsg = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxUser/txWxUserIndex";
	}
	
	@RequestMapping(value = "/indexFriend", method = RequestMethod.GET)
	public String indexFriend(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxUser/txWxUserIndexFriend";
	}
	
	@Permission(value = "/txWxUser/addTxWxUser")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxUser/txWxUserAdd";
	}
	@Permission(value = "/txWxUser/updateTxWxUser")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxWxUser txWxUser = txWxUserService.getTxWxUserById(id);
		model.addAttribute("txWxUser", txWxUser);
		return "/txWxUser/txWxUserUpdate";
	}
	
	@RequestMapping(value = "/showID/{id}", method = RequestMethod.GET)
	public String showID(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxWxUser txWxUser = txWxUserService.getTxWxUserById(id);
		model.addAttribute("txWxUser", txWxUser);
		return "/txWxUser/showID";
	}
	@RequestMapping(value = "/showCard/{id}", method = RequestMethod.GET)
	public String showCard(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxWxUser txWxUser = txWxUserService.getTxWxUserById(id);
		model.addAttribute("txWxUser", txWxUser);
		return "/txWxUser/showCard";
	}
	@Permission(value = "/txWxUser/getTxWxUserList")
	@RequestMapping(value = "/getTxWxUserList", method = RequestMethod.GET)
	public void getTxWxUserList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.DB_ROLE_ID).intValue()){
			txWxUser.setPromoterId(loginUser.getAdminId());
			txWxUser.setTwoPromoterId(-1);
		}else if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.TWO_DB_ROLE_ID).intValue()){
			txWxUser.setTwoPromoterId(loginUser.getAdminId());
		}
		PageHelper.offsetPage(txWxUser.getOffset(), txWxUser.getLimit());
		if(txWxUser.getSort() != null && txWxUser.getOrder() != null){
			PageHelper.orderBy(txWxUser.getSort() +" "+ txWxUser.getOrder());
		}			
		List<TxWxUser> txWxUserList = txWxUserService.getTxWxUserList(txWxUser);
		PageInfo<TxWxUser> pageInfo = new PageInfo<TxWxUser>(txWxUserList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txWxUser/getTxWxUserListFriend")
	@RequestMapping(value = "/getTxWxUserListFriend", method = RequestMethod.GET)
	public void getTxWxUserListFriend(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		txWxUser.setParentId(loginUser.getAdminId());
		PageHelper.offsetPage(txWxUser.getOffset(), txWxUser.getLimit());
		if(txWxUser.getSort() != null && txWxUser.getOrder() != null){
			PageHelper.orderBy(txWxUser.getSort() +" "+ txWxUser.getOrder());
		}			
		List<TxWxUser> txWxUserList = txWxUserService.getTxWxUserList(txWxUser);
		PageInfo<TxWxUser> pageInfo = new PageInfo<TxWxUser>(txWxUserList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txWxUser/addTxWxUser")
	@RequestMapping(value = "/addTxWxUser", method = RequestMethod.POST)
	public void addTxWxUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		txWxUserService.insertTxWxUser(txWxUser);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txWxUser/updateTxWxUser")
	@RequestMapping(value = "/updateTxWxUser", method = RequestMethod.POST)
	public void updateTxWxUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		int count = txWxUserService.updateTxWxUserById(txWxUser);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txWxUser/removeTxWxUser")
	@RequestMapping(value = "/removeTxWxUser", method = RequestMethod.POST)
	public void removeTxWxUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txWxUserService.deleteTxWxUserById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@RequestMapping(value = "/setState", method = RequestMethod.POST)
	public void setState(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUser txWxUser = requst2Bean(request,TxWxUser.class);
		int flag = txWxUserService.updateTxWxUserById(txWxUser);
		if(flag>0){
			txWxUser = txWxUserService.getTxWxUserById(txWxUser.getId());
			wxTemplateMsg.sendCheckTempltMsg(txWxUser.getOpenId(), txWxUser.getRealName());
		}
		writeSuccessMsg("审核成功", null, response);
	}
	@Permission(value = "/txWxUser/removeAllTxWxUser")
	@RequestMapping(value = "/removeAllTxWxUser", method = RequestMethod.POST)
	public void removeAllTxWxUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txWxUserService.deleteTxWxUserById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
