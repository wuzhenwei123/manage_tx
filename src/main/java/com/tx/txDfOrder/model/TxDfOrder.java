package com.tx.txDfOrder.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 代付申请表	
 * @author	wzw
 * @time	2018-07-25 10:50:55
 */
public class TxDfOrder extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 申请人id **/
	private Integer userId;
	/** 申请人姓名 **/
	private String realName;
	/** 订单号 **/
	private String orderCode;
	/** 申请时间 **/
	private Date createTime;
	/** 状态 0 未处理 1 已处理 **/
	private Integer state;
	/** 退费金额 **/
	private Long fee;
	/** 购卡时间 **/
	private Date orderTime;
		
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
	 * 申请人id
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 申请人id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 申请人姓名
	 * @return realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 申请人姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 订单号
	 * @return orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 订单号
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 申请时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 申请时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 状态 0 未处理 1 已处理
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 未处理 1 已处理
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 退费金额
	 * @return fee
	 */
	public Long getFee() {
		return fee;
	}
	/**
	 * 退费金额
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}
	/**
	 * 购卡时间
	 * @return orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}
	/**
	 * 购卡时间
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}