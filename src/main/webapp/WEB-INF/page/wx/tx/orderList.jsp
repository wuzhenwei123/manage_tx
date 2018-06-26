<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <title>吉云水电气</title>
    <script src="${ctx}/js/hotcss.js"></script>
    <link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
    <link rel="stylesheet" href="${ctx}/css/wx/base.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
    <link rel="stylesheet" href="${ctx}/css/wx/card.css?t=<%=Math.random()%>">
    <link rel="stylesheet" href="${ctx}/css/wx/tx/index.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weui.css">
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/weui.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
    <div class="scroller-container">
        <div class="vm order-detail">
            <div class="deal_box" style="height: 120px;">
                <dl style="font-size: 0.5rem;">
                    <dt style="text-align: left;padding-left: 10px;padding-top: 25px;">亲爱的合伙人，您的售卡交易共<em id="allCount">0</em>笔，已结算金额￥<em id="allMoney">0</em>元，还有￥<em id="money">0</em>的金额正在为您赚钱中...</dt>
                    <dt style="padding-top: 10px;padding-left: 10px;text-align: left;">您还可以点击以下交易查看详情：</<dt>
                </dl>
            </div>
			<div class="deal_list" style="margin-top:0px;">
            	<dl>
                    <dt>
                        <ul class="clearfix">
                            <li>交易日期</li>
                            <li style="width: 26%;">售卡周期(月)</li>
                            <li>结算日期</li>
                            <li>状态</li>
                            <li>结算金额</li>
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
	var itemTpl = '<dd onclick="showDetail({id})"><ul class="clearfix"><li>{createTimeStr}</li><li style="width: 26%;">1</li><li>{endTimeStr}</li><li>{state}</li><li>{money}</li></ul></dd>';
	var itemTpl2 = '<dd onclick="showDetail({id})"><ul class="clearfix"><li>{createTimeStr}</li><li style="width: 26%;">1</li><li>{endTimeStr}</li><li>{state}</li><li>{profitManey}</li></ul></dd>';
	var itemTpl1 = '<dd onclick="showDetail({id})"><ul class="clearfix"><li>{createTimeStr}</li><li style="width: 26%;">0</li><li>{endTimeStr}</li><li>{state}</li><li>{money}</li></ul></dd>';
	/**
	 * 载入内容
	 * @return {[type]} [description]
	 */
	function loadData(page) {
		$.get("${ctx}/unionpay/getOrderListByCustumer?rowCount=10&pageNo="+page+"&_t="+Math.random()+"&wxUserId=${wxUser.id}",function(data,status){
			var json = eval("("+data+")");
			$("#allCount").html(json.allCount);
			$("#allMoney").html(json.allMoney);
			$("#money").html(json.money);
			if(json.message=="ok"){
				$(".loading").remove();
				var testData = json.items
				for(var i in testData) {
					if("可结算"==testData[i].state){
						$("#zd-item").append(renderTpl(itemTpl1, testData[i]));
					}else if("未到期"==testData[i].state){
						$("#zd-item").append(renderTpl(itemTpl, testData[i]));
					}else if("已结算"==testData[i].state){
						$("#zd-item").append(renderTpl(itemTpl2, testData[i]));
					}
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
	
	function showDetail(id){
		window.location.href = "${ctx}/unionpay/toOrderDetail?id="+id;
	}
</script>
</html>