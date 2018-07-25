package com.tx.txDfOrder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txDfOrder.dao.TxDfOrderDAO;
import com.tx.txDfOrder.model.TxDfOrder;

/**
 * @author	wzw
 * @time	2018-07-25 10:50:55
 */
@Service
@Transactional
public class TxDfOrderServiceImpl implements TxDfOrderService{
	@Resource
    private TxDfOrderDAO txDfOrderDAO;
    
    public List<TxDfOrder> getTxDfOrderList(TxDfOrder txDfOrder) {
        return txDfOrderDAO.getTxDfOrderList(txDfOrder);
    }
    public List<TxDfOrder> getTxDfOrderListByDF(TxDfOrder txDfOrder) {
    	return txDfOrderDAO.getTxDfOrderListByDF(txDfOrder);
    }

    public TxDfOrder getTxDfOrderById(long id) { 
        return txDfOrderDAO.getTxDfOrderById(id);
    }

    public long insertTxDfOrder(TxDfOrder txDfOrder){
        return txDfOrderDAO.insertTxDfOrder(txDfOrder);
    }

    public int updateTxDfOrderById(TxDfOrder txDfOrder){
        return txDfOrderDAO.updateTxDfOrderById(txDfOrder);
    }
    
    public int deleteTxDfOrderById(long id){
        return txDfOrderDAO.deleteTxDfOrderById(id);
    }
}
