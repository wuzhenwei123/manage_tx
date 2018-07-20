<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html>
	<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="#e8447e">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" CONTENT="-1">           
    <meta http-equiv="Cache-Control" CONTENT="no-cache">           
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta name="description" content="">
    <meta name="Keywords" content="">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/comm.css">
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/zepto.min.js" ></script>
</head>
<body class="body">
<%@ include file="/WEB-INF/page/common/share2.jsp"%>
<div class="box">
    <div class="topBox" style="background: #0c8ee6;">选择缴费结构</div>
     <c:forEach items="${listType}" var="buss">
     	<div class="ch_ui_box marginTop16">
	        <a href="${ctx}/other/toFirstPay?id=${buss.id}" class="ui_cell ui_cells">
	            <div class="ui_center ui_cell_flex">${buss.chargeUnit}</div>
	            <div class="ui_cell_link"></div>
	        </a>
	    </div>
     </c:forEach>
    
    
</div>
<script type="text/javascript">
    
</script>
<body>
</body>
</html>
