package com.tx.txRate.controller;

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
import com.tx.txRate.model.TxRate;
import com.tx.txRate.service.TxRateService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-24 09:02:43
 */
@Controller
@RequestMapping("/txRate")
public class TxRateController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxRateController.class); //Logger
	
	@Autowired
	private TxRateService txRateService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRate/txRateIndex";
	}
	@Permission(value = "/txRate/addTxRate")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRate/txRateAdd";
	}
	@Permission(value = "/txRate/updateTxRate")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxRate txRate = txRateService.getTxRateById(id);
		model.addAttribute("txRate", txRate);
		return "/txRate/txRateUpdate";
	}
	@Permission(value = "/txRate/getTxRateList")
	@RequestMapping(value = "/getTxRateList", method = RequestMethod.GET)
	public void getTxRateList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRate txRate = requst2Bean(request,TxRate.class);
		PageHelper.offsetPage(txRate.getOffset(), txRate.getLimit());
		if(txRate.getSort() != null && txRate.getOrder() != null){
			PageHelper.orderBy(txRate.getSort() +" "+ txRate.getOrder());
		}			
		List<TxRate> txRateList = txRateService.getTxRateList(txRate);
		PageInfo<TxRate> pageInfo = new PageInfo<TxRate>(txRateList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txRate/addTxRate")
	@RequestMapping(value = "/addTxRate", method = RequestMethod.POST)
	public void addTxRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRate txRate = requst2Bean(request,TxRate.class);
		txRateService.insertTxRate(txRate);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txRate/updateTxRate")
	@RequestMapping(value = "/updateTxRate", method = RequestMethod.POST)
	public void updateTxRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRate txRate = requst2Bean(request,TxRate.class);
		int count = txRateService.updateTxRateById(txRate);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txRate/removeTxRate")
	@RequestMapping(value = "/removeTxRate", method = RequestMethod.POST)
	public void removeTxRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txRateService.deleteTxRateById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txRate/removeAllTxRate")
	@RequestMapping(value = "/removeAllTxRate", method = RequestMethod.POST)
	public void removeAllTxRate(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txRateService.deleteTxRateById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
