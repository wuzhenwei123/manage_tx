<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp"%>
<html lang="zh-cn" data-dpr="1" max-width="540"
	style="font-size: 25.875px;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta name="hotcss" content="initial-dpr=1">
<title>实名认证</title>
<script src="${ctx}/js/hotcss.js"></script>
<link rel="stylesheet" href="${ctx}/css/wx/style.min.css">
<link rel="stylesheet" href="${ctx}/css/wx/base.css">
<link rel="stylesheet" href="${ctx}/css/wx/icons.css">
<link rel="stylesheet" href="${ctx}/css/wx/icons-rem.css">
<script src="${ctx}/js/jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/css/wx/login.css">
</head>

<body style="">
	<%@ include file="/WEB-INF/page/common/share2.jsp"%>
	<div class="scroller-container">
		<div class="vm order-detail">
			<div class="register_list cer_list" style="margin-top: 0px;">
				<ul>
					<li><label>真实姓名</label> <input type="text" placeholder="请输入真实姓名" id="realName" value=""></li>
					<li><label>手机号</label> <input type="text" placeholder="请输入手机号" id="mobile" value=""></li>
					<li><label>身份证号</label> <input type="text" placeholder="请输入身份证号" id="IDNumber" value=""></li>
					<li><label>结算卡号</label> <input type="text" placeholder="请输入名下借记卡号" id="cardNumber" value=""></li>
					<li><label>设置密码</label> <input type="password" placeholder="请输入密码" id="password" value=""></li>
					<li><label>确认密码</label> <input type="password" placeholder="请再次输入密码" id="repassword" value=""></li>
				</ul>
			</div>
			<div class="certi_box">
				<ul class="clearfix">
					<li onclick="pai(1)">
						<div class="card_z">
							<img src="${ctx}/images/wx/card_f.png" alt="" id="card_z" style="width: 170px;height: 102px;"> 
							<input type="hidden" id="IDUrl" value="1">
						</div>
						<p>
							<i>*</i>身份证照片面
						</p> 
						<em>文字清晰，四角齐全</em>
					</li>
					<li onclick="pai(2)">
						<div class="card_f">
							<img src="${ctx}/images/wx/card_z.png" alt="" id="card_f" style="width: 170px;height: 102px;">
							<input type="hidden" id="IDFanUrl" value="1">
						</div>
						<p>
							<i>*</i>身份证国徽面
						</p> <em>文字清晰，四角齐全</em>
					</li>
					<li onclick="pai(3)">
						<div class="card_s">
							<img src="${ctx}/images/wx/card_s.png" alt="" id="card_s" style="width: 170px;height: 102px;">
							<input type="hidden" id="IDPersonUrl" value="1">
						</div>
						<p>
							<i>*</i>手持身份证照片
						</p> <em>文字清晰，四角齐全</em>
					</li>
					<li onclick="pai(4)">
						<div class="card_b">
							<img src="${ctx}/images/wx/card_b.png" alt="" id="card_b" style="width: 170px;height: 102px;"> 
							<input type="hidden" id="cardUrl" value="1">
						</div>
						<p>
							<i>*</i>结算卡正面
						</p> <em>文字清晰，四角齐全</em>
					</li>
				</ul>
			</div>
			<a href="javascript:tijiao();" class="btn_cer">提交</a>
		</div>
	</div>
</body>
<script src="${ctx}/js/lib/layer/layer.js"></script>
<script>

	function pai(v) {
		wx.chooseImage({
			count : 1, // 默认9
			sizeType : [ 'original' ], // 可以指定是原图还是压缩图，默认二者都有
			sourceType : [ 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
			success : function(res) {
				var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				uploadP(localIds[0], v);
			}
		});

	}

	function uploadP(localIds, v) {
		wx.uploadImage({
			localId : localIds, // 需要上传的图片的本地ID，由chooseImage接口获得
			isShowProgressTips : 1, // 默认为1，显示进度提示
			success : function(res) {
				var serverId = res.serverId; // 返回图片的服务器端ID
				//下载到本地
				$.get("${ctx}/weixin/saveImageToDisk?mediaId=" + serverId,
						function(data) {
							var json = eval("(" + data + ")");
							if (json.c == "0") {
								if (v == "1") {
									$("#card_z").attr("src","${pic}" + json.d);
									$("#IDUrl").val("${pic}" + json.d);
								} else if (v == "2") {
									$("#card_f").attr("src", "${pic}" + json.d);
									$("#IDFanUrl").val("${pic}" + json.d);
								} else if (v == "3") {
									$("#card_s").attr("src","${pic}" + json.d);
									$("#IDPersonUrl").val("${pic}" + json.d);
								} else if (v == "4") {
									$("#card_b").attr("src","${pic}" + json.d);
									$("#cardUrl").val("${pic}" + json.d);
								}
							} else {
								layer.msg(json.m);
							}
						});

			}
		});
	}
	
	var flag = true;
	function tijiao(){
		var realName = $("#realName").val();
		if(realName==""){
			layer.msg("请输入真实姓名");
			return false;
		}
		
		var mobile = $("#mobile").val();
		if(mobile==""){
			layer.msg("请输入手机号");
			return false;
		}
		
		var IDNumber = $("#IDNumber").val();
		if(IDNumber==""){
			layer.msg("请输入身份证号");
			return false;
		}
		
		var cardNumber = $("#cardNumber").val();
		if(cardNumber==""){
			layer.msg("请输入结算卡号");
			return false;
		}
		
		var password = $("#password").val();
		if(password==""){
			layer.msg("请输入密码");
			return false;
		}
		
		var repassword = $("#repassword").val();
		if(repassword==""){
			layer.msg("请输入确认密码");
			return false;
		}
		
		if(password!=repassword){
			layer.msg("密码和确认密码不一致");
			return false;
		}
		
		var IDUrl = $("#IDUrl").val();
		if(IDUrl==""){
			layer.msg("请上传身份证正面照片");
			return false;
		}
		
		var IDFanUrl = $("#IDFanUrl").val();
		if(IDFanUrl==""){
			layer.msg("请上传身份证反面照片");
			return false;
		}
		
		var IDPersonUrl = $("#IDPersonUrl").val();
		if(IDPersonUrl==""){
			layer.msg("请上传手持身份证照片");
			return false;
		}
		
		var cardUrl = $("#cardUrl").val();
		if(cardUrl==""){
			layer.msg("请上传结算卡正面照片");
			return false;
		}
		
		if(flag){
			flag = false;
			var index = layer.load(1, {
			  shade: [0.6,'#666666'] //0.1透明度的白色背景
			});
			$.post("<c:url value='/weixin/reg'/>",
	       	{
				realName : realName,
				mobile : mobile,
				IDNumber : IDNumber,
				cardNumber : cardNumber,
				IDUrl : IDUrl,
				IDFanUrl : IDFanUrl,
				IDPersonUrl : IDPersonUrl,
				cardUrl : cardUrl,
				password : password,
				openId : '${openId}',
				 _t:Math.random()},
		       	function(data){
					flag = true;
					layer.close(index);
		        	var result = eval('('+data+')'); 
		            if (result.c == '0') {
		            	layer.msg("注册成功");
		            	setTimeout("closeWin()",3000);
		            } else {
		            	layer.msg(result.m);
		            }
	       });
		}
	}
	function closeWin(){
		WeixinJSBridge.call('closeWindow');
	}
</script>
</html>