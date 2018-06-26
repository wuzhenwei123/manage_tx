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
    <title>我的客户</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/customForm.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/wx/comm.css">
    <link href="${ctx}/css/wx/weui.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>
<body class="body">
<div class="box">
	<%@ include file="/WEB-INF/page/common/share1.jsp" %>
    <div class="topBox"><a class=" offen_back" href="javascript:history.go(-1);"></a>我的客户</div>
    
    <div class="bsBox marginTop16">
    	<div class="searchBox" id="searchBox">查询<a id="searchIcon" class="searchIcon" href="javascript:void(0);"></a></div>
        <div id="searchBoxItem" class="searchBoxItem" style="height:0;">
            <div class="itemsContentBox">
                <div class="searItems sel">
                    <div class="fl searItem" style="width: 100%;padding-right:0px;">
                        <dl class="input" style="overflow:visible;">
                            <dd class="line">
                                <div class='diy_select select1'>
                                    <input type='hidden' id='state' class='diy_select_input'/>
                                    <div class='diy_select_txt'>审核状态</div>
                                    <div class='diy_select_btn'></div>
                                    <ul class='diy_select_list'>
                                        <li>全部</li>
                                        <li>未审核</li>
                                        <li>已审核</li>
                                        
                                    </ul>
                                </div>
                            </dd>
                        </dl>
                    </div>
                    <span class="clear"></span>
                </div>
                <div class="searItems">
                    <div class="fl searItem">
                        <input class="input" type="text" placeholder="客户姓名" id="realName"/>
                    </div>
                    <div class="fr searItem">
                        <input class="input" type="text" placeholder="客户手机" id="mobile"/>
                    </div>
                </div>
                
            </div>
            <div class="itemsContentBox">
                <a href="javascript:searchDate(1)" class="btn btn_primary searBtnBox">查询</a>
            </div>
        </div> 
    </div>
    
    
    <div class="ch_ui_box bsBox marginTop16">
        <div class="ui_cell ui_cells">
            <div class="ui_center ui_cell_flex jls">记录条数</div>
            <div class="ui_cell_ft"><font class="" id="fansCount"></font></div>
        </div>
        <div class="ui_cell ui_cells">
            <div class="ui_center ui_cell_flex jls">交易总金额</div>
            <div class="ui_cell_ft"><font class="" id="totalMoney"></font></div>
        </div>
        <div class="ui_cell ui_cells">
            <div class="ui_center ui_cell_flex jls">佣金总金额</div>
            <div class="ui_cell_ft"><font class="" id="oneRate"></font></div>
        </div>
    </div>
	
	<div id="zd-item">
		
	</div>    
    
    
</div>
<script src="${ctx}/js/select.js"></script>
<script>
	var stop=true;
	var page = 1;
	$(function(){
		stop=false;
		$(document).on("click","#searchBox",function(){
			var h = 0;
			if($(this).hasClass("on")){
				$(this).removeClass("on");
				
			} else {
				$(this).addClass("on");
				h =  210;
			}
			$("#searchBoxItem").animate({"height": h + "px"},300);
		});
		
        loading();
		loadData(page);
		
		
	})
	
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
	
	
	/**
	 * 载入内容
	 * @return {[type]} [description]
	 */
	function loadData(page) {
		var realName = $("#realName").val();
		var mobile =$("#mobile").val();
		var state =$("#state").val();
		if(!state){
			state='';
		}else if(state=="全部"){
			state='';
		}else if(state=="未审核"){
			state='0';
		}else if(state=="已审核"){
			state='1';
		}
		$.get("${ctx}/weixin/getMyCustomerSell?pageNo="+page+"&_t="+Math.random()+"&openId=${openId}&state="+state+"&mobile="+mobile+"&realName="+realName,function(data,status){
			var json = eval("("+data+")");
			if(json.message=="ok"){
				$("#fansCount").html(json.totalResults);
				$("#totalMoney").html(json.totalMoney);
				$("#oneRate").html(json.oneRate);
				$(".loading").remove();
				var testData = json.items
				for(var i in testData) {
					
					var itemTpl = '<div class="bsBox ItemLi marginTop16"><div>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">客户手机号</li><li class="fr">{mobile}</li></ul>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">客户名称</li><li class="fr">{realName}</li></ul>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">审核状态</li><li class="fr">{state_name}</li></ul>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">验证状态</li><li class="fr">{checkStateName}</li></ul>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">失败原因</li><li class="fr">{msg}</li></ul>';
					itemTpl = itemTpl + '<ul class="clearAfter ItemuL"><li class="fl">注册日期</li><li class="fr">{createTime}</li></ul>';
					itemTpl = itemTpl + '</div><div class="itemsContentBox lisBtnBox">';
					if(testData[i].state=="0"&&testData[i].checkState=="1"){
						itemTpl = itemTpl + '<a href="javascript:void(0);" onclick="setState({id})" style="margin-top: 0px;margin-left: 5px;" class="fr btn btn_primary listBtn">审核</a>';
					}
					itemTpl = itemTpl + '<a href="javascript:void(0);" onclick="showOrder({id})" style="margin-top: 0px;margin-left: 5px;" class="fr btn btn_primary listBtn">查看交易记录</a>';
// 					itemTpl = itemTpl + '<a href="javascript:void(0);" onclick="showInfo({id})" style="margin-top: 0px;margin-left: 5px;" class="fr btn btn_primary listBtn">查看证件</a>';
					itemTpl = itemTpl + '</div></div>';
					
					$("#zd-item").append(renderTpl(itemTpl, testData[i]));
				}
				stop = true;
			}else if(json.message=="end"){
				$(".loading").remove();
			}
		});
	}
	
	
	function renderTpl(tpl, op) {
		return tpl.replace(/\{(\w+)\}/g, function(e1, e2) {
			return op[e2] != null ? op[e2] : "";
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
	
	function showInfo(id){
		window.location.href = '${ctx}/weixin/showInfo?id='+id;
	}
	
	function setState(id){
		if (id != ""){
			$.post("<c:url value='/txWxUser/setState'/>",
        	{
				id	:id,
				state	:1,
				ranNum:Math.random()
			},
        	function(data){
	        	var result = eval('('+data+')'); 
	            if (result.code == '1') {
	            	layer.msg(result.message);
	            	setTimeout(searchDate(1),2000);
	             } else {
	            	layer.msg(result.message);
	             }
	        });
   	  	}
	}
	
	function showOrder(id){
		window.location.href = '${ctx}/weixin/showDBOrder?id='+id;
	}
</script>
</body>
</html>
