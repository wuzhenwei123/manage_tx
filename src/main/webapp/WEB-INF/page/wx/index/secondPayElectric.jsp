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
        	<c:if test="${shopCode=='3202'}">
<!--             	<input type="text" placeholder="输入大于${mapresult['qianfei']}元的100元的整数倍" id="moneys" onkeyup="this.value=this.value.replace(/\D/gi,'')"> -->
            	<input type="text" placeholder="输入大于${mapresult['qianfei']}元的100元的整数倍" id="moneys">
            </c:if>
            <c:if test="${shopCode=='3102'}">
            	<input type="text" placeholder="输入大于${mapresult['yingjiao']}元（允许两位小数）" id="moneys">
            </c:if>
        </div>
        <ul>
            <li class="clear">
                <label>缴费单位</label>
                <p>国网北京市电力公司</p>
            </li>
            <li class="clear">
                <label>客户编号</label>
                <p>${mapresult["customerNumber"]}</p>
            </li>
            <li class="clear">
                <label>户名</label>
                <p>${mapresult["username"]}</p>
            </li>
            <li class="clear">
                <label>用户地址</label>
                <p class="w70">${mapresult["userAddress"]}</p>
            </li>
            <c:if test="${shopCode=='3202'}">
            	<li class="clear">
	                <label>补加金额</label>
	                <p>${mapresult["bujia"]}</p>
	            </li>
	            <li class="clear">
	                <label>扣减金额</label>
	                <p>${mapresult["koujian"]}</p>
	            </li>
	            <li class="clear">
	                <label>补交阶梯差价金额</label>
	                <p>${mapresult["chajia"]}<c:if test="${mapresult['chajia']=='0.00'}">元</c:if>	</p>
	            </li>
            </c:if>
            <c:if test="${shopCode=='3102'}">
            	<li class="clear">
	                <label>账单金额</label>
	                <p>${mapresult["zhangdan"]}</p>
	            </li>
	            <li class="clear">
	                <label>滞纳金额</label>
	                <p>${mapresult["zhina"]}</p>
	            </li>
	            <li class="clear">
	                <label>欠费金额</label>
	                <p>${mapresult["应缴金额"]}元</p>
	            </li>
            </c:if>
        </ul>
    </div>

        <div class="btn_boxs">
            <a href="javascript:tijiao();">下一步</a>
        </div>
</div>


</body>
<script>
   function tijiao(){
	   var moneys = $("#moneys").val();
	   var shopCode = "${shopCode}";
		var qianfei = "";
		if(shopCode=="3202"){
			qianfei = '${mapresult['qianfei']}';
		}else{
			qianfei = '${mapresult['yingjiao']}';
		}
		if(qianfei!=""){
			qianfei = parseFloat(qianfei);
		}
		if(moneys==""){
			layer.msg("请输入充值金额");
			return false;
		}else if(parseFloat(moneys)<qianfei){
			if(shopCode=="3202"){
				layer.msg("请输入大于"+qianfei+"元的100元的整数倍");
			}else{
				layer.msg("请输入大于"+qianfei+"（允许两位小数）");
			}
			return false;
		}else if(parseFloat(moneys) > 50000) {
			layer.msg("最大充值金额不能超过50000");
			return false;
		}
		if(shopCode=="3202"){
// 			if(moneys%100 > 0){
// 				layer.msg("请输入100的整数倍");
// 				return false;
// 			}
		}else{
			var reg = /^\d+(?:.\d{1,2})?$/;
			if(!reg.test(moneys)){
				layer.msg("请输入大于"+qianfei+"的金额（允许两位小数）");
				return false;
			}
		}
		$("#money").val(moneys);
		window.location.href = "${ctx}/index/queryPayNumberMsgTwo?shopCode="+shopCode+"&paynumber=${mapresult['customerNumber']}&money="+moneys;
   }
</script>
</html>