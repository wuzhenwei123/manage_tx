package com.wx.model;

import com.base.model.BaseModel;

public class YongJinModel  extends BaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nickName;
	private String time;
	private String fee;
	private String yongjin;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getYongjin() {
		return yongjin;
	}
	public void setYongjin(String yongjin) {
		this.yongjin = yongjin;
	}

}
