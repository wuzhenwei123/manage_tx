package com.tx.txRecord.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 提现记录表	
 * @author	wzw
 * @time	2018-07-12 11:44:17
 */
public class TxRecord extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 用户id **/
	private Integer userId;
	/** 提现金额 **/
	private Long fee;
	/** 提现人姓名 **/
	private String name;
	/** 提现银行卡号 **/
	private String accNo;
	/** 提现状态 0 失败 1成功 **/
	private Integer state;
	/** 接口返回码 **/
	private String rspCode;
	/** 接口返回信息 **/
	private String rspData;
	/** 提现申请记录id **/
	private Long applyId;
	private String orderCode;
		
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 用户id
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 提现金额
	 * @return fee
	 */
	public Long getFee() {
		return fee;
	}
	/**
	 * 提现金额
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}
	/**
	 * 提现人姓名
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 提现人姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 提现银行卡号
	 * @return accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * 提现银行卡号
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * 提现状态 0 失败 1成功
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 提现状态 0 失败 1成功
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 接口返回码
	 * @return rspCode
	 */
	public String getRspCode() {
		return rspCode;
	}
	/**
	 * 接口返回码
	 */
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	/**
	 * 接口返回信息
	 * @return rspData
	 */
	public String getRspData() {
		return rspData;
	}
	/**
	 * 接口返回信息
	 */
	public void setRspData(String rspData) {
		this.rspData = rspData;
	}
	/**
	 * 提现申请记录id
	 * @return applyId
	 */
	public Long getApplyId() {
		return applyId;
	}
	/**
	 * 提现申请记录id
	 */
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}