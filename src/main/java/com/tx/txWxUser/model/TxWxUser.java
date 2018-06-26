package com.tx.txWxUser.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 用户表（购买商品用户）	
 * @author	wzw
 * @time	2018-02-01 13:47:41
 */
public class TxWxUser extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 昵称 **/
	private String nickName;
	/**  **/
	private String openId;
	/** 头像地址 **/
	private String headUrl;
	/** 性别 1 男 2 女 **/
	private Integer sex;
	/** 注册时间 **/
	private Date createTime;
	/** 真实姓名 **/
	private String realName;
	/** 身份证号 **/
	private String IDNumber;
	/** 结算卡号 **/
	private String cardNumber;
	/** 身份证正面照地址 **/
	private String IDUrl;
	/** 身份证反面照地址 **/
	private String IDFanUrl;
	/** 手持身份证照片地址 **/
	private String IDPersonUrl;
	/** 银行卡正面照地址 **/
	private String cardUrl;
	/** 手机 **/
	private String mobile;
	/** 状态 0 未审核 1 审核通过 **/
	private Integer state;
	/** 开户行行号 **/
	private String openBank;
	/** 推广员id **/
	private Integer promoterId;
	
	/** 商户id **/
	private String merId;
	/** 接口验证状态 **/
	private Integer checkState;
	/** 验证失败原因，接口返回 **/
	private String msg;
	private String promoterName;
	private String twoPromoterName;
	
	private Integer parentId;
	
	private Integer userCount;
	private String mouthA;
	private String year_str;
	
	public String getYear_str() {
		return year_str;
	}
	public void setYear_str(String year_str) {
		this.year_str = year_str;
	}
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	public String getMouthA() {
		return mouthA;
	}
	public void setMouthA(String mouthA) {
		this.mouthA = mouthA;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getTwoPromoterName() {
		return twoPromoterName;
	}
	public void setTwoPromoterName(String twoPromoterName) {
		this.twoPromoterName = twoPromoterName;
	}
	private Integer twoPromoterId;
		
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
	public Integer getCheckState() {
		return checkState;
	}
	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
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
	 * 昵称
	 * @return nick_name
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
	 * 
	 * @return openId
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 头像地址
	 * @return head_url
	 */
	public String getHeadUrl() {
		return headUrl;
	}
	/**
	 * 头像地址
	 */
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	/**
	 * 性别 1 男 2 女
	 * @return sex
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 性别 1 男 2 女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 注册时间
	 * @return create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 注册时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 真实姓名
	 * @return real_name
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 身份证号
	 * @return ID_number
	 */
	public String getIDNumber() {
		return IDNumber;
	}
	/**
	 * 身份证号
	 */
	public void setIDNumber(String IDNumber) {
		this.IDNumber = IDNumber;
	}
	/**
	 * 结算卡号
	 * @return card_number
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	/**
	 * 结算卡号
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * 身份证正面照地址
	 * @return ID_url
	 */
	public String getIDUrl() {
		return IDUrl;
	}
	/**
	 * 身份证正面照地址
	 */
	public void setIDUrl(String IDUrl) {
		this.IDUrl = IDUrl;
	}
	/**
	 * 身份证反面照地址
	 * @return ID_fan_url
	 */
	public String getIDFanUrl() {
		return IDFanUrl;
	}
	/**
	 * 身份证反面照地址
	 */
	public void setIDFanUrl(String IDFanUrl) {
		this.IDFanUrl = IDFanUrl;
	}
	/**
	 * 手持身份证照片地址
	 * @return ID_person_url
	 */
	public String getIDPersonUrl() {
		return IDPersonUrl;
	}
	/**
	 * 手持身份证照片地址
	 */
	public void setIDPersonUrl(String IDPersonUrl) {
		this.IDPersonUrl = IDPersonUrl;
	}
	/**
	 * 银行卡正面照地址
	 * @return card_url
	 */
	public String getCardUrl() {
		return cardUrl;
	}
	/**
	 * 银行卡正面照地址
	 */
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	/**
	 * 手机
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 状态 0 未审核 1 审核通过
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 未审核 1 审核通过
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 开户行行号
	 * @return open_bank
	 */
	public String getOpenBank() {
		return openBank;
	}
	/**
	 * 开户行行号
	 */
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}
	/**
	 * 推广员id
	 * @return promoter_id
	 */
	public Integer getPromoterId() {
		return promoterId;
	}
	/**
	 * 推广员id
	 */
	public void setPromoterId(Integer promoterId) {
		this.promoterId = promoterId;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}