<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="false">
	<meta content="telephone=no" name="format-detection">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="msapplication-tap-highlight" content="no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<link rel="stylesheet" href="${ctx}/css/index/fontawesome-webfont.css" />
	<link rel="stylesheet" href="${ctx}/css/index/style.css" />
	<script src="${ctx}/js/jquery.1.9.0.min.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="suc-box box">
		<div class="suc-top">
			<i class="fa fa-check-circle"></i>
			付款成功！
		</div>
		
		<div class="suc-info suc-chongzhi">
			<h3 class="btm-border">
				电费充值
			</h3>
			<p class="pline">
				<label>缴费号</label>
				<span>${customerNumber}</span>
			</p>
			<p class="pline">
				<label>购电金额</label>
				<span>${money}元</span>
			</p>
			<p class="pline">
				<label>抵用券金额</label>
				<span><fmt:formatNumber value="${money-realmoney}" pattern="0.00"/>元</span>
			</p>
			<p class="pline">
				<label>实际支付金额</label>
				<span>${realmoney}元</span>
			</p>
			<p class="pline">
				<label>支付流水号</label>
				<span style="font-size: 14px;">${transaction_id}</span>
			</p>
			<div class="clear"></div>
			<p class="pline">
				<label>交易状态</label>
				<span>支付成功</span>
			</p>
			<p class="pline">
				<label>销账机构</label>
				<span>${chargeUnit}</span>
			</p>
			<p>
				实际到账时间以销帐机构处理为准，充值金额可能分多笔入表。客服电话：010-96199
			</p>

		</div>
	</div>
</body>
</html>