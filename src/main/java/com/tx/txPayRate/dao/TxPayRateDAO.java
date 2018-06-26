package com.tx.txPayRate.dao;

import java.util.List;

import com.tx.txPayRate.model.TxPayRate;
/**
 * @author	wzw
 * @time	2018-05-02 13:35:17
 */
public interface TxPayRateDAO{
	
	public List<TxPayRate> getTxPayRateList(TxPayRate txPayRate);

	public TxPayRate getTxPayRateById(int id);

    public int insertTxPayRate(TxPayRate txPayRate);

    public int updateTxPayRateById(TxPayRate txPayRate);
    
    public int deleteTxPayRateById(int id);
}
