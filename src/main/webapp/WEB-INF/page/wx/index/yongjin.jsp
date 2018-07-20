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
	<style type="text/css">
	input {
	    height: 35px;
	    width: 70%;
	    border: 1px solid #757575;
	    padding: 0 5%;
	    font-size: 16px;
	    color: #757575;
	}
	</style>
</head>
<body>
<%@ include file="/WEB-INF/page/common/share2.jsp"%>
<div class="wrap">
    <div class="com_top">
        <p>佣金罐余额： <i>${yuFee}</i></p>
    </div>
    <div class="com_con">
		<c:if test="${week==2}">
        	<a href="${ctx}/other/toApply?yuFee=${yuFee}">提取佣金</a>
        </c:if>

        <div class="com_detail clear">
            <a href="${ctx}/other/toApplyDetail" style="border-radius: 4px;color: #fff;background: #0c8ee6;">查看明细</a>
            <p>注：每周2之前提取 的佣金在当周5到账</p>
        </div>
        <div class="clear">
            <label>已到账佣金：</label>
            <i>${recordFee}元</i>
        </div>
        <div class="clear">
            <label>提取审核金额：</label>
            <i>${applyFee}元</i>
        </div>
    </div>

    <div class="more_m">
        <p>嫌钱少？请您试试</p>
        <a href="${ctx}/other/toMyEmq"><img src="${ctx}/images/index/1.png" alt="">展业建团队</a>
        <a href="${ctx}/index/toRecharge"><img src="${ctx}/images/index/11.png" alt="">批电躺着赚</a>
    </div>

</div>
</body>
</html>