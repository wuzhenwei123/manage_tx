package com.tx.txDfRate.controller;

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
import com.tx.txDfRate.model.TxDfRate;
import com.tx.txDfRate.service.TxDfRateService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-10 18:38:15
 */
@Controller
@RequestMapping("/txDfRate")
public class TxDfRateController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxDfRateController.class); //Logger
	
	@Autowired
	private TxDfRateService txDfRateService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfRate/txDfRateIndex";
	}
	@Permission(value = "/txDfRate/addTxDfRate")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfRate/txDfRateAdd";
	}
	@Permission(value = "/txDfRate/updateTxDfRate")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxDfRate txDfRate = txDfRateService.getTxDfRateById(id);
		model.addAttribute("txDfRate", txDfRate);
		return "/txDfRate/txDfRateUpdate";
	}
	@Permission(value = "/txDfRate/getTxDfRateList")
	@RequestMapping(value = "/getTxDfRateList", method = RequestMethod.GET)
	public void getTxDfRateList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfRate txDfRate = requst2Bean(request,TxDfRate.class);
		PageHelper.offsetPage(txDfRate.getOffset(), txDfRate.getLimit());
		if(txDfRate.getSort() != null && txDfRate.getOrder() != null){
			PageHelper.orderBy(txDfRate.getSort() +" "+ txDfRate.getOrder());
		}			
		List<TxDfRate> txDfRateList = txDfRateService.getTxDfRateList(txDfRate);
		PageInfo<TxDfRate> pageInfo = new PageInfo<TxDfRate>(txDfRateList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txDfRate/addTxDfRate")
	@RequestMapping(value = "/addTxDfRate", method = RequestMethod.POST)
	public void addTxDfRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfRate txDfRate = requst2Bean(request,TxDfRate.class);
		txDfRateService.insertTxDfRate(txDfRate);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txDfRate/updateTxDfRate")
	@RequestMapping(value = "/updateTxDfRate", method = RequestMethod.POST)
	public void updateTxDfRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfRate txDfRate = requst2Bean(request,TxDfRate.class);
		int count = txDfRateService.updateTxDfRateById(txDfRate);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txDfRate/removeTxDfRate")
	@RequestMapping(value = "/removeTxDfRate", method = RequestMethod.POST)
	public void removeTxDfRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txDfRateService.deleteTxDfRateById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txDfRate/removeAllTxDfRate")
	@RequestMapping(value = "/removeAllTxDfRate", method = RequestMethod.POST)
	public void removeAllTxDfRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txDfRateService.deleteTxDfRateById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
