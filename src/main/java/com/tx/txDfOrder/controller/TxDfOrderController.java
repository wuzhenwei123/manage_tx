package com.tx.txDfOrder.controller;

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
import com.tx.txDfOrder.model.TxDfOrder;
import com.tx.txDfOrder.service.TxDfOrderService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-07-25 10:50:55
 */
@Controller
@RequestMapping("/txDfOrder")
public class TxDfOrderController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxDfOrderController.class); //Logger
	
	@Autowired
	private TxDfOrderService txDfOrderService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfOrder/txDfOrderIndex";
	}
	@Permission(value = "/txDfOrder/addTxDfOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfOrder/txDfOrderAdd";
	}
	@Permission(value = "/txDfOrder/updateTxDfOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxDfOrder txDfOrder = txDfOrderService.getTxDfOrderById(id);
		model.addAttribute("txDfOrder", txDfOrder);
		return "/txDfOrder/txDfOrderUpdate";
	}
	@Permission(value = "/txDfOrder/getTxDfOrderList")
	@RequestMapping(value = "/getTxDfOrderList", method = RequestMethod.GET)
	public void getTxDfOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		PageHelper.offsetPage(txDfOrder.getOffset(), txDfOrder.getLimit());
		if(txDfOrder.getSort() != null && txDfOrder.getOrder() != null){
			PageHelper.orderBy(txDfOrder.getSort() +" "+ txDfOrder.getOrder());
		}			
		List<TxDfOrder> txDfOrderList = txDfOrderService.getTxDfOrderList(txDfOrder);
		PageInfo<TxDfOrder> pageInfo = new PageInfo<TxDfOrder>(txDfOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txDfOrder/addTxDfOrder")
	@RequestMapping(value = "/addTxDfOrder", method = RequestMethod.POST)
	public void addTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		txDfOrder.setCreateTime(new Date());
		txDfOrderService.insertTxDfOrder(txDfOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txDfOrder/updateTxDfOrder")
	@RequestMapping(value = "/updateTxDfOrder", method = RequestMethod.POST)
	public void updateTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		int count = txDfOrderService.updateTxDfOrderById(txDfOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txDfOrder/removeTxDfOrder")
	@RequestMapping(value = "/removeTxDfOrder", method = RequestMethod.POST)
	public void removeTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txDfOrderService.deleteTxDfOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txDfOrder/removeAllTxDfOrder")
	@RequestMapping(value = "/removeAllTxDfOrder", method = RequestMethod.POST)
	public void removeAllTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txDfOrderService.deleteTxDfOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
