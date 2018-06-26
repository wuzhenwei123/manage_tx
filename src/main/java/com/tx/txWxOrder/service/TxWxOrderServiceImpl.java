package com.tx.txWxOrder.service;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.base.utils.ConfigConstants;
import com.base.utils.MemcacheUtil;
import com.base.utils.SessionName;
import com.tx.txBank.dao.TxBankDAO;
import com.tx.txBank.model.TxBank;
import com.tx.txWxOrder.dao.TxWxOrderDAO;
import com.tx.txWxOrder.model.TxWxOrder;

/**
 * @author	wzw
 * @time	2018-02-07 12:45:27
 */
@Service
@Transactional
public class TxWxOrderServiceImpl implements TxWxOrderService{
	
	public static Integer textNo = 0;
	
	@Resource
    private TxWxOrderDAO txWxOrderDAO;
	@Resource
    private TxBankDAO txBankDAO;
    
    public List<TxWxOrder> getTxWxOrderList(TxWxOrder txWxOrder) {
        return txWxOrderDAO.getTxWxOrderList(txWxOrder);
    }
    public List<TxWxOrder> getMoneyByOnePromoter(TxWxOrder txWxOrder) {
    	return txWxOrderDAO.getMoneyByOnePromoter(txWxOrder);
    }
    public List<TxWxOrder> orderAnalysis(TxWxOrder txWxOrder) {
    	return txWxOrderDAO.orderAnalysis(txWxOrder);
    }
    public List<TxWxOrder> orderAnalysisState(TxWxOrder txWxOrder) {
    	return txWxOrderDAO.orderAnalysisState(txWxOrder);
    }
    public List<TxWxOrder> getMoneyByTwoPromoter(TxWxOrder txWxOrder) {
    	return txWxOrderDAO.getMoneyByTwoPromoter(txWxOrder);
    }
    
    public List<TxWxOrder> getTxWxOrderListByPage(TxWxOrder txWxOrder){
    	 return txWxOrderDAO.getTxWxOrderListByPage(txWxOrder);
    }

    public TxWxOrder getTxWxOrderById(int id) { 
        return txWxOrderDAO.getTxWxOrderById(id);
    }
    
    public TxWxOrder getTxWxOrderByCode(String orderCode){
    	return txWxOrderDAO.getTxWxOrderByCode(orderCode);
    }

    public int insertTxWxOrder(TxWxOrder txWxOrder){
        return txWxOrderDAO.insertTxWxOrder(txWxOrder);
    }

    public int updateTxWxOrderById(TxWxOrder txWxOrder){
        return txWxOrderDAO.updateTxWxOrderById(txWxOrder);
    }
    
    public int getTxWxOrderCount(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getTxWxOrderCount(txWxOrder);
    }
    public Long getTxWxOrderMoney(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getTxWxOrderMoney(txWxOrder);
    }
    public Long getMoneyByRate(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getMoneyByRate(txWxOrder);
    }
    public Long getMoneyByDev(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getMoneyByDev(txWxOrder);
    }
    public Long getTxWxOrderMoneyByTwoPromoter(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getTxWxOrderMoneyByTwoPromoter(txWxOrder);
    }
    
    public TxWxOrder getTxWxOrderMoneyRate(TxWxOrder txWxOrder){
    	return txWxOrderDAO.getTxWxOrderMoneyRate(txWxOrder);
    }
    
    public int deleteTxWxOrderById(int id){
        return txWxOrderDAO.deleteTxWxOrderById(id);
    }
    public int deleteTxWxOrderByOrderCode(String orderCode){
    	return txWxOrderDAO.deleteTxWxOrderByOrderCode(orderCode);
    }
    
