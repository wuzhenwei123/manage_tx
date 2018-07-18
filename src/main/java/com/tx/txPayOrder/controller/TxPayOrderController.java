package com.tx.txPayOrder.controller;

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
import com.tx.txPayOrder.model.TxPayOrder;
import com.tx.txPayOrder.service.TxPayOrderService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-07 14:03:06
 */
@Controller
@RequestMapping("/txPayOrder")
public class TxPayOrderController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxPayOrderController.class); //Logger
	
	@Autowired
	private TxPayOrderService txPayOrderService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayOrder/txPayOrderIndex";
	}
	@Permission(value = "/txPayOrder/addTxPayOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayOrder/txPayOrderAdd";
	}
	@Permission(value = "/txPayOrder/updateTxPayOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxPayOrder txPayOrder = txPayOrderService.getTxPayOrderById(id);
		model.addAttribute("txPayOrder", txPayOrder);
		return "/txPayOrder/txPayOrderUpdate";
	}
	@Permission(value = "/txPayOrder/getTxPayOrderList")
	@RequestMapping(value = "/getTxPayOrderList", method = RequestMethod.GET)
	public void getTxPayOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayOrder txPayOrder = requst2Bean(request,TxPayOrder.class);
		PageHelper.offsetPage(txPayOrder.getOffset(), txPayOrder.getLimit());
		if(txPayOrder.getSort() != null && txPayOrder.getOrder() != null){
			PageHelper.orderBy(txPayOrder.getSort() +" "+ txPayOrder.getOrder());
		}			
		List<TxPayOrder> txPayOrderList = txPayOrderService.getTxPayOrderListAndPromeat(txPayOrder);
		PageInfo<TxPayOrder> pageInfo = new PageInfo<TxPayOrder>(txPayOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txPayOrder/addTxPayOrder")
	@RequestMapping(value = "/addTxPayOrder", method = RequestMethod.POST)
	public void addTxPayOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayOrder txPayOrder = requst2Bean(request,TxPayOrder.class);
		txPayOrder.setCreateTime(new Date());
		txPayOrderService.insertTxPayOrder(txPayOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txPayOrder/updateTxPayOrder")
	@RequestMapping(value = "/updateTxPayOrder", method = RequestMethod.POST)
	public void updateTxPayOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayOrder txPayOrder = requst2Bean(request,TxPayOrder.class);
		int count = txPayOrderService.updateTxPayOrderById(txPayOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txPayOrder/removeTxPayOrder")
	@RequestMapping(value = "/removeTxPayOrder", method = RequestMethod.POST)
	public void removeTxPayOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txPayOrderService.deleteTxPayOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txPayOrder/removeAllTxPayOrder")
	@RequestMapping(value = "/removeAllTxPayOrder", method = RequestMethod.POST)
	public void removeAllTxPayOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txPayOrderService.deleteTxPayOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
