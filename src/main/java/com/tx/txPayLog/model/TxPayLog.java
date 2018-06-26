package com.tx.txPayLog.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 支付交易接口日志	
 * @author	wzw
 * @time	2018-06-10 17:57:45
 */
public class TxPayLog extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 类型 1 开通token 2 发送短信 3 支付 **/
	private Integer style;
	/** 发送类型 1 发送 2 回调 **/
	private Integer type;
	/** 报文 **/
	private String content;
	/** 时间 **/
	private Date createTime;
	/** 用户id **/
	private Integer userId;
	/** 售假货 **/
	private String mobile;
	/** 昵称 **/
	private String nickName;
	/** 银行卡号 **/
	private String accNo;
		
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
	 * 类型 1 开通token 2 发送短信 3 支付
	 * @return style
	 */
	public Integer getStyle() {
		return style;
	}
	/**
	 * 类型 1 开通token 2 发送短信 3 支付
	 */
	public void setStyle(Integer style) {
		this.style = style;
	}
	/**
	 * 发送类型 1 发送 2 回调
	 * @return type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 发送类型 1 发送 2 回调
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 报文
	 * @return content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 报文
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 售假货
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 售假货
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 昵称
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 银行卡号
	 * @return accNo
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
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}