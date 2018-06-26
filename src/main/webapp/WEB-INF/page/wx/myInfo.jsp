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
<div class="box">
	<%@ include file="/WEB-INF/page/common/share1.jsp" %>
    <div class="topBox">个人中心</div>
    <c:set value="<%=ConfigConstants.DB_ROLE_ID %>" var="db"></c:set>
    <div class="ch_ui_box marginTop16">
        <a href="${ctx}/weixin/toMyCustomer?openId=${openId}" class="ui_cell ui_cells">
        	<div style="background-image:url('${ctx}/images/wx/user_icon_!12.png');" class="ui_cell_icon_box"></div>
            <div class="ui_center ui_cell_flex">我的客户</div>
            <div class="ui_cell_link"></div>
        </a>
    </div> 
    <c:if test="${db==admin_user.role_id}">
	    <div class="ch_ui_box marginTop16">
	        <a href="${ctx}/weixin/toMyFriend?openId=${openId}" class="ui_cell ui_cells">
	        	<div style="background-image:url('${ctx}/images/wx/user_icon_!.png');" class="ui_cell_icon_box"></div>
	            <div class="ui_center ui_cell_flex">我的朋友</div>
	            <div class="ui_cell_link"></div>
	        </a>
	    </div> 
	    <div class="ch_ui_box marginTop16">
	        <a href="${ctx}/weixin/toMyQRcodeFriend?openId=${openId}" class="ui_cell ui_cells">
	        	<div style="background-image:url('${ctx}/images/wx/user_icon_!13.png');" class="ui_cell_icon_box"></div>
	            <div class="ui_center ui_cell_flex">我的朋友二维码</div>
	            <div class="ui_cell_link"></div>
	        </a>
	    </div> 
    </c:if>
    <div class="ch_ui_box marginTop16">
        <a href="${ctx}/weixin/toMyQRcode?openId=${openId}" class="ui_cell ui_cells">
        	<div style="background-image:url('${ctx}/images/wx/user_icon_!13.png');" class="ui_cell_icon_box"></div>
            <div class="ui_center ui_cell_flex">我的客户二维码</div>
            <div class="ui_cell_link"></div>
        </a>
    </div> 
    <div class="ch_ui_box marginTop16">
        <a href="javascript:showSZ()" class="ui_cell ui_cells">
        	<div style="background-image:url('${ctx}/images/wx/user_icon_!15.png');" class="ui_cell_icon_box"></div>
            <div class="ui_center ui_cell_flex">解绑</div>
            <div class="ui_cell_link"></div>
        </a>
    </div>        
    
    
</div>
<div class="weui_dialog_confirm" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd">
          <strong class="weui_dialog_title">您确定需要解绑吗？</strong>
        </div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog default" onclick="closeConfirm()">取消</a>
            <a href="javascript:;" class="weui_btn_dialog primary" onclick="unbind()">确定</a>
        </div>
    </div>
</div>
<div class="weui_dialog_alert" style="display: none;" id="closemsg">
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

<div class="weui_dialog_alert" style="display: none;" id="tixianmsg">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
    	<div class="weui_dialog_hd">
    		<strong class="weui_dialog_title">请先完善提现信息</strong>
      	</div>
        <div class="weui_dialog_ft">
            <a href="${ctx}/weixin/toPostalInfo?openId=${openId}" class="weui_btn_dialog primary">去完善</a>
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
    
    function unbind(){
    	$.post("${ctx}/weixin/unbind", {
    		_t : Math.random()
    	}, function(data) {
    		var result = eval("(" + data + ")");
    		if (result.code == 1) {
    			if (result.message == "success") {
    				$("#closemsg").show();
    				$("#msg").html("解绑成功！");
    				flag = true;
    			}
    		}else{
    			if (result.message == "error") {
    				$("#closemsg").show();
    				$("#msg").html("解绑失败！");
    			} 
    		}
    	});
    }
    
    function closeAlert(){
    	closeWin();
    	$("#closemsg").hide();
    }
    function showSZ(){
		$(".weui_dialog_confirm").show();
	}
	
	function closeConfirm(){
		$(".weui_dialog_confirm").hide();
	}
	
	function toAccount(){
// 		var bankFlag = '${bankFlag}A';
// 		if(bankFlag=="A"){
			window.location.href = '${ctx }/weixin/toAccount';
// 		}else{
// 			$("#tixianmsg").show();
// 		}
	}
    </script>
<body>
</body>
</html>
