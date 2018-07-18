package com.tx.txRecord.controller;

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
import com.tx.txRecord.model.TxRecord;
import com.tx.txRecord.service.TxRecordService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-07-12 11:44:17
 */
@Controller
@RequestMapping("/txRecord")
public class TxRecordController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxRecordController.class); //Logger
	
	@Autowired
	private TxRecordService txRecordService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRecord/txRecordIndex";
	}
	@Permission(value = "/txRecord/addTxRecord")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txRecord/txRecordAdd";
	}
	@Permission(value = "/txRecord/updateTxRecord")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxRecord txRecord = txRecordService.getTxRecordById(id);
		model.addAttribute("txRecord", txRecord);
		return "/txRecord/txRecordUpdate";
	}
	@Permission(value = "/txRecord/getTxRecordList")
	@RequestMapping(value = "/getTxRecordList", method = RequestMethod.GET)
	public void getTxRecordList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRecord txRecord = requst2Bean(request,TxRecord.class);
		PageHelper.offsetPage(txRecord.getOffset(), txRecord.getLimit());
		if(txRecord.getSort() != null && txRecord.getOrder() != null){
			PageHelper.orderBy(txRecord.getSort() +" "+ txRecord.getOrder());
		}			
		List<TxRecord> txRecordList = txRecordService.getTxRecordAndApply(txRecord);
		PageInfo<TxRecord> pageInfo = new PageInfo<TxRecord>(txRecordList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txRecord/addTxRecord")
	@RequestMapping(value = "/addTxRecord", method = RequestMethod.POST)
	public void addTxRecord(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRecord txRecord = requst2Bean(request,TxRecord.class);
		txRecordService.insertTxRecord(txRecord);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txRecord/updateTxRecord")
	@RequestMapping(value = "/updateTxRecord", method = RequestMethod.POST)
	public void updateTxRecord(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxRecord txRecord = requst2Bean(request,TxRecord.class);
		int count = txRecordService.updateTxRecordById(txRecord);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txRecord/removeTxRecord")
	@RequestMapping(value = "/removeTxRecord", method = RequestMethod.POST)
	public void removeTxRecord(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txRecordService.deleteTxRecordById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txRecord/removeAllTxRecord")
	@RequestMapping(value = "/removeAllTxRecord", method = RequestMethod.POST)
	public void removeAllTxRecord(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txRecordService.deleteTxRecordById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
