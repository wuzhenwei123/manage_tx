<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<html>
	<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="#e8447e">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" CONTENT="-1">           
    <meta http-equiv="Cache-Control" CONTENT="no-cache">           
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta name="description" content="">
    <meta name="Keywords" content="">
    <title>交易记录</title>
    
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/customForm.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/comm.css">
    <link rel="stylesheet" href="${ctx}/css/wx/mobiscroll_date.css" />
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    
	<script src="${ctx}/js/mobiscroll_date.js"></script>
	<script src="${ctx}/js/mobiscroll.js"></script>
</head>
<body class="body">
<div class="box">
	<%@ include file="/WEB-INF/page/common/share1.jsp" %>
    <div class="topBox"><a class=" offen_back" href="javascript:history.go(-1);"></a>${wxUser.realName}的交易记录</div>
    
    <div class="bsBox marginTop16" style="display: none;">
    	<div class="searchBox">查询<a id="searchIcon" class="searchIcon" href="javascript:void(0);"></a></div>
        <div id="searchBoxItem" class="searchBoxItem" style="height:0;">
            <div class="itemsContentBox">
                
                <div class="">
                    <input class="input" type="text" placeholder="订单号" id="orderCode"/>
                </div>
                <div class="searItems">
                    <div class="fl searItem">
                        <input class="input" type="text" placeholder="起始日期" id="startTime"/>
                    </div>
                    <div class="fr searItem">
                        <input class="input" type="text" placeholder="结束日期" id="endTime"/>
                    </div>
                </div>
            </div>
            <div class="itemsContentBox">
                <a href="javascript:page = 1;searchDate(1);" class="btn btn_primary searBtnBox">查询</a>
            </div>
        </div> 
    </div>
    <div class="ch_ui_box bsBox marginTop16">
        <div class="ui_cell ui_cells">
            <div class="ui_center ui_cell_flex jls">记录条数</div>
            <div class="ui_cell_ft"><font class="" id="allCount"></font></div>
        </div>
    </div>
    <div class="bsBox ItemLi bot">
    	<ul class="clearAfter ItemuL">
			<li class="fl">交易总额</li>
            <li class="fr right"><font id="allDetailFee"></font></li>        
        </ul>
    </div>
    <div class="bsBox ItemLi bot">
    	<ul class="clearAfter ItemuL">
			<li class="fl">佣金总额</li>
            <li class="fr right"><font id="totalMoneyRate"></font></li>        
        </ul>
    </div>
	<div id="zd-item"></div>   
</div>
<script src="${ctx}/js/select.js"></script>
<script>
	var stop=true;
	var page = 1;
	$(function(){
		$(document).on("click",".searchBox",function(){
			var h = 0;
			if($(this).hasClass("on")){
				$(this).removeClass("on");
				
			} else {
				$(this).addClass("on");
				h =  245;
			}
			$("#searchBoxItem").animate({"height": h + "px"},300);
		});
		var currYear = (new Date()).getFullYear();
        var opt = {};
        opt.date = {
            preset: 'date'
        };
        opt.datetime = {
            preset: 'datetime'
        };
        opt.time = {
            preset: 'time'
        };
        opt.default = {
            theme: 'android-ics light', //皮肤样式
            dateFormat: 'yyyy-mm-dd',
            showNow: true,
            nowText: "今天",
            setText: '确定', //确认按钮名称
            cancelText: '取消',//取消按钮名籍我
            dateOrder: 'yymmdd', //面板中日期排列格式
            dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
            startYear: currYear - 50, //开始年份
            endYear: currYear + 10 //结束年份
        };
        
        $("#startTime").mobiscroll($.extend(opt['date'], opt['default']));
        $("#endTime").mobiscroll($.extend(opt['date'], opt['default']));
//         loading();
		loadData(page);
	})
	
// 	$('.diy_select_list li').each(function(index, obj) {
// 		if ($(obj).hasClass('focus')) {
// 			console.log(obj)
// 		}
// 	})
	
	$(window).on("scroll", function() {
		totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	    if($(document).height() <= totalheight){
	        if(stop){
	            stop=false;
	            loading();
	            page = parseInt(page)+1;
	            loadData(page);
	        }
	    }
		
		var st = $(document).scrollTop();
		if(st > 52) {
			$(".search-box-new").addClass("fixed");
		} else {
			$(".search-box-new").removeClass("fixed");
		}
	});
	var itemTpl = '<div class="bsBox ItemLi zhmx marginTop16">';
	itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">订单号</li><li class="fr"><font>{orderCode}</font></li></ul>';
// 	itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">银行卡号</li><li class="fr"><font>{accNo}</font></li></ul>';
	itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">交易金额</li><li class="fr"><font>{money}</font></li></ul>';
	itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">消费状态</li><li class="fr"><font>{xfState}</font></li></ul>';
	itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">代付状态</li><li class="fr"><font>{dfState}</font></li></ul>';
	itemTpl = itemTpl + '<ul class="clearAfter ItemuL zhmxBox"><li class="fl">{createTimeStr}</li><li class="fr right"><span class="money">+{rate}</span></li></ul>';
	itemTpl = itemTpl + '<span class="clear"></span></div>';	
	/**
	 * 载入内容
	 * @return {[type]} [description]
	 */
	function loadData(page) {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var orderCode =$("#orderCode").val();
		$.get("${ctx}/weixin/getOrderList?rowCount=10&pageNo="+page+"&_t="+Math.random()+"&wxUserId=${wxUser.id}&startTime="+startTime+"&endTime="+endTime+"&orderCode="+orderCode,function(data,status){
			var json = eval("("+data+")");
			$("#allCount").html(json.totalResults);
			$("#allDetailFee").html(json.totalMoney);
			$("#totalMoneyRate").html(json.totalMoneyRate);
// 			if(json.message=="ok"){
// 				$(".loading").remove();
// 				var testData = json.items
// 				for(var i in testData) {
// 					$("#zd-item").append(renderTpl(itemTpl, testData[i]));
// 				}
// 				stop = true;
// 			}else if(json.message=="end"){
// 				$(".loading").remove();
// 				layer.msg("已到底了");
// 			}
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
	    $("#zd-item").append('<div class="loading"><img src="${ctx}/images/loading.gif" alt=""></div>');
	}
</script>
</body>
</html>
