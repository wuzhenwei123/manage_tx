package com.tx.txBank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txBank.dao.TxBankDAO;
import com.tx.txBank.model.TxBank;

/**
 * @author	wzw
 * @time	2018-02-02 09:09:39
 */
@Service
@Transactional
public class TxBankServiceImpl implements TxBankService{
	@Resource
    private TxBankDAO txBankDAO;
    
    public List<TxBank> getTxBankList(TxBank txBank) {
        return txBankDAO.getTxBankList(txBank);
    }

    public TxBank getTxBankById(int id) { 
        return txBankDAO.getTxBankById(id);
    }
    
    public TxBank getTxBankByBankNumber(String bankNumber){
    	return txBankDAO.getTxBankByBankNumber(bankNumber);
    }

    public int insertTxBank(TxBank txBank){
        return txBankDAO.insertTxBank(txBank);
    }

    public int updateTxBankById(TxBank txBank){
        return txBankDAO.updateTxBankById(txBank);
    }
    
    public int deleteTxBankById(int id){
        return txBankDAO.deleteTxBankById(id);
    }
}
