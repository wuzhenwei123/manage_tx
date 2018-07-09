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
        <p>您的地区 <i>${billArea}</i></p>
    </div>
    <div class="bus_con">
    	<c:if test="${cityCode=='010'&&billType=='002'}">
	        <div class="sel_box">
	            <input type="text" readonly placeholder="请选择缴费类型"/>
	            <div class="sel_list">
	                <ul>
	                    <li>智能电</li>
	                    <li>抄表电</li>
	                </ul>
	            </div>
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
        })
    })
    
    function next(){
    	var cityCode = '${cityCode}';
    	var billType = '${billType}';
    	var leixing = '';
    	if("010"==cityCode&&"002"==billType){
    		var type = $(".sel_box input").val();
    		if(""==type){
    			layer.msg("请选择缴费类型");
    			return false;
    		}
    		if("智能电"==type){
    			leixing = '3202';
    		}else if("抄表电"==type){
    			leixing = '3102';
    		}
    	}
    	var paynumber = $("#paynumber").val();
    	if(""==paynumber){
    		layer.msg("请填写缴费号");
			return false;
    	}
    	window.location.href = "${ctx}/index/queryPayNumberMsg?paynumber="+paynumber+"&shopCode="+leixing;
    }
</script>
</html>