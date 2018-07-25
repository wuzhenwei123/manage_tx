<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/wx/tx/index.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css?t=<%=Math.random()%>">
</head>
<body>
<%@ include file="/WEB-INF/page/common/share2.jsp"%>
<div class="wrap">
	<div class="vm order-detail">
            <div class="deal_box">
                <dl>
                    <dt style="margin-left: 20px;padding-top: 10px;padding-right: 20px;font-size: 0.915rem;line-height: 1.6;letter-spacing: 1px;">温馨告知：<br/>
						1、如您需要提前退款，请点击该笔交易的详情。<br/>
						2、预批电费的收益及提前退款可能产生的手续费请参阅合伙人协议有关条款。
					</dt>
                </dl>
            </div>
     </div>
    <div class="details_list">
        <ul>
            <li class="clear">
                <label>订单号</label>
                <em>${order.code}</em>
            </li>
            <li class="clear">
                <label>支付流水号</label>
                <em>${order.queryId}</em>
            </li>
            <li class="clear">
                <label>金额（元）</label>
                <em>${fee}</em>
            </li>
            <li class="clear">
                <label>支付日期</label>
                <em><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>委托售电周期</label>
                <em>${order.selTime}个月</em>
            </li>
            <li class="clear">
                <label>售电结算日期</label>
                <em><fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>状态</label>
                <em>${state}</em>
            </li>
        </ul>
    </div>
</div>
<div class="btn_box btns">
	<c:if test="${empty txDfOrder}">
		<c:if test="${order.refundState==0}"><a href="javascript:tuika(${order.id},1);">立即退款</a></c:if>
		<c:if test="${order.refundState==0}"><a href="javascript:tuika(${order.id},0);">申请退款</a></c:if>
	</c:if>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script type="text/javascript">
function closeWin(){
	WeixinJSBridge.call('closeWindow');
}

var flag = true;
function tuika(id,backCard){
	if(flag){
		flag = false;
		var msg = '你确定要立即退款？';
		if(backCard=="0"){
			msg = '你确定要申请退款？';
		}
		layer.msg(msg, {
			  time: 0 //不自动关闭
			  ,btn: ['确定', '取消']
			  ,yes: function(index){
				  layer.close(index);
				  var index = layer.load(1, {
	      			  shade: [0.6,'#666666'] //0.1透明度的白色背景
	      		  });
				  $.post("${ctx}/unionpay/refundPay", {
					  	id:id,
					  	backCard:backCard,
			    		_t : Math.random()
			    	}, function(data) {
			    		var result = eval("(" + data + ")");
			    		if (result.code == 1) {
			    			if(backCard=="0"){
			    				layer.msg("申请成功，等待退卡处理");
			    			}else{
			    				layer.msg("退卡成功");
			    			}
			    			setTimeout("window.location.href ='${ctx}/unionpay/toMyOrder'",2000);
			    		}else{
			    			layer.close(index);
			    			layer.msg(result.message);
			    		}
			    	});
			  }
			});
	}
}
</script>
</body>
</html>