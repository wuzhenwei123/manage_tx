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
    购电和缴费
</div>
<div class="wrap">
    <h5>请使用本人的银行卡或微信支付！（暂不支持招商银行）：</h5>
    <div class="sel_box">
        <ul>
            <li class='clear sl_list active'  data-id='1'>
                <a href="javascript:;"></a>
                <em>智能表购电</em>
            </li>
            <li class='clear sl_list'  data-id='2'>
                <a href="javascript:;"></a>
                <em>抄电表充值</em>
            </li>
            <li class='clear sl_list' data-id='3'>
                <a href="javascript:;"></a>
                <em>售电合伙人批发电费充值卡</em>
            </li>
        </ul>
    </div>
</div>
<div class="btn_box">
    <a href="javascript:;">提交</a>
</div>
</body>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/weui.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>
    $(function () {
        $(".sl_list").on("click",function () {
            $(this).addClass("active").siblings().removeClass("active");
        })
        
        $(".btn_box a").on("click",function () {
        	var selid = "";
        	$(".sl_list").each(function(){
        		if($(this).hasClass("active")){
        			selid = $(this).attr("data-id");
        		}
        	})
        	 if(selid=="3"){
             	var admin_user = '${admin_user}';
             	if(admin_user!=""){
             		var state = '${admin_user.state}';
             		var checkState = '${admin_user.checkState}';
             		if(state=="1"&&checkState=="1"){
             			window.location.href = "${ctx}/unionpay/toPaySecond";
             		}else{
             			layer.msg("请先注册");
             		}
             	}else{
             		layer.msg("请先注册");
             	}
             }else if(selid=="1"){
             	window.location.href = "http://weixin.chinaepay.com/redirect_uri.jsp?state=s,intelligentPower";
             }else if(selid=="2"){
             	window.location.href = "http://weixin.chinaepay.com/redirect_uri.jsp?state=s,meterReading";
             }
        })
    })
</script>
</html>