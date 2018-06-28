package com.tx.txWxUser.service;

import java.util.List;

import com.tx.txWxUser.model.TxWxUser;

/**
 * @author	wzw
 * @time	2018-02-01 13:47:41
 */
public interface TxWxUserService {
	
	public List<TxWxUser> getTxWxUserList(TxWxUser txWxUser);
	public List<TxWxUser> getTxWxUserListByPage(TxWxUser txWxUser);

	public TxWxUser getTxWxUserById(int id);

    public int insertTxWxUser(TxWxUser txWxUser);
    
    public List<TxWxUser> userAnalysis(TxWxUser txWxUser);
    public List<TxWxUser> userAnalysisState(TxWxUser txWxUser);

    public int updateTxWxUserById(TxWxUser txWxUser);
    public int updateTxWxUserByOpenId(TxWxUser txWxUser);
    
    public int deleteTxWxUserById(int id);
    
    public int getTxWxUserListCount(TxWxUser txWxUser);
    
    public TxWxUser getTxWxUserByOpenId(String openId);
    
    public int unBindWx(String openId);
}
