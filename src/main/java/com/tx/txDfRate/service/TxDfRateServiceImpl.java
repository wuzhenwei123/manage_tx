package com.tx.txDfRate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txDfRate.dao.TxDfRateDAO;
import com.tx.txDfRate.model.TxDfRate;

/**
 * @author	wzw
 * @time	2018-06-10 18:38:15
 */
@Service
@Transactional
public class TxDfRateServiceImpl implements TxDfRateService{
	@Resource
    private TxDfRateDAO txDfRateDAO;
    
    public List<TxDfRate> getTxDfRateList(TxDfRate txDfRate) {
        return txDfRateDAO.getTxDfRateList(txDfRate);
    }

    public TxDfRate getTxDfRateById(int id) { 
        return txDfRateDAO.getTxDfRateById(id);
    }

    public int insertTxDfRate(TxDfRate txDfRate){
        return txDfRateDAO.insertTxDfRate(txDfRate);
    }

    public int updateTxDfRateById(TxDfRate txDfRate){
        return txDfRateDAO.updateTxDfRateById(txDfRate);
    }
    
    public int deleteTxDfRateById(int id){
        return txDfRateDAO.deleteTxDfRateById(id);
    }
}
