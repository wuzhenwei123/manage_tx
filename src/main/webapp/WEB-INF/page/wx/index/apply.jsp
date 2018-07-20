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
    <div class="bus_top">
        <p>可提现金额: <i>${yuFee}</i></p>
    </div>
    <div class="bus_con">
        <div class="num_box">
            <p>请输提现金额</p>
            <input type="text" id="fee">
        </div>
    </div>

    <div class="btn_boxs">
        <a href="javascript:next();">确定</a>
    </div>
</div>
</body>
<script>
	var flag = true;
    function next(){
    	var yuFee = '${yuFee}';
    	var fee = $("#fee").val();
    	var reg = /^\d+(?:.\d{1,2})?$/;
    	if(""==fee){
    		layer.msg("请填写金额");
			return false;
    	}else if(!reg.test(fee)){
    		layer.msg("请输入小于"+yuFee+"的金额（允许两位小数）");
			return false;
    	}else if(parseFloat(fee) > parseFloat(yuFee)){
    		layer.msg("请输入小于"+yuFee+"的金额（允许两位小数）");
    		return false;
    	}
    	if(flag){
    		flag = false;
    		$.post("<c:url value='/other/apply'/>",
   	       	{
    			fee :fee,
   			},
   	       	function(data){
   	        	var result = eval('('+data+')'); 
   	            if (result.code == '1') {
   	            	location.href = "${ctx}/other/toMyYongjin";
   	             } else {
   	            	flag = true;
   	            	layer.msg(result.data);
   	             }
   	        });
    	}
    }
</script>
</html>