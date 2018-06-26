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
    <link href="${ctx}/css/kefu.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="/WEB-INF/page/common/share2.jsp"%>	
<div class="top" style="background: #3f9bfe">
    订单确认
</div>
<div class="wrap">
    <div class="details_list">
        <ul>
            <li class="clear">
                <label>订单类型</label>
                <em>委托售卡</em>
            </li>
            <li class="clear">
                <label>购卡面值</label>
                <em>${money}.00</em>
            </li>
            <li class="clear">
                <label>委托售卡周期</label>
                <em><c:if test="${backCard==0}">1个月</c:if><c:if test="${backCard==1}">不定期</c:if></em>
            </li>
            <li class="clear">
                <label>售卡结算日期</label>
                <em><fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/></em>
            </li>
            <li class="clear">
                <label>银行卡号</label>
                <a href="javascript:;" class="clear fr bank_btn">
                	<p>银行卡/储蓄卡</p>
                	<em><img src="${ctx}/images/wx/bank_logo.png"></em>
                </a>
            </li>
            
            <li class="clear h50">
                <input type="number" name="code" placeholder="请输入验证码" id="smsCode" style="width: 50%;">
                <em><a href="javascript:;" class="code_btn" id="codeBtn" onclick="sendCode()">获取验证码</a></em>
            </li>
        </ul>
    </div>
    <div class="pay_box">
        <div class="red clear">
            <div class="fl">支付金额</div>
            <div class="fr">${money}元</div>
        </div>
        <div class="clear sel_box">
            <a href="javascript:;" class="sel_btn active"></a>
            <div class="fl">
                我同意
                <a href="#">《购买及委托售卡协议》</a>
            </div>
        </div>

    </div>
</div>
<div class="btn_box">
    <a href="javascript:;">已同意协议并支付</a>
</div>
<div class="dailog">
	<div class="mask"></div>
	<div class="bank_list">
		<ul>
			<li class="active clear">
				<i></i>
				请选择 银行卡
			</li>
			<c:forEach items="${list}" var="bank">
	       		<li id="${bank.accNo}">
	                <span><i></i></span>
	                ****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;${bank.endCode}
	            </li>
	       	</c:forEach>
		</ul>
	</div>
</div>
<input type="hidden" id="accNo" readonly="readonly">
<input type="hidden" id="orderNo" readonly="readonly">
<!-- <div class="side"> -->
<!-- 	<ul> -->
<!-- 		<li><a href="javascript:closeWin();"><div class="sidebox" style="font-size: 10px;text-align: center;">在线客服</div></a></li> -->
<!-- 	</ul> -->
<!-- </div> -->
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/weui.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>
    $(function () {
        $(".sel_btn").on("click",function () {
            $(this).toggleClass("active")
        })
        $(".btn_box a").on("click",function () {
            if($(".sel_btn").hasClass("active")){
            	tijiao();
            }else {
                layer.msg("请阅读并同意《购买及售卡协议》")
            }
        })
        $(".bank_btn").on("click",function() {
        	$(".dailog").show();
        })
        $(".bank_list li").on("click",function(){
        	if($(this).index() == 0){
        		$(".bank_btn p").text("银行卡/储蓄卡");
                $("#accNo").val('');
            }else{
            	$(".bank_btn p").text($(this).text());
                $("#accNo").val($.trim($(this).attr("id")));
            }
        	$(this).addClass("active").siblings().removeClass("active");
        	$(".dailog").hide();
        })
    })
    
    var dxflag = true;//防止短信重复发送
	function sendCode(){
    	var accNo = $("#accNo").val();
    	if(accNo==""){
    		layer.msg("请选择银行卡");
    		return false;
    	}
		if(dxflag){
			dxflag = false;
			var index = layer.load(1, {
   			  shade: [0.6,'#666666'] //0.1透明度的白色背景
   			});
			$.post("<c:url value='/unionpay/vercode'/>",
	       	{
// 				money :'${money}',
				money :5,
				backCard :'${backCard}',
				accNo :accNo,
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
	function btnNum(){
		var num = 59;
		$("#codeBtn").attr('disabled',true);
		$("#codeBtn").attr('onclick','');
		$("#codeBtn").addClass('active');
		$("#codeBtn").html('还剩'+num-- +'秒');
		var intervalID = setInterval(function(){
			if(num>0){
				$("#codeBtn").html('还剩'+num+'秒');
				num--;
			}else{
				$("#codeBtn").html('发送验证码');
				$("#codeBtn").removeClass('active');
				$("#codeBtn").attr('disabled',false);
				$("#codeBtn").attr('onclick','sendCode()');
				clearInterval(intervalID)
			}
		}, 1000);
	}
	
	function validatemobile(mobile){ 
		if (mobile.length == 0) {
			return false;
		}
		if (mobile.length != 11) {
			return false;
		}
		var myreg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if (!myreg.test(mobile)) {
			return false;
		}
		return true;
	}
	
	var flag = true;
    function tijiao(){
   		//检查用户是否认证
      	var accNo = $("#accNo").val();
      	if(accNo==""){
      		layer.msg("请输选择银行卡");
      		return false;
      	}
      	
      	var smsCode = $("#smsCode").val();
      	if(smsCode==""){
      		layer.msg("请输入验证码");
      		return false;
      	}
      	
      	if(flag){
      		flag = false;
      		var index = layer.load(1, {
    			  shade: [0.6,'#666666'] //0.1透明度的白色背景
    			});
     			$.post("<c:url value='/unionpay/pay'/>",
     	       	{
//      				money :'${money}',
     				money :5,
     				accNo :accNo,
     				smsCode :smsCode,
     				backCard :'${backCard}',
     				ranNum:Math.random()
     			},
     	       	function(data){
     				flag = true;
     				layer.close(index);
     	        	var result = eval('('+data+')'); 
     	            if (result.code == '1') {
     	            	layer.msg(result.message);
     	            	setTimeout("toPayResult(${backCard})",3000);
     	             } else {
     	            	layer.msg(result.message);
     	             }
     	        });
      	}
    }
    
    function closeWin(){
		WeixinJSBridge.call('closeWindow');
		$.get("${ctx}/kefu/openKF",function(data){
			
		})
	}
    
    function toPayResult(backCard){
    	window.location.href="${ctx}/unionpay/toPayResult?orderNo="+$("#orderNo").val()+"&backCard="+backCard;
    }
</script>
</body>
</html>