package com.tx.txPaynumberMsg.service;

import java.util.List;
import java.util.Map;

import com.tx.txPaynumberMsg.model.TxPaynumberMsg;

/**
 * @author	wzw
 * @time	2018-06-07 13:36:06
 */
public interface TxPaynumberMsgService {
	
	public List<TxPaynumberMsg> getTxPaynumberMsgList(TxPaynumberMsg txPaynumberMsg);

	public TxPaynumberMsg getTxPaynumberMsgById(long id);

    public long insertTxPaynumberMsg(TxPaynumberMsg txPaynumberMsg);

    public int updateTxPaynumberMsgById(TxPaynumberMsg txPaynumberMsg);
    
    public int deleteTxPaynumberMsgById(long id);
    
    public Map<String,String> getOrderNo();
}
