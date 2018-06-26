package com.tx.txRefundOrder.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txRefundOrder.dao.TxRefundOrderDAO;
import com.tx.txRefundOrder.model.TxRefundOrder;

/**
 * @author	wzw
 * @time	2018-05-07 09:08:14
 */
@Service
@Transactional
public class TxRefundOrderServiceImpl implements TxRefundOrderService{
	@Resource
    private TxRefundOrderDAO txRefundOrderDAO;
    
    public List<TxRefundOrder> getTxRefundOrderList(TxRefundOrder txRefundOrder) {
        return txRefundOrderDAO.getTxRefundOrderList(txRefundOrder);
    }

    public TxRefundOrder getTxRefundOrderById(long id) { 
        return txRefundOrderDAO.getTxRefundOrderById(id);
    }

    public long insertTxRefundOrder(TxRefundOrder txRefundOrder){
        return txRefundOrderDAO.insertTxRefundOrder(txRefundOrder);
    }

    public int updateTxRefundOrderById(TxRefundOrder txRefundOrder){
        return txRefundOrderDAO.updateTxRefundOrderById(txRefundOrder);
    }
    
    public int deleteTxRefundOrderById(long id){
        return txRefundOrderDAO.deleteTxRefundOrderById(id);
    }
}
