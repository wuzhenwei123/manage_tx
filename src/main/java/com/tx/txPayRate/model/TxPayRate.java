package com.tx.txPayRate.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 年化收益分润比例表	
 * @author	wzw
 * @time	2018-05-02 13:35:17
 */
public class TxPayRate extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 一级代理分润比例 **/
	private java.math.BigDecimal oneRate;
	/** 二级代理分润比例 **/
	private java.math.BigDecimal twoRate;
	/** 开发分润比例 **/
	private java.math.BigDecimal devRate;
		
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
	 * 一级代理分润比例
	 * @return one_rate
	 */
	public java.math.BigDecimal getOneRate() {
		return oneRate;
	}
	/**
	 * 一级代理分润比例
	 */
	public void setOneRate(java.math.BigDecimal oneRate) {
		this.oneRate = oneRate;
	}
	/**
	 * 二级代理分润比例
	 * @return two_rate
	 */
	public java.math.BigDecimal getTwoRate() {
		return twoRate;
	}
	/**
	 * 二级代理分润比例
	 */
	public void setTwoRate(java.math.BigDecimal twoRate) {
		this.twoRate = twoRate;
	}
	/**
	 * 开发分润比例
	 * @return dev_rate
	 */
	public java.math.BigDecimal getDevRate() {
		return devRate;
	}
	/**
	 * 开发分润比例
	 */
	public void setDevRate(java.math.BigDecimal devRate) {
		this.devRate = devRate;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}