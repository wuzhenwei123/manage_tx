package com.tx.txBusinessType.dao;

import java.util.List;

import com.tx.txBusinessType.model.TxBusinessType;
/**
 * @author	wzw
 * @time	2018-06-02 11:36:08
 */
public interface TxBusinessTypeDAO{
	
	public List<TxBusinessType> getTxBusinessTypeList(TxBusinessType txBusinessType);

	public TxBusinessType getTxBusinessTypeById(int id);
	
	public TxBusinessType getTxBusinessType(TxBusinessType txBusinessType);

    public int insertTxBusinessType(TxBusinessType txBusinessType);

    public int updateTxBusinessTypeById(TxBusinessType txBusinessType);
    
    public int deleteTxBusinessTypeById(int id);
    
    public int getTxBusinessTypeCount(TxBusinessType txBusinessType);
}
