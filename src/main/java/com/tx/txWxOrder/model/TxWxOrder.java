package com.tx.txWxOrder.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 订单表	
 * @author	wzw
 * @time	2018-02-07 12:45:27
 */
public class TxWxOrder extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 微信用户id **/
	private Integer wxUserId;
	/** 订单金额 **/
	private Long money;
	/** 订单时间 **/
	private Date createTime;
	/** 订单号 **/
	private String orderCode;
	/** 银行卡号 **/
	private String accNo;
	/** 支付状态 0 未支付 1 已支付 **/
	private Integer state;
	/** 订单类型 1 开卡 2 支付 **/
	private Integer style;
	/** 消费银行交易流水 **/
	private String queryId;
	/** 消费清算日期 **/
	private String xfSettleDate;
	/** 消费标记 **/
	private String xfFlg;
	/** 消费状态 0 未消费 1 已消费 **/
	private Integer xfState;
	/** 代付状态 0 未代付 1 已代付 **/
	private Integer dfState;
	/** 手续费 **/
	private Integer counterFee;
	/** 代付标记 **/
	private String dfFlg;
	/** 代付清算日 **/
	private String dfSettleDate;
	/** 系统跟踪号 **/
	private String traceNo;
	/** 代付流水单号 **/
	private String dfQueryId;
	/** 代付金额 **/
	private Integer orderAmt;
	private String txnTime;
	
	private Date startTime;
	private Date endTime;
		
	/** 推广员id **/
	private Integer promoterId;
	
	private String wxUserName;
	private String promoterName;
	private String twoPromoterName;
	private String bankName;
	private String cardNumber;
	private String bankNo;
	
	private Long userCount;
	
	private String year_str;
	private String zfOrderNo;
	private Integer zfOrderFee;
	
	public String getZfOrderNo() {
		return zfOrderNo;
	}
	public void setZfOrderNo(String zfOrderNo) {
		this.zfOrderNo = zfOrderNo;
	}
	public Integer getZfOrderFee() {
		return zfOrderFee;
	}
	public void setZfOrderFee(Integer zfOrderFee) {
		this.zfOrderFee = zfOrderFee;
	}
	public String getYear_str() {
		return year_str;
	}
	public void setYear_str(String year_str) {
		this.year_str = year_str;
	}
	
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTwoPromoterName() {
		return twoPromoterName;
	}
	public void setTwoPromoterName(String twoPromoterName) {
		this.twoPromoterName = twoPromoterName;
	}
	private Integer twoPromoterId;
	private Integer parentId;
	
	private String mobile;
	private String realName;
	private Integer promoter_state;
	
	private Long one_rate;
	private Long two_rate;
	private Long dev_rate;
	private String msg;
	private String msgDf;

	public String getMsgDf() {
		return msgDf;
	}
	public void setMsgDf(String msgDf) {
		this.msgDf = msgDf;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getOne_rate() {
		return one_rate;
	}
	public void setOne_rate(Long one_rate) {
		this.one_rate = one_rate;
	}
	public Long getTwo_rate() {
		return two_rate;
	}
	public void setTwo_rate(Long two_rate) {
		this.two_rate = two_rate;
	}
	public Long getDev_rate() {
		return dev_rate;
	}
	public void setDev_rate(Long dev_rate) {
		this.dev_rate = dev_rate;
	}
	public Integer getPromoter_state() {
		return promoter_state;
	}
	public void setPromoter_state(Integer promoter_state) {
		this.promoter_state = promoter_state;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getTwoPromoterId() {
		return twoPromoterId;
	}
	public void setTwoPromoterId(Integer twoPromoterId) {
		this.twoPromoterId = twoPromoterId;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getWxUserName() {
		return wxUserName;
	}
	public void setWxUserName(String wxUserName) {
		this.wxUserName = wxUserName;
	}
	public Integer getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
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
	 * 订单金额
	 * @return money
	 */
	public Long getMoney() {
		return money;
	}
	/**
	 * 订单金额
	 */
	public void setMoney(Long money) {
		this.money = money;
	}
	/**
	 * 订单时间
	 * @return create_time
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
	 * 订单号
	 * @return order_code
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
	 * 支付状态 0 未支付 1 已支付
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 支付状态 0 未支付 1 已支付
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 订单类型 1 开卡 2 支付
	 * @return style
	 */
	public Integer getStyle() {
		return style;
	}
	/**
	 * 订单类型 1 开卡 2 支付
	 */
	public void setStyle(Integer style) {
		this.style = style;
	}
	/**
	 * 消费银行交易流水
	 * @return queryId
	 */
	public String getQueryId() {
		return queryId;
	}
	/**
	 * 消费银行交易流水
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	/**
	 * 消费清算日期
	 * @return xf_settleDate
	 */
	public String getXfSettleDate() {
		return xfSettleDate;
	}
	/**
	 * 消费清算日期
	 */
	public void setXfSettleDate(String xfSettleDate) {
		this.xfSettleDate = xfSettleDate;
	}
	/**
	 * 消费标记
	 * @return xf_flg
	 */
	public String getXfFlg() {
		return xfFlg;
	}
	/**
	 * 消费标记
	 */
	public void setXfFlg(String xfFlg) {
		this.xfFlg = xfFlg;
	}
	/**
	 * 消费状态 0 未消费 1 已消费
	 * @return xf_state
	 */
	public Integer getXfState() {
		return xfState;
	}
	/**
	 * 消费状态 0 未消费 1 已消费
	 */
	public void setXfState(Integer xfState) {
		this.xfState = xfState;
	}
	/**
	 * 代付状态 0 未代付 1 已代付
	 * @return df_state
	 */
	public Integer getDfState() {
		return dfState;
	}
	/**
	 * 代付状态 0 未代付 1 已代付
	 */
	public void setDfState(Integer dfState) {
		this.dfState = dfState;
	}
	/**
	 * 手续费
	 * @return counterFee
	 */
	public Integer getCounterFee() {
		return counterFee;
	}
	/**
	 * 手续费
	 */
	public void setCounterFee(Integer counterFee) {
		this.counterFee = counterFee;
	}
	/**
	 * 代付标记
	 * @return df_flg
	 */
	public String getDfFlg() {
		return dfFlg;
	}
	/**
	 * 代付标记
	 */
	public void setDfFlg(String dfFlg) {
		this.dfFlg = dfFlg;
	}
	/**
	 * 代付清算日
	 * @return df_settleDate
	 */
	public String getDfSettleDate() {
		return dfSettleDate;
	}
	/**
	 * 代付清算日
	 */
	public void setDfSettleDate(String dfSettleDate) {
		this.dfSettleDate = dfSettleDate;
	}
	/**
	 * 系统跟踪号
	 * @return traceNo
	 */
	public String getTraceNo() {
		return traceNo;
	}
	/**
	 * 系统跟踪号
	 */
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	/**
	 * 代付流水单号
	 * @return dfQueryId
	 */
	public String getDfQueryId() {
		return dfQueryId;
	}
	/**
	 * 代付流水单号
	 */
	public void setDfQueryId(String dfQueryId) {
		this.dfQueryId = dfQueryId;
	}
	/**
	 * 代付金额
	 * @return orderAmt
	 */
	public Integer getOrderAmt() {
		return orderAmt;
	}
	/**
	 * 代付金额
	 */
	public void setOrderAmt(Integer orderAmt) {
		this.orderAmt = orderAmt;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}