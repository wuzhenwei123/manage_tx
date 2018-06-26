package com.tx.txWxUserBankNo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tx.txWxUser.model.TxWxUser;
import com.tx.txWxUserBankNo.model.TxWxUserBankNo;

/**
 * @author	wzw
 * @time	2018-02-05 10:34:13
 */
public interface TxWxUserBankNoService {
	
	
	public List<TxWxUserBankNo> getTxWxUserBankNoList(TxWxUserBankNo txWxUserBankNo);

	public TxWxUserBankNo getTxWxUserBankNoById(int id);
	
	public TxWxUserBankNo getTxWxUserBankNoByAccNo(String accNo);

    public int insertTxWxUserBankNo(TxWxUserBankNo txWxUserBankNo);

    public int updateTxWxUserBankNoById(TxWxUserBankNo txWxUserBankNo);
    
    public int deleteTxWxUserBankNoById(int id);
    
    /**
     * 获取银联token
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    public String getUnionPayToken(TxWxUser wxUser,String orderId,String txnTime,String accNo);
    
    /**
     * 开通回调
     * @param request
     * @return
     */
    public String backTransUrl(HttpServletRequest req);
    /**
     * 开通回调
     * @param request
     * @return
     */
    public String frontTransUrl(HttpServletRequest req);
    
    /**
     * 发送验证码
     * @param wxUser
     * @param orderId
     * @param txnTime
     * @param txWxUserBankNo
     * @param txnAmt
     * @param phoneNo
     * @return
     */
    public boolean vercode(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt);
    /**
     * 发送验证码(新版)
     * @param wxUser
     * @param orderId
     * @param txnTime
     * @param txWxUserBankNo
     * @param txnAmt
     * @param phoneNo
     * @return
     */
    public boolean vercodeNew(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,Integer backCard);
    
    /**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> pay(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode);
    
    /**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> paySell(TxWxUser wxUser,String orderId,String txnTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode,Integer backCard);
    
    
    public String backUrlDF(HttpServletRequest req);
    
    public String backUrlTrans(HttpServletRequest req);
    
    public String backUrlTrans_sell(HttpServletRequest req);
    
    public String xw_backUrl(HttpServletRequest req);
    
    /**
	 * 交易更新
	 * @param orderId
	 * @param txnTime
	 */
	public boolean updatePay(String orderId,String txnTime,TxWxUser wxUser);
	
	/**
     * 交易
     * @param request
     * @return
     */
    public Map<String, String> xwDF(TxWxUser wxUser,String orderId,String merOrderTime,TxWxUserBankNo txWxUserBankNo,String txnAmt,String smsCode,Integer backCard,String flag);
}
