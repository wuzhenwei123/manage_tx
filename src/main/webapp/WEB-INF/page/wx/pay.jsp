<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <title>购电和缴费</title>
    <script src="${ctx}/js/hotcss.js"></script>
    <link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
    <link rel="stylesheet" href="${ctx}/css/wx/base.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons.css">
    <link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
    <link rel="stylesheet" href="${ctx}/css/wx/pay.css">
    <link rel="stylesheet" href="${ctx}/css/wx/login.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/weui.css">
    <link href="${ctx}/css/kefu.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/weui.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
    <style type="text/css">
    .tips {
    	height: 55px;
    }
    </style>
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
    <div class="scroller-container">
        <div class="vm order-detail">
           <c:set var="name_len" value="${fn:length(admin_user.realName)}"></c:set>
			<div class="tips">
                <p>请使用本人的微信支付或本人银行卡（暂不支持招商银行）</p>
			</div>
            <div class="pay_con">
            	<div class="pay_list">
                    <ul>
                        <li onclick="showPayWay()">
                            <label>缴费类型</label>
                            <input type="text" id="payway" value="智能表购电" readonly>
                            <a href="javascript:;" class="btn_way"></a>
                        </li>
                    </ul>
                </div>
                <div id="jiaofei" style="display: none;">
                <div class="pay_list" style="margin-top: 10px;">
                    <ul>
                        <li>
                            <label>姓名</label>
                            <input type="text" value="${fn:substring(admin_user.realName, 0, 1)}*" >
                        </li>

                        <li>
                            <label>地区</label>
                            <input type="text" value="北京市·北京市·东城区" placeholder="请选择地区" id="cascadePickerBtn" readonly="readonly" onclick="showlocal()">
                            <a href="javascript:showlocal();" class="btn_place"></a>
                        </li>
                    </ul>
                </div>
                <div class="pay_list">
                    <ul>
                        <li>
                            <label>支付金额</label>
                            <input type="text" placeholder="支付金额" value="" id="money" onkeyup="this.value=this.value.replace(/\D/gi,'')">
                        </li>
                        <li>
                            <label>银行卡号</label>
                            <c:if test="${not empty txWxUserBankNo}">
	                            <input type="text" value="****  ****  ****  ${txWxUserBankNo.endCode}" placeholder="信用卡/储蓄卡" id="accNo1" readonly="readonly" onclick="showCard()">
	                            <input type="hidden" value="${txWxUserBankNo.accNo}" placeholder="信用卡/储蓄卡" id="accNo" readonly="readonly" onclick="showCard()">
                            </c:if>
                            <c:if test="${empty txWxUserBankNo}">
	                            <input type="text" placeholder="信用卡/储蓄卡" id="accNo1" readonly="readonly" onclick="showCard()">
	                            <input type="hidden" placeholder="信用卡/储蓄卡" id="accNo" readonly="readonly" onclick="showCard()">
                            </c:if>
                            <a href="javascript:showCard();" class="btn_card"></a>
                        </li>
                    </ul>
                </div>
                <div class="register_list">
	                <ul>
	                    <li class="h50">
	                        <input type="text" placeholder="请输入验证码" id="smsCode" onkeyup="this.value=this.value.replace(/\D/gi,'')">
	                        <!-- <a href="javascript:;" class="btn_code">获取验证码</a> -->
	                        <a href="javascript:sendCode();" class="btn_code active" id="codeBtn">获取验证码</a>
	                    </li>
	                </ul>
	            </div>
                <div class="pay_way_box">
                    <div class="pay_way">选择收款方式</div>
                    <ul>
                        <li>
                            <dl>
                                <dt>
                                    <img src="${ctx}/images/wx/online_pay.png" alt="">在线支付
                                </dt>
                                <dd>
                                    <span>额度：100~20000元/笔</span><em>手续费：0.48%+2元/笔</em>
                                </dd>
                            </dl>
                            <a href="javascript:;" class="btn_online_pay"></a>
                        </li>
