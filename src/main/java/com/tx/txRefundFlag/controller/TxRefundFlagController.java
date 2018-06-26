package com.tx.txRefundFlag.controller;

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
import com.tx.txRefundFlag.model.TxRefundFlag;
import com.tx.txRefundFlag.service.TxRefundFlagService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-05-07 11:17:54
 */
@Controller
@RequestMapping("/txRefundFlag")
public class TxRefundFlagController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxRefundFlagController.class); //Logger
	
	@Autowired
	private TxRefundFlagService txRefundFlagService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRefundFlag/txRefundFlagIndex";
	}
	@Permission(value = "/txRefundFlag/addTxRefundFlag")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRefundFlag/txRefundFlagAdd";
	}
	@Permission(value = "/txRefundFlag/updateTxRefundFlag")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxRefundFlag txRefundFlag = txRefundFlagService.getTxRefundFlagById(id);
		model.addAttribute("txRefundFlag", txRefundFlag);
		return "/txRefundFlag/txRefundFlagUpdate";
	}
	@Permission(value = "/txRefundFlag/getTxRefundFlagList")
	@RequestMapping(value = "/getTxRefundFlagList", method = RequestMethod.GET)
	public void getTxRefundFlagList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundFlag txRefundFlag = requst2Bean(request,TxRefundFlag.class);
		PageHelper.offsetPage(txRefundFlag.getOffset(), txRefundFlag.getLimit());
		if(txRefundFlag.getSort() != null && txRefundFlag.getOrder() != null){
			PageHelper.orderBy(txRefundFlag.getSort() +" "+ txRefundFlag.getOrder());
		}			
		List<TxRefundFlag> txRefundFlagList = txRefundFlagService.getTxRefundFlagList(txRefundFlag);
		PageInfo<TxRefundFlag> pageInfo = new PageInfo<TxRefundFlag>(txRefundFlagList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txRefundFlag/addTxRefundFlag")
	@RequestMapping(value = "/addTxRefundFlag", method = RequestMethod.POST)
	public void addTxRefundFlag(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundFlag txRefundFlag = requst2Bean(request,TxRefundFlag.class);
		txRefundFlagService.insertTxRefundFlag(txRefundFlag);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txRefundFlag/updateTxRefundFlag")
	@RequestMapping(value = "/updateTxRefundFlag", method = RequestMethod.POST)
	public void updateTxRefundFlag(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundFlag txRefundFlag = requst2Bean(request,TxRefundFlag.class);
		int count = txRefundFlagService.updateTxRefundFlagById(txRefundFlag);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txRefundFlag/removeTxRefundFlag")
	@RequestMapping(value = "/removeTxRefundFlag", method = RequestMethod.POST)
	public void removeTxRefundFlag(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txRefundFlagService.deleteTxRefundFlagById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txRefundFlag/removeAllTxRefundFlag")
	@RequestMapping(value = "/removeAllTxRefundFlag", method = RequestMethod.POST)
	public void removeAllTxRefundFlag(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txRefundFlagService.deleteTxRefundFlagById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
