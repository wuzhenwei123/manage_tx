package com.tx.txSellingOrder.service;

import java.util.List;

import com.tx.txSellingOrder.model.TxSellingOrder;

/**
 * @author	wzw
 * @time	2018-04-30 22:59:36
 */
public interface TxSellingOrderService {
	
	public List<TxSellingOrder> getTxSellingOrderList(TxSellingOrder txSellingOrder);
	
	public List<TxSellingOrder> getTxSellingOrderListByPage(TxSellingOrder txSellingOrder);
	
	public List<TxSellingOrder> getTxSellingOrderListBySY(TxSellingOrder txSellingOrder);
	public List<TxSellingOrder> getTxSellingOrderListBySY1(TxSellingOrder txSellingOrder);
	
	public List<TxSellingOrder> getTxSellingOrderListMsg(TxSellingOrder txSellingOrder);
	
	public List<TxSellingOrder> orderAnalysis(TxSellingOrder txSellingOrder);
	
	public List<TxSellingOrder> orderAnalysisState(TxSellingOrder txSellingOrder);

	public TxSellingOrder getTxSellingOrderById(long id);

    public long insertTxSellingOrder(TxSellingOrder txSellingOrder);

    public int updateTxSellingOrderById(TxSellingOrder txSellingOrder);
    
    public int deleteTxSellingOrderById(long id);
    
    public int deleteTxSellingOrderByCode(String code);
    
    public TxSellingOrder getTxSellingOrderByCode(String code);
    
    public TxSellingOrder getTxSellingOrderByRefundCode(String refundCode);
    
    public int getTxSellingOrderCount(TxSellingOrder txSellingOrder);
    
    public TxSellingOrder getTxSellingOrderMoney(TxSellingOrder txSellingOrder);
    
    public Long getMoneyByRate(TxSellingOrder txSellingOrder);
    
    public TxSellingOrder getSellingOrderByTwoPromoter(TxSellingOrder txSellingOrder);
}
