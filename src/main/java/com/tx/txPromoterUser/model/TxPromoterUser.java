package com.tx.txPromoterUser.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.base.model.BaseModel;
/**
 * 第三方推广人对应用户关系表	
 * @author	wzw
 * @time	2018-08-03 10:02:20
 */
public class TxPromoterUser extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**  **/
	private Integer id;
	/** 用户id **/
	private Integer userId;
	/** 第三方token **/
	private String promoterToken;
		
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
	 * 用户id
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 第三方token
	 * @return promoterToken
	 */
	public String getPromoterToken() {
		return promoterToken;
	}
	/**
	 * 第三方token
	 */
	public void setPromoterToken(String promoterToken) {
		this.promoterToken = promoterToken;
	}
	@Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
   	}
}