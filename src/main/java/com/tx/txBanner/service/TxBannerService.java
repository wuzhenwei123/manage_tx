package com.tx.txBanner.service;

import java.util.List;
import com.tx.txBanner.model.TxBanner;

/**
 * @author	wzw
 * @time	2018-06-08 15:45:42
 */
public interface TxBannerService {
	
	public List<TxBanner> getTxBannerList(TxBanner txBanner);

	public TxBanner getTxBannerById(int id);

    public int insertTxBanner(TxBanner txBanner);

    public int updateTxBannerById(TxBanner txBanner);
    
    public int deleteTxBannerById(int id);
}
