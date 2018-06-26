package com.tx.txCity.controller;

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
import com.tx.txCity.model.TxCity;
import com.tx.txCity.service.TxCityService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-01 09:59:34
 */
@Controller
@RequestMapping("/txCity")
public class TxCityController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxCityController.class); //Logger
	
	@Autowired
	private TxCityService txCityService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txCity/txCityIndex";
	}
	@Permission(value = "/txCity/addTxCity")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txCity/txCityAdd";
	}
	@Permission(value = "/txCity/updateTxCity")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxCity txCity = txCityService.getTxCityById(id);
		model.addAttribute("txCity", txCity);
		return "/txCity/txCityUpdate";
	}
	@Permission(value = "/txCity/getTxCityList")
	@RequestMapping(value = "/getTxCityList", method = RequestMethod.GET)
	public void getTxCityList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxCity txCity = requst2Bean(request,TxCity.class);
		PageHelper.offsetPage(txCity.getOffset(), txCity.getLimit());
		if(txCity.getSort() != null && txCity.getOrder() != null){
			PageHelper.orderBy(txCity.getSort() +" "+ txCity.getOrder());
		}			
		List<TxCity> txCityList = txCityService.getTxCityList(txCity);
		PageInfo<TxCity> pageInfo = new PageInfo<TxCity>(txCityList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txCity/addTxCity")
	@RequestMapping(value = "/addTxCity", method = RequestMethod.POST)
	public void addTxCity(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxCity txCity = requst2Bean(request,TxCity.class);
		txCityService.insertTxCity(txCity);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txCity/updateTxCity")
	@RequestMapping(value = "/updateTxCity", method = RequestMethod.POST)
	public void updateTxCity(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxCity txCity = requst2Bean(request,TxCity.class);
		int count = txCityService.updateTxCityById(txCity);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txCity/removeTxCity")
	@RequestMapping(value = "/removeTxCity", method = RequestMethod.POST)
	public void removeTxCity(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txCityService.deleteTxCityById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txCity/removeAllTxCity")
	@RequestMapping(value = "/removeAllTxCity", method = RequestMethod.POST)
	public void removeAllTxCity(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txCityService.deleteTxCityById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
