package com.tx.txProvince.controller;

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
import com.tx.txProvince.model.TxProvince;
import com.tx.txProvince.service.TxProvinceService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-01 10:00:12
 */
@Controller
@RequestMapping("/txProvince")
public class TxProvinceController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxProvinceController.class); //Logger
	
	@Autowired
	private TxProvinceService txProvinceService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txProvince/txProvinceIndex";
	}
	@Permission(value = "/txProvince/addTxProvince")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txProvince/txProvinceAdd";
	}
	@Permission(value = "/txProvince/updateTxProvince")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxProvince txProvince = txProvinceService.getTxProvinceById(id);
		model.addAttribute("txProvince", txProvince);
		return "/txProvince/txProvinceUpdate";
	}
	@Permission(value = "/txProvince/getTxProvinceList")
	@RequestMapping(value = "/getTxProvinceList", method = RequestMethod.GET)
	public void getTxProvinceList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxProvince txProvince = requst2Bean(request,TxProvince.class);
		PageHelper.offsetPage(txProvince.getOffset(), txProvince.getLimit());
		if(txProvince.getSort() != null && txProvince.getOrder() != null){
			PageHelper.orderBy(txProvince.getSort() +" "+ txProvince.getOrder());
		}			
		List<TxProvince> txProvinceList = txProvinceService.getTxProvinceList(txProvince);
		PageInfo<TxProvince> pageInfo = new PageInfo<TxProvince>(txProvinceList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txProvince/addTxProvince")
	@RequestMapping(value = "/addTxProvince", method = RequestMethod.POST)
	public void addTxProvince(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxProvince txProvince = requst2Bean(request,TxProvince.class);
		txProvinceService.insertTxProvince(txProvince);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txProvince/updateTxProvince")
	@RequestMapping(value = "/updateTxProvince", method = RequestMethod.POST)
	public void updateTxProvince(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxProvince txProvince = requst2Bean(request,TxProvince.class);
		int count = txProvinceService.updateTxProvinceById(txProvince);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txProvince/removeTxProvince")
	@RequestMapping(value = "/removeTxProvince", method = RequestMethod.POST)
	public void removeTxProvince(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txProvinceService.deleteTxProvinceById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txProvince/removeAllTxProvince")
	@RequestMapping(value = "/removeAllTxProvince", method = RequestMethod.POST)
	public void removeAllTxProvince(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txProvinceService.deleteTxProvinceById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