    public Map<String,String> exportTxt(List<TxWxOrder> listOne,List<TxWxOrder> listTwo,int totalResults,String allMoney,Long dev_money){
    	SimpleDateFormat sf1 = new SimpleDateFormat("MMdd");
    	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    	Map<String,String> map = new HashMap<String,String>();
    	try{
    		
    		
    		String saveFilePath = ConfigConstants.UPLOAD_FILE_ROOT;
    		String fileToday = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
    		File file = new File(saveFilePath + File.separator + fileToday + File.separator);
    		if(!file.exists()){
    			file.mkdirs();
    		}
    		totalResults = 0;
    		
    		
    		int i = 1;
    		StringBuffer buffer = new StringBuffer();
    		buffer.append("总金额:"+allMoney+"\r\n");
    		buffer.append("---------------------------------------------------\r\n");
    		buffer.append("机构流水号|备付金账号|备付金户名|备付金账户类型|收款账号|收款户名|收款账号开户行名称|收款账号开户行号|汇路|本他行标志|交易金额|业务类型|用途|摘要|\r\n");
    		if(listOne!=null&&listOne.size()>0){
    			for(TxWxOrder o:listOne){
    				if(o.getPromoterId()!=null){
    					totalResults = totalResults + 1;
    					buffer.append(sf1.format(new Date())+"021"+getIndexNo(i)).append("|");
    					buffer.append("630012340").append("|");
    					buffer.append("北京恒信通电信服务有限公司客户备付金").append("|");
    					buffer.append("0").append("|");
    					buffer.append(o.getCardNumber()).append("|");
    					buffer.append(o.getRealName()).append("|");
    					buffer.append(o.getBankName()).append("|");
    					buffer.append(o.getBankNo()).append("|");
    					buffer.append("1").append("|");
    					buffer.append("1").append("|");
    					buffer.append(getMoney(o.getMoney())).append("|02|0|收单业务款|\r\n");
    					i = i + 1;
    				}
    			}
    		}
    		if(listTwo!=null&&listTwo.size()>0){
    			for(TxWxOrder o:listTwo){
    				if(o.getTwoPromoterId()!=null){
    					totalResults = totalResults + 1;
    					buffer.append(sf1.format(new Date())+"021"+getIndexNo(i)).append("|");
    					buffer.append("630012340").append("|");
    					buffer.append("北京恒信通电信服务有限公司客户备付金").append("|");
    					buffer.append("0").append("|");
    					buffer.append(o.getCardNumber()).append("|");
    					buffer.append(o.getRealName()).append("|");
    					buffer.append(o.getBankName()).append("|");
    					buffer.append(o.getBankNo()).append("|");
    					buffer.append("1").append("|");
    					buffer.append("1").append("|");
    					buffer.append(getMoney(o.getMoney())).append("|02|0|收单业务款|\r\n");
    					i = i + 1;
    				}
    			}
    		}
    		
//    		BigDecimal bg = new BigDecimal(dev_money);
//    		long cao = (bg.multiply(new BigDecimal(0.4))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//    		long deng = (bg.multiply(new BigDecimal(0.3))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//    		long wu = (bg.multiply(new BigDecimal(0.3))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//    		
    		buffer.append(sf1.format(new Date())+"021"+getIndexNo(i)).append("|");
			buffer.append("630012340").append("|");
			buffer.append("北京恒信通电信服务有限公司客户备付金").append("|");
			buffer.append("0").append("|");
			buffer.append("6217730711423325").append("|");
			buffer.append("曹伟").append("|");
			buffer.append("中信银行北京世纪城支行 ").append("|");
			buffer.append("302100011368").append("|");
			buffer.append("1").append("|");
			buffer.append("1").append("|");
			buffer.append(getMoney(dev_money)).append("|02|0|收单业务款|\r\n");
			i = i + 1;
//			
//			buffer.append(sf1.format(new Date())+"021"+getIndexNo(i)).append("|");
//			buffer.append("630012340").append("|");
//			buffer.append("北京恒信通电信服务有限公司客户备付金").append("|");
//			buffer.append("0").append("|");
//			buffer.append("").append("|");
//			buffer.append("邓健").append("|");
//			buffer.append("").append("|");
//			buffer.append("").append("|");
//			buffer.append("1").append("|");
//			buffer.append("1").append("|");
//			buffer.append(getMoney(deng)).append("|02|0|收单业务款|\r\n");
//			i = i + 1;
//			
//			buffer.append(sf1.format(new Date())+"021"+getIndexNo(i)).append("|");
//			buffer.append("630012340").append("|");
//			buffer.append("北京恒信通电信服务有限公司客户备付金").append("|");
//			buffer.append("0").append("|");
//			buffer.append("6214830104477962").append("|");
//			buffer.append("吴振伟").append("|");
//			buffer.append("北京分行清华园支行").append("|");
//			buffer.append("308100005256").append("|");
//			buffer.append("1").append("|");
//			buffer.append("1").append("|");
//			buffer.append(getMoney(wu)).append("|02|0|收单业务款|\r\n");
//			i = i + 1;
    		
			if(textNo.intValue()>999){
				textNo = 0;
			}else{
				textNo = textNo + 1;
			}
			String textNo_str = textNo+"";
			if(textNo_str.length()==1){
				textNo_str = "00" + textNo_str;
			}else if(textNo_str.length()==2){
				textNo_str = "0" + textNo_str;
			}
    		
			if(totalResults>0){
				totalResults = totalResults + 1;
			}
			
			StringBuffer buffer1 = new StringBuffer();
    		buffer1.append("商户号:10116\r\n");
    		buffer1.append("记录总数:"+totalResults+"\r\n");
			
    		File file1 = new File(saveFilePath + File.separator + fileToday + File.separator+"PAYREQ_10116_"+sf.format(new Date())+textNo_str+".txt");
			FileWriter fileWriter = new FileWriter(file1);
			fileWriter.write(buffer1.append(buffer.toString()).toString());
			fileWriter.flush();
			fileWriter.close();
			map.put("path", File.separator + fileToday + File.separator+"PAYREQ_10116_"+sf.format(new Date())+textNo_str+".txt");
			map.put("name", "PAYREQ_10116_"+sf.format(new Date())+textNo_str+".txt");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map;
    }
    
    public String getIndexNo(int i){
    	String i_str = String.valueOf(i);
    	if(i_str.length()==1){
    		return "00"+i;
    	}else if(i_str.length()==2){
    		return "0"+i;
    	}else{
    		return ""+i;
    	}
    }
    
    
    public String getMoney(Long money){
		String totalFeeStr1 = null;
		if(money!=null&&money>0){
			if(String.valueOf(money).length()>2){
				Long fen = money%100;
				if(fen>0&&fen<10){
					totalFeeStr1 = money/100 + ".0" + fen;
				}else if(fen>=10){
					totalFeeStr1 = money/100 + "." + fen;
				}else{
					totalFeeStr1 = money/100 + ".00";
				}
			}else if(String.valueOf(money).length()==1){
				totalFeeStr1 = "0.0"+money;
			}else{
				totalFeeStr1 = "0."+money;
			}
		}else{
			totalFeeStr1 = "0.00";
		}
		return totalFeeStr1;
	}
    
}
