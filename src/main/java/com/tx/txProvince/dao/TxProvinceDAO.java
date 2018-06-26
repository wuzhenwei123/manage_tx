package com.tx.txProvince.dao;

import java.util.List;

import com.tx.txProvince.model.TxProvince;
/**
 * @author	wzw
 * @time	2018-02-01 10:00:12
 */
public interface TxProvinceDAO{
	
	public List<TxProvince> getTxProvinceList(TxProvince txProvince);

	public TxProvince getTxProvinceById(int id);

    public int insertTxProvince(TxProvince txProvince);

    public int updateTxProvinceById(TxProvince txProvince);
    
    public int deleteTxProvinceById(int id);
}
