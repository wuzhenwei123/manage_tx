package com.tx.txArea.dao;

import java.util.List;

import com.tx.txArea.model.TxArea;
/**
 * @author	wzw
 * @time	2018-02-01 09:59:59
 */
public interface TxAreaDAO{
	
	public List<TxArea> getTxAreaList(TxArea txArea);

	public TxArea getTxAreaById(int id);

    public int insertTxArea(TxArea txArea);

    public int updateTxAreaById(TxArea txArea);
    
    public int deleteTxAreaById(int id);
}
