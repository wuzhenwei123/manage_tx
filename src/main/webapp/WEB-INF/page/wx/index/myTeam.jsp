<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/wx/tx/index.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css?t=<%=Math.random()%>">
    <style type="text/css">
    .top, .c_top {
	    height: 40px;
	    line-height: 40px;
	    text-align: center;
	    font-size: 18px;
	    font-weight: bold;
	    border-bottom: 1px solid #d8d8d8;
	    background: #000;
	    color: #fff;
	}
	.ui_cell_link:after {
	    content: " ";
	    display: inline-block;
	    -webkit-transform: rotate(45deg);
	    transform: rotate(45deg);
	    height: 10px;
	    width: 10px;
	    border-width: 2px 2px 0 0;
	    border-style: solid;
	    position: relative;
	    top: -1px;
	    margin-left: .3em;
	}
    </style>
</head>
<body>

<div class="wrap">
	<div class="top" style="background: #3f9bfe">
    	合伙人团队
	</div>
    <div class="details_list">
        <ul>
            <li class="clear" onclick="toMyFans()">
                <label>我的团队</label>
                <em>${countF}&nbsp;人<img src="${ctx}/images/wx/right.png"> </em>
            </li>
            <li class="clear">
                <label>团队本期业绩</label>
                <em>${moneyAll}&nbsp;元</em>
            </li>
            <li class="clear">
                <label>团队本期佣金</label>
                <em>${money}&nbsp;元</em>
            </li>
            <li class="clear">
                <label>团队本期贡献</label>
                <em>${moneySelf}&nbsp;元</em>
            </li>
        </ul>
    </div>
</div>
<div class="btn_box btns">
	<a href="javascript:closeWin();">返回</a>
</div>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script type="text/javascript">
function closeWin(){
	WeixinJSBridge.call('closeWindow');
}

function toMyFans(){
	window.location.href = "${ctx}/other/toMyFans";
}
</script>
</body>
</html>