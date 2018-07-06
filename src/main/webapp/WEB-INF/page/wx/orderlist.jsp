<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <script src="${ctx}/js/hotcss.js"></script>
    <link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
    <link rel="stylesheet" href="${ctx}/css/wx/base.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css?t=<%=Math.random()%>">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weui.css">
    <link rel="stylesheet" href="${ctx}/css/wx/login.css">
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/weui.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    <style type="text/css">
    .deal_list dl li {
	    float: left;
	    width: 25%;
	    text-align: center;
	}
    </style>
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
    <div class="scroller-container">
        <div class="vm order-detail">
            <div class="deal_box">
                <dl>
                    <dt style="margin-left: 20px;padding-top: 10px;">温馨告知：<br/>
						1、如您需要提前退款，请点击该笔交易的详情。<br/>
						2、预批电费的收益及提前退款可能产生的手续费请参阅合伙人协议有关条款。
					</dt>
                </dl>
            </div>
			<div class="deal_list">
            	<dl>
                    <dt>
                        <ul class="clearfix">
                            <li>交易时间</li>
                            <li>交易金额</li>
                            <li>交易状态</li>
                            <li>详情</li>
                        </ul>
                    </dt>
                    <div id="zd-item">
                    	
                    </div>
                </dl>
            </div>
            <div class="page_box">
                <ul class="clearfix">
                    <li onclick="searchDate(1)">首页</li>
                    <li onclick="pro()">上一页</li>
                    <li onclick="next()">下一页</li>
                    <li onclick="end()">尾页</li>
                </ul>
            </div>
            
            <a href="javascript:toPay();" class="btn_cer">再赚一笔</a>
            <a href="javascript:closeWin();" class="btn_cer" style="margin-top: 10px;">返回</a>
        </div>
    </div>
</body>
<script>
	var stop=true;
	var page = 1;
    $(function(){
    	loading();
		loadData(page);
    })
    
//     $(window).on("scroll", function() {
// 		totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
// 	    if($(document).height() <= totalheight){
// 	        if(stop){
// 	            stop=false;
// 	            loading();
// 	            page = parseInt(page)+1;
// 	            loadData(page);
// 	        }
// 	    }
// 	});
	var itemTpl = '<dd onclick="detail({id})"><ul class="clearfix"><li>{createTimeStr1}</li><li>{money}</li><li style="color:red;">{state}</li><li>>></li></ul></dd>';
	/**
	 * 载入内容
	 * @return {[type]} [description]
	 */
	function loadData(page) {
		$.get("${ctx}/weixin/getOrderListByCustumer?rowCount=10&pageNo="+page+"&_t="+Math.random()+"&wxUserId=${wxUser.id}",function(data,status){
			var json = eval("("+data+")");
			if(json.message=="ok"){
				$(".loading").remove();
				var testData = json.items
				for(var i in testData) {
					$("#zd-item").append(renderTpl(itemTpl, testData[i]));
				}
// 				stop = true;
			}else if(json.message=="end"){
				$(".loading").remove();
				layer.msg("已到底了");
			}
		});
	}
	
	
	function renderTpl(tpl, op) {
		return tpl.replace(/\{(\w+)\}/g, function(e1, e2) {
			if('amount'==e2){
				return op[e2] != null ? (eval(op[e2])/100) : "";
			}else{
				return op[e2] != null ? op[e2] : "";
			}
			
		});
	}
	
	function searchDate(pages){
		$("#zd-item").empty();
		loading();
		loadData(pages);
	}
	
	function loading() {
	    $("#zd-item").append('<div class="loading" style="text-align: center;"><img src="${ctx}/images/loading.gif" alt=""></div>');
	}
	
	function next(){
        page = parseInt(page)+1;
        searchDate(page);
	}
	function pro(){
        page = parseInt(page)-1;
        if(page<=0){
        	page = 1;
        }
        searchDate(page);
	}
	function end(){
		var totals = parseInt(parseInt($("#allCount").html())/10);
		var yu = parseInt($("#allCount").html())%10;
		if(yu>0){
	        page = totals+1;
		}
        searchDate(page);
	}
	
	function detail(id){
		window.location.href = "${ctx}/other/toSellOrderDetail?id="+id;
	}
	
	function toPay(){
		window.location.href = "${ctx}/index/toRecharge";
	}
	
	function closeWin(){
		window.location.href='${ctx}/weixin/dbIndex?openId=${admin_user.openId}';
	}
</script>
</html>