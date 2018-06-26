package com.tx.txCity.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 	
 * @author	wzw
 * @time	2018-02-01 09:59:34
 */
public class TxCity extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/**  **/
	private String cityCode;
	/**  **/
	private String cityName;
	/**  **/
	private String provinceCode;
		
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
	/**
	 * 
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}
	/**
	 * 
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}