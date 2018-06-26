package com.tx.txBanner.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 轮播图	
 * @author	wzw
 * @time	2018-06-08 15:45:42
 */
public class TxBanner extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 图片地址 **/
	private String imgUrl;
	/** 状态 0 不可用 1 可用 **/
	private Integer state;
	/** 跳转地址 **/
	private String targetUrl;
		
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
	 * 图片地址
	 * @return imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 图片地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 状态 0 不可用 1 可用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态 0 不可用 1 可用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 跳转地址
	 * @return targetUrl
	 */
	public String getTargetUrl() {
		return targetUrl;
	}
	/**
	 * 跳转地址
	 */
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}