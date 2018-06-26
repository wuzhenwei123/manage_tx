package com.tx.txBank.controller;

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
import com.tx.txBank.model.TxBank;
import com.tx.txBank.service.TxBankService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-02 09:09:39
 */
@Controller
@RequestMapping("/txBank")
public class TxBankController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxBankController.class); //Logger
	
	@Autowired
	private TxBankService txBankService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBank/txBankIndex";
	}
	@Permission(value = "/txBank/addTxBank")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBank/txBankAdd";
	}
	@Permission(value = "/txBank/updateTxBank")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxBank txBank = txBankService.getTxBankById(id);
		model.addAttribute("txBank", txBank);
		return "/txBank/txBankUpdate";
	}
	@Permission(value = "/txBank/getTxBankList")
	@RequestMapping(value = "/getTxBankList", method = RequestMethod.GET)
	public void getTxBankList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBank txBank = requst2Bean(request,TxBank.class);
		PageHelper.offsetPage(txBank.getOffset(), txBank.getLimit());
		if(txBank.getSort() != null && txBank.getOrder() != null){
			PageHelper.orderBy(txBank.getSort() +" "+ txBank.getOrder());
		}			
		List<TxBank> txBankList = txBankService.getTxBankList(txBank);
		PageInfo<TxBank> pageInfo = new PageInfo<TxBank>(txBankList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txBank/addTxBank")
	@RequestMapping(value = "/addTxBank", method = RequestMethod.POST)
	public void addTxBank(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBank txBank = requst2Bean(request,TxBank.class);
		txBankService.insertTxBank(txBank);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txBank/updateTxBank")
	@RequestMapping(value = "/updateTxBank", method = RequestMethod.POST)
	public void updateTxBank(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBank txBank = requst2Bean(request,TxBank.class);
		int count = txBankService.updateTxBankById(txBank);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txBank/removeTxBank")
	@RequestMapping(value = "/removeTxBank", method = RequestMethod.POST)
	public void removeTxBank(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txBankService.deleteTxBankById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txBank/removeAllTxBank")
	@RequestMapping(value = "/removeAllTxBank", method = RequestMethod.POST)
	public void removeAllTxBank(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txBankService.deleteTxBankById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
