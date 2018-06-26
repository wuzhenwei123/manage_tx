package com.tx.txPaynumberMsg.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.tx.txPaynumberMsg.dao.TxPaynumberMsgDAO;
import com.tx.txPaynumberMsg.model.TxPaynumberMsg;

/**
 * @author	wzw
 * @time	2018-06-07 13:36:06
 */
@Service
@Transactional
public class TxPaynumberMsgServiceImpl implements TxPaynumberMsgService{
	@Resource
    private TxPaynumberMsgDAO txPaynumberMsgDAO;
    
    public List<TxPaynumberMsg> getTxPaynumberMsgList(TxPaynumberMsg txPaynumberMsg) {
        return txPaynumberMsgDAO.getTxPaynumberMsgList(txPaynumberMsg);
    }

    public TxPaynumberMsg getTxPaynumberMsgById(long id) { 
        return txPaynumberMsgDAO.getTxPaynumberMsgById(id);
    }

    public long insertTxPaynumberMsg(TxPaynumberMsg txPaynumberMsg){
        return txPaynumberMsgDAO.insertTxPaynumberMsg(txPaynumberMsg);
    }

    public int updateTxPaynumberMsgById(TxPaynumberMsg txPaynumberMsg){
        return txPaynumberMsgDAO.updateTxPaynumberMsgById(txPaynumberMsg);
    }
    
    public int deleteTxPaynumberMsgById(long id){
        return txPaynumberMsgDAO.deleteTxPaynumberMsgById(id);
    }
    
    public Map<String,String> getOrderNo(){
    	Map<String,String> map = new HashMap<String,String>();
    	SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
    	String sceneId = "";
    	for(int i=0;i<6;i++){
			sceneId += (new Random().nextInt(9)+1);
		}
    	String d = sf1.format(new Date());
		String orderNoT = "TK"+d+ sceneId;
		map.put("orderNoTime", d);
		map.put("orderNo", orderNoT);
		return map;
    }
}
