package com.tx.txPayRate.controller;

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
import com.tx.txPayRate.model.TxPayRate;
import com.tx.txPayRate.service.TxPayRateService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-05-02 13:35:17
 */
@Controller
@RequestMapping("/txPayRate")
public class TxPayRateController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxPayRateController.class); //Logger
	
	@Autowired
	private TxPayRateService txPayRateService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayRate/txPayRateIndex";
	}
	@Permission(value = "/txPayRate/addTxPayRate")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayRate/txPayRateAdd";
	}
	@Permission(value = "/txPayRate/updateTxPayRate")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxPayRate txPayRate = txPayRateService.getTxPayRateById(id);
		model.addAttribute("txPayRate", txPayRate);
		return "/txPayRate/txPayRateUpdate";
	}
	@Permission(value = "/txPayRate/getTxPayRateList")
	@RequestMapping(value = "/getTxPayRateList", method = RequestMethod.GET)
	public void getTxPayRateList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayRate txPayRate = requst2Bean(request,TxPayRate.class);
		PageHelper.offsetPage(txPayRate.getOffset(), txPayRate.getLimit());
		if(txPayRate.getSort() != null && txPayRate.getOrder() != null){
			PageHelper.orderBy(txPayRate.getSort() +" "+ txPayRate.getOrder());
		}			
		List<TxPayRate> txPayRateList = txPayRateService.getTxPayRateList(txPayRate);
		PageInfo<TxPayRate> pageInfo = new PageInfo<TxPayRate>(txPayRateList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txPayRate/addTxPayRate")
	@RequestMapping(value = "/addTxPayRate", method = RequestMethod.POST)
	public void addTxPayRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayRate txPayRate = requst2Bean(request,TxPayRate.class);
		txPayRateService.insertTxPayRate(txPayRate);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txPayRate/updateTxPayRate")
	@RequestMapping(value = "/updateTxPayRate", method = RequestMethod.POST)
	public void updateTxPayRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayRate txPayRate = requst2Bean(request,TxPayRate.class);
		int count = txPayRateService.updateTxPayRateById(txPayRate);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txPayRate/removeTxPayRate")
	@RequestMapping(value = "/removeTxPayRate", method = RequestMethod.POST)
	public void removeTxPayRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txPayRateService.deleteTxPayRateById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txPayRate/removeAllTxPayRate")
	@RequestMapping(value = "/removeAllTxPayRate", method = RequestMethod.POST)
	public void removeAllTxPayRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txPayRateService.deleteTxPayRateById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
