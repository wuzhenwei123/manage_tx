<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <link rel="icon" type="image/png" href="http://cdn1.dayomall.com/img/logo.jpg">
    <title>吉云水电气</title>
    <script src="${ctx}/js/hotcss.js"></script>
	<link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
	<link rel="stylesheet" href="${ctx}/css/wx/base.css">
	<link rel="stylesheet" href="${ctx}/css/wx/icons.css">
	<link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css">
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    
</head>

<body style="">
    <div class="scroller-container">
        <div class="vm order-detail">
			<div class="mycard_list">
            	<ul>
            		<c:forEach items="${list}" var="bank">
            			<li onclick="delcard(${bank.id})">
	                        <div class="bank_pic">
	                            <img src="${ctx}/images/wx/bank.png" alt="">
	                        </div>
	                        <dl>
	                            <dt>${bank.accName}</dt>
	                            <dd>****<i></i>****<i></i>****<i></i>${bank.endCode}</dd>
	                        </dl>
	                    </li>
            		</c:forEach>
                </ul>
                
             <a href="${ctx}/weixin/toAddCard" class="btn_save">添加</a>
            </div>
            
        </div>
    </div>
    <script type="text/javascript">
    function delcard(id){
    	layer.msg('你确定要删除该卡吗？', {
    		  time: 0 //不自动关闭
    		  ,btn: ['确定', '取消']
    		  ,yes: function(index){
    			  $.post("${ctx}/weixin/delMyCard", {
    				  	id:id,
    		    		_t : Math.random()
    		    	}, function(data) {
    		    		var result = eval("(" + data + ")");
    		    		if (result.code == 1) {
    		    			layer.msg("删除成功");
    		    			setTimeout("window.location.reload()",3000);
    		    		}else{
    		    			layer.msg("删除失败");
    		    		}
    		    	});
    		  }
    		});
    }
    </script>
</body>
</html>