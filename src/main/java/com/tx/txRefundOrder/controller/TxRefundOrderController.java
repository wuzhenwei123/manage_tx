package com.tx.txRefundOrder.controller;

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
import com.tx.txRefundOrder.model.TxRefundOrder;
import com.tx.txRefundOrder.service.TxRefundOrderService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-05-07 09:08:14
 */
@Controller
@RequestMapping("/txRefundOrder")
public class TxRefundOrderController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxRefundOrderController.class); //Logger
	
	@Autowired
	private TxRefundOrderService txRefundOrderService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRefundOrder/txRefundOrderIndex";
	}
	@Permission(value = "/txRefundOrder/addTxRefundOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRefundOrder/txRefundOrderAdd";
	}
	@Permission(value = "/txRefundOrder/updateTxRefundOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxRefundOrder txRefundOrder = txRefundOrderService.getTxRefundOrderById(id);
		model.addAttribute("txRefundOrder", txRefundOrder);
		return "/txRefundOrder/txRefundOrderUpdate";
	}
	@Permission(value = "/txRefundOrder/getTxRefundOrderList")
	@RequestMapping(value = "/getTxRefundOrderList", method = RequestMethod.GET)
	public void getTxRefundOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundOrder txRefundOrder = requst2Bean(request,TxRefundOrder.class);
		PageHelper.offsetPage(txRefundOrder.getOffset(), txRefundOrder.getLimit());
		if(txRefundOrder.getSort() != null && txRefundOrder.getOrder() != null){
			PageHelper.orderBy(txRefundOrder.getSort() +" "+ txRefundOrder.getOrder());
		}			
		List<TxRefundOrder> txRefundOrderList = txRefundOrderService.getTxRefundOrderList(txRefundOrder);
		PageInfo<TxRefundOrder> pageInfo = new PageInfo<TxRefundOrder>(txRefundOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txRefundOrder/addTxRefundOrder")
	@RequestMapping(value = "/addTxRefundOrder", method = RequestMethod.POST)
	public void addTxRefundOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundOrder txRefundOrder = requst2Bean(request,TxRefundOrder.class);
		txRefundOrder.setCreateTime(new Date());
		txRefundOrderService.insertTxRefundOrder(txRefundOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txRefundOrder/updateTxRefundOrder")
	@RequestMapping(value = "/updateTxRefundOrder", method = RequestMethod.POST)
	public void updateTxRefundOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRefundOrder txRefundOrder = requst2Bean(request,TxRefundOrder.class);
		int count = txRefundOrderService.updateTxRefundOrderById(txRefundOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txRefundOrder/removeTxRefundOrder")
	@RequestMapping(value = "/removeTxRefundOrder", method = RequestMethod.POST)
	public void removeTxRefundOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txRefundOrderService.deleteTxRefundOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txRefundOrder/removeAllTxRefundOrder")
	@RequestMapping(value = "/removeAllTxRefundOrder", method = RequestMethod.POST)
	public void removeAllTxRefundOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txRefundOrderService.deleteTxRefundOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
