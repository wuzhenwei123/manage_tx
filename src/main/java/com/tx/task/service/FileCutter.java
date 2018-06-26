package com.tx.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.tx.txWxOrder.model.TxWxOrder;
import com.wx.service.WxTemplateMsg;

@Component
public class FileCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;
	@Autowired
	private WxTemplateMsg wxTemplateMsg;

	public void filesMng(TxWxOrder txWxOrder) {
		this.taskExecutor.execute(new CutFilesThread(txWxOrder));
	}

	private class CutFilesThread implements Runnable {
		private TxWxOrder txWxOrder;

		private CutFilesThread(TxWxOrder txWxOrder) {
			super();
			this.txWxOrder = txWxOrder;
		}

		@Override
		public void run() {
			if(txWxOrder.getPromoterId()!=null){
				ManageAdminUser adminUser = manageadminuserService.getManageAdminUserById(txWxOrder.getPromoterId());
				wxTemplateMsg.sendYJTempltMsg(adminUser.getOpenId(), txWxOrder.getOne_rate());
			}
			
			if(txWxOrder.getTwoPromoterId()!=null){
				ManageAdminUser adminUser = manageadminuserService.getManageAdminUserById(txWxOrder.getTwoPromoterId());
				wxTemplateMsg.sendYJTempltMsg(adminUser.getOpenId(), txWxOrder.getTwo_rate());
			}
			
			
		}
	}
}
