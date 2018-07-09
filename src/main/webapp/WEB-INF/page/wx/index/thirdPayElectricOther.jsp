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
    <div class="charge_logo">电费</div>
    <div class="charge_con">
        <div class="charge_m clear">
            <p>缴费金额</p>
            <em>${money}元</em>
        </div>
        <ul>
            <li class="clear">
                <label>缴费单位</label>
                <p>${txBusinessType.chargeUnit}</p>
            </li>
            <li class="clear">
                <label>客户编号</label>
                <p>${paynumber}</p>
            </li>
            <li class="clear">
                <label>户名</label>
                <p>${displayInfo}</p>
            </li>
        </ul>
    </div>

        <div class="btn_boxs">
            <a href="javascript:tijiao();">立即缴费</a>
        </div>
</div>


</body>
<script>
   function tijiao(){
		window.location.href = '${ctx}/other/toSelPayWayOther?PaymentInfo=${paynumber}&fee=${fee}&paynumber=${paynumber}';
   }
</script>
</html>