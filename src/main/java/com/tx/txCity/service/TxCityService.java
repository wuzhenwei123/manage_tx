package com.tx.txCity.service;

import java.util.List;
import com.tx.txCity.model.TxCity;

/**
 * @author	wzw
 * @time	2018-02-01 09:59:34
 */
public interface TxCityService {
	
	public List<TxCity> getTxCityList(TxCity txCity);

	public TxCity getTxCityById(int id);

    public int insertTxCity(TxCity txCity);

    public int updateTxCityById(TxCity txCity);
    
    public int deleteTxCityById(int id);
}
