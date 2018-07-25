package com.tx.txDfOrder.dao;

import java.util.List;

import com.tx.txDfOrder.model.TxDfOrder;
/**
 * @author	wzw
 * @time	2018-07-25 10:50:55
 */
public interface TxDfOrderDAO{
	
	public List<TxDfOrder> getTxDfOrderList(TxDfOrder txDfOrder);
	public List<TxDfOrder> getTxDfOrderListByDF(TxDfOrder txDfOrder);

	public TxDfOrder getTxDfOrderById(long id);

    public long insertTxDfOrder(TxDfOrder txDfOrder);

    public int updateTxDfOrderById(TxDfOrder txDfOrder);
    
    public int deleteTxDfOrderById(long id);
}
