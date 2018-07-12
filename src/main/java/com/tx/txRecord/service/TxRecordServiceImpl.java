package com.tx.txRecord.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txRecord.dao.TxRecordDAO;
import com.tx.txRecord.model.TxRecord;

/**
 * @author	wzw
 * @time	2018-07-12 11:44:17
 */
@Service
@Transactional
public class TxRecordServiceImpl implements TxRecordService{
	@Resource
    private TxRecordDAO txRecordDAO;
    
    public List<TxRecord> getTxRecordList(TxRecord txRecord) {
        return txRecordDAO.getTxRecordList(txRecord);
    }

    public TxRecord getTxRecordById(long id) { 
        return txRecordDAO.getTxRecordById(id);
    }

    public long insertTxRecord(TxRecord txRecord){
        return txRecordDAO.insertTxRecord(txRecord);
    }

    public int updateTxRecordById(TxRecord txRecord){
        return txRecordDAO.updateTxRecordById(txRecord);
    }
    
    public int deleteTxRecordById(long id){
        return txRecordDAO.deleteTxRecordById(id);
    }
    
    public TxRecord getTxRecordMoney(TxRecord txRecord){
    	return txRecordDAO.getTxRecordMoney(txRecord);
    }
}
