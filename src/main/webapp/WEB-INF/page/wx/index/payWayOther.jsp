<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/index/myindex.css">
    <script src="${ctx}/js/jquery.1.9.0.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
    <div class="wrap">
        <div class="sel_bank">
            <ul>
                <li class="clear active" id="3">
                    <div class="fl">
                        <img src="${ctx}/images/index/spay.png" alt="">
                        银行卡快捷支付
                    </div>
                    <i class="fr"></i>
                </li>
                <li class="clear" id="4">
                    <div class="fl">
                        <img src="${ctx}/images/index/upay.png" alt="">
                        银行卡支付
                    </div>
                    <i class="fr"></i>
                </li>
<!--                 <li class="clear"> -->
<!--                     <div class="fl"> -->
<!--                         <img src="./images/wchat.png" alt=""> -->
<!--                         微信支付 -->
<!--                     </div> -->
<!--                     <i class="fr"></i> -->
<!--                 </li> -->
            </ul>
        </div>
        <div class="pay_msg">
            <h5>温馨告知</h5>
            <p>“银行卡支付”和“银行快捷支付”暂不支持招商银行发行的储蓄卡和信用卡。</p>
        </div>
        <div class="btn_boxs">
            <a href="javascript:;">下一步</a>
        </div>
    </div>
</body>
<script>
    $(function () {
        $(".sel_bank li").on("click",function () {
            $(this).addClass("active").siblings().removeClass("active")
        })
        $(".btn_boxs").on("click",function () {
        	var id = $(".sel_bank li.active").attr("id");
        	if(id=="3"){
	       		location.href = "${ctx}/other/getListCard?PaymentInfo=${PaymentInfo}&fee=${fee}&paynumber=${paynumber}&cityCode=${cityCode}&loopID=${loopID}&ServiceType=${ServiceType}&centerSerial=${centerSerial}";
	       	}else if(id=="4"){
	       		location.href = "${ctx}/other/toUnionpay?_t="+Math.random()+"&PaymentInfo=${PaymentInfo}&fee=${fee}&paynumber=${paynumber}&cityCode=${cityCode}&loopID=${loopID}&ServiceType=${ServiceType}&centerSerial=${centerSerial}";
	       	}
        })
    })
</script>
</html>