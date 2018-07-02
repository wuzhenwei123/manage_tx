package com.tx.task.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.base.utils.ConfigConstants;
import com.base.utils.MakeImei;
import com.base.utils.SessionName;
import com.tx.txPayRate.model.TxPayRate;
import com.tx.txPayRate.service.TxPayRateService;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;

@Component
public class SendCodeDFCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	@Autowired
	private TxPayRateService txPayRateService = null;
	@Autowired
	private TxWxUserBankNoService txWxUserBankNoService = null;

	public void filesMng(Integer sel_time,Integer backCard,String accNo,TxWxUserBankNo txWxUserBankNo,Integer money,TxWxUser wxUser) {
		this.taskExecutor.execute(new SendCodeThread(sel_time, backCard,accNo, txWxUserBankNo, money, wxUser));
	}

	private class SendCodeThread implements Runnable {
		private Integer sel_time;
		private Integer backCard;
		private String accNo;
		private TxWxUserBankNo txWxUserBankNo;
		private Integer money;
		private TxWxUser wxUser;
		

		private SendCodeThread(Integer sel_time,Integer backCard,String accNo,TxWxUserBankNo txWxUserBankNo,Integer money,TxWxUser wxUser) {
			super();
			this.sel_time = sel_time;
			this.backCard = backCard;
			this.accNo = accNo;
			this.txWxUserBankNo = txWxUserBankNo;
			this.money = money;
			this.wxUser = wxUser;
		}

		@Override
		public void run() {
			
			try {
				String orderId = new MakeImei().getCode();
				Date d = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				String txnTime = sf.format(d);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, sel_time);
				TxSellingOrder txSellingOrder = new TxSellingOrder();
				txSellingOrder.setBackCard(backCard);
				txSellingOrder.setCode(orderId);
				txSellingOrder.setSelTime(sel_time);
				txSellingOrder.setAccNo(accNo);
				txSellingOrder.setCreateTime(new Date());
				txSellingOrder.setEndTime(calendar.getTime());
				txSellingOrder.setMoney(money*1L);
				txSellingOrder.setPromoterId(wxUser.getPromoterId());
				txSellingOrder.setTwoPromoterId(wxUser.getTwoPromoterId());
				txSellingOrder.setWxUserName(wxUser.getRealName());
				txSellingOrder.setWxUserId(wxUser.getId());
				txSellingOrder.setProfits(new BigDecimal(ConfigConstants.PAY_RATE));
				
				List<TxPayRate> list = txPayRateService.getTxPayRateList(new TxPayRate());
				
				BigDecimal bg = new BigDecimal(money);
				if(list!=null&&list.size()>0){
					TxPayRate rate = list.get(0);
					if(wxUser.getPromoterId()!=null){
						int one = (bg.multiply(rate.getOneRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						txSellingOrder.setOneRate(one);
					}
					if(wxUser.getTwoPromoterId()!=null){
						int two = (bg.multiply(rate.getTwoRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
						txSellingOrder.setTwoRate(two);
					}
					int devRate = (bg.multiply(rate.getDevRate())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					txSellingOrder.setDevRate(devRate);
				}
				txSellingOrderService.insertTxSellingOrder(txSellingOrder);
				SessionName.MAPORDERNO.put("C_"+accNo, orderId+"_"+txnTime);
				boolean b = txWxUserBankNoService.vercodeNew(wxUser, orderId, txnTime, txWxUserBankNo, money+"",backCard);
				if(!b){
					SessionName.MAPORDERNO.remove("C_"+accNo);
					txSellingOrderService.deleteTxSellingOrderByCode(orderId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
