package com.tx.txPayOrder.dao;

import java.util.List;

import com.tx.txPayOrder.model.TxPayOrder;
/**
 * @author	wzw
 * @time	2018-06-07 14:03:06
 */
public interface TxPayOrderDAO{
	
	public List<TxPayOrder> getTxPayOrderList(TxPayOrder txPayOrder);

	public TxPayOrder getTxPayOrderById(long id);
	
	public TxPayOrder getTxPayOrderByOrderNumber(String orderNumber);

    public long insertTxPayOrder(TxPayOrder txPayOrder);

    public int updateTxPayOrderById(TxPayOrder txPayOrder);
    
    public int deleteTxPayOrderById(long id);
}
