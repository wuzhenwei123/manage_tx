package com.tx.txSellingOrder.controller;

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
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-04-30 22:59:35
 */
@Controller
@RequestMapping("/txSellingOrder")
public class TxSellingOrderController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxSellingOrderController.class); //Logger
	
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txSellingOrder/txSellingOrderIndex";
	}
	@Permission(value = "/txSellingOrder/addTxSellingOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txSellingOrder/txSellingOrderAdd";
	}
	@Permission(value = "/txSellingOrder/updateTxSellingOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxSellingOrder txSellingOrder = txSellingOrderService.getTxSellingOrderById(id);
		model.addAttribute("txSellingOrder", txSellingOrder);
		return "/txSellingOrder/txSellingOrderUpdate";
	}
	@Permission(value = "/txSellingOrder/getTxSellingOrderList")
	@RequestMapping(value = "/getTxSellingOrderList", method = RequestMethod.GET)
	public void getTxSellingOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		PageHelper.offsetPage(txSellingOrder.getOffset(), txSellingOrder.getLimit());
		if(txSellingOrder.getSort() != null && txSellingOrder.getOrder() != null){
			PageHelper.orderBy(txSellingOrder.getSort() +" "+ txSellingOrder.getOrder());
		}			
		List<TxSellingOrder> txSellingOrderList = txSellingOrderService.getTxSellingOrderList(txSellingOrder);
		PageInfo<TxSellingOrder> pageInfo = new PageInfo<TxSellingOrder>(txSellingOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txSellingOrder/addTxSellingOrder")
	@RequestMapping(value = "/addTxSellingOrder", method = RequestMethod.POST)
	public void addTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		txSellingOrder.setCreateTime(new Date());
		txSellingOrderService.insertTxSellingOrder(txSellingOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txSellingOrder/updateTxSellingOrder")
	@RequestMapping(value = "/updateTxSellingOrder", method = RequestMethod.POST)
	public void updateTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		int count = txSellingOrderService.updateTxSellingOrderById(txSellingOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txSellingOrder/removeTxSellingOrder")
	@RequestMapping(value = "/removeTxSellingOrder", method = RequestMethod.POST)
	public void removeTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txSellingOrderService.deleteTxSellingOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txSellingOrder/removeAllTxSellingOrder")
	@RequestMapping(value = "/removeAllTxSellingOrder", method = RequestMethod.POST)
	public void removeAllTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txSellingOrderService.deleteTxSellingOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
