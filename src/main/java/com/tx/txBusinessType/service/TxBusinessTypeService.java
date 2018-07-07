package com.tx.txBusinessType.service;

import java.util.List;
import java.util.Map;

import com.tx.txBusinessType.model.TxBusinessType;

/**
 * @author	wzw
 * @time	2018-06-02 11:36:09
 */
public interface TxBusinessTypeService {
	
	public List<TxBusinessType> getTxBusinessTypeList(TxBusinessType txBusinessType);
	
	public List<TxBusinessType> getTxBusinessTypeListGroup(TxBusinessType txBusinessType);

	public TxBusinessType getTxBusinessTypeById(int id);

    public int insertTxBusinessType(TxBusinessType txBusinessType);

    public int updateTxBusinessTypeById(TxBusinessType txBusinessType);
    
    public int deleteTxBusinessTypeById(int id);
    
    public TxBusinessType getTxBusinessType(TxBusinessType txBusinessType);
    
    public int getTxBusinessTypeCount(TxBusinessType txBusinessType);
    
    public String getTraceNo();
    
    /**
     * 
     * @param cityCode      城市代码
     * @param serviceType   业务代码
     * @param payNo         用户缴费编号
     * @param preTotalFee   预存金额,单位：分真针对可以预存的业务；其他不可预存业务填“0”。
     * @param plotNo        小区号,格式 G0001 IsNeedPoltNo 决定是否需要小区号默认 0 不需要 1 需要
     * @param accountPeriod 上送账期,格式 YYYYMM （ 根据 开 通 查 询 中IsNeedDate 决定是否需要输入，IsNeedDat 为 0 时补足空格IsNeedDat 为 1 时左补 足 空 格 即“ YYYYMM”IsNeedDat 为 2 时右补 足 0 即“YYYYMM00”）
     * @param bankCardNo    付款卡号
     * @return
     */
    public String queryBusinessMsg(String cityCode,String serviceType,String payNo,Integer preTotalFee,Integer plotNo,String accountPeriod,String bankCardNo);
    
    /**
	 * 商户信息查询
	 * @time : 2015年8月25日 下午4:13:45
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description: TODO
	 */
	public Map<String, Object> queryAccountUser(String shopCode,String userCode,Integer TotalFee,String ip);
	
	/**
	 * 解析抄表充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsgCB(String customerNumber,String resultInfo,Integer TotalFee);
	
	/**
	 * 解析智能充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsg(String customerNumber,String resultInfo,Integer TotalFee);
	
	/**
	 * 支付前查询
	 * @time : 2015年8月25日 下午4:13:45
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description: TODO
	 */
	public Map<String, Object> queryPayMessage(String shopCode,String userCode,Integer TotalFee,String ip);
	
	
	/**
	 * 解析智能充电账户信息
	 * @param customerNumber
	 * @param openId
	 * @param shopCode
	 * @param resultInfo
	 * @param TotalFee
	 * @return
	 */
	public Map<String, Object> getCustomerMsg1(String customerNumber,String resultInfo,Integer TotalFee);
}
