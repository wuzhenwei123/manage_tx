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
</head>
<body>
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
    <div class="wrap">
    	<div class="pay_msg">
            <p>请选择缴费信息</p>
        </div>
        <div class="sel_bank">
            <ul>
            	<c:forEach items="${list}" var="paynumber">
            		<li class="clear active" onclick="toPay(${paynumber.payNumber},'${paynumber.remark1}')">
	                    <div class="fl">
	                        ${paynumber.payNumber}/${paynumber.nickName}
	                    </div>
	                </li>
            	</c:forEach>
            </ul>
        </div>
        <div class="btn_boxs">
            <a href="javascript:toOther();">给别的地址缴费</a>
        </div>
    </div>
</body>
<script>
    function toPay(payNumber,shopCode){
    	window.location.href = "${ctx}/index/queryPayNumberMsg?paynumber="+payNumber+"&shopCode="+shopCode;
    }
    function toOther(){
    	window.location.href = "${ctx}/index/toOther?billType=002&cityCode=010";
    }
</script>
</html>