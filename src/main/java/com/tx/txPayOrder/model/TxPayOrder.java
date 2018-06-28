package com.tx.txPayOrder.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 缴费订单	
 * @author	wzw
 * @time	2018-06-07 14:03:06
 */
public class TxPayOrder extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 订单类型 **/
	private String orderType;
	/** 缴费号 **/
	private String payNumber;
	/** 订单号 **/
	private String orderNumber;
	/** 流水号 **/
	private String queryNumber;
	/** 缴费状态 0 未支付 1 已支付 **/
	private Integer state;
	/** 订单时间 **/
	private Date createTime;
	/** 缴费金额 **/
	private Long fee;
	/** 实际缴费金额 **/
	private Long realFee;
	/** 缴费人id **/
	private Integer userId;
	/** 缴费用户姓名 **/
	private String userName;
	/** 一代id **/
	private Integer promoterId;
	/** 一代名称 **/
	private String promoterName;
	/** 二代id **/
	private Integer twoPromoterId;
	/** 二代名称 **/
	private String twoPromoterName;
	/** 一代分润 **/
	private Integer oneRate;
	/** 二代分润 **/
	private Integer twoRate;
	/** 开发分润 **/
	private Integer devRate;
	/** 总代分润 **/
	private Integer totalRate;
	/** 支付银行卡号 **/
	private String accNo;
	/** 支付方式 1 微信支付 3 快捷支付 4 银联web支付 **/
	private Integer payWay;
	/** 清算日期 **/
	private Date settleDate;
	private String refundNumber;
		
	public String getRefundNumber() {
		return refundNumber;
	}
	public void setRefundNumber(String refundNumber) {
		this.refundNumber = refundNumber;
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
	 * 订单类型
	 * @return orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * 订单类型
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * 缴费号
	 * @return payNumber
	 */
	public String getPayNumber() {
		return payNumber;
	}
	/**
	 * 缴费号
	 */
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	/**
	 * 订单号
	 * @return orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 订单号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 流水号
	 * @return queryNumber
	 */
	public String getQueryNumber() {
		return queryNumber;
	}
	/**
	 * 流水号
	 */
	public void setQueryNumber(String queryNumber) {
		this.queryNumber = queryNumber;
	}
	/**
	 * 缴费状态 0 未支付 1 已支付
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 缴费状态 0 未支付 1 已支付
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 订单时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 订单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 缴费金额
	 * @return fee
	 */
	public Long getFee() {
		return fee;
	}
	/**
	 * 缴费金额
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}
	/**
	 * 实际缴费金额
	 * @return realFee
	 */
	public Long getRealFee() {
		return realFee;
	}
	/**
	 * 实际缴费金额
	 */
	public void setRealFee(Long realFee) {
		this.realFee = realFee;
	}
	/**
	 * 缴费人id
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 缴费人id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 缴费用户姓名
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 缴费用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 一代id
	 * @return promoterId
	 */
	public Integer getPromoterId() {
		return promoterId;
	}
	/**
	 * 一代id
	 */
	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}
	/**
	 * 一代名称
	 * @return promoterName
	 */
	public String getPromoterName() {
		return promoterName;
	}
	/**
	 * 一代名称
	 */
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	/**
	 * 二代id
	 * @return twoPromoterId
	 */
	public Integer getTwoPromoterId() {
		return twoPromoterId;
	}
	/**
	 * 二代id
	 */
	public void setTwoPromoterId(Integer twoPromoterId) {
		this.twoPromoterId = twoPromoterId;
	}
	/**
	 * 二代名称
	 * @return twoPromoterName
	 */
	public String getTwoPromoterName() {
		return twoPromoterName;
	}
	/**
	 * 二代名称
	 */
	public void setTwoPromoterName(String twoPromoterName) {
		this.twoPromoterName = twoPromoterName;
	}
	/**
	 * 一代分润
	 * @return oneRate
	 */
	public Integer getOneRate() {
		return oneRate;
	}
	/**
	 * 一代分润
	 */
	public void setOneRate(Integer oneRate) {
		this.oneRate = oneRate;
	}
	/**
	 * 二代分润
	 * @return twoRate
	 */
	public Integer getTwoRate() {
		return twoRate;
	}
	/**
	 * 二代分润
	 */
	public void setTwoRate(Integer twoRate) {
		this.twoRate = twoRate;
	}
	/**
	 * 开发分润
	 * @return devRate
	 */
	public Integer getDevRate() {
		return devRate;
	}
	/**
	 * 开发分润
	 */
	public void setDevRate(Integer devRate) {
		this.devRate = devRate;
	}
	/**
	 * 总代分润
	 * @return totalRate
	 */
	public Integer getTotalRate() {
		return totalRate;
	}
	/**
	 * 总代分润
	 */
	public void setTotalRate(Integer totalRate) {
		this.totalRate = totalRate;
	}
	/**
	 * 支付银行卡号
	 * @return accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * 支付银行卡号
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * 支付方式 1 微信支付 3 快捷支付 4 银联web支付
	 * @return payWay
	 */
	public Integer getPayWay() {
		return payWay;
	}
	/**
	 * 支付方式 1 微信支付 3 快捷支付 4 银联web支付
	 */
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	/**
	 * 清算日期
	 * @return settleDate
	 */
	public Date getSettleDate() {
		return settleDate;
	}
	/**
	 * 清算日期
	 */
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}