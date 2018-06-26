package com.tx.txBusinessType.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 业务表	
 * @author	wzw
 * @time	2018-06-02 11:36:09
 */
public class TxBusinessType extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 费用类型 001水费，002电费 **/
	private String billType;
	/** 对应费用类型的中文描述如电费 **/
	private String feeTypeDesc;
	/** 缴费地区例如:大庆市等等 **/
	private String billArea;
	/** 收费单位最终的收费单位，如“大庆市电力局 **/
	private String chargeUnit;
	/** 是否需要上送账期 0 ：不需要  1：需要 **/
	private Integer isNeedDate;
	/** 城市码 **/
	private String cityCode;
	/** 缴费项目编号 **/
	private String serviceType;
	/** 附加信息 **/
	private String addInfo;
	/** 城市首字母（大写） **/
	private String bigLetters;
	/** 状态 0 禁用，1 可用 **/
	private Integer state;
	/** 对应城市表cityCode **/
	private String remark1;
	/** 备用 **/
	private String remark2;
	/** 备用 **/
	private String remark3;
	
	private Integer isNeedFee;
	private String feeRule;
	private Integer isNeedPolt;
		
	public Integer getIsNeedPolt() {
		return isNeedPolt;
	}
	public void setIsNeedPolt(Integer isNeedPolt) {
		this.isNeedPolt = isNeedPolt;
	}
	public String getFeeRule() {
		return feeRule;
	}
	public void setFeeRule(String feeRule) {
		this.feeRule = feeRule;
	}
	public Integer getIsNeedFee() {
		return isNeedFee;
	}
	public void setIsNeedFee(Integer isNeedFee) {
		this.isNeedFee = isNeedFee;
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
	 * 费用类型 001水费，002电费
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * 费用类型 001水费，002电费
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * 对应费用类型的中文描述如电费
	 * @return feeTypeDesc
	 */
	public String getFeeTypeDesc() {
		return feeTypeDesc;
	}
	/**
	 * 对应费用类型的中文描述如电费
	 */
	public void setFeeTypeDesc(String feeTypeDesc) {
		this.feeTypeDesc = feeTypeDesc;
	}
	/**
	 * 缴费地区例如:大庆市等等
	 * @return billArea
	 */
	public String getBillArea() {
		return billArea;
	}
	/**
	 * 缴费地区例如:大庆市等等
	 */
	public void setBillArea(String billArea) {
		this.billArea = billArea;
	}
	/**
	 * 收费单位最终的收费单位，如“大庆市电力局
	 * @return chargeUnit
	 */
	public String getChargeUnit() {
		return chargeUnit;
	}
	/**
	 * 收费单位最终的收费单位，如“大庆市电力局
	 */
	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}
	/**
	 * 是否需要上送账期 0 ：不需要  1：需要
	 * @return isNeedDate
	 */
	public Integer getIsNeedDate() {
		return isNeedDate;
	}
	/**
	 * 是否需要上送账期 0 ：不需要  1：需要
	 */
	public void setIsNeedDate(Integer isNeedDate) {
		this.isNeedDate = isNeedDate;
	}
	/**
	 * 城市码
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 城市码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 缴费项目编号
	 * @return serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * 缴费项目编号
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * 附加信息
	 * @return addInfo
	 */
	public String getAddInfo() {
		return addInfo;
	}
	/**
	 * 附加信息
	 */
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	/**
	 * 城市首字母（大写）
	 * @return bigLetters
	 */
	public String getBigLetters() {
		return bigLetters;
	}
	/**
	 * 城市首字母（大写）
	 */
	public void setBigLetters(String bigLetters) {
		this.bigLetters = bigLetters;
	}
	/**
	 * 状态 0 禁用，1 可用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 禁用，1 可用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 对应城市表cityCode
	 * @return remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * 对应城市表cityCode
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * 备用
	 * @return remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * 备用
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * 备用
	 * @return remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	/**
	 * 备用
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}