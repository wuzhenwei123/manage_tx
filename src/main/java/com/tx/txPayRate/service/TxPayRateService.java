package com.tx.txPayRate.service;

import java.util.List;
import com.tx.txPayRate.model.TxPayRate;

/**
 * @author	wzw
 * @time	2018-05-02 13:35:17
 */
public interface TxPayRateService {
	
	public List<TxPayRate> getTxPayRateList(TxPayRate txPayRate);

	public TxPayRate getTxPayRateById(int id);

    public int insertTxPayRate(TxPayRate txPayRate);

    public int updateTxPayRateById(TxPayRate txPayRate);
    
    public int deleteTxPayRateById(int id);
}
