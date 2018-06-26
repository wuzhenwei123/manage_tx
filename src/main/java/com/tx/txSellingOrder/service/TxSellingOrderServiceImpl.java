package com.tx.txSellingOrder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txSellingOrder.dao.TxSellingOrderDAO;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txWxOrder.model.TxWxOrder;

/**
 * @author	wzw
 * @time	2018-04-30 22:59:36
 */
@Service
@Transactional
public class TxSellingOrderServiceImpl implements TxSellingOrderService{
	@Resource
    private TxSellingOrderDAO txSellingOrderDAO;
    
    public List<TxSellingOrder> getTxSellingOrderList(TxSellingOrder txSellingOrder) {
        return txSellingOrderDAO.getTxSellingOrderList(txSellingOrder);
    }
    
    public List<TxSellingOrder> orderAnalysis(TxSellingOrder txSellingOrder){
    	 return txSellingOrderDAO.orderAnalysis(txSellingOrder);
    }
	
	public List<TxSellingOrder> orderAnalysisState(TxSellingOrder txSellingOrder){
		return txSellingOrderDAO.orderAnalysisState(txSellingOrder);
	}
    
    public List<TxSellingOrder> getTxSellingOrderListByPage(TxSellingOrder txSellingOrder) {
    	return txSellingOrderDAO.getTxSellingOrderListByPage(txSellingOrder);
    }
    
    public List<TxSellingOrder> getTxSellingOrderListBySY(TxSellingOrder txSellingOrder) {
    	return txSellingOrderDAO.getTxSellingOrderListBySY(txSellingOrder);
    }
    
    public TxSellingOrder getTxSellingOrderByRefundCode(String refundCode){
    	return txSellingOrderDAO.getTxSellingOrderByRefundCode(refundCode);
    }

    public TxSellingOrder getTxSellingOrderById(long id) { 
        return txSellingOrderDAO.getTxSellingOrderById(id);
    }
    
    public TxSellingOrder getTxSellingOrderByCode(String code){
    	 return txSellingOrderDAO.getTxSellingOrderByCode(code);
    }

    public long insertTxSellingOrder(TxSellingOrder txSellingOrder){
        return txSellingOrderDAO.insertTxSellingOrder(txSellingOrder);
    }

    public int updateTxSellingOrderById(TxSellingOrder txSellingOrder){
        return txSellingOrderDAO.updateTxSellingOrderById(txSellingOrder);
    }
    
    public int deleteTxSellingOrderById(long id){
        return txSellingOrderDAO.deleteTxSellingOrderById(id);
    }
    
    public int deleteTxSellingOrderByCode(String code){
    	return txSellingOrderDAO.deleteTxSellingOrderByCode(code);
    }
    
    public int getTxSellingOrderCount(TxSellingOrder txSellingOrder){
    	return txSellingOrderDAO.getTxSellingOrderCount(txSellingOrder);
    }
    
    public TxSellingOrder getTxSellingOrderMoney(TxSellingOrder txSellingOrder){
    	return txSellingOrderDAO.getTxSellingOrderMoney(txSellingOrder);
    }
    
    public Long getMoneyByRate(TxSellingOrder txSellingOrder){
    	return txSellingOrderDAO.getMoneyByRate(txSellingOrder);
    }
    
    public TxSellingOrder getSellingOrderByTwoPromoter(TxSellingOrder txSellingOrder){
    	return txSellingOrderDAO.getSellingOrderByTwoPromoter(txSellingOrder);
    }
}
