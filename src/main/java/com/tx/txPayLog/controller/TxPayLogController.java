package com.tx.txPayLog.controller;

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
import com.tx.txPayLog.model.TxPayLog;
import com.tx.txPayLog.service.TxPayLogService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-10 17:57:44
 */
@Controller
@RequestMapping("/txPayLog")
public class TxPayLogController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxPayLogController.class); //Logger
	
	@Autowired
	private TxPayLogService txPayLogService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayLog/txPayLogIndex";
	}
	@Permission(value = "/txPayLog/addTxPayLog")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txPayLog/txPayLogAdd";
	}
	@Permission(value = "/txPayLog/updateTxPayLog")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxPayLog txPayLog = txPayLogService.getTxPayLogById(id);
		model.addAttribute("txPayLog", txPayLog);
		return "/txPayLog/txPayLogUpdate";
	}
	@Permission(value = "/txPayLog/getTxPayLogList")
	@RequestMapping(value = "/getTxPayLogList", method = RequestMethod.GET)
	public void getTxPayLogList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayLog txPayLog = requst2Bean(request,TxPayLog.class);
		PageHelper.offsetPage(txPayLog.getOffset(), txPayLog.getLimit());
		if(txPayLog.getSort() != null && txPayLog.getOrder() != null){
			PageHelper.orderBy(txPayLog.getSort() +" "+ txPayLog.getOrder());
		}			
		List<TxPayLog> txPayLogList = txPayLogService.getTxPayLogList(txPayLog);
		PageInfo<TxPayLog> pageInfo = new PageInfo<TxPayLog>(txPayLogList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txPayLog/addTxPayLog")
	@RequestMapping(value = "/addTxPayLog", method = RequestMethod.POST)
	public void addTxPayLog(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayLog txPayLog = requst2Bean(request,TxPayLog.class);
		txPayLog.setCreateTime(new Date());
		txPayLogService.insertTxPayLog(txPayLog);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txPayLog/updateTxPayLog")
	@RequestMapping(value = "/updateTxPayLog", method = RequestMethod.POST)
	public void updateTxPayLog(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxPayLog txPayLog = requst2Bean(request,TxPayLog.class);
		int count = txPayLogService.updateTxPayLogById(txPayLog);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txPayLog/removeTxPayLog")
	@RequestMapping(value = "/removeTxPayLog", method = RequestMethod.POST)
	public void removeTxPayLog(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txPayLogService.deleteTxPayLogById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txPayLog/removeAllTxPayLog")
	@RequestMapping(value = "/removeAllTxPayLog", method = RequestMethod.POST)
	public void removeAllTxPayLog(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txPayLogService.deleteTxPayLogById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
