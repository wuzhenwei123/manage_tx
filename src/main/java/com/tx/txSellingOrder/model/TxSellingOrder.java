package com.tx.txSellingOrder.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 售卡订单	
 * @author	wzw
 * @time	2018-04-30 22:59:35
 */
public class TxSellingOrder extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 订单号 **/
	private String code;
	/** 金额 **/
	private Long money;
	/** 支付日期 **/
	private Date createTime;
	/** 支付银行卡卡号 **/
	private String accNo;
	/** 结算日期 **/
	private Date endTime;
	/** 状态 0 未支付 1 支付成功 **/
	private Integer state;
	/** 银行流水号 **/
	private String queryId;
	/** 业务员id **/
	private Integer promoterId;
	/** 二级业务员id **/
	private Integer twoPromoterId;
	/** 用户姓名 **/
	private String wxUserName;
	/** 业务员分润金额 **/
	private Integer oneRate;
	/** 二级业务员分润金额 **/
	private Integer twoRate;
	/** 开发分润 **/
	private Integer devRate;
	/** 微信用户id **/
	private Integer wxUserId;
	/** 返还金额 **/
	private Long profitManey;
	/** 收益比例 **/
	private java.math.BigDecimal profits;
	/** 不定期退卡标记 1同意 0 不同意 **/
	private Integer backCard;
	/** 退费状态 0 未退费 1 已退费 **/
	private Integer refundState;
	/** 退费流水号 **/
	private String refundQueryId;
	/** 退费银行卡号 **/
	private String refundAccNo;
	/** 退费单号 **/
	private String refundCode;
	private String promoterName;
	private String twoPromoterName;
	private Date refundTime;
	
	private String year_str;
	
	private Integer parentId;
	
	private String mobile;
	private String realName;
	private Integer promoter_state;
	private Integer selTime;
	private String xwMerId;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getXwMerId() {
		return xwMerId;
	}
	public void setXwMerId(String xwMerId) {
		this.xwMerId = xwMerId;
	}
	public Integer getSelTime() {
		return selTime;
	}
	public void setSelTime(Integer selTime) {
		this.selTime = selTime;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getPromoter_state() {
		return promoter_state;
	}
	public void setPromoter_state(Integer promoter_state) {
		this.promoter_state = promoter_state;
	}
	public String getYear_str() {
		return year_str;
	}
	public void setYear_str(String year_str) {
		this.year_str = year_str;
	}
		
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getTwoPromoterName() {
		return twoPromoterName;
	}
	public void setTwoPromoterName(String twoPromoterName) {
		this.twoPromoterName = twoPromoterName;
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
	 * 订单号
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 订单号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 金额
	 * @return money
	 */
	public Long getMoney() {
		return money;
	}
	/**
	 * 金额
	 */
	public void setMoney(Long money) {
		this.money = money;
	}
	/**
	 * 支付日期
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 支付日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 支付银行卡卡号
	 * @return accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * 支付银行卡卡号
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * 结算日期
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 结算日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 状态 0 未支付 1 支付成功
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 未支付 1 支付成功
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 银行流水号
	 * @return queryId
	 */
	public String getQueryId() {
		return queryId;
	}
	/**
	 * 银行流水号
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	/**
	 * 业务员id
	 * @return promoterId
	 */
	public Integer getPromoterId() {
		return promoterId;
	}
	/**
	 * 业务员id
	 */
	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}
	/**
	 * 二级业务员id
	 * @return twoPromoterId
	 */
	public Integer getTwoPromoterId() {
		return twoPromoterId;
	}
	/**
	 * 二级业务员id
	 */
	public void setTwoPromoterId(Integer twoPromoterId) {
		this.twoPromoterId = twoPromoterId;
	}
	/**
	 * 用户姓名
	 * @return wxUserName
	 */
	public String getWxUserName() {
		return wxUserName;
	}
	/**
	 * 用户姓名
	 */
	public void setWxUserName(String wxUserName) {
		this.wxUserName = wxUserName;
	}
	/**
	 * 业务员分润金额
	 * @return one_rate
	 */
	public Integer getOneRate() {
		return oneRate;
	}
	/**
	 * 业务员分润金额
	 */
	public void setOneRate(Integer oneRate) {
		this.oneRate = oneRate;
	}
	/**
	 * 二级业务员分润金额
	 * @return two_rate
	 */
	public Integer getTwoRate() {
		return twoRate;
	}
	/**
	 * 二级业务员分润金额
	 */
	public void setTwoRate(Integer twoRate) {
		this.twoRate = twoRate;
	}
	/**
	 * 开发分润
	 * @return dev_rate
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
	 * 微信用户id
	 * @return wxUserId
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
	 * 返还金额
	 * @return profitManey
	 */
	public Long getProfitManey() {
		return profitManey;
	}
	/**
	 * 返还金额
	 */
	public void setProfitManey(Long profitManey) {
		this.profitManey = profitManey;
	}
	/**
	 * 收益比例
	 * @return profits
	 */
	public java.math.BigDecimal getProfits() {
		return profits;
	}
	/**
	 * 收益比例
	 */
	public void setProfits(java.math.BigDecimal profits) {
		this.profits = profits;
	}
	/**
	 * 不定期退卡标记 1同意 0 不同意
	 * @return backCard
	 */
	public Integer getBackCard() {
		return backCard;
	}
	/**
	 * 不定期退卡标记 1同意 0 不同意
	 */
	public void setBackCard(Integer backCard) {
		this.backCard = backCard;
	}
	/**
	 * 退费状态 0 未退费 1 已退费
	 * @return refundState
	 */
	public Integer getRefundState() {
		return refundState;
	}
	/**
	 * 退费状态 0 未退费 1 已退费
	 */
	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}
	/**
	 * 退费流水号
	 * @return refundQueryId
	 */
	public String getRefundQueryId() {
		return refundQueryId;
	}
	/**
	 * 退费流水号
	 */
	public void setRefundQueryId(String refundQueryId) {
		this.refundQueryId = refundQueryId;
	}
	/**
	 * 退费银行卡号
	 * @return refundAccNo
	 */
	public String getRefundAccNo() {
		return refundAccNo;
	}
	/**
	 * 退费银行卡号
	 */
	public void setRefundAccNo(String refundAccNo) {
		this.refundAccNo = refundAccNo;
	}
	/**
	 * 退费单号
	 * @return refundCode
	 */
	public String getRefundCode() {
		return refundCode;
	}
	/**
	 * 退费单号
	 */
	public void setRefundCode(String refundCode) {
		this.refundCode = refundCode;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}