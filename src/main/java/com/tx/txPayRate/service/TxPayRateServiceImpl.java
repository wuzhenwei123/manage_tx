package com.tx.txPayRate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txPayRate.dao.TxPayRateDAO;
import com.tx.txPayRate.model.TxPayRate;

/**
 * @author	wzw
 * @time	2018-05-02 13:35:17
 */
@Service
@Transactional
public class TxPayRateServiceImpl implements TxPayRateService{
	@Resource
    private TxPayRateDAO txPayRateDAO;
    
    public List<TxPayRate> getTxPayRateList(TxPayRate txPayRate) {
        return txPayRateDAO.getTxPayRateList(txPayRate);
    }

    public TxPayRate getTxPayRateById(int id) { 
        return txPayRateDAO.getTxPayRateById(id);
    }

    public int insertTxPayRate(TxPayRate txPayRate){
        return txPayRateDAO.insertTxPayRate(txPayRate);
    }

    public int updateTxPayRateById(TxPayRate txPayRate){
        return txPayRateDAO.updateTxPayRateById(txPayRate);
    }
    
    public int deleteTxPayRateById(int id){
        return txPayRateDAO.deleteTxPayRateById(id);
    }
}
