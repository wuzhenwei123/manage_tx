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
    <div class="bus_top">
        <p><i>${txBusinessType.chargeUnit}</i></p>
    </div>
    <div class="bus_con" style="padding-top: 0px;">
        <c:if test="${not empty gz}">
        	<c:if test="${gz=='E'}">
	        	<div class="sel_box">
		            <input type="text" readonly placeholder="请选择缴费金额" id="fee"/>
		            <div class="sel_list">
		                <ul>
		                	<c:forEach items="${strFeeList}" var="fee">
		                		<li>${fee}</li>
		                	</c:forEach>
		                </ul>
		            </div>
		        </div>
	        </c:if>
	        <c:if test="${gz=='M'}">
	        	<div class="num_box">
		            <p>请输入缴费金额</p>
		            <input type="number" id="fee" max="${maxfee}" min="${minfee}" step="${beishu}">
		        </div>
	        </c:if>
        </c:if>
        <c:if test="${empty gz}">
        	<div class="num_box">
	            <p>请输入缴费金额</p>
	            <input type="text" id="fee">
	        </div>
        </c:if>
        <div class="num_box">
            <p>请输入智能表客户编号或抄表电缴费号</p>
            <input type="text" id="paynumber">
        </div>
        <div class="msg_box">
            <p>1.如果您是智能表客户，请在电力公司发给您的 《居民客户用电登记表》右上角找到您的客户编号。</p>
            <p>2.如果您是抄表电客户，请在您以往的缴费通知 单或电费发票上找到您的电费缴费号。您也可以 拨打电力公司客服电话95598提供电地址查询。</p>
        </div>
    </div>

    <div class="btn_boxs">
        <a href="javascript:next();">下一步</a>
    </div>
</div>
</body>
<script>
    $(function () {
        $(".sel_box input").on("click",function () {
            $(this).toggleClass("active");
            $(".sel_list").toggle();
        })
        $(".sel_list li").on("click",function () {
            $(".sel_box input").val($(this).text()).removeClass("active")
            $(".sel_list").hide()
            $("#typeId").val($(this).attr("id"))
        })
    })
    
    function next(){
    	var cityCode = '${txBusinessType.cityCode}';
    	
   		var fee = $("#fee").val();
   		if(""==fee){
   			layer.msg("请输入缴费金额");
   			return false;
   		}
    	
    	var paynumber = $("#paynumber").val();
    	if(""==paynumber){
    		layer.msg("请填写缴费号");
			return false;
    	}
    	window.location.href = "${ctx}/other/queryPayNumberMsg?paynumber="+paynumber+"&cityCode="+cityCode+"&typeId=${txBusinessType.id}&fee="+fee;
    }
</script>
</html>