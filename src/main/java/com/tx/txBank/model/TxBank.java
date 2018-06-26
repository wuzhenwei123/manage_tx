package com.tx.txBank.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 开户行信息	
 * @author	wzw
 * @time	2018-02-02 09:09:39
 */
public class TxBank extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 银行名称 **/
	private String name;
	/** 银联号（开户行行号） **/
	private String bankNumber;
	/** 银行卡校验码 **/
	private String verifyCode;
	/** 校验位长度 **/
	private Integer verifyLength;
	/** 缩写 **/
	private String shortName;
		
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
	 * 银行名称
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 银行名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 银联号（开户行行号）
	 * @return bank_number
	 */
	public String getBankNumber() {
		return bankNumber;
	}
	/**
	 * 银联号（开户行行号）
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	/**
	 * 银行卡校验码
	 * @return verify_code
	 */
	public String getVerifyCode() {
		return verifyCode;
	}
	/**
	 * 银行卡校验码
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	/**
	 * 校验位长度
	 * @return verify_length
	 */
	public Integer getVerifyLength() {
		return verifyLength;
	}
	/**
	 * 校验位长度
	 */
	public void setVerifyLength(Integer verifyLength) {
		this.verifyLength = verifyLength;
	}
	/**
	 * 缩写
	 * @return short_name
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * 缩写
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}