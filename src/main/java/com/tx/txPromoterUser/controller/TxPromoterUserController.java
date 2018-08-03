package com.tx.txPromoterUser.controller;

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
import com.tx.txPromoterUser.model.TxPromoterUser;
import com.tx.txPromoterUser.service.TxPromoterUserService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-08-03 10:02:20
 */
@Controller
@RequestMapping("/txPromoterUser")
public class TxPromoterUserController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxPromoterUserController.class); //Logger
	
	@Autowired
	private TxPromoterUserService txPromoterUserService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPromoterUser/txPromoterUserIndex";
	}
	@Permission(value = "/txPromoterUser/addTxPromoterUser")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPromoterUser/txPromoterUserAdd";
	}
	@Permission(value = "/txPromoterUser/updateTxPromoterUser")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxPromoterUser txPromoterUser = txPromoterUserService.getTxPromoterUserById(id);
		model.addAttribute("txPromoterUser", txPromoterUser);
		return "/txPromoterUser/txPromoterUserUpdate";
	}
	@Permission(value = "/txPromoterUser/getTxPromoterUserList")
	@RequestMapping(value = "/getTxPromoterUserList", method = RequestMethod.GET)
	public void getTxPromoterUserList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPromoterUser txPromoterUser = requst2Bean(request,TxPromoterUser.class);
		PageHelper.offsetPage(txPromoterUser.getOffset(), txPromoterUser.getLimit());
		if(txPromoterUser.getSort() != null && txPromoterUser.getOrder() != null){
			PageHelper.orderBy(txPromoterUser.getSort() +" "+ txPromoterUser.getOrder());
		}			
		List<TxPromoterUser> txPromoterUserList = txPromoterUserService.getTxPromoterUserList(txPromoterUser);
		PageInfo<TxPromoterUser> pageInfo = new PageInfo<TxPromoterUser>(txPromoterUserList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txPromoterUser/addTxPromoterUser")
	@RequestMapping(value = "/addTxPromoterUser", method = RequestMethod.POST)
	public void addTxPromoterUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPromoterUser txPromoterUser = requst2Bean(request,TxPromoterUser.class);
		txPromoterUserService.insertTxPromoterUser(txPromoterUser);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txPromoterUser/updateTxPromoterUser")
	@RequestMapping(value = "/updateTxPromoterUser", method = RequestMethod.POST)
	public void updateTxPromoterUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPromoterUser txPromoterUser = requst2Bean(request,TxPromoterUser.class);
		int count = txPromoterUserService.updateTxPromoterUserById(txPromoterUser);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txPromoterUser/removeTxPromoterUser")
	@RequestMapping(value = "/removeTxPromoterUser", method = RequestMethod.POST)
	public void removeTxPromoterUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txPromoterUserService.deleteTxPromoterUserById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txPromoterUser/removeAllTxPromoterUser")
	@RequestMapping(value = "/removeAllTxPromoterUser", method = RequestMethod.POST)
	public void removeAllTxPromoterUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txPromoterUserService.deleteTxPromoterUserById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
