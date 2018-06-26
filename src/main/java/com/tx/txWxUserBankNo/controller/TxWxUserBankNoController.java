package com.tx.txWxUserBankNo.controller;

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
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-05 10:34:13
 */
@Controller
@RequestMapping("/txWxUserBankNo")
public class TxWxUserBankNoController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxWxUserBankNoController.class); //Logger
	
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxUserBankNo/txWxUserBankNoIndex";
	}
	@Permission(value = "/txWxUserBankNo/addTxWxUserBankNo")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxUserBankNo/txWxUserBankNoAdd";
	}
	@Permission(value = "/txWxUserBankNo/updateTxWxUserBankNo")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxWxUserBankNo txWxUserBankNo = txWxUserBankNoService.getTxWxUserBankNoById(id);
		model.addAttribute("txWxUserBankNo", txWxUserBankNo);
		return "/txWxUserBankNo/txWxUserBankNoUpdate";
	}
	@Permission(value = "/txWxUserBankNo/getTxWxUserBankNoList")
	@RequestMapping(value = "/getTxWxUserBankNoList", method = RequestMethod.GET)
	public void getTxWxUserBankNoList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUserBankNo txWxUserBankNo = requst2Bean(request,TxWxUserBankNo.class);
		PageHelper.offsetPage(txWxUserBankNo.getOffset(), txWxUserBankNo.getLimit());
		if(txWxUserBankNo.getSort() != null && txWxUserBankNo.getOrder() != null){
			PageHelper.orderBy(txWxUserBankNo.getSort() +" "+ txWxUserBankNo.getOrder());
		}			
		List<TxWxUserBankNo> txWxUserBankNoList = txWxUserBankNoService.getTxWxUserBankNoList(txWxUserBankNo);
		PageInfo<TxWxUserBankNo> pageInfo = new PageInfo<TxWxUserBankNo>(txWxUserBankNoList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txWxUserBankNo/addTxWxUserBankNo")
	@RequestMapping(value = "/addTxWxUserBankNo", method = RequestMethod.POST)
	public void addTxWxUserBankNo(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUserBankNo txWxUserBankNo = requst2Bean(request,TxWxUserBankNo.class);
		txWxUserBankNoService.insertTxWxUserBankNo(txWxUserBankNo);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txWxUserBankNo/updateTxWxUserBankNo")
	@RequestMapping(value = "/updateTxWxUserBankNo", method = RequestMethod.POST)
	public void updateTxWxUserBankNo(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxUserBankNo txWxUserBankNo = requst2Bean(request,TxWxUserBankNo.class);
		int count = txWxUserBankNoService.updateTxWxUserBankNoById(txWxUserBankNo);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txWxUserBankNo/removeTxWxUserBankNo")
	@RequestMapping(value = "/removeTxWxUserBankNo", method = RequestMethod.POST)
	public void removeTxWxUserBankNo(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txWxUserBankNoService.deleteTxWxUserBankNoById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txWxUserBankNo/removeAllTxWxUserBankNo")
	@RequestMapping(value = "/removeAllTxWxUserBankNo", method = RequestMethod.POST)
	public void removeAllTxWxUserBankNo(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txWxUserBankNoService.deleteTxWxUserBankNoById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
