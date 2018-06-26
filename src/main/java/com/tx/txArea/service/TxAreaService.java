package com.tx.txArea.service;

import java.util.List;
import com.tx.txArea.model.TxArea;

/**
 * @author	wzw
 * @time	2018-02-01 09:59:59
 */
public interface TxAreaService {
	
	public List<TxArea> getTxAreaList(TxArea txArea);

	public TxArea getTxAreaById(int id);

    public int insertTxArea(TxArea txArea);

    public int updateTxAreaById(TxArea txArea);
    
    public int deleteTxAreaById(int id);
}
