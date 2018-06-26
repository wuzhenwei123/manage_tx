package com.tx.txBanner.controller;

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
import com.tx.txBanner.model.TxBanner;
import com.tx.txBanner.service.TxBannerService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-06-08 15:45:42
 */
@Controller
@RequestMapping("/txBanner")
public class TxBannerController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxBannerController.class); //Logger
	
	@Autowired
	private TxBannerService txBannerService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBanner/txBannerIndex";
	}
	@Permission(value = "/txBanner/addTxBanner")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txBanner/txBannerAdd";
	}
	@Permission(value = "/txBanner/updateTxBanner")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxBanner txBanner = txBannerService.getTxBannerById(id);
		model.addAttribute("txBanner", txBanner);
		return "/txBanner/txBannerUpdate";
	}
	@Permission(value = "/txBanner/getTxBannerList")
	@RequestMapping(value = "/getTxBannerList", method = RequestMethod.GET)
	public void getTxBannerList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBanner txBanner = requst2Bean(request,TxBanner.class);
		PageHelper.offsetPage(txBanner.getOffset(), txBanner.getLimit());
		if(txBanner.getSort() != null && txBanner.getOrder() != null){
			PageHelper.orderBy(txBanner.getSort() +" "+ txBanner.getOrder());
		}			
		List<TxBanner> txBannerList = txBannerService.getTxBannerList(txBanner);
		PageInfo<TxBanner> pageInfo = new PageInfo<TxBanner>(txBannerList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txBanner/addTxBanner")
	@RequestMapping(value = "/addTxBanner", method = RequestMethod.POST)
	public void addTxBanner(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBanner txBanner = requst2Bean(request,TxBanner.class);
		txBannerService.insertTxBanner(txBanner);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txBanner/updateTxBanner")
	@RequestMapping(value = "/updateTxBanner", method = RequestMethod.POST)
	public void updateTxBanner(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxBanner txBanner = requst2Bean(request,TxBanner.class);
		int count = txBannerService.updateTxBannerById(txBanner);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txBanner/removeTxBanner")
	@RequestMapping(value = "/removeTxBanner", method = RequestMethod.POST)
	public void removeTxBanner(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txBannerService.deleteTxBannerById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txBanner/removeAllTxBanner")
	@RequestMapping(value = "/removeAllTxBanner", method = RequestMethod.POST)
	public void removeAllTxBanner(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txBannerService.deleteTxBannerById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
