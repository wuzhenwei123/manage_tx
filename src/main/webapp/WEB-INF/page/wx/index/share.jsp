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
<div class="wrap">
	<%@ include file="/WEB-INF/page/common/share2.jsp" %>
    <div class="share_title">
        <h3>吉云信网</h3>
        <p>国网北京市电力公司指定售电机构</p>
    </div>
    <div class="share_con">
        <img src="${wxUser.qrCodeUrl}" alt="">
        <p>便利缴费，信用卡可用，推广赚钱</p>
        <em>长按二维码并选择“识别图中二维码”，关注后即可缴费和批发，还可预定门票和优惠购物。</em>
        <em class="s_title">合作品牌：</em>
        <div class="share_logo clear">
            <img src="${ctx}/images/index/meiwei.png" alt="">
            <img src="${ctx}/images/index/hengxin.png" alt="">
        </div>
        <em class="share_msg">请保存本图片并转发到朋友圈或微信群</em>
    </div>
</div>
</body>

</html>