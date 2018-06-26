<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>吉云水电气</title>
    <link rel="stylesheet" href="${ctx}/css/wx/tx/index.css">
</head>
<body>
<div class="top" style="background: #3f9bfe">
    交易详情
</div>
<div class="wrap">
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
                <label>支付金额（元）</label>
                <em>${money}</em>
            </li>
            <li class="clear">
                <label>购卡面值（元）</label>
                <em>${money}</em>
            </li>
            <li class="clear">
                <label>支付日期</label>
                <em><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>委托售卡周期</label>
                <em><c:if test="${order.backCard==0}">1个月</c:if><c:if test="${order.backCard==1}">不定期</c:if></em>
            </li>
            <li class="clear">
                <label>售卡结算日期</label>
                <em><fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>状态</label>
                <em><c:if test="${order.refundState==0}">未结算</c:if><c:if test="${order.refundState==1}">已结算</c:if></em>
            </li>
        </ul>
    </div>
</div>
<div class="btn_box btns">
	<c:if test="${order.backCard==1}"><c:if test="${order.refundState==0}"><a href="javascript:tuika(${order.id},${order.backCard});">承担手续费并退卡</a></c:if></c:if>
	<c:if test="${order.backCard==0}"><c:if test="${order.refundState==0}"><a href="javascript:tuika(${order.id},${order.backCard});">放弃收益并提前退卡</a></c:if></c:if>
    <a href="${ctx}/unionpay/toMyOrder">返回上页</a>
    <a href="javascript:closeWin();">返回到首页</a>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script type="text/javascript">
function closeWin(){
	WeixinJSBridge.call('closeWindow');
}

var flag = true;
function tuika(id){
	if(flag){
		
	}
	var backCard = '${order.backCard}';
	var msg = '你确定要承担手续费并退卡？';
	if(backCard=="0"){
		msg = '你确定要放弃收益并提前退卡？';
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
</script>
</body>
</html>