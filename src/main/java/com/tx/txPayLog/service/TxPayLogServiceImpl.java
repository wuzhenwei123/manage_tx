package com.tx.txPayLog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txPayLog.dao.TxPayLogDAO;
import com.tx.txPayLog.model.TxPayLog;

/**
 * @author	wzw
 * @time	2018-06-10 17:57:45
 */
@Service
@Transactional
public class TxPayLogServiceImpl implements TxPayLogService{
	@Resource
    private TxPayLogDAO txPayLogDAO;
    
    public List<TxPayLog> getTxPayLogList(TxPayLog txPayLog) {
        return txPayLogDAO.getTxPayLogList(txPayLog);
    }

    public TxPayLog getTxPayLogById(long id) { 
        return txPayLogDAO.getTxPayLogById(id);
    }

    public long insertTxPayLog(TxPayLog txPayLog){
        return txPayLogDAO.insertTxPayLog(txPayLog);
    }

    public int updateTxPayLogById(TxPayLog txPayLog){
        return txPayLogDAO.updateTxPayLogById(txPayLog);
    }
    
    public int deleteTxPayLogById(long id){
        return txPayLogDAO.deleteTxPayLogById(id);
    }
}
