package com.tx.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.base.utils.https.HttpUtils;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.wx.utils.WxApiURL;

@Component
public class ManageUserCutter {

	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ManageAdminUserService manageadminuserService = null;

	public void filesMng(ManageAdminUser manageAdminUser,String accessToken) {
		this.taskExecutor.execute(new UserInfoCutterThread(manageAdminUser,accessToken));
	}

	private class UserInfoCutterThread implements Runnable {
		private ManageAdminUser manageAdminUser;
		private String accessToken;

		private UserInfoCutterThread(ManageAdminUser manageAdminUser,String accessToken) {
			super();
			this.manageAdminUser = manageAdminUser;
			this.accessToken = accessToken;
		}

		@Override
		public void run() {
			try {
				 String jsonInfo = HttpUtils.httpsRequest(WxApiURL.GET_USERINFO_URL, "GET", "access_token=" + accessToken + "&openid=" + manageAdminUser.getOpenId() + "&lang=zh_CN");
				 JSONObject json = JSONObject.parseObject(jsonInfo);
				 manageAdminUser.setNickName(json.getString("nickname"));
				 manageAdminUser.setHeadImg(json.getString("headimgurl"));
				 manageAdminUser.setSex(json.getInteger("sex"));
				 manageadminuserService.updateManageAdminUser(manageAdminUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
