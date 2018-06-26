package com.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tx.txBank.model.TxBank;
import com.tx.txBank.service.TxBankService;

public class GetBankToMemcache implements ApplicationListener<ContextRefreshedEvent> {

	protected static Logger logger = Logger.getLogger(GetBankToMemcache.class);
	
	@Autowired
	private TxBankService txBankService = null;
	
	@Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
        	init();
        }
    }
	
	public void init(){
		try{
			TxBank txBank = new TxBank();
			List<TxBank> list = txBankService.getTxBankList(txBank);
			Map<String,String> map = null;
			if(list!=null&&list.size()>0){
				map = new HashMap<String,String>();
				for(TxBank bank:list){
					map.put(bank.getVerifyCode(), bank.getBankNumber());
				}
			}
			MemcacheUtil.set(SessionName.LIST_BANK, 0, map);
		}catch(Exception e){
			logger.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
