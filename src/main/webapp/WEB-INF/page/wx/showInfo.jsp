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
    <title>查看证件信息</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/customForm.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/comm.css">
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    <style type="text/css">
    .maskbg {
			background: rgba(0, 0, 0, .7);
			display: none;
			height: 100%;
			left: 0;
			position: fixed;
			top: 0;
			width: 100%;
			z-index: 19999;
			overflow: auto;
			text-align: center;
		}
    </style>
</head>
<body class="body">
<div class="box">
	<%@ include file="/WEB-INF/page/common/share1.jsp" %>
    <div class="topBox"><a class=" offen_back" href="javascript:history.go(-1);"></a>查看证件信息</div>
	<div id="zd-item">
		<img alt="" src="${txWxUser.IDUrl}" width="100%" onclick="showPic(this)">
		<img alt="" src="${txWxUser.IDFanUrl}" width="100%" onclick="showPic(this)">
		<img alt="" src="${txWxUser.IDPersonUrl}" width="100%" onclick="showPic(this)">
		<img alt="" src="${txWxUser.cardUrl}" width="100%" onclick="showPic(this)">
	</div>    
    
    <div class="preview maskbg" data-p="" onclick="closePreview()">
		<img src="images/icon.jpg" />
	</div>
</div>
<script>
function showPic(obj) {
	//获取图片的宽和高
	var image = new Image();
	image.src = obj.src;
	var naturalWidth = image.width;
	var naturalHight = image.height;
	$(".preview").find("img").attr('src', obj.src);
	$(".preview").find("img").attr('width', naturalWidth);
	$(".preview").find("img").attr('height', naturalHight);
	$(".preview").show();
}

function closePreview() {
	$(".preview").hide();
}
</script>
</body>
</html>
