package com.base.utils.yft.bean;

public class QrCodeTran {
	private String trxType="UNIONPAY_QRCODE";//接口类型 是 固定值 UNIONPAY_QRCODE 银联二维码扫码
	private String merchantNo="";//merchantNo 商户编号 是 10 位 B1000001 2
	private String orderNum="";// 商户订单号
	private String amount="";// 金额 是 元为单
	private String goodsName="";// 订单描述 是 <150 一条裤子 5
	private String timeOut="";// 订单失效时
	private String callbackUrl="";// 页面回调地址否 <300 http://c.a.com/page 7
	private String serverCallbackUrl="";//服务器回调地 否 <300 http://c.a.com/server 8
	private String orderIp="";// 用户支付 IP 是 Ip 地址 1.1.1.1 9
	private String toibkn="";// 收款行联行 号 否 长度
	private String cardNo="";// 入账卡号 否 3des 加密后，使用 base64，utf8编码做加密。
	private String idCardNo="";// 入帐卡对应身份证号 否 同上 
	private String payerName=""; //入帐卡对应姓名否 同上 1314 
	private String encrypt=""; //T0/T1 标识，若此项为T0，对应的10,11,12,13必填是 T1 目前银联二维码只支持 T1 交易
	private String sign="";// 签名 是 =32 15
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getServerCallbackUrl() {
		return serverCallbackUrl;
	}
	public void setServerCallbackUrl(String serverCallbackUrl) {
		this.serverCallbackUrl = serverCallbackUrl;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getToibkn() {
		return toibkn;
	}
	public void setToibkn(String toibkn) {
		this.toibkn = toibkn;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
