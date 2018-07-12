package com.tx.txRecord.service;

import java.util.List;
import com.tx.txRecord.model.TxRecord;

/**
 * @author	wzw
 * @time	2018-07-12 11:44:17
 */
public interface TxRecordService {
	
	public List<TxRecord> getTxRecordList(TxRecord txRecord);

	public TxRecord getTxRecordById(long id);

    public long insertTxRecord(TxRecord txRecord);

    public int updateTxRecordById(TxRecord txRecord);
    
    public int deleteTxRecordById(long id);
    
    public TxRecord getTxRecordMoney(TxRecord txRecord);
}
