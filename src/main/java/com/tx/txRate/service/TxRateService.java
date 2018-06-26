package com.tx.txRate.service;

import java.util.List;
import com.tx.txRate.model.TxRate;

/**
 * @author	wzw
 * @time	2018-02-24 09:02:44
 */
public interface TxRateService {
	
	public List<TxRate> getTxRateList(TxRate txRate);

	public TxRate getTxRateById(int id);

    public int insertTxRate(TxRate txRate);

    public int updateTxRateById(TxRate txRate);
    
    public int deleteTxRateById(int id);
}
