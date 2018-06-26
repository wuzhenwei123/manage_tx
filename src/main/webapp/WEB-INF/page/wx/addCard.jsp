<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="${ctx}/css/wx/card.css">
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>	
    <div class="scroller-container">
        <div class="vm order-detail">
           
<!-- 			<div class="add_card"> -->
<!-- 				<a href="javascript:tijiao();"><img src="${ctx}/images/add.png" alt="">添加信用卡</a> -->
<!-- 			</div> -->
			
			 <div class="add_card_box" style="display: block;">
                <div class="card_top">
                	<ul>
	                    <li>
	                        <label>卡号</label><input style="width: 180px;" type="text" placeholder="请输入银行卡号" id="accNo" onkeyup="this.value=this.value.replace(/\D/gi,'')">
	                    </li>
<!-- 	                    <li> -->
<!-- 	                        <label>银行</label><input type="text" id="bank" name="" placeholder="请输入银行名称" value="" readonly> -->
<!-- 	                        <a href="javascript:;" class="add_bank"></a> -->
<!-- 	                    </li> -->
<!-- 	                    <li> -->
<!-- 	                        <label>姓名</label><input type="text" name="" placeholder="请输入姓名" value="" id="name"> -->
<!-- 	                    </li> -->
	                </ul>
                </div>
<!--                 <div class="card_bot"> -->
<!--                 	<ul> -->
<!-- 	                    <li> -->
<!-- 	                        <label>还款提醒</label> -->
<!--                             <a href="javascript:;" class="btn_remind"><i></i></a> -->
<!-- 	                    </li> -->
<!-- 	                    <li> -->
<!-- 	                        <label>还款日</label><input type="text" id="time" name="" placeholder="每月1日" value="" readonly> -->
<!--                             <a href="javascript:;" class="add_time"></a> -->
<!-- 	                    </li> -->
<!-- 	                </ul> -->
<!--                 </div> -->

                <a href="javascript:tijiao();" class="btn_save">确定</a>
            </div>

            <div class="bank_list mask">
                <div class="dalog">
                    <ul>
                        <li class="active">
                            <span><i></i></span>
                            请选择银行名称
                        </li>
                        <li>
                            <span><i></i></span>
                            邮政储蓄银行1
                        </li>
                        <li>
                            <span><i></i></span>
                            邮政储蓄银行2
                        </li>
                        <li>
                            <span><i></i></span>
                            邮政储蓄银行3
                        </li>
                        <li>
                            <span><i></i></span>
                            邮政储蓄银行
                        </li>
                        
                    </ul>
                </div>
            </div>

            <div class="time_list mask">
                <div class="dalog">
                    <ul>
                        <li class="active">
                            <span><i></i></span>
                            每月1日
                        </li>
                        <li>
                            <span><i></i></span>
                            每月5日
                        </li>
                        <li>
                            <span><i></i></span>
                            每月10日
                        </li>
                        <li>
                            <span><i></i></span>
                            每月15日
                        </li>
                        <li>
                            <span><i></i></span>
                            每月20日
                        </li>
                        
                    </ul>
                </div>
            </div>


        </div>
    </div>
</body>
<script>
    $(function(){
        //
//         $(".add_card").on("click",function(){
//             $(this).hide().siblings('.add_card_box').show();
//             $("title").text("添加信用卡")
//         })


        //add bank
//         $(".add_bank").on("click",function(){
//             $(".bank_list").show();
//         })
//         $(".mask").on("click",function(){
//             $(".mask").hide();
//         })
//         $(".bank_list li").on("click",function(){
//             $(this).addClass('active').siblings('li').removeClass('active');
//             if($(this).index() == 0){
//                 $("#bank").val('');
//             }else{
//                 $("#bank").val($.trim($(this).text()));
//             }
//         })

//         滑块
//         $(".btn_remind").on("click",function(){
//             $(this).toggleClass('active');
//         })

//         add time
//         $(".add_time").on("click",function(){
//             $(".time_list").show();
//         })
//         $(".time_list li").on("click",function(){
//             $(this).addClass('active').siblings('li').removeClass('active');
//             $("#time").val($.trim($(this).text()));
//         })
    })
    
    var flag = true;
    function tijiao(){
    	var accNo = $("#accNo").val();
    	if(accNo==""){
    		layer.msg("请输入卡号");
    		return false;
    	}
    	if(flag){
    		flag = false;
    		var index = layer.load(1, {
  			  shade: [0.6,'#666666'] //0.1透明度的白色背景
  			});
    		$.post("<c:url value='/weixin/checkCard'/>",
	       	{
				accNo :accNo,
				ranNum:Math.random()
			},
	       	function(data){
				flag = true;
				layer.close(index);
	        	var result = eval('('+data+')'); 
	            if (result.code == '1') {
	            	location.href = "${ctx}/weixin/addCard?accNo="+accNo+"&_t="+Math.random();
	             } else {
	            	layer.msg(result.message);
	             }
	        });
    	}
    }
</script>
</html>