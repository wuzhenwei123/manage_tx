package com.tx.txRate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txRate.dao.TxRateDAO;
import com.tx.txRate.model.TxRate;

/**
 * @author	wzw
 * @time	2018-02-24 09:02:44
 */
@Service
@Transactional
public class TxRateServiceImpl implements TxRateService{
	@Resource
    private TxRateDAO txRateDAO;
    
    public List<TxRate> getTxRateList(TxRate txRate) {
        return txRateDAO.getTxRateList(txRate);
    }

    public TxRate getTxRateById(int id) { 
        return txRateDAO.getTxRateById(id);
    }

    public int insertTxRate(TxRate txRate){
        return txRateDAO.insertTxRate(txRate);
    }

    public int updateTxRateById(TxRate txRate){
        return txRateDAO.updateTxRateById(txRate);
    }
    
    public int deleteTxRateById(int id){
        return txRateDAO.deleteTxRateById(id);
    }
}
