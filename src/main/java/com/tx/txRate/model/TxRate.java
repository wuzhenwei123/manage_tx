package com.tx.txRate.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 分润比例	
 * @author	wzw
 * @time	2018-02-24 09:02:44
 */
public class TxRate extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 一级推广员费率 **/
	private java.math.BigDecimal onePromoterRate;
	/** 二级推广员费率 **/
	private java.math.BigDecimal twoPromoterRate;
	/** 开发人员费率 **/
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
	 * 一级推广员费率
	 * @return one_promoter_rate
	 */
	public java.math.BigDecimal getOnePromoterRate() {
		return onePromoterRate;
	}
	/**
	 * 一级推广员费率
	 */
	public void setOnePromoterRate(java.math.BigDecimal onePromoterRate) {
		this.onePromoterRate = onePromoterRate;
	}
	/**
	 * 二级推广员费率
	 * @return two_promoter_rate
	 */
	public java.math.BigDecimal getTwoPromoterRate() {
		return twoPromoterRate;
	}
	/**
	 * 二级推广员费率
	 */
	public void setTwoPromoterRate(java.math.BigDecimal twoPromoterRate) {
		this.twoPromoterRate = twoPromoterRate;
	}
	/**
	 * 开发人员费率
	 * @return dev_rate
	 */
	public java.math.BigDecimal getDevRate() {
		return devRate;
	}
	/**
	 * 开发人员费率
	 */
	public void setDevRate(java.math.BigDecimal devRate) {
		this.devRate = devRate;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}