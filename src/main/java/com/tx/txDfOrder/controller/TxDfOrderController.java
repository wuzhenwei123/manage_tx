package com.tx.txDfOrder.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.tx.txDfOrder.model.TxDfOrder;
import com.tx.txDfOrder.service.TxDfOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.WeiXinService;
import com.base.utils.MakeImei;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-07-25 10:50:55
 */
@Controller
@RequestMapping("/txDfOrder")
public class TxDfOrderController extends BaseController
{
	
	private static Logger logger = Logger.getLogger(TxDfOrderController.class); //Logger
	
	@Autowired
	private TxDfOrderService txDfOrderService = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfOrder/txDfOrderIndex";
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		try {
    		System.out.println("-------kaishi -------");
    		TxDfOrder txDfOrder = new TxDfOrder();
    		txDfOrder.setState(0);
    		List<TxDfOrder> list = txDfOrderService.getTxDfOrderListByDF(txDfOrder);
    		if(list!=null&&list.size()>0){
    			for(TxDfOrder order:list){
    				TxWxUser wxUser = txWxUserService.getTxWxUserById(order.getUserId());
    				String orderId = new MakeImei().getCode();
    				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    				Date d = new Date();
    				String merOrderTime = sf.format(d);
					
					
					//调用接口退费
					TxWxUserBankNo txWxUserBankNo = new TxWxUserBankNo();
					//查询开户行名称
				 	String accNo = wxUser.getCardNumber();
				 	String accName = weiXinService.getKHBankName(accNo);
				 	txWxUserBankNo.setAccName(accName);
				 	txWxUserBankNo.setAccNo(accNo);
    				Map<String, String>	map = txWxUserBankNoService.xwDFTQ(wxUser, orderId, merOrderTime, txWxUserBankNo, order.getFee()+"", order.getOrderCode(), 1, 0,0);
    				if(map!=null&&"00".equals(map.get("respCode"))){
    					logger.info(wxUser.getId()+"-----------"+order.getOrderCode()+"---------------"+merOrderTime+"------------成功");
					}else{
						logger.info(wxUser.getId()+"-----------"+order.getOrderCode()+"---------------"+merOrderTime+"------------失败"+URLDecoder.decode(map.get("respMsg"),"UTF-8"));
					}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Permission(value = "/txDfOrder/addTxDfOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txDfOrder/txDfOrderAdd";
	}
	@Permission(value = "/txDfOrder/updateTxDfOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxDfOrder txDfOrder = txDfOrderService.getTxDfOrderById(id);
		model.addAttribute("txDfOrder", txDfOrder);
		return "/txDfOrder/txDfOrderUpdate";
	}
	@Permission(value = "/txDfOrder/getTxDfOrderList")
	@RequestMapping(value = "/getTxDfOrderList", method = RequestMethod.GET)
	public void getTxDfOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		PageHelper.offsetPage(txDfOrder.getOffset(), txDfOrder.getLimit());
		if(txDfOrder.getSort() != null && txDfOrder.getOrder() != null){
			PageHelper.orderBy(txDfOrder.getSort() +" "+ txDfOrder.getOrder());
		}			
		List<TxDfOrder> txDfOrderList = txDfOrderService.getTxDfOrderList(txDfOrder);
		PageInfo<TxDfOrder> pageInfo = new PageInfo<TxDfOrder>(txDfOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txDfOrder/addTxDfOrder")
	@RequestMapping(value = "/addTxDfOrder", method = RequestMethod.POST)
	public void addTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		txDfOrder.setCreateTime(new Date());
		txDfOrderService.insertTxDfOrder(txDfOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txDfOrder/updateTxDfOrder")
	@RequestMapping(value = "/updateTxDfOrder", method = RequestMethod.POST)
	public void updateTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxDfOrder txDfOrder = requst2Bean(request,TxDfOrder.class);
		int count = txDfOrderService.updateTxDfOrderById(txDfOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txDfOrder/removeTxDfOrder")
	@RequestMapping(value = "/removeTxDfOrder", method = RequestMethod.POST)
	public void removeTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txDfOrderService.deleteTxDfOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txDfOrder/removeAllTxDfOrder")
	@RequestMapping(value = "/removeAllTxDfOrder", method = RequestMethod.POST)
	public void removeAllTxDfOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txDfOrderService.deleteTxDfOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
}
