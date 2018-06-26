package com.tx.txArea.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txArea.dao.TxAreaDAO;
import com.tx.txArea.model.TxArea;

/**
 * @author	wzw
 * @time	2018-02-01 09:59:59
 */
@Service
@Transactional
public class TxAreaServiceImpl implements TxAreaService{
	@Resource
    private TxAreaDAO txAreaDAO;
    
    public List<TxArea> getTxAreaList(TxArea txArea) {
        return txAreaDAO.getTxAreaList(txArea);
    }

    public TxArea getTxAreaById(int id) { 
        return txAreaDAO.getTxAreaById(id);
    }

    public int insertTxArea(TxArea txArea){
        return txAreaDAO.insertTxArea(txArea);
    }

    public int updateTxAreaById(TxArea txArea){
        return txAreaDAO.updateTxAreaById(txArea);
    }
    
    public int deleteTxAreaById(int id){
        return txAreaDAO.deleteTxAreaById(id);
    }
}
