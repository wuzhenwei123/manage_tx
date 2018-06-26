package com.tx.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.sys.manageAdminUser.model.ManageAdminUser;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
import com.tx.txWxUserBankNo.service.TxWxUserBankNoService;
import com.wx.service.IndexService;

@Component
public class SendCodeCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private IndexService indexService = null;

	public void filesMng(String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String fee,TxWxUser wxUser) {
		this.taskExecutor.execute(new SendCodeThread(orderId, txnTime, txWxUserBankNo, fee, wxUser));
	}

	private class SendCodeThread implements Runnable {
		private String orderId;
		private String txnTime;
		private TxWxUserBankNo txWxUserBankNo;
		private String fee;
		private TxWxUser wxUser;
		

		private SendCodeThread(String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String fee,TxWxUser wxUser) {
			super();
			this.orderId = orderId;
			this.txnTime = txnTime;
			this.txWxUserBankNo = txWxUserBankNo;
			this.fee = fee;
			this.wxUser = wxUser;
		}

		@Override
		public void run() {
			
			try {
				indexService.vercode(orderId, txnTime, txWxUserBankNo.getToken(), fee, wxUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
