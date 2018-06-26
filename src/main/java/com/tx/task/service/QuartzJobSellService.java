/**
 * @Description: 
 *
 * @Title: QuartzJob.java
 * @Package com.joyce.quartz
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:37:11
 * @version V2.0
 */
package com.tx.task.service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.base.utils.ConfigConstants;
import com.tx.txSellingOrder.model.TxSellingOrder;
import com.tx.txSellingOrder.service.TxSellingOrderService;
import com.unionpay.acp.demo.DemoBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * @Description: 任务执行类
 *
 * @ClassName: QuartzJob
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:37:11
 * @version V2.0
 */
@Service
public class QuartzJobSellService implements Job {
	
	private static Logger logger = Logger.getLogger(QuartzJobSellService.class);
	
	@Autowired
	private TxSellingOrderService txSellingOrderService = null;
	
	private String orderNo;
	private String txnTime;
	private String backCard;

	public QuartzJobSellService(){
	}
	
	public QuartzJobSellService(String orderNo,String txnTime,String backCard){
		this.orderNo = orderNo;
		this.txnTime = txnTime;
		this.backCard = backCard;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		System.out.println("----->"+arg0.getJobDetail().getName());
		orderNo = arg0.getJobDetail().getName().split("_")[0];
		txnTime = arg0.getJobDetail().getName().split("_")[1];
		backCard = arg0.getJobDetail().getName().split("_")[2];
		QuartzManager.removeJob(arg0.getJobDetail().getName()); 
		try {
			Map<String, String> data = new HashMap<String, String>();
			
			/***商户接入参数***/
			data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
			data.put("version", DemoBase.version);                   //版本号
			data.put("encoding", DemoBase.encoding);         
			data.put("merId", ConfigConstants.MER_ID);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			if("0".equals(backCard)){
				data.put("merId", ConfigConstants.PK_MER_ID); 
			}
			
			/***要调通交易以下字段必须修改***/
			data.put("orderId", orderNo);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
			data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

			/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
			
			Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			System.out.println("交易状态查询");
			System.out.println("http:// 114.113.238.50:54041/XW/Query");
			System.out.println("发送报文");
			System.out.println("--------交易查询报文------------>"+reqData);
			String url = SDKConfig.getConfig().getSingleQueryUrl();						          //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
			Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			System.out.println("--------交易接收结果------------>"+rspData);
			System.out.println(URLDecoder.decode(rspData.get("respMsg"),"UTF-8"));
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				String queryId =rspData.get("queryId");
				if("00".equals(rspData.get("XF_RespCode"))){
					TxSellingOrder txSellingOrder = txSellingOrderService.getTxSellingOrderByCode(orderNo);
					if(txSellingOrder!=null&&txSellingOrder.getId()>0&&txSellingOrder.getMoney().intValue()==Integer.valueOf(rspData.get("XF_OrderAmt")).intValue()){
						txSellingOrder.setState(1);
    					txSellingOrder.setQueryId(queryId);
    					txSellingOrderService.updateTxSellingOrderById(txSellingOrder);
					}
				}
			}else{
				//未返回正确的http状态
				logger.info("未获取到返回报文或返回http状态码非200");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	
}
