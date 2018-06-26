package com.tx.txPaynumberMsg.controller;

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
import com.tx.txPaynumberMsg.model.TxPaynumberMsg;
import com.tx.txPaynumberMsg.service.TxPaynumberMsgService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-07 13:36:06
 */
@Controller
@RequestMapping("/txPaynumberMsg")
public class TxPaynumberMsgController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxPaynumberMsgController.class); //Logger
	
	@Autowired
	private TxPaynumberMsgService txPaynumberMsgService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPaynumberMsg/txPaynumberMsgIndex";
	}
	@Permission(value = "/txPaynumberMsg/addTxPaynumberMsg")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPaynumberMsg/txPaynumberMsgAdd";
	}
	@Permission(value = "/txPaynumberMsg/updateTxPaynumberMsg")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxPaynumberMsg txPaynumberMsg = txPaynumberMsgService.getTxPaynumberMsgById(id);
		model.addAttribute("txPaynumberMsg", txPaynumberMsg);
		return "/txPaynumberMsg/txPaynumberMsgUpdate";
	}
	@Permission(value = "/txPaynumberMsg/getTxPaynumberMsgList")
	@RequestMapping(value = "/getTxPaynumberMsgList", method = RequestMethod.GET)
	public void getTxPaynumberMsgList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPaynumberMsg txPaynumberMsg = requst2Bean(request,TxPaynumberMsg.class);
		PageHelper.offsetPage(txPaynumberMsg.getOffset(), txPaynumberMsg.getLimit());
		if(txPaynumberMsg.getSort() != null && txPaynumberMsg.getOrder() != null){
			PageHelper.orderBy(txPaynumberMsg.getSort() +" "+ txPaynumberMsg.getOrder());
		}			
		List<TxPaynumberMsg> txPaynumberMsgList = txPaynumberMsgService.getTxPaynumberMsgList(txPaynumberMsg);
		PageInfo<TxPaynumberMsg> pageInfo = new PageInfo<TxPaynumberMsg>(txPaynumberMsgList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txPaynumberMsg/addTxPaynumberMsg")
	@RequestMapping(value = "/addTxPaynumberMsg", method = RequestMethod.POST)
	public void addTxPaynumberMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPaynumberMsg txPaynumberMsg = requst2Bean(request,TxPaynumberMsg.class);
		txPaynumberMsg.setCreateTime(new Date());
		txPaynumberMsgService.insertTxPaynumberMsg(txPaynumberMsg);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txPaynumberMsg/updateTxPaynumberMsg")
	@RequestMapping(value = "/updateTxPaynumberMsg", method = RequestMethod.POST)
	public void updateTxPaynumberMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPaynumberMsg txPaynumberMsg = requst2Bean(request,TxPaynumberMsg.class);
		int count = txPaynumberMsgService.updateTxPaynumberMsgById(txPaynumberMsg);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txPaynumberMsg/removeTxPaynumberMsg")
	@RequestMapping(value = "/removeTxPaynumberMsg", method = RequestMethod.POST)
	public void removeTxPaynumberMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txPaynumberMsgService.deleteTxPaynumberMsgById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txPaynumberMsg/removeAllTxPaynumberMsg")
	@RequestMapping(value = "/removeAllTxPaynumberMsg", method = RequestMethod.POST)
	public void removeAllTxPaynumberMsg(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txPaynumberMsgService.deleteTxPaynumberMsgById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
