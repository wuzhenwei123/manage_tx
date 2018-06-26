package com.tx.txWxUser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txWxUser.dao.TxWxUserDAO;
import com.tx.txWxUser.model.TxWxUser;

/**
 * @author	wzw
 * @time	2018-02-01 13:47:41
 */
@Service
@Transactional
public class TxWxUserServiceImpl implements TxWxUserService{
	@Resource
    private TxWxUserDAO txWxUserDAO;
    
    public List<TxWxUser> getTxWxUserList(TxWxUser txWxUser) {
        return txWxUserDAO.getTxWxUserList(txWxUser);
    }
    
    public List<TxWxUser> getTxWxUserListByPage(TxWxUser txWxUser) {
    	return txWxUserDAO.getTxWxUserListByPage(txWxUser);
    }
    
    public List<TxWxUser> userAnalysis(TxWxUser txWxUser){
    	return txWxUserDAO.userAnalysis(txWxUser);
    }
    public List<TxWxUser> userAnalysisState(TxWxUser txWxUser){
    	return txWxUserDAO.userAnalysisState(txWxUser);
    }
    
    public TxWxUser getTxWxUserByOpenId(String openId){
    	return txWxUserDAO.getTxWxUserByOpenId(openId);
    }

    public TxWxUser getTxWxUserById(int id) { 
        return txWxUserDAO.getTxWxUserById(id);
    }

    public int insertTxWxUser(TxWxUser txWxUser){
        return txWxUserDAO.insertTxWxUser(txWxUser);
    }

    public int updateTxWxUserById(TxWxUser txWxUser){
        return txWxUserDAO.updateTxWxUserById(txWxUser);
    }
    
    public int updateTxWxUserByOpenId(TxWxUser txWxUser){
    	return txWxUserDAO.updateTxWxUserByOpenId(txWxUser);
    }
    
    public int deleteTxWxUserById(int id){
        return txWxUserDAO.deleteTxWxUserById(id);
    }
    
    public int getTxWxUserListCount(TxWxUser txWxUser){
    	return txWxUserDAO.getTxWxUserListCount(txWxUser);
    }
}
