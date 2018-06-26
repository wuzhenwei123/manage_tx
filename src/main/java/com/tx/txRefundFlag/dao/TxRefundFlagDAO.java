package com.tx.txRefundFlag.dao;

import java.util.List;

import com.tx.txRefundFlag.model.TxRefundFlag;
/**
 * @author	wzw
 * @time	2018-05-07 11:17:54
 */
public interface TxRefundFlagDAO{
	
	public List<TxRefundFlag> getTxRefundFlagList(TxRefundFlag txRefundFlag);

	public TxRefundFlag getTxRefundFlagById(int id);

    public int insertTxRefundFlag(TxRefundFlag txRefundFlag);

    public int updateTxRefundFlagById(TxRefundFlag txRefundFlag);
    
    public int deleteTxRefundFlagById(int id);
}
