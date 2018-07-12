package com.tx.txRecord.dao;

import java.util.List;

import com.tx.txRecord.model.TxRecord;
/**
 * @author	wzw
 * @time	2018-07-12 11:44:17
 */
public interface TxRecordDAO{
	
	public List<TxRecord> getTxRecordList(TxRecord txRecord);
	
	public TxRecord getTxRecordMoney(TxRecord txRecord);

	public TxRecord getTxRecordById(long id);

    public long insertTxRecord(TxRecord txRecord);

    public int updateTxRecordById(TxRecord txRecord);
    
    public int deleteTxRecordById(long id);
}
