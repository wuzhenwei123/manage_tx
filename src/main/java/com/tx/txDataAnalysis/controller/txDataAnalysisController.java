package com.tx.txDataAnalysis.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.base.controller.BaseController;
import com.base.utils.RequestHandler;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.tx.txWxOrder.service.TxWxOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;

@Controller
@RequestMapping("/txDataAnalysisController")
public class txDataAnalysisController extends BaseController{
	
	@Autowired
	private TxWxUserService txWxUserService = null;
	@Autowired
	private TxWxOrderService txWxOrderService = null;
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;

	
	@RequestMapping(value = "/userCountAnalysis", method = RequestMethod.GET)
	public String userCountAnalysis(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String year_str = RequestHandler.getString(request, "year_str");
			Integer month = 12;
			if(!StringUtils.isNotBlank(year_str)){
				year_str = sf.format(new Date()).substring(0, 4);
				month =  Integer.valueOf(sf.format(new Date()).substring(5, 7));
			}else{
				String year_str_now = sf.format(new Date()).substring(0, 4);
				if(year_str.equals(year_str_now)){
					month =  Integer.valueOf(sf.format(new Date()).substring(5, 7));
				}
			}
			JSONObject json = new JSONObject();
			
			
//			if(list!=null&&list.size()>0){
			String data_str = null;
			String data_str1 = null;
			String mouthY_str = null;
			for(int i=1;i<=month;i++){
				String mouthY = null;
				if(i<10){
					mouthY = "0" + i;
				}else{
					mouthY = i + "";
				}
				
				TxWxUser txWxUser = new TxWxUser();
				txWxUser.setYear_str(year_str+"-"+mouthY);
				List<TxWxUser> list = txWxUserService.userAnalysisState(txWxUser);
				
				TxWxUser txWxUser1 = new TxWxUser();
				txWxUser1.setYear_str(year_str+"-"+mouthY);
				List<TxWxUser> list1 = txWxUserService.userAnalysis(txWxUser1);
				
				Integer data = 0;
				if(list!=null&&list.size()>0){
					for(TxWxUser c:list){
							data = c.getUserCount();
					}
				}
				if(data_str==null){
					data_str = data + "";
				}else{
					data_str = data_str + "," + data;
				}
				if(data_str1==null){
					data_str1 = list1.get(0).getUserCount() + "";
				}else{
					data_str1 = data_str1 + "," + list1.get(0).getUserCount();
				}
				
				if(mouthY_str==null){
					mouthY_str = "'" + mouthY + "月'";
				}else{
					mouthY_str = mouthY_str + ",'" + mouthY + "月'";
				}
				
			}
			json.put("year_str", year_str);
			json.put("mouthY_str", mouthY_str);
			json.put("data_str", data_str);
			json.put("data_str1", data_str1);
			writeSuccessMsg("ok", json.toString(), response);
//			}else{
//				writeErrorMsg("nodata", null, response);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/orderCountAnalysis", method = RequestMethod.GET)
	public String orderCountAnalysis(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String year_str = RequestHandler.getString(request, "year_str");
			Integer month = 12;
			if(!StringUtils.isNotBlank(year_str)){
				year_str = sf.format(new Date()).substring(0, 4);
				month =  Integer.valueOf(sf.format(new Date()).substring(5, 7));
			}else{
				String year_str_now = sf.format(new Date()).substring(0, 4);
				if(year_str.equals(year_str_now)){
					month =  Integer.valueOf(sf.format(new Date()).substring(5, 7));
				}
			}
			JSONObject json = new JSONObject();
			
			
//			if(list!=null&&list.size()>0){
			String data_str = null;
			String data_str1 = null;
			String mouthY_str = null;
			for(int i=1;i<=month;i++){
				String mouthY = null;
				if(i<10){
					mouthY = "0" + i;
				}else{
					mouthY = i + "";
				}
				
				TxSellingOrder txSellingOrder = new TxSellingOrder();
				txSellingOrder.setYear_str(year_str+"-"+mouthY);
				List<TxSellingOrder> list = txSellingOrderService.orderAnalysis(txSellingOrder);
				
				TxSellingOrder txSellingOrder1 = new TxSellingOrder();
				txSellingOrder1.setYear_str(year_str+"-"+mouthY);
				List<TxSellingOrder> list1 = txSellingOrderService.orderAnalysisState(txSellingOrder1);
				
				Long data = 0L;
				if(list!=null&&list.size()>0){
					for(TxSellingOrder c:list){
						data = c.getMoney();
					}
				}
				if(data_str==null){
					data_str = super.getMoney(data) + "";
				}else{
					data_str = data_str + "," + super.getMoney(data);
				}
				if(data_str1==null){
					data_str1 = super.getMoney(list1.get(0).getProfitManey()) + "";
				}else{
					data_str1 = data_str1 + "," + super.getMoney(list1.get(0).getProfitManey());
				}
				
				if(mouthY_str==null){
					mouthY_str = "'" + mouthY + "月'";
				}else{
					mouthY_str = mouthY_str + ",'" + mouthY + "月'";
				}
				
			}
			json.put("year_str", year_str);
			json.put("mouthY_str", mouthY_str);
			json.put("data_str", data_str);
			json.put("data_str1", data_str1);
			writeSuccessMsg("ok", json.toString(), response);
//			}else{
//				writeErrorMsg("nodata", null, response);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