<!--                         <li> -->
<!--                             <dl> -->
<!--                                 <dt> -->
<!--                                     <img src="${ctx}/images/wx/wx_pay.png" alt="">微信收款 -->
<!--                                 </dt> -->
<!--                                 <dd> -->
<!--                                     <span>额度：0~20000元/笔</span><em>手续费：0.30%</em> -->
<!--                                 </dd> -->
<!--                             </dl> -->
<!--                             <a href="javascript:;" class="btn_wx_pay"></a> -->
<!--                         </li> -->
                    </ul>
                </div>
            </div>
           </div>
           <div class="pay_way_list mask">
                <div class="dalog">
                    <ul>
                    	<li class="active">
                            <span><i></i></span>
                            	请选择 银行卡
                        </li>
                    	<c:forEach items="${list}" var="bank">
                    		<li id="${bank.accNo}">
	                            <span><i></i></span>
	                            ****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;${bank.endCode}
	                        </li>
                    	</c:forEach>
                    </ul>
                </div>
            </div>
            
            <div class="pay_way_list1 mask">
                <div class="dalog">
                    <ul>
                    	<li class="active">
                            <span><i></i></span>
                            	请选择缴费类型
                        </li>
                        <li>
                            <span><i></i></span>
                           	智能表购电
                        </li>
                        <li>
                            <span><i></i></span>
                           	抄表电缴费
                        </li>
                        <li>
                            <span><i></i></span>
                           	 批发电费
                        </li>
                    </ul>
                </div>
            </div>
            
            <a href="javascript:tijiao();" class="btn_cer">提交</a>
        </div>
    </div>
    <div class="side">
		<ul>
			<li><a href="javascript:closeWin();"><div class="sidebox" style="font-size: 10px;text-align: center;">在线客服</div></a></li>
		</ul>
	</div>
</body>
<script  type="text/javascript" src="${ctx}/js/location_p.js"  charset="gb2312"></script>
<script>
    $(function(){
        $(".btn_way").on("click",function(){
            $(".pay_way_list1").show();
        })
        $(".mask").on("click",function(){
            $(".mask").hide();
        })
        $(".pay_way_list li").on("click",function(){
            $(this).addClass('active').siblings('li').removeClass('active');
            if($(this).index() == 0){
                $("#accNo1").val('');
                $("#accNo").val('');
            }else{
                $("#accNo1").val($.trim($(this).text()));
                $("#accNo").val($.trim($(this).attr("id")));
            }
        })
        
        $(".pay_way_list1 li").on("click",function(){
            $(this).addClass('active').siblings('li').removeClass('active');
            if($(this).index() == 0){
                $("#payway").val('');
            }else{
                $("#payway").val($.trim($(this).text()));
                if($.trim($(this).text())=="批发电费"){
                	var admin_user = '${admin_user}';
                	if(admin_user!=""){
                		var state = '${admin_user.state}';
                		var checkState = '${admin_user.checkState}';
                		if(state=="1"&&checkState=="1"){
    	                	$("#jiaofei").show();
                		}else{
                			layer.msg("请联系业务员加盟");
                		}
                	}else{
                		layer.msg("请联系业务员加盟");
                	}
                }else{
                	$("#jiaofei").hide();
                }
            }
        })


    })
    
    function showCard(){
    	$(".pay_way_list").show();
    }
    function showPayWay(){
    	$(".pay_way_list1").show();
    }
    
     // 地区
// 	document.querySelector('#cascadePickerBtn').addEventListener('click', function () {
	    
