package com.tx.txRefundOrder.service;

import java.util.List;
import com.tx.txRefundOrder.model.TxRefundOrder;

/**
 * @author	wzw
 * @time	2018-05-07 09:08:14
 */
public interface TxRefundOrderService {
	
	public List<TxRefundOrder> getTxRefundOrderList(TxRefundOrder txRefundOrder);

	public TxRefundOrder getTxRefundOrderById(long id);

    public long insertTxRefundOrder(TxRefundOrder txRefundOrder);

    public int updateTxRefundOrderById(TxRefundOrder txRefundOrder);
    
    public int deleteTxRefundOrderById(long id);
}
