package com.tx.txSellingOrder.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

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
import com.alibaba.fastjson.JSONObject;
import com.base.perm.Permission;
import com.tx.txRefundOrder.model.TxRefundOrder;
import com.tx.txRefundOrder.service.TxRefundOrderService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.wx.utils.https.HttpRequest;
import com.base.utils.ConfigConstants;
import com.base.utils.RequestHandler;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
/**
 * @author	wzw
 * @time	2018-04-30 22:59:35
 */
@Controller
@RequestMapping("/txSellingOrder")
public class TxSellingOrderController extends BaseController
{
	
	private static Logger logger = Logger.getLogger(TxSellingOrderController.class); //Logger
	
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxRefundOrderService txRefundOrderService = null;
	
	/*****************页面中转*********************/
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txSellingOrder/txSellingOrderIndex";
	}
	@Permission(value = "/txSellingOrder/addTxSellingOrder")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		return "/txSellingOrder/txSellingOrderAdd";
	}
	@Permission(value = "/txSellingOrder/updateTxSellingOrder")
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable Long id)
	{	
		TxSellingOrder txSellingOrder = txSellingOrderService.getTxSellingOrderById(id);
		model.addAttribute("txSellingOrder", txSellingOrder);
		return "/txSellingOrder/txSellingOrderUpdate";
	}
	@Permission(value = "/txSellingOrder/getTxSellingOrderList")
	@RequestMapping(value = "/getTxSellingOrderList", method = RequestMethod.GET)
	public void getTxSellingOrderList(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		PageHelper.offsetPage(txSellingOrder.getOffset(), txSellingOrder.getLimit());
		if(txSellingOrder.getSort() != null && txSellingOrder.getOrder() != null){
			PageHelper.orderBy(txSellingOrder.getSort() +" "+ txSellingOrder.getOrder());
		}			
		List<TxSellingOrder> txSellingOrderList = txSellingOrderService.getTxSellingOrderList(txSellingOrder);
		PageInfo<TxSellingOrder> pageInfo = new PageInfo<TxSellingOrder>(txSellingOrderList);
		writeSuccessMsg("OK",pageInfo, response);
	}
	@Permission(value = "/txSellingOrder/addTxSellingOrder")
	@RequestMapping(value = "/addTxSellingOrder", method = RequestMethod.POST)
	public void addTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		txSellingOrder.setCreateTime(new Date());
		txSellingOrderService.insertTxSellingOrder(txSellingOrder);
		writeSuccessMsg("添加成功", null, response);
	}
	@Permission(value = "/txSellingOrder/updateTxSellingOrder")
	@RequestMapping(value = "/updateTxSellingOrder", method = RequestMethod.POST)
	public void updateTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		TxSellingOrder txSellingOrder = requst2Bean(request,TxSellingOrder.class);
		int count = txSellingOrderService.updateTxSellingOrderById(txSellingOrder);
		if(count == 1){
			writeSuccessMsg("修改成功", null, response);
		}else{
			writeErrorMsg("修改失败", null, response);
		}
	}
	@Permission(value = "/txSellingOrder/removeTxSellingOrder")
	@RequestMapping(value = "/removeTxSellingOrder", method = RequestMethod.POST)
	public void removeTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		Long id = RequestHandler.getLong(request, "id");
		txSellingOrderService.deleteTxSellingOrderById(id);
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/txSellingOrder/removeAllTxSellingOrder")
	@RequestMapping(value = "/removeAllTxSellingOrder", method = RequestMethod.POST)
	public void removeAllTxSellingOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException
	{
		String ids = RequestHandler.getString(request, "ids");
		if(ids != null){
			List<Long> list = JSONArray.parseArray(ids, Long.class);
			if(list != null){
				for (Long id : list) {
					txSellingOrderService.deleteTxSellingOrderById(id);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException{
    	try {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    		TxSellingOrder txSellingOrder = new TxSellingOrder();
        	txSellingOrder.setState(1);
        	txSellingOrder.setRefundState(0);
        	
        	Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
        	txSellingOrder.setEndTime(calendar.getTime());
        	
        	List<TxSellingOrder> list = txSellingOrderService.getTxSellingOrderListBySY1(txSellingOrder);
        	if(list!=null&&list.size()>0){
        		JSONObject json = new JSONObject();
        		json.put("msgType", "T1XW01");
        		json.put("tranSum", list.size());
        		json.put("merchantID", ConfigConstants.MER_ID);
        		Long fee = 0L;
        		JSONArray array = new JSONArray();
        		String tmpMd5="";
        		for(TxSellingOrder order:list){
        			JSONObject jsonDate = new JSONObject();
        			BigDecimal bg = new BigDecimal(order.getMoney());
					BigDecimal bgRate = new BigDecimal(Double.valueOf(ConfigConstants.RATE));
					System.out.println(bg.multiply(bgRate).divide(new BigDecimal(12)));
					int txnAmtDF = (bg.multiply(bgRate).divide(new BigDecimal(12).multiply(new BigDecimal(order.getSelTime())))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        			fee = order.getMoney() + txnAmtDF + fee;
        			String md5str = MD5.getMD5ofStr(order.getXwMerId()+ txnAmtDF+ order.getMoney());
        			jsonDate.put("xwMerId", order.getXwMerId());
            		jsonDate.put("amt", txnAmtDF);
            		jsonDate.put("orderAmt", order.getMoney());
            		jsonDate.put("dataMd5", md5str);
            		if(tmpMd5==""){
            			tmpMd5 =  md5str;
            		}else{
            			tmpMd5 = MD5.getMD5ofStr(md5str+tmpMd5);
            		}
            		array.add(jsonDate);
        		}
        		json.put("totalFee", fee);
        		json.put("data", array);
        		json.put("md5Str", MD5.getMD5ofStr(tmpMd5));
        		json.put("batchNo", sf.format(new Date())+"0001");
        		
        		String jsonStr = HttpRequest.sendPost(ConfigConstants.SY_URL, json.toString());
        		
//        		String jsonStr = com.base.utils.https.HttpUtils.httpsRequest(ConfigConstants.SY_URL, "POST", json.toString());
        		
                JSONObject result = JSONObject.parseObject(jsonStr);
        		if("0".equals(result.get("respCode"))){
        			for(TxSellingOrder order:list){
            			TxRefundOrder txRefundOrder = new TxRefundOrder();
    					txRefundOrder.setUserId(order.getWxUserId());
    					txRefundOrder.setRealName(order.getRealName());
    					txRefundOrder.setCreateTime(new Date());
    					txRefundOrder.setFee(order.getMoney());
    					txRefundOrder.setOrderCode(order.getCode());
    					txRefundOrder.setOrderTime(order.getCreateTime());
    					txRefundOrder.setBatch(sf.format(new Date())+"0001");
    					txRefundOrderService.insertTxRefundOrder(txRefundOrder);
                	}
        		}else{
        			logger.info(sf.format(new Date())+"0001" + "---------------收益--------------------"+result.get("respMsg"));
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
