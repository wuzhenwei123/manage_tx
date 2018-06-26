package com.tx.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.base.utils.https.HttpUtils;
import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUser.service.TxWxUserService;
import com.wx.utils.WxApiURL;

@Component
public class UserInfoCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private TxWxUserService txWxUserService = null;

	public void filesMng(TxWxUser txWxUser,String accessToken) {
		this.taskExecutor.execute(new UserInfoCutterThread(txWxUser,accessToken));
	}

	private class UserInfoCutterThread implements Runnable {
		private TxWxUser txWxUser;
		private String accessToken;

		private UserInfoCutterThread(TxWxUser txWxUser,String accessToken) {
			super();
			this.txWxUser = txWxUser;
			this.accessToken = accessToken;
		}

		@Override
		public void run() {
			try {
				 String jsonInfo = HttpUtils.httpsRequest(WxApiURL.GET_USERINFO_URL, "GET", "access_token=" + accessToken + "&openid=" + txWxUser.getOpenId() + "&lang=zh_CN");
				 JSONObject json = JSONObject.parseObject(jsonInfo);
				 txWxUser.setNickName(json.getString("nickname"));
				 txWxUser.setHeadUrl(json.getString("headimgurl"));
				 txWxUser.setSex(json.getInteger("sex"));
				 txWxUserService.updateTxWxUserById(txWxUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
