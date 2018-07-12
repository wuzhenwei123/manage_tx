package com.tx.txPayOrder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txPayOrder.dao.TxPayOrderDAO;
import com.tx.txPayOrder.model.TxPayOrder;

/**
 * @author	wzw
 * @time	2018-06-07 14:03:06
 */
@Service
@Transactional
public class TxPayOrderServiceImpl implements TxPayOrderService{
	@Resource
    private TxPayOrderDAO txPayOrderDAO;
    
    public List<TxPayOrder> getTxPayOrderList(TxPayOrder txPayOrder) {
        return txPayOrderDAO.getTxPayOrderList(txPayOrder);
    }

    public TxPayOrder getTxPayOrderByOrderNumber(String orderNumber){
    	return txPayOrderDAO.getTxPayOrderByOrderNumber(orderNumber);
    }
    
    public TxPayOrder getTxPayOrderById(long id) { 
        return txPayOrderDAO.getTxPayOrderById(id);
    }

    public long insertTxPayOrder(TxPayOrder txPayOrder){
        return txPayOrderDAO.insertTxPayOrder(txPayOrder);
    }

    public int updateTxPayOrderById(TxPayOrder txPayOrder){
        return txPayOrderDAO.updateTxPayOrderById(txPayOrder);
    }
    
    public int deleteTxPayOrderById(long id){
        return txPayOrderDAO.deleteTxPayOrderById(id);
    }
    
    public TxPayOrder getTxPayOrderSumMoney(TxPayOrder txPayOrder){
    	return txPayOrderDAO.getTxPayOrderSumMoney(txPayOrder);
    }
}
