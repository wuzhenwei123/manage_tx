package com.tx.txBanner.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import com.tx.txBanner.dao.TxBannerDAO;
import com.tx.txBanner.model.TxBanner;

/**
 * @author	wzw
 * @time	2018-06-08 15:45:42
 */
@Service
@Transactional
public class TxBannerServiceImpl implements TxBannerService{
	@Resource
    private TxBannerDAO txBannerDAO;
    
    public List<TxBanner> getTxBannerList(TxBanner txBanner) {
        return txBannerDAO.getTxBannerList(txBanner);
    }

    public TxBanner getTxBannerById(int id) { 
        return txBannerDAO.getTxBannerById(id);
    }

    public int insertTxBanner(TxBanner txBanner){
        return txBannerDAO.insertTxBanner(txBanner);
    }

    public int updateTxBannerById(TxBanner txBanner){
        return txBannerDAO.updateTxBannerById(txBanner);
    }
    
    public int deleteTxBannerById(int id){
        return txBannerDAO.deleteTxBannerById(id);
    }
}
