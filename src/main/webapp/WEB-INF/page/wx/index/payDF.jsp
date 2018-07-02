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
<%@ include file="/WEB-INF/page/common/share.jsp" %>
<div class="pay_top" style="background: #0c8ee6;">
    <a href="javascript:history.back(-1)" class="back_btn"></a>
    	银行卡快捷支付
</div>
<div class="code_box">
    <label for="code"><c:if test="${not empty endCode}">您的后四位为${endCode}的银行卡已成功开通快捷支付服务，</c:if>请输入您收到的支付短信验证码完成付款：</label>
    <div class="clear">
        <input type="text" id="smsCode">
        <a href="javascript:;" class="code_btn" style="background: #0c8ee6;">获取验证码</a>
    </div>
</div>
<div class="btn_box cardlist_btn" style="margin-top: 5px;">
    <a href="javascript:pay();" class="btn" style="background: #0c8ee6;">支付</a>
</div>
<input type="hidden" id="accNo" readonly="readonly">
<input type="hidden" id="orderNo" readonly="readonly">
</body>
<script src="${ctx}/js/jquery.1.9.0.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>
    $(function () {
    	$(".code_btn").addClass("active");
    	code();
        $(".code_btn").on("click",function () {
            if(!$(this).hasClass("active")){
                $(this).addClass("active");
                sendCode();
            }
        })

    })
     var timer,num=60;
     function code() {
         clearInterval(timer);
         $(".code_btn").text("60s重新获取")
         timer = setInterval(function () {
             num--;
             $(".code_btn").text(num+"s重新获取")
             if(num == -1){
                 clearInterval(timer);
                 num=60;
                 $(".code_btn").removeClass("active").text("获取验证码")
             }
         },1000)
     }
     var dxflag = true;//防止短信重复发送
 	function sendCode(){
 		if(dxflag){
 			dxflag = false;
 			var index = layer.load(1, {
    			  shade: [0.6,'#666666'] //0.1透明度的白色背景
    			});
 			$.post("<c:url value='/unionpay/vercode'/>",
 	       	{
 				money :'${money}',
 				backCard :'${backCard}',
 				accNo :'${accNo}',
 				sel_time :'${sel_time}',
 				ranNum:Math.random()
 			},
 	       	function(data){
 				dxflag = true;
 				layer.close(index);
 	        	var result = eval('('+data+')'); 
 	            if (result.code == '1') {
 	            	$("#orderNo").val(result.rows);
 	            	layer.msg("发送成功，请注意查收验证码");
 	            	btnNum();
 	             } else {
 	            	layer.msg(result.message);
 	             }
 	        });
 		}
 	}
    
	var flag = true;
    function pay(){
    	var smsCode = $("#smsCode").val();
    	if(smsCode==""){
    		layer.msg("请输入验证码");
    		return false;
    	}
    	if(flag){
    		flag = false;
//     		var loading = weui.loading('正在等待支付结果，请稍后...', {
// 			    className: 'custom-classname'
// 			});
			layer.msg('正在等待支付结果，请稍后...', {
			  icon: 16
			  ,shade: 0.02
			});
   			$.post("<c:url value='/unionpay/pay'/>",
   	       	{
				money :'${money}',
 				accNo :${accNo},
 				smsCode :smsCode,
 				backCard :'${backCard}',
   				ranNum:Math.random()
   			},
   	       	function(data){
   	        	var result = eval('('+data+')'); 
   	        	if (result.code == '1') {
 	            	layer.msg(result.message);
 	            	$("#orderNo").val(result.rows);
 	            	setTimeout("toPayResult()",2000);
 	             } else {
 	            	flag = true;
 	            	layer.msg(result.message);
 	             }
   	        });
    	}
    }
    
    function toPayResult(){
    	window.location.href="${ctx}/unionpay/toPayResult?orderNo="+$("#orderNo").val()+"&backCard=${backCard}";
    }
</script>
</html>