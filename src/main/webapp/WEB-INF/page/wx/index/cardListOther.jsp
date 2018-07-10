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
    <div class="wrap">
        <div class="sel_bank">
            <ul>
            	<li class="clear" accNo="addcard">
                    <div class="fl">
                        <img src="/images/wx/bank_logo.png" alt="">
                       	 使用其他银行卡
                    </div>
                    <i class="fr"></i>
                </li>
            	<c:forEach items="${list}" var="card">
            		<li class="clear" accNo="${card.accNo}">
	                    <div class="fl">
	                        <img src="/images/wx/bank_logo.png" alt="">
	                        **** **** **** **** ${card.endCode}
	                    </div>
	                    <i class="fr"></i>
	                </li>
            	</c:forEach>
            </ul>
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
            var accNo = $(".sel_bank li.active").attr("accNo");
            if(typeof(accNo)=="undefined"){
            	layer.msg("请您选择银行卡");
            	return false;
            }else{
            	if("addcard"==accNo){
            		 location.href = "${ctx}/other/toAddCard?_t="+Math.random()+"&ordercode=${PaymentInfo}&fee=${fee}&cityCode=${cityCode}&LoopInfo=${LoopInfo}&ServiceType=${ServiceType}";
            	}else{
		            location.href = "${ctx}/other/toPay?accNo="+accNo+"&_t="+Math.random()+"&ordercode=${PaymentInfo}&fee=${fee}&cityCode=${cityCode}&LoopInfo=${LoopInfo}&ServiceType=${ServiceType}";
            	}
            }
        })
    })
</script>
</html>