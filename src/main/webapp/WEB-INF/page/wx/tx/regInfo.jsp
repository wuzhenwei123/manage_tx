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
    赚点钱
</div>
<div class="c_top">
    吉云合伙人计划要点
</div>
<div class="wrap">
    <div class="agreement">
    1、您推广，我返佣<br>
	注册成为吉云合伙人可以获得专属推广二维码，凡是居民用户通过扫推广码关注本公众号并完成生活缴费或商品订购，合伙人可获得推广佣金。<br>
	2、您批发，我零售<br>
	配合电网售电改革创新，吉云合伙人可以参与预付批发电费并委托吉云零售给电网用户，获得每年约5%~10%的收益。
    </div>
</div>
<div class="btn_box">
    <a href="${ctx}/weixin/toReg?openId=${openId}">注册成为售电合伙人</a>
</div>
</body>
</html>