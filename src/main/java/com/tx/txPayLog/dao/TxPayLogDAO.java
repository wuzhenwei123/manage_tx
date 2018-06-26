package com.tx.txPayLog.dao;

import java.util.List;

import com.tx.txPayLog.model.TxPayLog;
/**
 * @author	wzw
 * @time	2018-06-10 17:57:44
 */
public interface TxPayLogDAO{
	
	public List<TxPayLog> getTxPayLogList(TxPayLog txPayLog);

	public TxPayLog getTxPayLogById(long id);

    public long insertTxPayLog(TxPayLog txPayLog);

    public int updateTxPayLogById(TxPayLog txPayLog);
    
    public int deleteTxPayLogById(long id);
}
