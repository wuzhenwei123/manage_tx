package com.tx.txApplay.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 提现申请表	
 * @author	wzw
 * @time	2018-07-12 11:49:15
 */
public class TxApplay extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 申请人id **/
	private Integer userId;
	/** 申请人姓名 **/
	private String name;
	/** 申请金额 **/
	private Long fee;
	/** 申请时间 **/
	private Date createTime;
	/** 申请状态 0 未审核 1 已审核 **/
	private Integer state;
	/** 申请批次 **/
	private String batch;
	/** 审核时间 **/
	private Date checkTime;
		
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
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 申请人姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 申请金额
	 * @return fee
	 */
	public Long getFee() {
		return fee;
	}
	/**
	 * 申请金额
	 */
	public void setFee(Long fee) {
		this.fee = fee;
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
	 * 申请状态 0 未审核 1 已审核
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 申请状态 0 未审核 1 已审核
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 申请批次
	 * @return batch
	 */
	public String getBatch() {
		return batch;
	}
	/**
	 * 申请批次
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}
	/**
	 * 审核时间
	 * @return checkTime
	 */
	public Date getCheckTime() {
		return checkTime;
	}
	/**
	 * 审核时间
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}