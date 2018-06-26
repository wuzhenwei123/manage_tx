package com.tx.txProvince.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txProvince.dao.TxProvinceDAO;
import com.tx.txProvince.model.TxProvince;

/**
 * @author	wzw
 * @time	2018-02-01 10:00:12
 */
@Service
@Transactional
public class TxProvinceServiceImpl implements TxProvinceService{
	@Resource
    private TxProvinceDAO txProvinceDAO;
    
    public List<TxProvince> getTxProvinceList(TxProvince txProvince) {
        return txProvinceDAO.getTxProvinceList(txProvince);
    }

    public TxProvince getTxProvinceById(int id) { 
        return txProvinceDAO.getTxProvinceById(id);
    }

    public int insertTxProvince(TxProvince txProvince){
        return txProvinceDAO.insertTxProvince(txProvince);
    }

    public int updateTxProvinceById(TxProvince txProvince){
        return txProvinceDAO.updateTxProvinceById(txProvince);
    }
    
    public int deleteTxProvinceById(int id){
        return txProvinceDAO.deleteTxProvinceById(id);
    }
}
