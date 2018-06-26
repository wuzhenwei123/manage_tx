package com.tx.txWxUserBankNo.dao;

import java.util.List;

import com.tx.txWxUserBankNo.model.TxWxUserBankNo;
/**
 * @author	wzw
 * @time	2018-02-05 10:34:13
 */
public interface TxWxUserBankNoDAO{
	
	public List<TxWxUserBankNo> getTxWxUserBankNoList(TxWxUserBankNo txWxUserBankNo);

	public TxWxUserBankNo getTxWxUserBankNoById(int id);
	public TxWxUserBankNo getTxWxUserBankNoByAccNo(String accNo);

    public int insertTxWxUserBankNo(TxWxUserBankNo txWxUserBankNo);

    public int updateTxWxUserBankNoById(TxWxUserBankNo txWxUserBankNo);
    
    public int deleteTxWxUserBankNoById(int id);
}
