package com.tx.task.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.tx.txPayLog.model.TxPayLog;
import com.tx.txPayLog.service.TxPayLogService;

@Component
public class PayLogCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private TxPayLogService txPayLogService = null;

	public void filesMng(Integer style,Integer type,String content,Integer userId,String mobile,String nickName,String accNo) {
		this.taskExecutor.execute(new CutPayLogThread(style, type, content, userId, mobile, nickName, accNo));
	}

	private class CutPayLogThread implements Runnable {
		private Integer style;
		private Integer type;
		private String content;
		private Integer userId;
		private String mobile;
		private String nickName;
		private String accNo;
		

		private CutPayLogThread(Integer style,Integer type,String content,Integer userId,String mobile,String nickName,String accNo) {
			super();
			this.style = style;
			this.type = type;
			this.content = content;
			this.userId = userId;
			this.mobile = mobile;
			this.nickName = nickName;
			this.accNo = accNo;
		}

		@Override
		public void run() {
			try {
				TxPayLog txPayLog = new TxPayLog();
				txPayLog.setStyle(style);
				txPayLog.setUserId(userId);
				txPayLog.setAccNo(accNo);
				txPayLog.setMobile(mobile);
				txPayLog.setContent(content);
				txPayLog.setCreateTime(new Date());
				txPayLog.setNickName(nickName);
				txPayLog.setType(type);
				txPayLogService.insertTxPayLog(txPayLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
