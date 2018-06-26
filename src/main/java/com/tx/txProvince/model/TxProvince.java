package com.tx.txProvince.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 	
 * @author	wzw
 * @time	2018-02-01 10:00:12
 */
public class TxProvince extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/**  **/
	private String provinceCode;
	/**  **/
	private String provinceName;
		
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
	/**
	 * 
	 * @return provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * 
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}