package com.tx.txPaynumberMsg.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 缴费号信息表	
 * @author	wzw
 * @time	2018-06-07 13:36:06
 */
public class TxPaynumberMsg extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Long id;
	/** 用户id **/
	private Integer userId;
	/** 交易类型代码：001-水费002-电费003-燃气费004-通讯费005-流量费006-手机充值 **/
	private String BillType;
	/** 客户编号：9-12位数字字符 **/
	private String payNumber;
	/** 客户名称 **/
	private String tradeName;
	/** 客户名称 **/
	private String nickName;
	/** 用电地址：200个字符 **/
	private String userAddress;
	/** 添加时间 **/
	private Date createTime;
	/** 有效标志：1有效/0无效 **/
	private Integer status;
	/** 备用1 **/
	private String remark1;
	/** 备用2 **/
	private String remark2;
	/** 备用3 **/
	private String remark3;
		
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
	 * 交易类型代码：001-水费002-电费003-燃气费004-通讯费005-流量费006-手机充值
	 * @return BillType
	 */
	public String getBillType() {
		return BillType;
	}
	/**
	 * 交易类型代码：001-水费002-电费003-燃气费004-通讯费005-流量费006-手机充值
	 */
	public void setBillType(String BillType) {
		this.BillType = BillType;
	}
	/**
	 * 客户编号：9-12位数字字符
	 * @return payNumber
	 */
	public String getPayNumber() {
		return payNumber;
	}
	/**
	 * 客户编号：9-12位数字字符
	 */
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	/**
	 * 客户名称
	 * @return tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}
	/**
	 * 客户名称
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	/**
	 * 客户名称
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 客户名称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 用电地址：200个字符
	 * @return userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}
	/**
	 * 用电地址：200个字符
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	/**
	 * 添加时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 添加时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 有效标志：1有效/0无效
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 有效标志：1有效/0无效
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 备用1
	 * @return remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * 备用1
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * 备用2
	 * @return remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * 备用2
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * 备用3
	 * @return remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	/**
	 * 备用3
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}