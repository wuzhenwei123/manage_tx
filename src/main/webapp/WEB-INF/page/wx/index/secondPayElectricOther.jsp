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
    <div class="charge_logo">电费</div>
    <div class="charge_con">
        <div class="charge_m clear">
        	<c:if test="${lx==1}">
            	<input type="text" placeholder="缴费金额输入大于${qf}元" id="moneys">
        	</c:if>
        	<c:if test="${lx==0}">
        		<input type="text" placeholder="缴费金额" id="moneys">
        	</c:if>
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
            <a href="javascript:tijiao();">下一步</a>
        </div>
</div>


</body>
<script>
   function tijiao(){
	   var lx = '${lx}';
	   var qf = '${qf}';
	   var moneys = $("#moneys").val();
	   var reg = /^\d+(?:.\d{1,2})?$/;
		if(!reg.test(moneys)){
			layer.msg("请输入大于"+qianfei+"的金额（允许两位小数）");
			return false;
		}
	   if(lx=="1"){
		   if(parseFloat(moneys)<parseFloat(qf)){
			   layer.msg("请输入大于"+parseFloat(qf)+"（允许两位小数）");
			   return false;
		   }
	   }
	   window.location.href = '${ctx}/other/toSelPayWayOther?PaymentInfo=${PaymentInfo}&fee='+moneys+'&paynumber=${paynumber}&cityCode=${cityCode}&loopID=${loopID}&ServiceType=${ServiceType}&centerSerial=${centerSerial}';
   }
</script>
</html>