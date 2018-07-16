<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <script src="${ctx}/js/hotcss.js"></script>
    <link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
    <link rel="stylesheet" href="${ctx}/css/wx/base.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css?t=<%=Math.random()%>">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weui.css">
    <link rel="stylesheet" href="${ctx}/css/wx/login.css">
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/weui.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    <style type="text/css">
    .deal_list dl li {
	    float: left;
	    width: 25%;
	    text-align: center;
	}
	.topBox {
	    height: 40px;
	    position: relative;
	    background-color: #01a796;
	    overflow: hidden;
	    padding-left: 40px;
	    padding-right: 40px;
	    text-align: center;
	    font-size: 18px;
	    line-height: 38px;
	    color: #ffffff;
	}
    </style>
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
	<div class="topBox" style="background: #0c8ee6;">  合伙人佣金明细</div>
    <div class="scroller-container" style="    margin-top: 40px;">
        <div class="vm order-detail">
			<div class="deal_list">
            	<dl>
                    <dt>
                        <ul class="clearfix">
                            <li>粉丝昵称</li>
                            <li>交易时间</li>
                            <li>金额</li>
                            <li>佣金</li>
                        </ul>
                    </dt>
                    <div id="zd-item">
                    	<c:forEach items="${list }" var="order">
							<dd>
								<ul class="clearfix">
									<li>${order.nickName}</li>
									<li>${order.time}</li>
									<li>${order.fee}</li>
									<li>${order.yongjin}</li>
								</ul>
							</dd>
						</c:forEach>
                    </div>
                </dl>
            </div>
        </div>
    </div>
</body>
</html>