<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/unionpay/index.css?t=<%=Math.random()%>">
</head>
<body>
	<div class="pay_top" style="background: #0c8ee6;">
	    <a href="javascript:history.back(-1)" class="back_btn"></a>
	    输入卡号
	</div>
	<div class="add_crad">
	    <label for="cardnumber">请您输入银行卡卡号：</label>
	    <input type="number" id="accNo" value="">
	    <span>注：暂不支持招商银行的储蓄卡和信用卡</span>
	</div>
	<div class="btn_box cardlist_btn" style="margin-top: 5px">
	    <a href="javascript:tijiao();" class="btn" style="background: #0c8ee6;">下一步</a>
	</div>
	<script src="${ctx}/js/jquery.1.9.0.min.js"></script>
	<script type="text/javascript">
	var flag = true;
	function tijiao(){
		var accNo = $("#accNo").val();
		if(accNo==""){
			weui.alert("请输入卡号");
			return false;
		}
		if(flag){
			
// 			var loading = weui.loading('正在为您开通银行卡快捷支付服务，请稍后...', {
// 			    className: 'custom-classname'
// 			});
			if(accNo.length==16||accNo.length==19){
				flag = false;
				location.href = "${ctx}/index/addCardDF?accNo="+accNo+"&_t="+Math.random()+"&&sel_time=${sel_time}&backCard=${backCard}&money=${money}";
			}else{
				weui.alert("您输入的卡号有误，请重新输入");
				return false;
			}
		}
	}
	</script>
</body>
</html>