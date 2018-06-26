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
</head>
<body>
	<%@ include file="/WEB-INF/page/common/share.jsp" %>
	<div class="wrapper">
		<div class="form-box box">
			<div class="error-info">
				<p>支付失败，以下是支付失败原因：</p>
				<p class="error-main">${msg}<c:if test="${not empty resultCode}">：${resultCode}</c:if> </p>
				<p>您可以拨打客服电话：010-96199，咨询具体情况。</p>
			</div>
		</div>

		<div class="btn-box2">
			<a href="javascript:closeWin()" class="btn btn-next btn-orange" style="background: #0c8ee6;">返回首页</a>
		</div>
	</div>
	
	<script type="text/javascript">
	function closeWin(){
		WeixinJSBridge.call('closeWindow');
	}
	</script>
</body>
</html>