package com.tx.txApplay.controller;

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
import com.tx.txApplay.model.TxApplay;
import com.tx.txApplay.service.TxApplayService;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-07-12 11:49:15
 */
@Controller
@RequestMapping("/txApplay")
public class TxApplayController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxApplayController.class); //Logger
	
	@Autowired
	private TxApplayService txApplayService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txApplay/txApplayIndex";
	}
	@Permission(value = "/txApplay/addTxApplay")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txApplay/txApplayAdd";
	}
	@Permission(value = "/txApplay/updateTxApplay")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxApplay txApplay = txApplayService.getTxApplayById(id);
		model.addAttribute("txApplay", txApplay);
		return "/txApplay/txApplayUpdate";
	}
	@Permission(value = "/txApplay/getTxApplayList")
	@RequestMapping(value = "/getTxApplayList", method = RequestMethod.GET)
	public void getTxApplayList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxApplay txApplay = requst2Bean(request,TxApplay.class);
		PageHelper.offsetPage(txApplay.getOffset(), txApplay.getLimit());
		if(txApplay.getSort() != null && txApplay.getOrder() != null){
			PageHelper.orderBy(txApplay.getSort() +" "+ txApplay.getOrder());
		}			
		List<TxApplay> txApplayList = txApplayService.getTxApplayList(txApplay);
		PageInfo<TxApplay> pageInfo = new PageInfo<TxApplay>(txApplayList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txApplay/addTxApplay")
	@RequestMapping(value = "/addTxApplay", method = RequestMethod.POST)
	public void addTxApplay(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxApplay txApplay = requst2Bean(request,TxApplay.class);
		txApplay.setCreateTime(new Date());
		txApplayService.insertTxApplay(txApplay);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txApplay/updateTxApplay")
	@RequestMapping(value = "/updateTxApplay", method = RequestMethod.POST)
	public void updateTxApplay(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxApplay txApplay = requst2Bean(request,TxApplay.class);
		int count = txApplayService.updateTxApplayById(txApplay);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txApplay/removeTxApplay")
	@RequestMapping(value = "/removeTxApplay", method = RequestMethod.POST)
	public void removeTxApplay(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txApplayService.deleteTxApplayById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txApplay/removeAllTxApplay")
	@RequestMapping(value = "/removeAllTxApplay", method = RequestMethod.POST)
	public void removeAllTxApplay(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txApplayService.deleteTxApplayById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
