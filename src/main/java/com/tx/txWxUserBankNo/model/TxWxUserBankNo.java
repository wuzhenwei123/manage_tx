package com.tx.txWxUserBankNo.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 用户银行卡号关联表	
 * @author	wzw
 * @time	2018-02-05 10:34:13
 */
public class TxWxUserBankNo extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 微信用户id **/
	private Integer wxUserId;
	/** 银行卡号 **/
	private String accNo;
	/**  **/
	private String token;
	private String accName;
	private String endCode;
	private String phone;
		
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/**
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 微信用户id
	 * @return wx_user_id
	 */
	public Integer getWxUserId() {
		return wxUserId;
	}
	/**
	 * 微信用户id
	 */
	public void setWxUserId(Integer wxUserId) {
		this.wxUserId = wxUserId;
	}
	/**
	 * 银行卡号
	 * @return acc_no
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * 银行卡号
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * 
	 * @return token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 
	 */
	public void setToken(String token) {
		this.token = token;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}