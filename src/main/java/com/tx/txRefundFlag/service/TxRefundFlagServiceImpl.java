package com.tx.txRefundFlag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txRefundFlag.dao.TxRefundFlagDAO;
import com.tx.txRefundFlag.model.TxRefundFlag;

/**
 * @author	wzw
 * @time	2018-05-07 11:17:54
 */
@Service
@Transactional
public class TxRefundFlagServiceImpl implements TxRefundFlagService{
	@Resource
    private TxRefundFlagDAO txRefundFlagDAO;
    
    public List<TxRefundFlag> getTxRefundFlagList(TxRefundFlag txRefundFlag) {
        return txRefundFlagDAO.getTxRefundFlagList(txRefundFlag);
    }

    public TxRefundFlag getTxRefundFlagById(int id) { 
        return txRefundFlagDAO.getTxRefundFlagById(id);
    }

    public int insertTxRefundFlag(TxRefundFlag txRefundFlag){
        return txRefundFlagDAO.insertTxRefundFlag(txRefundFlag);
    }

    public int updateTxRefundFlagById(TxRefundFlag txRefundFlag){
        return txRefundFlagDAO.updateTxRefundFlagById(txRefundFlag);
    }
    
    public int deleteTxRefundFlagById(int id){
        return txRefundFlagDAO.deleteTxRefundFlagById(id);
    }
}
