<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>吉云水电气</title>
    <link rel="stylesheet" href="${ctx}/css/wx/tx/index.css">
</head>
<body>
<%@ include file="/WEB-INF/page/common/share2.jsp"%>	
<div class="top" style="background: #3f9bfe">
    批卡金额与售卡周期选择
</div>
<div class="wrap">
    <h5>请选择批卡总金额及委托售卡周期：</h5>
    <div class='period_box'>
        <ul>
            <li class='clear sl_list active'  data-id='1'>
                <a href="javascript:;"></a>
                <em>10000元及一个月售完（推荐）</em>
                <i>说明：收益年化4.5%</i>
            </li>
            <li class='clear sl_list'  data-id='2'>
                <a href="javascript:;"></a>
                <em>10万元以上长期合作</em>
                <i>说明：我们将留下您的联系方式并派专职客户经理登门与您签约。</i>
            </li>
            <li class='clear sl_list' data-id='3'>
                <a href="javascript:;"></a>
                <em>购买其他金额及不定期退卡</em>
                <input type="number" id="othernumber" onkeyup="this.value=this.value.replace(/\D/gi,'')">
            </li>
        </ul>
        <div class="clear sel_box">
            <a href="javascript:;" class="sel_btn"></a>
            <div class="fl">
                我同意
                <a href="#">《购卡及不定期委托退卡协议》</a>
            </div>
        </div>
    </div>
</div>
<div class="btn_box">
    <a href="javascript:;">确认</a>
</div>
</body>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/weui.min.js"></script>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>
    $(function () {
        var selid=1;
        $(".sl_list").on("click",function () {
            $(this).addClass("active").siblings().removeClass("active");
            selid=$(this).attr("data-id");
            if($(this).attr("data-id")=="2"){
            	$(".sel_btn").removeClass("active");
            	 layer.open({
           		  type: 2,
           		  title: '联系方式',
           		  area: ['80%', '50%'],
           		  content: ['${ctx}/unionpay/toRecordBigManey', 'no']
           		});
            }else if($(this).attr("data-id")=="3"){
            	$(".sel_btn").addClass("active");
            }else{
            	$(".sel_btn").removeClass("active");
            }
        })

//         $(".sel_btn").on("click",function () {
//             $(this).toggleClass("active")
//         })
		
        $(".btn_box a").on("click",function () {
            if(selid == 3){
            	window.location.href = "${ctx}/unionpay/toSellOrder?backCard=1&money="+$("#othernumber").val();
            }else if(selid == 2){
            	 layer.open({
            		  type: 2,
            		  title: '联系方式',
            		  area: ['80%', '50%'],
            		  content: ['${ctx}/unionpay/toRecordBigManey', 'no']
            		});
            }else{
            	window.location.href = "${ctx}/unionpay/toSellOrder?backCard=0&money=10000";
            }
        })
    })
</script>
</html>