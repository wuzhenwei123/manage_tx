package com.tx.txWxOrder.service;

import java.util.List;
import java.util.Map;

import com.tx.txWxOrder.model.TxWxOrder;

/**
 * @author	wzw
 * @time	2018-02-07 12:45:27
 */
public interface TxWxOrderService {
	
	public List<TxWxOrder> getTxWxOrderList(TxWxOrder txWxOrder);
	public List<TxWxOrder> getMoneyByOnePromoter(TxWxOrder txWxOrder);
	public List<TxWxOrder> getMoneyByTwoPromoter(TxWxOrder txWxOrder);
	public List<TxWxOrder> orderAnalysis(TxWxOrder txWxOrder);
	public List<TxWxOrder> orderAnalysisState(TxWxOrder txWxOrder);

	public TxWxOrder getTxWxOrderById(int id);

    public int insertTxWxOrder(TxWxOrder txWxOrder);

    public int updateTxWxOrderById(TxWxOrder txWxOrder);
    public int getTxWxOrderCount(TxWxOrder txWxOrder);
    public Long getTxWxOrderMoney(TxWxOrder txWxOrder);
    public Long getMoneyByDev(TxWxOrder txWxOrder);
    public Long getTxWxOrderMoneyByTwoPromoter(TxWxOrder txWxOrder);
    public Long getMoneyByRate(TxWxOrder txWxOrder);
    
    public TxWxOrder getTxWxOrderMoneyRate(TxWxOrder txWxOrder);
    
    public int deleteTxWxOrderById(int id);
    
    public TxWxOrder getTxWxOrderByCode(String orderCode);
    
    public int deleteTxWxOrderByOrderCode(String orderCode);
    
    public List<TxWxOrder> getTxWxOrderListByPage(TxWxOrder txWxOrder);
    
    public Map<String,String> exportTxt(List<TxWxOrder> listOne,List<TxWxOrder> listTwo,int totalResults,String allMoney,Long dev_money);
}
