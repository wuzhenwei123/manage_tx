package com.tx.txApplay.service;

import java.util.List;
import com.tx.txApplay.model.TxApplay;

/**
 * @author	wzw
 * @time	2018-07-12 11:49:15
 */
public interface TxApplayService {
	
	public List<TxApplay> getTxApplayList(TxApplay txApplay);

	public TxApplay getTxApplayById(long id);

    public long insertTxApplay(TxApplay txApplay);

    public int updateTxApplayById(TxApplay txApplay);
    
    public int deleteTxApplayById(long id);
    
    public TxApplay getTxApplayMoney(TxApplay txApplay);
}
