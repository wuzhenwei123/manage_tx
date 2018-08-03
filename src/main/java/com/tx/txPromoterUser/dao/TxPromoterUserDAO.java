package com.tx.txPromoterUser.dao;

import java.util.List;

import com.tx.txPromoterUser.model.TxPromoterUser;
/**
 * @author	wzw
 * @time	2018-08-03 10:02:20
 */
public interface TxPromoterUserDAO{
	
	public List<TxPromoterUser> getTxPromoterUserList(TxPromoterUser txPromoterUser);

	public TxPromoterUser getTxPromoterUserById(int id);
	
	public TxPromoterUser getTxPromoterUserByPromoterToken(String promoterToken);

    public int insertTxPromoterUser(TxPromoterUser txPromoterUser);

    public int updateTxPromoterUserById(TxPromoterUser txPromoterUser);
    
    public int deleteTxPromoterUserById(int id);
}
