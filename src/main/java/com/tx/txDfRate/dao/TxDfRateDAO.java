package com.tx.txDfRate.dao;

import java.util.List;

import com.tx.txDfRate.model.TxDfRate;
/**
 * @author	wzw
 * @time	2018-06-10 18:38:14
 */
public interface TxDfRateDAO{
	
	public List<TxDfRate> getTxDfRateList(TxDfRate txDfRate);

	public TxDfRate getTxDfRateById(int id);

    public int insertTxDfRate(TxDfRate txDfRate);

    public int updateTxDfRateById(TxDfRate txDfRate);
    
    public int deleteTxDfRateById(int id);
}
