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
    <div class="com_top">
        <p>佣金罐余额： <i>${yuFee}</i></p>
    </div>
    <div class="com_con">

        <a href="javascript:;">提取佣金</a>

        <div class="com_detail clear">
            <a href="#" style="border-radius: 4px;color: #fff;background: #0c8ee6;">查看明细</a>
            <p>注：每周2之前提取 的佣金在当周5到账</p>
        </div>
        <div class="clear">
            <label>已到账佣金：</label>
            <i>${recordFee}元</i>
        </div>
        <div class="clear">
            <label>提取审核金额：</label>
            <i><input type="text" id="applyFee">元</i>
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