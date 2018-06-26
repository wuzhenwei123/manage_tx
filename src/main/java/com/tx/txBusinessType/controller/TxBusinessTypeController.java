package com.tx.txBusinessType.controller;

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
import com.tx.txBusinessType.model.TxBusinessType;
import com.tx.txBusinessType.service.TxBusinessTypeService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-02 11:36:08
 */
@Controller
@RequestMapping("/txBusinessType")
public class TxBusinessTypeController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxBusinessTypeController.class); //Logger
	
	@Autowired
	private TxBusinessTypeService txBusinessTypeService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBusinessType/txBusinessTypeIndex";
	}
	@Permission(value = "/txBusinessType/addTxBusinessType")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBusinessType/txBusinessTypeAdd";
	}
	@Permission(value = "/txBusinessType/updateTxBusinessType")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxBusinessType txBusinessType = txBusinessTypeService.getTxBusinessTypeById(id);
		model.addAttribute("txBusinessType", txBusinessType);
		return "/txBusinessType/txBusinessTypeUpdate";
	}
	@Permission(value = "/txBusinessType/getTxBusinessTypeList")
	@RequestMapping(value = "/getTxBusinessTypeList", method = RequestMethod.GET)
	public void getTxBusinessTypeList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBusinessType txBusinessType = requst2Bean(request,TxBusinessType.class);
		PageHelper.offsetPage(txBusinessType.getOffset(), txBusinessType.getLimit());
		if(txBusinessType.getSort() != null && txBusinessType.getOrder() != null){
			PageHelper.orderBy(txBusinessType.getSort() +" "+ txBusinessType.getOrder());
		}			
		List<TxBusinessType> txBusinessTypeList = txBusinessTypeService.getTxBusinessTypeList(txBusinessType);
		PageInfo<TxBusinessType> pageInfo = new PageInfo<TxBusinessType>(txBusinessTypeList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txBusinessType/addTxBusinessType")
	@RequestMapping(value = "/addTxBusinessType", method = RequestMethod.POST)
	public void addTxBusinessType(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBusinessType txBusinessType = requst2Bean(request,TxBusinessType.class);
		txBusinessTypeService.insertTxBusinessType(txBusinessType);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txBusinessType/updateTxBusinessType")
	@RequestMapping(value = "/updateTxBusinessType", method = RequestMethod.POST)
	public void updateTxBusinessType(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBusinessType txBusinessType = requst2Bean(request,TxBusinessType.class);
		int count = txBusinessTypeService.updateTxBusinessTypeById(txBusinessType);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txBusinessType/removeTxBusinessType")
	@RequestMapping(value = "/removeTxBusinessType", method = RequestMethod.POST)
	public void removeTxBusinessType(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txBusinessTypeService.deleteTxBusinessTypeById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txBusinessType/removeAllTxBusinessType")
	@RequestMapping(value = "/removeAllTxBusinessType", method = RequestMethod.POST)
	public void removeAllTxBusinessType(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txBusinessTypeService.deleteTxBusinessTypeById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
