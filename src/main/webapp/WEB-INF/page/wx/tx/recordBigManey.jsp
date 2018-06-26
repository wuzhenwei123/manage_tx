<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp"%>
<html lang="zh-cn" data-dpr="1" max-width="540"
	style="font-size: 25.875px;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta name="hotcss" content="initial-dpr=1">
<title>吉云水电气</title>
<script src="${ctx}/js/hotcss.js"></script>
<link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
<link rel="stylesheet" href="${ctx}/css/wx/base.css">
<link rel="stylesheet" href="${ctx}/css/wx/icons.css">
<link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
<script src="${ctx}/js/jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/wx/login.css">
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
	<div class="scroller-container">
		<div class="vm order-detail">
			<div class="register_list cer_list" style="margin-top: 0px;">
				<ul>
					<li><label>姓名</label> <input type="text" placeholder="请输入姓名" id="realName"></li>
					<li><label>手机号</label> <input type="text" placeholder="请输入手机号" id="mobile"></li>
				</ul>
			</div>
			<a href="javascript:tijiao();" class="btn_cer">提交</a>
		</div>
	</div>
</body>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>
var flag = true;
function tijiao(){
	var realName = $("#realName").val();
	if(realName==""){
		layer.msg("请输入真实姓名");
		return false;
	}
	
	var mobile = $("#mobile").val();
	if(mobile==""){
		layer.msg("请输入手机号");
		return false;
	}
	if(flag){
		flag = false;
		var index = layer.load(1, {
		  shade: [0.6,'#666666'] //0.1透明度的白色背景
		});
		$.post("<c:url value='/weixin/reg'/>",
       	{
			realName : realName,
			mobile : mobile,
			openId : '${openId}',
			 _t:Math.random()},
	       	function(data){
				flag = true;
				layer.close(index);
	        	var result = eval('('+data+')'); 
	            if (result.c == '0') {
	            	layer.msg("提交成功，工作人员会尽快与您取得联系！");
	            	setTimeout("closeWin()",3000);
	            } else {
	            	layer.msg(result.m);
	            }
       });
	}
}
function closeWin(){
	WeixinJSBridge.call('closeWindow');
}
</script>
</html>