package com.tx.txWxOrder.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.tx.txRate.model.TxRate;
import com.tx.txRate.service.TxRateService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxOrder.service.TxWxOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.utils.SessionName;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-02-07 12:45:27
 */
@Controller
@RequestMapping("/txWxOrder")
public class TxWxOrderController extends BaseController
{
	
	//private static Logger logger = Logger.getLogger(TxWxOrderController.class); //Logger
	
	@Autowired
	private TxWxOrderService txWxOrderService = null;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private TxRateService txRateService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		//获取业务拓展员
		ManageAdminUser user = new ManageAdminUser();
		user.setRole_id(Integer.valueOf(ConfigConstants.DB_ROLE_ID));
		user.setState(1);
		List<ManageAdminUser> listDb = manageadminuserService.getManageAdminUserBaseList(user);
		model.addAttribute("listDb", listDb);
		return "/txWxOrder/txWxOrderIndex";
	}
	@RequestMapping(value = "/indexFriden", method = RequestMethod.GET)
	public String indexFriden(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		//获取业务拓展员
		ManageAdminUser user = new ManageAdminUser();
		user.setRole_id(Integer.valueOf(ConfigConstants.TWO_DB_ROLE_ID));
		user.setState(1);
		user.setParent_id(loginUser.getAdminId());
		List<ManageAdminUser> listDb = manageadminuserService.getManageAdminUserBaseList(user);
		model.addAttribute("listDb", listDb);
		return "/txWxOrder/txWxOrderIndexFriend";
	}
	@Permission(value = "/txWxOrder/addTxWxOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txWxOrder/txWxOrderAdd";
	}
	@Permission(value = "/txWxOrder/updateTxWxOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Integer id)
	{	
		TxWxOrder txWxOrder = txWxOrderService.getTxWxOrderById(id);
		model.addAttribute("txWxOrder", txWxOrder);
		return "/txWxOrder/txWxOrderUpdate";
	}
	@Permission(value = "/txWxOrder/getTxWxOrderList")
	@RequestMapping(value = "/getTxWxOrderList", method = RequestMethod.GET)
	public void getTxWxOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String startTime = RequestHandler.getString(request, "startTime");
		String endTime = RequestHandler.getString(request, "endTime");
		
		ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		TxWxOrder txWxOrder = requst2Bean(request,TxWxOrder.class);
		
		if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.DB_ROLE_ID).intValue()){
			txWxOrder.setPromoterId(loginUser.getAdminId());
			txWxOrder.setTwoPromoterId(-1);
		}else if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.TWO_DB_ROLE_ID).intValue()){
			txWxOrder.setTwoPromoterId(loginUser.getAdminId());
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(StringUtils.isNotBlank(startTime)){
			txWxOrder.setStartTime(sf.parse(startTime+" 00:00:00"));
		}
		if(StringUtils.isNotBlank(endTime)){
			txWxOrder.setEndTime(sf.parse(endTime+" 23:59:59"));
		}
		
		PageHelper.offsetPage(txWxOrder.getOffset(), txWxOrder.getLimit());
		if(txWxOrder.getSort() != null && txWxOrder.getOrder() != null){
			PageHelper.orderBy(txWxOrder.getSort() +" "+ txWxOrder.getOrder());
		}			
		List<TxWxOrder> txWxOrderList = txWxOrderService.getTxWxOrderList(txWxOrder);
		PageInfo<TxWxOrder> pageInfo = new PageInfo<TxWxOrder>(txWxOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txWxOrder/getTxWxOrderListByMyFriend")
	@RequestMapping(value = "/getTxWxOrderListByMyFriend", method = RequestMethod.GET)
	public void getTxWxOrderListByMyFriend(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception
	{
		String startTime = RequestHandler.getString(request, "startTime");
		String endTime = RequestHandler.getString(request, "endTime");
		
		ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
		TxWxOrder txWxOrder = requst2Bean(request,TxWxOrder.class);
		
		txWxOrder.setParentId(loginUser.getAdminId());
		
		PageHelper.offsetPage(txWxOrder.getOffset(), txWxOrder.getLimit());
		if(txWxOrder.getSort() != null && txWxOrder.getOrder() != null){
			PageHelper.orderBy(txWxOrder.getSort() +" "+ txWxOrder.getOrder());
		}	
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(StringUtils.isNotBlank(startTime)){
			txWxOrder.setStartTime(sf.parse(startTime+" 00:00:00"));
		}
		if(StringUtils.isNotBlank(endTime)){
			txWxOrder.setEndTime(sf.parse(endTime+" 23:59:59"));
		}
		
		List<TxWxOrder> txWxOrderList = txWxOrderService.getTxWxOrderList(txWxOrder);
		PageInfo<TxWxOrder> pageInfo = new PageInfo<TxWxOrder>(txWxOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txWxOrder/addTxWxOrder")
	@RequestMapping(value = "/addTxWxOrder", method = RequestMethod.POST)
	public void addTxWxOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxOrder txWxOrder = requst2Bean(request,TxWxOrder.class);
		txWxOrderService.insertTxWxOrder(txWxOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txWxOrder/updateTxWxOrder")
	@RequestMapping(value = "/updateTxWxOrder", method = RequestMethod.POST)
	public void updateTxWxOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxWxOrder txWxOrder = requst2Bean(request,TxWxOrder.class);
		int count = txWxOrderService.updateTxWxOrderById(txWxOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txWxOrder/removeTxWxOrder")
	@RequestMapping(value = "/removeTxWxOrder", method = RequestMethod.POST)
	public void removeTxWxOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		txWxOrderService.deleteTxWxOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txWxOrder/updateDF")
	@RequestMapping(value = "/updateDF", method = RequestMethod.POST)
	public void updateDF(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Integer id = RequestHandler.getInteger(request, "id");
		TxWxOrder txWxOrder = txWxOrderService.getTxWxOrderById(id);
		//查询对应的商户信息
		TxWxUser wxUser = txWxUserService.getTxWxUserById(txWxOrder.getWxUserId());
		boolean b = txWxUserBankNoService.updatePay(txWxOrder.getOrderCode(), txWxOrder.getTxnTime(),wxUser);
		if(b){
			writeSuccessMsg("操作成功", null, response);
		}else{
			writeErrorMsg("操作失败", null, response);
		}
	}
	@Permission(value = "/txWxOrder/removeAllTxWxOrder")
	@RequestMapping(value = "/removeAllTxWxOrder", method = RequestMethod.POST)
	public void removeAllTxWxOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Integer> list = JSONArray.parseArray(ids, Integer.class);
			if(list != null){
				for (Integer id : list) {
					txWxOrderService.deleteTxWxOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
	
	@Permission(value = "/txWxOrder/exportTxt")
	@RequestMapping(value = "/exportTxt", method = RequestMethod.GET)
	public String exportTxt(HttpServletRequest request, HttpServletResponse response, Model model)
	{
	
		String startTime = RequestHandler.getString(request, "startTime");
		String endTime = RequestHandler.getString(request, "endTime");
		try{
			TxWxOrder txWxOrder = new TxWxOrder();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			if(StringUtils.isNotBlank(startTime)){
				txWxOrder.setStartTime(sf.parse(startTime+" 00:00:00"));
			}
			if(StringUtils.isNotBlank(endTime)){
				txWxOrder.setEndTime(sf.parse(endTime+" 23:59:59"));
			}
			
			txWxOrder.setXfState(1);
			txWxOrder.setDfState(1);
			
			//交易数量
			int totalResults = txWxOrderService.getTxWxOrderCount(txWxOrder);
			//生成文件
			if(totalResults>0){
				//交易总金额
				Long totalMoney = txWxOrderService.getTxWxOrderMoney(txWxOrder);
				List<TxRate> listRate = txRateService.getTxRateList(new TxRate());
				BigDecimal bg = new BigDecimal(totalMoney);
				long two = (bg.multiply(listRate.get(0).getTwoPromoterRate().add(listRate.get(0).getOnePromoterRate().add(listRate.get(0).getDevRate())))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				
				//一级代理分润
				List<TxWxOrder> listOne = txWxOrderService.getMoneyByOnePromoter(txWxOrder);
				//二级代理分润
				List<TxWxOrder> listTwo = txWxOrderService.getMoneyByTwoPromoter(txWxOrder);
				//开发分润
				Long dev_money = txWxOrderService.getMoneyByDev(txWxOrder);
//				String realPath = "PAYREQ_"+ConfigConstants.AGENT_ID+"_20170921411000111.txt";
				Map<String,String> map = txWxOrderService.exportTxt(listOne,listTwo,totalResults,super.getMoney(two),dev_money);
				writeSuccessMsg("ok", map, response);
			}else{
				writeSuccessMsg("nodata", null, response);
			}
		}catch(Exception e){
			writeSuccessMsg("nodata", null, response);
			e.printStackTrace();
		}
		return null;
	}
}
