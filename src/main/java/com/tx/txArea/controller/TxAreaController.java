package com.tx.txArea.controller;

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
import com.tx.txArea.model.TxArea;
import com.tx.txArea.service.TxAreaService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-01 09:59:59
 */
@Controller
@RequestMapping("/txArea")
public class TxAreaController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxAreaController.class); //Logger
	
	@Autowired
	private TxAreaService txAreaService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txArea/txAreaIndex";
	}
	@Permission(value = "/txArea/addTxArea")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txArea/txAreaAdd";
	}
	@Permission(value = "/txArea/updateTxArea")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxArea txArea = txAreaService.getTxAreaById(id);
		model.addAttribute("txArea", txArea);
		return "/txArea/txAreaUpdate";
	}
	@Permission(value = "/txArea/getTxAreaList")
	@RequestMapping(value = "/getTxAreaList", method = RequestMethod.GET)
	public void getTxAreaList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxArea txArea = requst2Bean(request,TxArea.class);
		PageHelper.offsetPage(txArea.getOffset(), txArea.getLimit());
		if(txArea.getSort() != null && txArea.getOrder() != null){
			PageHelper.orderBy(txArea.getSort() +" "+ txArea.getOrder());
		}			
		List<TxArea> txAreaList = txAreaService.getTxAreaList(txArea);
		PageInfo<TxArea> pageInfo = new PageInfo<TxArea>(txAreaList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txArea/addTxArea")
	@RequestMapping(value = "/addTxArea", method = RequestMethod.POST)
	public void addTxArea(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxArea txArea = requst2Bean(request,TxArea.class);
		txAreaService.insertTxArea(txArea);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txArea/updateTxArea")
	@RequestMapping(value = "/updateTxArea", method = RequestMethod.POST)
	public void updateTxArea(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxArea txArea = requst2Bean(request,TxArea.class);
		int count = txAreaService.updateTxAreaById(txArea);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txArea/removeTxArea")
	@RequestMapping(value = "/removeTxArea", method = RequestMethod.POST)
	public void removeTxArea(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txAreaService.deleteTxAreaById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txArea/removeAllTxArea")
	@RequestMapping(value = "/removeAllTxArea", method = RequestMethod.POST)
	public void removeAllTxArea(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txAreaService.deleteTxAreaById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
