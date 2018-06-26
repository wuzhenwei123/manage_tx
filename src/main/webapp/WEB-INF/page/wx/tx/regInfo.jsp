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
    售电合伙人新财富计划
</div>
<div class="c_top">
    您批卡，我保卖，公益新财富模式！
</div>
<div class="wrap">
    <div class="agreement">
        问：我没有时间、没有店铺。是否可以批发电费充值卡来卖？<br>
        答：完全没问题！没有时间我们帮您卖！本系统是最大的便民售电厅，有着众多的电费充值用户，电费充值卡供不应求。<br>
        <br>
        问：怎么批发电费充值卡，怎么卖？<br>
        答：您可以在本公众号直接批发电卡，然后我们通过微信直接销售并充值到电居民的电表中，约定时间（一个月）到期后我们将卖卡金额连同收益返回到您本人银行卡上。<br>
        <br>
        问：批发电费充值卡约定的周期还没到，但我想提前退出可以吗？<br>
        答：您可以在本系统中自主操作申请提前退款，我们将在10天内将您本金退到您本人银行卡中。如果您选择当日退款，我们将在收取约0.6%的成本手续费并为您完成当日退款。
    </div>
</div>
<div class="btn_box">
    <a href="${ctx}/weixin/toReg?openId=${openId}">注册成为售电合伙人</a>
</div>
</body>
</html>