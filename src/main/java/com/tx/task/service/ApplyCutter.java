package com.tx.task.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.base.utils.ConfigConstants;
import com.tx.txPaynumberMsg.service.TxPaynumberMsgService;
import com.tx.txWxUser.model.TxWxUser;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.wx.service.WeiXinService;

@Component
public class ApplyCutter {
	
	private static Logger logger = Logger.getLogger(ApplyCutter.class);

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private TxPaynumberMsgService txPaynumberMsgService = null;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private PayLogCutter payLogCutter;

	public void filesMng(Long money,TxWxUser wxUser,String ordercode,String orderNoTime) {
		this.taskExecutor.execute(new SendCodeThread(money, wxUser,ordercode,orderNoTime));
	}

	private class SendCodeThread implements Runnable {
		private Long money;
		private TxWxUser wxUser;
		private String  ordercode;
		private String  orderNoTime;
		

		private SendCodeThread(Long money,TxWxUser wxUser,String ordercode,String orderNoTime) {
			super();
			this.money = money;
			this.wxUser = wxUser;
			this.ordercode = ordercode;
			this.orderNoTime = orderNoTime;
		}

		@Override
		public void run() {
			
			try {
				
				Map<String, String> data = new HashMap<String, String>();
				
				data.put("txnType", "01"); 
				data.put("settType", "1");                   
				data.put("merId", ConfigConstants.MER_ID);         
				data.put("backUrl", ConfigConstants.FENRUN_BACK_URL);
				data.put("orderId", ordercode);
				data.put("merOrderTime", orderNoTime);
				data.put("txnAmt", money+"");
				data.put("bankNo", wxUser.getCardNumber());
				data.put("BankName", weiXinService.getKHBankName(wxUser.getCardNumber()));                  			   
				
				data.put("payName", wxUser.getRealName());
				data.put("ppType", "0");                
				
				
				Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           
				logger.info("分润代付");
				logger.info("http:// 114.113.238.50:54041/XW/Update");
				logger.info("发送报文");
				logger.info("--------分润代付发送报文------------>"+reqData);
				Map<String, String> rspData = AcpService.post(reqData,ConfigConstants.FENRUN_URL,DemoBase.encoding);
				logger.info("--------分润代付接收报文------------>"+rspData);
				logger.info(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
				/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
				
				payLogCutter.filesMng(16, 1, reqData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getRealName(), null);
	    		payLogCutter.filesMng(16, 2, rspData.toString(), wxUser.getId(), wxUser.getMobile(), wxUser.getRealName(), null);
				if(!rspData.isEmpty()){
					logger.info(rspData.get("respCode"));
				}else{
					logger.info(rspData.get("未获取到返回报文或返回http状态码非200"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
