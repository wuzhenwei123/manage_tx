package com.sys.manageAdminUser.model;

import java.util.Date;

import com.base.model.BaseModel;
/**
 * 	
 * @author	keeny
 * @time	2017-03-23 14:16:40
 */
public class ManageAdminUser extends BaseModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5516677085830533172L;
	/**  **/
	private Integer adminId;
	/** 用户名 **/
	private String adminName;
	/** 昵称 **/
	private String nickName;
	/** 密码 **/
	private String passwd;
	/** 真实姓名 **/
	private String realName;
	/** 手机 **/
	private String mobile;
	/** 电话 **/
	private String phone;
	/** 最后登陆日期 **/
	private Date lastLogin;
	/** 登陆IP **/
	private String loginIP;
	/** 密码修改日期 **/
	private Date pwdModifyTime;
	/** 状态1：正常，0：禁用 **/
	private Integer state;
	/** 注册时间 **/
	private Date createTime;
	/** 创建人 **/
	private Integer createrId;
	/** 头像 **/
	private String headImg;
	/** 角色ID **/
	private Integer role_id;
	/** 性别1男0女 **/
	private Integer sex;
	private String createAdminName;//创建人名称
	private String roleName;//角色名称
	
	/** 微信openId **/
	private String openId;
	/** 拓展业务员二维码地址 **/
	private String qr_code_url;
	/** 拓展业务员二维码票据（用于客户扫描） **/
	private String scan_ticket;
	/** 是否是默认拓展员 0 否 1 是 **/
	private Integer is_default_db;
	/** 业务拓展员id **/
	private Integer promoter_id;
	/** 业务拓展员姓名 **/
	private String promoter_name;
	/** 身份证照片地址 **/
	private String card_url;
	/** 身份证照片地址 **/
	private String card_fan_url;
	
	/** 营业执照照片地址 **/
	private String business_license_url;
	/** 批发商、零售商、代理商支付状态 0 未支付 1已支付 **/
	private Integer pay_state;
	/** 批发商、零售商、代理商服务结束时间 **/
	private Date end_time;
	
	private Date startTime;//用于查询
	private Date endTime;//用于查询
	
	private String pay_qrcode;
	/** 食品安全许可证照片地址 **/
	private String sp_url;
	private Integer parent_id;
	private String IDNumber;
	private String cardNumber;
	private String bankName;
	private String bankNo;
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getCard_fan_url() {
		return card_fan_url;
	}
	public void setCard_fan_url(String card_fan_url) {
		this.card_fan_url = card_fan_url;
	}
	
	public String getSp_url() {
		return sp_url;
	}
	public void setSp_url(String sp_url) {
		this.sp_url = sp_url;
	}
	public String getPay_qrcode() {
		return pay_qrcode;
	}
	public void setPay_qrcode(String pay_qrcode) {
		this.pay_qrcode = pay_qrcode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getPay_state() {
		return pay_state;
	}
	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getCard_url() {
		return card_url;
	}
	public void setCard_url(String card_url) {
		this.card_url = card_url;
	}
	public String getBusiness_license_url() {
		return business_license_url;
	}
	public void setBusiness_license_url(String business_license_url) {
		this.business_license_url = business_license_url;
	}
	public String getPromoter_name() {
		return promoter_name;
	}
	public void setPromoter_name(String promoter_name) {
		this.promoter_name = promoter_name;
	}
	public Integer getPromoter_id() {
		return promoter_id;
	}
	public void setPromoter_id(Integer promoter_id) {
		this.promoter_id = promoter_id;
	}
	public Integer getIs_default_db() {
		return is_default_db;
	}
	public void setIs_default_db(Integer is_default_db) {
		this.is_default_db = is_default_db;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getQr_code_url() {
		return qr_code_url;
	}
	public void setQr_code_url(String qr_code_url) {
		this.qr_code_url = qr_code_url;
	}
	public String getScan_ticket() {
		return scan_ticket;
	}
	public void setScan_ticket(String scan_ticket) {
		this.scan_ticket = scan_ticket;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreateAdminName() {
		return createAdminName;
	}
	public void setCreateAdminName(String createAdminName) {
		this.createAdminName = createAdminName;
	}
	/**
	 * 
	 * @return adminId
	 */
	public Integer getAdminId() {
		return adminId;
	}
	/**
	 * 
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	/**
	 * 用户名
	 * @return adminName
	 */
	public String getAdminName() {
		return adminName;
	}
	/**
	 * 用户名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	/**
	 * 昵称
	 * @return nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 密码
	 * @return passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 密码
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * 真实姓名
	 * @return realName
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 手机
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 电话
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 最后登陆日期
	 * @return lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}
	/**
	 * 最后登陆日期
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	/**
	 * 登陆IP
	 * @return loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}
	/**
	 * 登陆IP
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	/**
	 * 密码修改日期
	 * @return pwdModifyTime
	 */
	public Date getPwdModifyTime() {
		return pwdModifyTime;
	}
	/**
	 * 密码修改日期
	 */
	public void setPwdModifyTime(Date pwdModifyTime) {
		this.pwdModifyTime = pwdModifyTime;
	}
	/**
	 * 状态1：正常，0：禁用
	 * @return state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 状态1：正常，0：禁用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 注册时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 注册时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 创建人
	 * @return createrId
	 */
	public Integer getCreaterId() {
		return createrId;
	}
	/**
	 * 创建人
	 */
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	/**
	 * 头像
	 * @return headImg
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 角色ID
	 * @return role_id
	 */
	public Integer getRole_id() {
		return role_id;
	}
	/**
	 * 角色ID
	 */
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	/**
	 * 性别1男0女
	 * @return sex
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 性别1男0女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
}