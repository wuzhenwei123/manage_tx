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
    <title>身份验证</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/comm.css">
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/zepto.min.js" ></script>
</head>
<body class="body">

	<%@ include file="/WEB-INF/page/common/share1.jsp" %>
	<div class="box">
    	<div class="topBox" style="background: #0c8ee6;">
        	<a class=" offen_back" href="javascript:closeWin()"></a>
            验证身份
        </div>
        <div class="yzBox">
        	<div class="items">
            	<input class="input" type="text" placeholder="请输入手机号" id="phone"/>
            </div>
            <div class="items">
            	<input class="input" type="text" placeholder="请输入密码" id="pwd"/>
            </div>
            <div class="items">
            	<a href="javascript:verify();" class="btn btn_primary" style="background: #0c8ee6;">验证</a>
            </div>
            
        </div>
        
    </div>
    <div id="loadingToast" class="weui_loading_toast" style="display: none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <div class="weui_loading">
	            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
	        </div>
	        <p class="weui_toast_content">验证中</p>
	    </div>
	</div>
	<div class="weui_dialog_alert" style="display: none;">
	    <div class="weui_mask"></div>
	    <div class="weui_dialog">
	    	<div class="weui_dialog_hd">
	    		<strong class="weui_dialog_title" id="msg"></strong>
	      	</div>
	        <div class="weui_dialog_ft">
	            <a href="javascript:closeAlert();" class="weui_btn_dialog primary">确定</a>
	        </div>
	    </div>
	</div>
	<script type="text/javascript">
    if (typeof WeixinJSBridge == "undefined"){
   	   if( document.addEventListener ){
   	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   	   }else if (document.attachEvent){
   	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
   	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   	   }
   	}else{
   		   onBridgeReady();
   	}

   	function closeWin(){
   		WeixinJSBridge.call('closeWindow');
   	}

   	function onBridgeReady(){
   	}
   	
   	var flag = true;
   	function verify(){
   		var phone = $.trim($("#phone").val());
   		if(phone==""){
   			$("#msg").html("请输入手机号");
   			$(".weui_dialog_alert").show();
   			return false;
   		}
   		
   		var pwd = $.trim($("#pwd").val());
   		if(pwd==""){
   			$("#msg").html("请输入密码");
   			$(".weui_dialog_alert").show();
   			return false;
   		}
   		
   		$(".weui_loading_toast").show();
   		if(flag){
   			flag = false;
   			$.post("<c:url value='/weixin/verify'/>",
   			       	{
   			   			phone : phone,
   			   			pwd : pwd,
   			   			openId : '${openId}',
   						ranNum:Math.random()
   					},
   			       	function(data){
   						$(".weui_loading_toast").hide();
   			        	var result = eval('('+data+')');
   			        	flag = true;
   			            if (result.message == '1') {
   			            	$("#msg").html("验证成功");
   			       			$(".weui_dialog_alert").show();
   			       			window.location.href='${ctx}/weixin/dbIndex?openId=${openId}';
   			            }else if(result.message == '-4'){
   			            	$("#msg").html("用户名或密码错误");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-1'){
   			            	$("#msg").html("请输入账号");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-2'){
   			            	$("#msg").html("请输入密码");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-3'){
   			            	$("#msg").html("参数有误，请刷新页面");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-5'){
   			            	$("#msg").html("账号异常，请联系管理员");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-6'){
   			            	$("#msg").html("账号被冻结，请联系管理员");
   			       			$(".weui_dialog_alert").show();
   			            }else if(result.message == '-7'){
   			            	$("#msg").html("账号已被绑定，请先解绑");
   			       			$(".weui_dialog_alert").show();
   			            }else{
   			            	$("#msg").html("系统异常，请联系管理员");
   			       			$(".weui_dialog_alert").show();
   			            }
   					});
   		}
   	}
   	
   	function closeAlert(){
		$(".weui_dialog_alert").hide();
	}
    </script>
<body>
</body>
</html>
