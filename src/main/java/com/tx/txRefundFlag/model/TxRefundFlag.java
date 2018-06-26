package com.tx.txRefundFlag.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 退卡开关	
 * @author	wzw
 * @time	2018-05-07 11:17:54
 */
public class TxRefundFlag extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 退卡开关 0:T0 /1:T1/2:T2 **/
	private Integer trem;
	private Integer style;
		
	public Integer getStyle() {
		return style;
	}
	public void setStyle(Integer style) {
		this.style = style;
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
	 * 退卡开关 0:T0 /1:T1/2:T2
	 * @return trem
	 */
	public Integer getTrem() {
		return trem;
	}
	/**
	 * 退卡开关 0:T0 /1:T1/2:T2
	 */
	public void setTrem(Integer trem) {
		this.trem = trem;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}