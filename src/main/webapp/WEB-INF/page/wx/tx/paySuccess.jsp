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
<%@ include file="/WEB-INF/page/common/share2.jsp"%>	
<div class="top" style="background: #3f9bfe">
    支付结果
</div>
<div class="p_top">
    <h3>支付成功！</h3>
    <p>您可以在交易记录中查看详细情况，如有其它问题，请咨询客服电话：010-********。</p>
</div>
<div class="wrap">
    <div class="details_list">
        <ul>
            <li class="clear">
                <label>订单类型</label>
                <em>委托售卡</em>
            </li>
            <li class="clear">
                <label>订单号</label>
                <em>${txSellingOrder.code}</em>
            </li>
            <li class="clear">
                <label>支付流水号</label>
                <em>${txSellingOrder.queryId}</em>
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
                <em><fmt:formatDate value="${txSellingOrder.createTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>委托售卡周期</label>
                <em><c:if test="${backCard==0}">1个月</c:if><c:if test="${backCard==1}">不定期</c:if></em>
            </li>
            <li class="clear">
                <label>售卡结算日期</label>
                <em><fmt:formatDate value="${txSellingOrder.endTime}" pattern="yyyy-MM-dd"/></em>
            </li>
        </ul>
    </div>
</div>
<div class="btn_box btn_result">
<!--     <a href="javascript:;">订单详情</a> -->
    <a href="javascript:closeWin();">返回</a>
</div>
<script type="text/javascript">
function closeWin(){
	WeixinJSBridge.call('closeWindow');
}
</script>
</body>
</html>