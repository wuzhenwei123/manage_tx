package com.tx.txBank.dao;

import java.util.List;

import com.tx.txBank.model.TxBank;
/**
 * @author	wzw
 * @time	2018-02-02 09:09:39
 */
public interface TxBankDAO{
	
	public List<TxBank> getTxBankList(TxBank txBank);

	public TxBank getTxBankById(int id);
	public TxBank getTxBankByBankNumber(String bankNumber);

    public int insertTxBank(TxBank txBank);

    public int updateTxBankById(TxBank txBank);
    
    public int deleteTxBankById(int id);
}