// 	});
     
    function showlocal(){
    	weui.picker(location_p, {
	        depth: 3,
	        defaultValue: [110000, 110100, 110101],
	        onChange: function onChange(result) {
// 	            console.log(result);
	        },
	        onConfirm: function onConfirm(result) {
	        	var provinceCode = result[0];
	        	var cityCode = result[1];
	        	var areaCode = result[2];
	        	var html = "";
	        	for(var i =0;i<location_p.length;i++){
	        		if(location_p[i].value==provinceCode){
	        			html = location_p[i].label;
	        			var children_c = location_p[i].children;
	        			for(var j =0;j<children_c.length;j++){
	        				if(children_c[j].value==cityCode){
	        					html = html + "·" + children_c[j].label;
	        					var children_a = children_c[j].children;
	    	        			for(var k =0;k<children_a.length;k++){
	    	        				if(children_a[k].value==areaCode){
	    	        					html = html + "·" + children_a[k].label;
	    	        				}
	    	        			}
	        				}
	        			}
	        		}
	        	}
	        	$("#cascadePickerBtn").val(html);
	        },
	        id: 'cascadePicker'
	    });
    }
    
    var flag = true;
    function tijiao(){
    	var payway = $("#payway").val();
    	if(payway=="智能表购电"){
    		window.location.href = "http://weixin.chinaepay.com/redirect_uri.jsp?state=s,intelligentPower";
    	}else if(payway=="抄表电缴费"){
    		window.location.href = "http://weixin.chinaepay.com/redirect_uri.jsp?state=s,meterReading";
    	}else{
    		//检查用户是否认证
    		var admin_user = '${admin_user}';
    		var state = '${admin_user.state}';
    		var checkState = '${admin_user.checkState}';
    		if(admin_user!=""&&state=="1"&&checkState=="1"){
            	var cascadePickerBtn = $("#cascadePickerBtn").val();
            	if(cascadePickerBtn==""){
            		layer.msg("请选择地区");
            		return false;
            	}
            	
            	var money = $("#money").val();
            	if(money==""){
            		layer.msg("请输入金额");
            		return false;
            	}
            	
            	var accNo = $("#accNo").val();
            	if(accNo==""){
            		layer.msg("请输入银行卡号");
            		return false;
            	}
            	
            	var smsCode = $("#smsCode").val();
            	if(smsCode==""){
            		layer.msg("请输入验证码");
            		return false;
            	}
            	
            	if(flag){
            		flag = false;
            		var index = layer.load(1, {
          			  shade: [0.6,'#666666'] //0.1透明度的白色背景
          			});
           			$.post("<c:url value='/weixin/pay'/>",
           	       	{
           				money :money,
           				accNo :accNo,
           				smsCode :smsCode,
           				ranNum:Math.random()
           			},
           	       	function(data){
           				flag = true;
           				layer.close(index);
           	        	var result = eval('('+data+')'); 
           	            if (result.code == '1') {
           	            	layer.msg(result.message);
           	            	setTimeout("toMyOrder()",3000);
           	             } else {
           	            	layer.msg(result.message);
           	             }
           	        });
            	}
    		}else{
    			layer.msg("请联系业务员加盟");
    		}
    	}
    }
    
    var dxflag = true;//防止短信重复发送
	function sendCode(){
		var money = $("#money").val();
    	if(money==""){
    		layer.msg("请输入金额");
    		return false;
    	}
    	
    	var accNo = $("#accNo").val();
    	if(accNo==""){
    		layer.msg("请输入银行卡号");
    		return false;
    	}
		if(dxflag){
			dxflag = false;
			var index = layer.load(1, {
   			  shade: [0.6,'#666666'] //0.1透明度的白色背景
   			});
			$.post("<c:url value='/weixin/vercode'/>",
	       	{
				money :money,
				accNo :accNo,
				ranNum:Math.random()
			},
	       	function(data){
				dxflag = true;
				layer.close(index);
	        	var result = eval('('+data+')'); 
	            if (result.code == '1') {
	            	layer.msg("发送成功，请注意查收验证码");
	            	btnNum();
	             } else {
	            	layer.msg(result.message);
	             }
	        });
		}
	}
	function btnNum(){
		var num = 59;
		$("#codeBtn").attr('disabled',true);
		$("#codeBtn").removeClass('active');
		$("#codeBtn").html('还剩'+num-- +'秒');
		var intervalID = setInterval(function(){
			if(num>0){
				$("#codeBtn").attr('disabled',true);
				$("#codeBtn").attr('onclick','');
				$("#codeBtn").html('还剩'+num+'秒');
				num--;
			}else{
				$("#codeBtn").html('发送验证码');
				$("#codeBtn").addClass('active');
				$("#codeBtn").attr('disabled',false);
				$("#codeBtn").attr('onclick','sendCode()');
				clearInterval(intervalID)
			}
		}, 1000);
	}
	
	function validatemobile(mobile){ 
		if (mobile.length == 0) {
			return false;
		}
		if (mobile.length != 11) {
			return false;
		}
		var myreg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if (!myreg.test(mobile)) {
			return false;
		}
		return true;
	}
	
	function toMyOrder(){
		window.location.href = "${ctx}/weixin/toMyOrder?openId=${admin_user.openId}";
	}
	function closeWin(){
		WeixinJSBridge.call('closeWindow');
		$.get("${ctx}/kefu/openKF",function(data){
			
		})
	}
</script>
</html>