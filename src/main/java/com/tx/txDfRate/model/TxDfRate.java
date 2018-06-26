package com.tx.txDfRate.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 售电分润比例表	
 * @author	wzw
 * @time	2018-06-10 18:38:15
 */
public class TxDfRate extends BaseModel implements java.io.Serializable{
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
	/** 地区总代分润比例 **/
	private java.math.BigDecimal totalRate;
		
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
	/**
	 * 地区总代分润比例
	 * @return total_rate
	 */
	public java.math.BigDecimal getTotalRate() {
		return totalRate;
	}
	/**
	 * 地区总代分润比例
	 */
	public void setTotalRate(java.math.BigDecimal totalRate) {
		this.totalRate = totalRate;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}