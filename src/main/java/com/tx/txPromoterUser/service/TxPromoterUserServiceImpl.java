package com.tx.txPromoterUser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txPromoterUser.dao.TxPromoterUserDAO;
import com.tx.txPromoterUser.model.TxPromoterUser;

/**
 * @author	wzw
 * @time	2018-08-03 10:02:20
 */
@Service
@Transactional
public class TxPromoterUserServiceImpl implements TxPromoterUserService{
	@Resource
    private TxPromoterUserDAO txPromoterUserDAO;
    
    public List<TxPromoterUser> getTxPromoterUserList(TxPromoterUser txPromoterUser) {
        return txPromoterUserDAO.getTxPromoterUserList(txPromoterUser);
    }

    public TxPromoterUser getTxPromoterUserById(int id) { 
        return txPromoterUserDAO.getTxPromoterUserById(id);
    }

    public int insertTxPromoterUser(TxPromoterUser txPromoterUser){
        return txPromoterUserDAO.insertTxPromoterUser(txPromoterUser);
    }
    
    public TxPromoterUser getTxPromoterUserByPromoterToken(String promoterToken){
    	  return txPromoterUserDAO.getTxPromoterUserByPromoterToken(promoterToken);
    }

    public int updateTxPromoterUserById(TxPromoterUser txPromoterUser){
        return txPromoterUserDAO.updateTxPromoterUserById(txPromoterUser);
    }
    
    public int deleteTxPromoterUserById(int id){
        return txPromoterUserDAO.deleteTxPromoterUserById(id);
    }
}
