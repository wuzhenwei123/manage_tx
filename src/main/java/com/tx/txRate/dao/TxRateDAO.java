package com.tx.txRate.dao;

import java.util.List;

import com.tx.txRate.model.TxRate;
/**
 * @author	wzw
 * @time	2018-02-24 09:02:43
 */
public interface TxRateDAO{
	
	public List<TxRate> getTxRateList(TxRate txRate);

	public TxRate getTxRateById(int id);

    public int insertTxRate(TxRate txRate);

    public int updateTxRateById(TxRate txRate);
    
    public int deleteTxRateById(int id);
}
