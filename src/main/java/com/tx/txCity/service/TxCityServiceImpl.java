package com.tx.txCity.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txCity.dao.TxCityDAO;
import com.tx.txCity.model.TxCity;

/**
 * @author	wzw
 * @time	2018-02-01 09:59:34
 */
@Service
@Transactional
public class TxCityServiceImpl implements TxCityService{
	@Resource
    private TxCityDAO txCityDAO;
    
    public List<TxCity> getTxCityList(TxCity txCity) {
        return txCityDAO.getTxCityList(txCity);
    }

    public TxCity getTxCityById(int id) { 
        return txCityDAO.getTxCityById(id);
    }

    public int insertTxCity(TxCity txCity){
        return txCityDAO.insertTxCity(txCity);
    }

    public int updateTxCityById(TxCity txCity){
        return txCityDAO.updateTxCityById(txCity);
    }
    
    public int deleteTxCityById(int id){
        return txCityDAO.deleteTxCityById(id);
    }
}
