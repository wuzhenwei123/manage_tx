package com.tx.txArea.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 	
 * @author	wzw
 * @time	2018-02-01 09:59:59
 */
public class TxArea extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/**  **/
	private String areaCode;
	/**  **/
	private String areaName;
	/**  **/
	private String cityCode;
		
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
	 * 
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * 
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 
	 * @return areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * 
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}