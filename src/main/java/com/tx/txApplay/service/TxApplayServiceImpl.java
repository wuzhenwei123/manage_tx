package com.tx.txApplay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txApplay.dao.TxApplayDAO;
import com.tx.txApplay.model.TxApplay;

/**
 * @author	wzw
 * @time	2018-07-12 11:49:15
 */
@Service
@Transactional
public class TxApplayServiceImpl implements TxApplayService{
	@Resource
    private TxApplayDAO txApplayDAO;
    
    public List<TxApplay> getTxApplayList(TxApplay txApplay) {
        return txApplayDAO.getTxApplayList(txApplay);
    }

    public TxApplay getTxApplayById(long id) { 
        return txApplayDAO.getTxApplayById(id);
    }

    public long insertTxApplay(TxApplay txApplay){
        return txApplayDAO.insertTxApplay(txApplay);
    }

    public int updateTxApplayById(TxApplay txApplay){
        return txApplayDAO.updateTxApplayById(txApplay);
    }
    
    public int deleteTxApplayById(long id){
        return txApplayDAO.deleteTxApplayById(id);
    }
    
    public TxApplay getTxApplayMoney(TxApplay txApplay){
    	 return txApplayDAO.getTxApplayMoney(txApplay);
    }
}
